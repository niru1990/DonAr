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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.ui.auth.ui.idp.AuthMethodPickerActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import java.math.BigInteger;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import Adapters.ListAdapter;
import DonArDato.EventoAutoMach;
import DonArDato.EventoDTO;
import DonArDato.EventoReducidoDTO;
import DonArDato.PacienteConsultaDTO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class historial_de_consultas extends AppCompatActivity implements  View.OnClickListener {

    private Toolbar toolbar;

    private EditText mail;
    private Button buscar;

    private ListView lstConsultas;

    private List<EventoAutoMach> myList = new ArrayList<>();
    ListAdapter myAdapter;
    //private String idEvento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial_de_consultas);
        configView();
    }

    private void configView(){
        toolbar = (Toolbar) findViewById(R.id.donArToolBar);
        setSupportActionBar(toolbar);
        mail = (EditText) findViewById(R.id.txtEmail);
        buscar = (Button)findViewById(R.id.btnBuscar);
        lstConsultas = findViewById(R.id.listaConsultas);

        //Seteo onclick en boton
        buscar.setOnClickListener(this);

        myAdapter = new ListAdapter(getApplicationContext(), R.layout.list_item_row,myList);
        lstConsultas.setAdapter(myAdapter);
        lstConsultas.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent;
                SaveEventId(myList.get(position).getIdEvento());
                if(getTipoUsuario().equals("3") || getTipoUsuario().equals("4"))
                    intent = new Intent(getApplicationContext(), pacientesHistoriaCLinica.class);
                else
                    intent = new Intent(getApplicationContext(), pacienteAsignarEspecialidad.class);
                startActivity(intent);
            }
        } );

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

    private void SaveEventId(String id) {
        SharedPreferences preferencias = getSharedPreferences
                ("ID usuario", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencias.edit();
        editor.putString("idEvento",id);
        editor.commit();
    }

    private boolean verificarConexion() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        return (networkInfo != null && networkInfo.isConnected());
    }

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

    private String getTipoUsuario() {
        SharedPreferences preferencias = getSharedPreferences
                ("ID usuario", Context.MODE_PRIVATE);
        String tipoUsuario = preferencias.getString("tipo", "0");

        return tipoUsuario;
    }
}
