package com.example.donar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

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

public class reportesDonaciones extends AppCompatActivity {
    private ListView myListView;
    private List<EventoAutoMach> myList= new ArrayList<>();
    private ListAdapter myAdapter;
    Toolbar toolbar;
    String idDonacionPDF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportes_donaciones);
        myListView = findViewById(R.id.lista_donaciones);
        toolbar = (Toolbar) findViewById(R.id.donArToolBar);
        setSupportActionBar(toolbar);
        myList=new ArrayList();
        cargarDonaciones();
        myAdapter = new ListAdapter(this, R.layout.list_item_row, myList);
        myListView.setAdapter(myAdapter);
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public  void onItemClick(AdapterView<?> adapterView, View view, int position, long id){
                idDonacionPDF= myList.get(position).getIdEvento();//id de donacion
                guardarDonacionId(idDonacionPDF);//id de donacion
                Intent intent= new Intent(getApplicationContext(),donacionHistorial.class);
                startActivity(intent);
            }
        });
    }
    private void cargarDonaciones(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://donar.azurewebsites.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        DonacionesService donacionesService = retrofit.create(DonacionesService.class);
        Call<List<DonacionDTO>> http_call = donacionesService.getDonaciones();
        http_call.enqueue(new Callback<List<DonacionDTO>>() {
            @Override
            public void onResponse(Call<List<DonacionDTO>> call, Response<List<DonacionDTO>> response) {


                if(response.body() != null) {
                    for(DonacionDTO e : response.body()){

                        myList.add(new EventoAutoMach(String.valueOf(e.getDonacion_id()),
                                e.getDetalle().toUpperCase(),
                                e.getDestino(),
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

    private void guardarDonacionId(String id) {
        SharedPreferences  preferencias= getSharedPreferences("ID usuario", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencias.edit();
        editor.putString("idDonacionPDF", id);
        editor.commit();
    }


}
