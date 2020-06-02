package com.example.donar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import DonArDato.EventoDTO;
import DonArDato.EventoAutoMach;
import Adapters.ListAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class voluntariosAutoMach extends AppCompatActivity  {

    private ListView myListView;
    private List<EventoAutoMach> myList = new ArrayList<>();
    ListAdapter myAdapter;

    private String idPacient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voluntarios_auto_mach);

        myListView = findViewById(R.id.listaPacientes);
        myList.add(new EventoAutoMach("1", "nombre ", "apellido "));
        myList.add(new EventoAutoMach("2", "Emanuel Alejandro ", "Perez Gonsalez "));
        myAdapter = new ListAdapter(voluntariosAutoMach.this, R.layout.list_item_row,myList);
        myListView.setAdapter(myAdapter);
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String idEvento = "";
                Intent intent;
                switch (view.getId())
                {
                    case R.id.aceptar:
                        idEvento = myList.get(position).getIdEvento();
                        Toast.makeText(view.getContext(), "Mi id aceptado es : " + idEvento,
                                Toast.LENGTH_LONG).show();

//                        if(idMedico)
//                            intent = new Intent(view.getContext(), pacientesHistoriaCLinica.class);
//                        else
                            intent = new Intent(view.getContext(), pacienteAsignarEspecialidad.class);

                        intent.putExtra("idEvento", idEvento);
                        startActivity(intent);
                        break;

                    case R.id.rechazar:
                        idEvento = myList.get(position).getIdEvento();
                        Toast.makeText(view.getContext(), "Mi id rechazado es : " + idEvento,
                                Toast.LENGTH_LONG).show();
                        break;
                }
            }
        } );

    }

    private void loadConsultas(){
        try {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://donar.azurewebsites.net/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            SharedPreferences preferencias = getSharedPreferences
                    ("ID usuario", Context.MODE_PRIVATE);

            idPacient = preferencias.getString("ID", "0");

            EventoServices eventoServices = retrofit.create(EventoServices.class);
            //Call<List<EventoDTO>> http_call = eventoServices.getEventoByVoluntarioId(idPacient);
            Call<List<EventoDTO>> http_call = eventoServices.getEventoByVoluntarioId("2");

            http_call.enqueue(new Callback<List<EventoDTO>>() {
                @Override
                public void onResponse(Call<List<EventoDTO>> call, Response<List<EventoDTO>> response) {
                    if(response.isSuccessful()){
                        switch (response.code())
                        {
                            case 200:
                                break;
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<EventoDTO>> call, Throwable t) {

                }
            });

        }
        catch (Exception ex){

        }
    }

}
