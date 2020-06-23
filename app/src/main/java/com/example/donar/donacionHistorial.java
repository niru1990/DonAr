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

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

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
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://donar.azurewebsites.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        DonacionesService donacionesService = retrofit.create(DonacionesService.class);
        Call<List<DonacionDTO>> http_call = donacionesService.getHistoricoDonacion(idDonacionPDF);
        http_call.enqueue(new Callback<List<DonacionDTO>>() {
            @Override
            public void onResponse(Call<List<DonacionDTO>> call, Response<List<DonacionDTO>> response) {


                if(response.body() != null) {
                    for(DonacionDTO e : response.body()){

                        myList.add(new EventoAutoMach(e.getFechaCambio(),
                                e.getDestino(),
                                e.getEstado(),
                                String.valueOf(e.getId()),
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
        List<String> strings = new ArrayList<String>(myList.size());
        for (EventoAutoMach e : myList) {
            strings.add(e.getNombre() +" "+ e.getApellido() +" "+ e.getNombreMedico() + e.getIdEvento());
        }
        List<String> list = strings;
        String joined = TextUtils.join(", ", list);
        ReportesPDF report = new ReportesPDF("Donacion:"+ detalleDonacionPDF + new fechas().getDateTime(),
               joined );
        report.createDocumentExample();
        Toast.makeText(getApplicationContext(),"Se Creo su PDF revise su almacenamiento interno",Toast.LENGTH_LONG).show();

    }

 }