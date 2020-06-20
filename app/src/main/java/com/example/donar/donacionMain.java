package com.example.donar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import Adapters.ListAdapter;

import DonArDato.DonacionDTO;
import DonArDato.EventoAutoMach;
import Endpoints.DonacionesService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class donacionMain extends AppCompatActivity implements View.OnClickListener {

    private ListView myListView;
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
            }
        });
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

                DonacionesService donacionesService = retrofit.create(DonacionesService.class);
                Call<List<DonacionDTO>> http_call = donacionesService.getDonacionesUsuario(idUsuario);
                http_call.enqueue(new Callback<List<DonacionDTO>>() {
                    @Override
                    public void onResponse(Call<List<DonacionDTO>> call, Response<List<DonacionDTO>> response) {


                                if(response.body() != null) {
                                    for(DonacionDTO e : response.body()){

                                        myList.add(new EventoAutoMach(e.getId().toString(),
                                                e.getDetalle().toUpperCase(),
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