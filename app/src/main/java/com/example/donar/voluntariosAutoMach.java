package com.example.donar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import DonArDato.EventoDTO;
import DonArDato.EventoAutoMach;
import Adapters.ListAdapter;
import DonArDato.EventoRedicidoDTO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import DonArDato.EventoRedicidoDTO;

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
        loadConsultas();
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
                        //String idMedico = myList.get(position).getIdMedico();
                        Toast.makeText(view.getContext(), "Mi id aceptado es : " + idEvento,
                                Toast.LENGTH_LONG).show();

                        //TODO Llamar endpoint ACEPTAREVENTO
//                        if(idMedico)
//                            intent = new Intent(view.getContext(), pacientesHistoriaCLinica.class);
//                        else
                            intent = new Intent(view.getContext(), pacienteAsignarEspecialidad.class);

                        intent.putExtra("idEvento", idEvento);
                        startActivity(intent);
                        break;

                    case R.id.rechazar:

                        //TODO Llamar endpoint RECHAZAREVENTO -> Devuelve boolena si encontro medico.
                        ///Si no encontro medico lo tengo que meter en la lista para el handler
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
            Call<List<EventoDTO>> http_call = eventoServices.getEventoByVoluntarioId("1");
            http_call.enqueue(new Callback<List<EventoDTO>>() {
                @Override
                public void onResponse(Call<List<EventoDTO>> call, Response<List<EventoDTO>> response) {
                    if(response.isSuccessful()){
                        switch (response.code()) {
                            case 200:
                            if (response.body() != null) {
                                myList.clear();
                                for (EventoDTO e : response.body()) {
                                  //  myList.add(new EventoAutoMach(e.getId().toString(),"nombre ","apellido"));
                                    getEventoReducido(e.getId());
                                }
                                //myAdapter.notifyDataSetChanged();
                            }
                            break;
                            case 404:
                                Toast.makeText(getApplicationContext(),
                                        "Recurso no encontrado. \n codigo: " + response.code(),
                                        Toast.LENGTH_LONG).show();
                                break;
                            default:
                                Toast.makeText(getApplicationContext(),
                                        "error: " + response.code() + "por favor comuniquese " +
                                                "con el responsable del sistema.",
                                        Toast.LENGTH_LONG).show();
                                try {
                                    throw new Exception("error: " + response.code() + "por favor " +
                                            "comuniquese con el responsable del sistema.");
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                break;
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<EventoDTO>> call, Throwable t) {
                    Toast.makeText(voluntariosAutoMach.this, t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
        catch (Exception ex){
            Log.e("cargarConsultas", ex.getMessage());
        }
    }

    private void getEventoReducido(BigInteger id) {
        try{
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://donar.azurewebsites.net/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            EventoServices eventoServices = retrofit.create(EventoServices.class);
            Call<EventoRedicidoDTO> http_call = eventoServices.getEventoReducidoById(id.toString());
            http_call.enqueue(new Callback<EventoRedicidoDTO>() {
                @Override
                public void onResponse(Call<EventoRedicidoDTO> call, Response<EventoRedicidoDTO> response) {
                    try {
                        if(response.isSuccessful()) {
                            switch (response.code())
                            {
                                case 200:
                                    if(response.body() != null) {
                                        EventoRedicidoDTO e = (EventoRedicidoDTO) response.body();
                                        myList.add(new EventoAutoMach(e.getId().toString(),
                                                e.getNombrePaciente(),
                                                e.getApellidoPaciente()));
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
                    catch (Exception ex)
                    {
                        try {
                            throw new Exception("error obteniendo evento reducido. " + ex.getMessage() );
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<EventoRedicidoDTO> call, Throwable t) {
                    Toast.makeText(getApplicationContext(),
                            "ocurrio un error en la llamada a la API",
                            Toast.LENGTH_LONG).show();
                }
            });
        }
        catch (Exception ex){
            Log.e("EventoReducido", ex.getMessage());
        }
    }

}
