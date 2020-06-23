package com.example.donar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import Adapters.ListAdapter;
import DonArDato.DonacionDTO;
import DonArDato.EventoAutoMach;
import Negocio.ReportesPDF;
import Negocio.fechas;
import Service.DonacionesService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class donacionHistorial extends AppCompatActivity implements View.OnClickListener{
    private ListView myListView;
    private List<EventoAutoMach> myList= new ArrayList<>();
    private ListAdapter myAdapter;
    private String detalleDonacionPDF;
    private Button generarPDF;
    String idDonacionPDF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donacion_historial);
        myListView = findViewById(R.id.lista_donacion);
        generarPDF=(Button) findViewById(R.id.generarPDF);
        generarPDF.setOnClickListener(this);
        myList=new ArrayList();
        SharedPreferences preferencias= getSharedPreferences("ID usuario", Context.MODE_PRIVATE);
        idDonacionPDF=preferencias.getString("idDonacionPDF","0");
        cargarDonacion();
        myAdapter = new ListAdapter(this, R.layout.list_item_row, myList);
        myListView.setAdapter(myAdapter);

    }
    private void cargarDonacion(){
        GsonBuilder gBuilder = new GsonBuilder();
        gBuilder.registerTypeAdapter(DonacionDTO.class, new JsonDeserializer<DonacionDTO>() {
                    @Override
                    public DonacionDTO deserialize(JsonElement element, Type typeOfT, JsonDeserializationContext context)  {
                       JsonObject jObject = element.getAsJsonObject();
                        String estado="";
                        BigInteger id= jObject.get("idDonacion").getAsBigInteger();
                        String detalle=jObject.get("detalle").getAsString();
                        int cantidad=jObject.get("cantidad").getAsInt();
                        String fechaVencimiento=jObject.get("fechaVencimiento").getAsString();
                        try {
                            estado=jObject.get("estado").getAsString();
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        int idUsuario=jObject.get("idUsuario").getAsInt();
                        String destino=jObject.get("destino").getAsString();
                        String dateInicial=jObject.get("fecha").getAsString();
                        SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSS");
                        Date date = null;
                        try {
                            date = parser.parse(dateInicial);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        DonacionDTO donacionDTO=new DonacionDTO(id,detalle,cantidad,fechaVencimiento,destino,idUsuario,estado);
                        if (date!=null){
                            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                            String formattedDate = format.format(date);
                            donacionDTO.setFechaCambio(formattedDate);
                        }
                        return donacionDTO;
                        }
        });
        Gson gSon = gBuilder.create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://donar.azurewebsites.net/")
                .addConverterFactory(GsonConverterFactory.create(gSon))
                .build();
        DonacionesService donacionesService = retrofit.create(DonacionesService.class);
        Call<List<DonacionDTO>> http_call = donacionesService.getHistoricoDonacion(idDonacionPDF);
        http_call.enqueue(new Callback<List<DonacionDTO>>() {
            @Override
            public void onResponse(Call<List<DonacionDTO>> call, Response<List<DonacionDTO>> response) {


                if(response.body() != null) {
                    for(DonacionDTO e : response.body()){
                        String fecha=null;
                        if(e.getFechaCambio()!=null){
                            fecha=e.getFechaCambio();
                        }
                        myList.add(new EventoAutoMach(String.valueOf(e.getId()),
                                e.getDestino(),
                                e.getEstado(),
                                fecha,
                                "",
                                false));
                        detalleDonacionPDF=e.getDetalle();

                    }
                    myAdapter.notifyDataSetChanged();
                }



            }

            @Override
            public void onFailure(Call<List<DonacionDTO>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),
                        t.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onClick(@NotNull View view) {
        switch (view.getId()) {
            case R.id.generarPDF:
                generarPDF(myList);
                break;
            default:
                break;
        }

    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void generarPDF(List<EventoAutoMach> myList){
        if (!myList.isEmpty()){
        List<String> strings = new ArrayList<String>(myList.size());
        for (EventoAutoMach e : myList) {
            if(e.getNombreMedico()!=null){
                strings.add("Destino: "+e.getNombre() +" Estado: "+ e.getApellido()+" Fecha Modificación:"+e.getNombreMedico());
            }else {
                strings.add("Destino: "+e.getNombre() +" Estado: "+ e.getApellido());
            }
        }

        ReportesPDF report = new ReportesPDF("Donacion:"+ detalleDonacionPDF +" "+ new fechas().getDateTime(),
               strings );
        report.createDocumentArray();
        Toast.makeText(getApplicationContext(),"Se Creo su PDF revise su almacenamiento interno",Toast.LENGTH_LONG).show();

        }
    }

 }