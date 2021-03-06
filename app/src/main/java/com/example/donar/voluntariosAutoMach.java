package com.example.donar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;

import android.view.Menu;
import android.view.MenuItem;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;


import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;


import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import DonArDato.EventoDTO;
import DonArDato.EventoAutoMach;
import Adapters.ListAdapter;
import DonArDato.EventoReducidoDTO;
import DonArDato.cambiarEstado;
import Service.EventoServices;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class voluntariosAutoMach extends AppCompatActivity  {

    private ListView myListView;
    private List<EventoAutoMach> myList = new ArrayList<>();
    ListAdapter myAdapter;

    private Toolbar toolbar;

    private String idVoluntario;
    private String tipoUsuario;
    private String idEvento;
    private String nombreMedico;
    private int sendStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voluntarios_auto_mach);
        myListView = findViewById(R.id.listaPacientes);

        toolbar = (Toolbar) findViewById(R.id.donArToolBar);
        setSupportActionBar(toolbar);

        loadConsultas();
        myAdapter = new ListAdapter(voluntariosAutoMach.this, R.layout.list_item_row,myList);
        myListView.setAdapter(myAdapter);

        if(tipoUsuario.equals("3")) {

            myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    idEvento = myList.get(position).getIdEvento();
                    nombreMedico = myList.get(position).getNombreMedico();
                    switch (view.getId()) {
                        case R.id.aceptar:
                            sendStatus = 1;
                            modificarEstado(idEvento,
                                    sendStatus);

                            break;

                        case R.id.rechazar:
                            sendStatus = 2;
                            modificarEstado(idEvento,
                                    sendStatus);

                            break;
                    }
                }
            });
        }
        else
        {
            myListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent;
                    SaveEventId(myList.get(position).getIdEvento());
                    intent = new Intent(getApplicationContext(), pacienteAsignarEspecialidad.class);
                    startActivity(intent);
                }
            } );
        }

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
                tipoUsuario = preferencias.getString("tipo", "0");

                EventoServices eventoServices = retrofit.create(EventoServices.class);

                /*
                Tipo de usuario:
                1 = paciente
                2 = voluntario
                3 = medico
                 */
                if(tipoUsuario.equals("0") || idVoluntario.equals("0")) {
                    signOut();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }

                Call<List<EventoDTO>> http_call;
                switch (tipoUsuario)
                {
                    case "2":
                        http_call = eventoServices.getEventoByVoluntarioBasicoId(idVoluntario);
                        break;
                    case "3":
                        http_call = eventoServices.getEventoByVoluntarioMedicoId(idVoluntario);
                        break;
                    default:
                        http_call = eventoServices.getEventoByVoluntarioId(idVoluntario);
                }
                //Call<List<EventoDTO>> http_call = eventoServices.getEventoByVoluntarioId(idVoluntario); //Como debería quedar finalmente
                //Call<List<EventoDTO>> http_call = eventoServices.getEventoByVoluntarioId("1");//Voluntario basico
                //Call<List<EventoDTO>> http_call = eventoServices.getEventoByVoluntarioId("3"); //Voluntario medico
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

                                            if(e.getFecha().length() < 19)
                                                e.setFecha(e.getFecha() + " 00:00:00");

                                            if(tipoUsuario.equals("3")) {
                                                myList.add(new EventoAutoMach(e.getId().toString(),
                                                        e.getNombrePaciente(),
                                                        e.getApellidoPaciente() +  " "  + e.getFecha(),
                                                        e.getNombreMedico()));
                                            }
                                            else {
                                                myList.add(new EventoAutoMach(e.getId().toString(),
                                                        e.getNombrePaciente(),
                                                        e.getApellidoPaciente() + " " + e.getFecha(),
                                                        e.getFecha(),
                                                        "",
                                                        false));
                                            }

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


    private void modificarEstado(String idEvento, int estado) {
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
                                        finish();
                                    }
                                    else {
                                        Toast.makeText(getApplicationContext(),
                                                "Se rechazo la consulta.",
                                                Toast.LENGTH_LONG).show();
                                        recreate();
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
                                    }
                                    catch (Exception ep) {
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

    private void SaveEventId(String id) {
        SharedPreferences preferencias = getSharedPreferences("ID usuario", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = preferencias.edit();
        editor.putString("idEvento",id);
        editor.commit();
    }

    /**
     * Aqui creamos las opciones de Menu con el toolbar.
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar_menu2, menu);
        return true;
    }

    /**
     * Aqui tenemos las opciones para cada item del menu.
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_login:
                Toast.makeText(this, "Hago click en boton login", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.action_registro:
                Toast.makeText(this, "Haglo click en el boton registro", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.action_login_oculto:
                Toast.makeText(this, "Hago click en boton login oculto", Toast.LENGTH_LONG).show();
                return true;

            case R.id.action_registro_oculto:
                Toast.makeText(this, "Haglo click en el boton registro oculto", Toast.LENGTH_LONG).show();
                return true;

            case R.id.action_cerrarSesion:
                signOut();
                return true;

            default:
                //Aqui la accion del usuario no fue reconocida
                return super.onOptionsItemSelected(item);

        }

    }

    private void signOut() {
        GoogleSignInOptions gso = new GoogleSignInOptions.
                Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).
                build();

        GoogleSignInClient googleSignInClient= GoogleSignIn.getClient(this,gso);
        googleSignInClient.signOut();
        finish();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}
