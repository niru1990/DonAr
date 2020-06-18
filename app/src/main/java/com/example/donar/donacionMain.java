package com.example.donar;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import Adapters.ListAdapter;

import DonArDato.DonacionDTO;
import DonArDato.EventoAutoMach;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class donacionMain extends AppCompatActivity implements View.OnClickListener {

    private ListView myListView;
   //private List<String> myList = new ArrayList<>();
     private List<EventoAutoMach> myList= new ArrayList<>();
    private ListAdapter myAdapter;
   private  Toolbar toolbar;
   private Button botonAgregar;
   private Button botonLeerQR;
   private String idUsuario;
   private BigInteger idDonacion;
   private SharedPreferences preferencias;
    public static final int REQUEST_CODE_QR=100;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donacion_main);
        myListView = findViewById(R.id.lista_donaciones);
        toolbar = (Toolbar) findViewById(R.id.donArToolBar);
        setSupportActionBar(toolbar);
        botonAgregar=(Button) findViewById(R.id.buttonAgregarDonacion);
        botonLeerQR=(Button) findViewById(R.id.buttonLeerQR);
        botonAgregar.setOnClickListener(this);
        botonLeerQR.setOnClickListener(this);
        preferencias= getSharedPreferences("ID usuario", Context.MODE_PRIVATE);
        idUsuario=preferencias.getString("ID","0");

        myList=new ArrayList();
        cargarDonaciones();
        myAdapter = new ListAdapter(this, R.layout.list_item_row, myList);
        myListView.setAdapter(myAdapter);
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public  void onItemClick(AdapterView<?> adapterView, View view, int position, long id){
               guardarDonacionId(myList.get(position).getIdEvento());//id de donacion
                Intent intent= new Intent(getApplicationContext(),donacionDetalle.class);
                startActivity(intent);
               // Toast.makeText(donacionMain.this,"Selecciono: "+ myList.get(position),Toast.LENGTH_LONG).show();
            }
        });
        //ArrayAdapter adapter = new ArrayAdapter<DonacionDTO>(this,
          //      R.layout.list_donaciones, myLista);

        //myListView.setAdapter(adapter);
      /*  myAdapter = new ListAdapter(getApplicationContext(), R.layout.list_donaciones,myList);
        myListView.setAdapter(myAdapter);
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent;
                SaveDonacionId(myList.get(position).getIdEvento());
                intent = new Intent(getApplicationContext(), donacionDetalle.class);
                startActivity(intent);
            }
        } );


       */
      /*
//uso String para testear
        myList= new ArrayList<String>();
        myList.add("Donacion1");
        myList.add("Donacion2");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, myList);
        myListView.setAdapter(adapter);
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public  void onItemClick(AdapterView<?> adapterView, View view, int position, long id){
               Toast.makeText(donacionMain.this,"Selecciono: "+ myList.get(position),Toast.LENGTH_LONG).show();
            }
        });

       */

    }
@Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.buttonAgregarDonacion:
                intent = new Intent(v.getContext(), registroDonacion.class);
                startActivity(intent);
                break;
            case R.id.buttonLeerQR:
                intent = new Intent(v.getContext(), donacionLeer.class);
                startActivity(intent);
                break;
        }



    }
    private void guardarDonacionId(String id) {
        SharedPreferences.Editor editor = preferencias.edit();
        editor.putString("idDonacion", id);
        editor.commit();
    }
    private void cargarDonaciones(){
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://donar.azurewebsites.net/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                DonacionesService  donacionesService = retrofit.create(DonacionesService.class);
                Call<List<DonacionDTO>> http_call = donacionesService.getDonacionesUsuario(idUsuario);
                http_call.enqueue(new Callback<List<DonacionDTO>>() {
                    @Override
                    public void onResponse(Call<List<DonacionDTO>> call, Response<List<DonacionDTO>> response) {


                                if(response.body() != null) {
                                    for(DonacionDTO e : response.body()){

                                        myList.add(new EventoAutoMach(e.getId().toString(),
                                                e.getDetalle(),
                                                "",
                                                "",
                                                "",
                                                false));

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

}
/*



    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.btnBuscar:
                if(!mail.getText().equals(""))
                    searchEventByMail();
                else
                    Toast.makeText(this,
                            "Debe ingresar un mail para poder realizar la busqeuda.",
                            Toast.LENGTH_LONG).show();
                break;
            default:
                break;
        }
    }

    private void searchEventByMail(){
        try {
            if(verificarConexion())
            {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://donar.azurewebsites.net/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                EventoServices  es = retrofit.create(EventoServices.class);
                Call<List<EventoDTO>> http_call = es.getEventByPatientMail(mail.getText().toString());
                http_call.enqueue(new Callback<List<EventoDTO>>() {
                    @Override
                    public void onResponse(Call<List<EventoDTO>> call, Response<List<EventoDTO>> response) {
                        switch (response.code())
                        {
                            case 200:
                                if(response.body() != null) {
                                    for(EventoDTO event : response.body()){
                                        getEventoReducido(event.getId());
                                    }
                                }
                                else {
                                    Toast.makeText(getApplicationContext(),
                                            "Ocurrio un error, comuniquese con su administrador de sistemas.",
                                            Toast.LENGTH_LONG).show();
                                }
                                break;
                            case 404:
                                try {
                                    throw new Exception(response.code() +  "No se encontro el recurso.");
                                }
                                catch (Exception e) {
                                    e.printStackTrace();
                                }
                                break;
                            case 500:
                                Toast.makeText(getApplicationContext(),
                                        "No se encontro ningun usuario que tenga ese mail asociado.",
                                        Toast.LENGTH_LONG).show();
                                break;
                        }
                    }

                    @Override
                    public void onFailure(Call<List<EventoDTO>> call, Throwable t) {
                        Toast.makeText(getApplicationContext(),
                                t.getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                });
            }
            else {
                Toast.makeText(this,
                        "En este momento el dispositivo no cuneta con conexion a internet, " +
                                "por favor intente mas tarde.",
                        Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), sinConexionInternet.class);
                startActivity(intent);
            }
        }
        catch (Exception ex) {
            Log.e("searchEventByMail", ex.getMessage());
            Toast.makeText(this,
                    "Ocurrio un error inesperado.",
                    Toast.LENGTH_LONG).show();
        }
    }

    private void getEventoReducido(BigInteger id) {
        try
        {
            if(verificarConexion()) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://donar.azurewebsites.net/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                EventoServices eventoServices = retrofit.create(EventoServices.class);
                Call<EventoReducidoDTO> http_call = eventoServices.getEventoReducidoById(id.toString());
                http_call.enqueue(new Callback<EventoReducidoDTO>() {
                    @Override
                    public void onResponse(Call<EventoReducidoDTO> call, Response<EventoReducidoDTO> response) {
                        try {
                            if (response.isSuccessful()) {
                                switch (response.code()) {
                                    case 200:
                                        if (response.body() != null) {
                                            EventoReducidoDTO e = (EventoReducidoDTO) response.body();

                                            myList.add(new EventoAutoMach(e.getId().toString(),
                                                    e.getNombrePaciente() + " " + e.getApellidoPaciente(),
                                                    e.getFecha(),
                                                    e.getNombreMedico(),
                                                    "",
                                                    false));
                                            myAdapter.notifyDataSetChanged();
                                        }
                                        break;
                                    case 404:
                                        Toast.makeText(getApplicationContext(),
                                                "Error " + response.code() + " Recurso no encontrado",
                                                Toast.LENGTH_LONG).show();
                                        break;
                                    default:
                                        break;
                                }
                            }
                        }
                        catch (Exception ex) {
                            try {
                                throw new Exception("error obteniendo evento reducido. " + ex.getMessage());
                            }
                            catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<EventoReducidoDTO> call, Throwable t) {
                        Toast.makeText(getApplicationContext(),
                                "ocurrio un error en la llamada a la API",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }
            else {
                Intent intent = new Intent(getApplicationContext(), sinConexionInternet.class);
                startActivity(intent);
            }
        }
        catch (Exception ex){
            Log.e("EventoReducido", ex.getMessage());
        }
    }


 */