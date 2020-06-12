package com.example.donar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import DonArDato.EventoReducidoDTO;
import DonArDato.cambiarEstado;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class voluntariosAutoMach extends AppCompatActivity  {

    private ListView myListView;
    private List<EventoAutoMach> myList = new ArrayList<>();
    ListAdapter myAdapter;

    private String idVoluntario;
    private String idEvento;
    private String nombreMedico;
    private int sendStatus;

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

                idEvento = myList.get(position).getIdEvento();
                nombreMedico =myList.get(position).getNombreMedico();
                switch (view.getId())
                {
                    case R.id.aceptar:
                        sendStatus = 1;
                        modificarEstado(idEvento,
                                sendStatus);

                        break;

                    case R.id.rechazar:
                        sendStatus = 2;
                        modificarEstado(idEvento,
                                sendStatus);
                        Toast.makeText(view.getContext(),
                                "Se rechazo la consulta.",
                                Toast.LENGTH_LONG).show();
                        break;
                }
            }
        } );

    }

    private void loadConsultas(){
        try {
            if(verificarConexion()) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://donar.azurewebsites.net/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                SharedPreferences preferencias = getSharedPreferences
                        ("ID usuario", Context.MODE_PRIVATE);

                idVoluntario = preferencias.getString("ID", "0");
                EventoServices eventoServices = retrofit.create(EventoServices.class);

                //Obtengo los eventos a base del id del voluntario o voluntario medico.
                //Call<List<EventoDTO>> http_call = eventoServices.getEventoByVoluntarioId(idVoluntario); //Como debería quedar finalmente
                //Call<List<EventoDTO>> http_call = eventoServices.getEventoByVoluntarioId("1");//Voluntario basico
                Call<List<EventoDTO>> http_call = eventoServices.getEventoByVoluntarioId("3"); //Voluntario medico
                http_call.enqueue(new Callback<List<EventoDTO>>() {
                    @Override
                    public void onResponse(Call<List<EventoDTO>> call, Response<List<EventoDTO>> response) {
                        if (response.isSuccessful()) {
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
            else
            {
                Intent intent = new Intent(getApplicationContext(), sinConexionInternet.class);
                startActivity(intent);
            }
        }
        catch (Exception ex){
            Log.e("cargarConsultas", ex.getMessage());
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
                                                    e.getNombrePaciente(),
                                                    e.getApellidoPaciente(),
                                                    e.getNombreMedico()));
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
                        } catch (Exception ex) {
                            try {
                                throw new Exception("error obteniendo evento reducido. " + ex.getMessage());
                            } catch (Exception e) {
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
            else
            {
                Intent intent = new Intent(getApplicationContext(), sinConexionInternet.class);
                startActivity(intent);
            }
        }
        catch (Exception ex){
            Log.e("EventoReducido", ex.getMessage());
        }
    }

    private void modificarEstado(String idEvento, int estado)
    {
        try {

            if(verificarConexion()){

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://donar.azurewebsites.net/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                EventoServices eventoServices = retrofit.create(EventoServices.class);

                BigInteger eventId = BigInteger.valueOf(Long.parseLong(idEvento));

                cambiarEstado c = new cambiarEstado(eventId, estado);

                Call<EventoDTO> http_call = eventoServices.modifyStatus(c);
                http_call.enqueue(new Callback<EventoDTO>() {
                    @Override
                    public void onResponse(Call<EventoDTO> call, Response<EventoDTO> response) {

                        if(response.isSuccessful())
                        {
                            switch (response.code())
                            {
                                case 200:
                                    EventoDTO e = (EventoDTO) response.body();
                                    Intent intent;
                                    Context context = getApplicationContext();

                                    if(sendStatus == 1) {
                                        if (nombreMedico.equals(""))
                                            intent = new Intent(context, pacienteAsignarEspecialidad.class);
                                        else
                                            intent = new Intent(context, pacientesHistoriaCLinica.class);

                                        SaveEventId(e.getId().toString());
                                        startActivity(intent);
                                    }
                                    else
                                    {
                                        intent = new Intent(context, voluntariosAutoMach.class);
                                        startActivity(intent);
                                    }
                                    break;
                                case 400:
                                    Toast.makeText(getApplicationContext(),
                                            "Error 404 - recurso no encontrado.",
                                            Toast.LENGTH_LONG ).show();
                                    break;
                                default:
                                    try {
                                        throw new Exception(response.code() + " " + response.message());
                                    } catch (Exception ep) {
                                        ep.printStackTrace();
                                    }
                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<EventoDTO> call, Throwable t) {
                        Log.e("cambiarEstado", t.getMessage());
                        try {
                            throw new Exception(t.getMessage() + " " + t.getCause());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
            else {
                Toast.makeText(this,
                        "El dispositivo no cuenta actualmente con conexion a internet",
                        Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this, sinConexionInternet.class);
                startActivity(intent);
            }

        }
        catch (Exception ex){
            Log.e("cambiarEstado", ex.getMessage());
            Toast.makeText(this,
                    "Ocurrio un error inespedrado, por favor comuniquese con el administrador de sistemas",
                    Toast.LENGTH_LONG).show();
        }

    }

    private boolean verificarConexion() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        return (networkInfo != null && networkInfo.isConnected());
    }

    private void SaveEventId(String id)
    {
        SharedPreferences preferencias = getSharedPreferences
                ("ID usuario", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = preferencias.edit();
        editor.putString("idEvento",id);
        editor.commit();
    }

}
