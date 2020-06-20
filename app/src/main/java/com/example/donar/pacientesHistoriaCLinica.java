package com.example.donar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;
import java.util.Date;

import DonArDato.EventoDTO;
import DonArDato.PacienteConsultaDTO;
import Service.EventoServices;
import Service.PacientesService;
import Negocio.Evento;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class pacientesHistoriaCLinica extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;

    private TextView nombre;
    private TextView edad;
    private TextView documento;
    private TextView genero;
    private TextView email;
    private TextView telefono;
    private EditText sintomas;
    private EditText detalle;

    private Switch diagnosticoPresuntivo;
    private Switch tratamientoFarmacologico;
    private Switch seguimiento;

    private ImageButton agregar;
    private ImageButton cancelar;

    //Rescatables
    private TextView eventId;
    private BigInteger pacienteId;
    private String sintomasSave;
    private Integer idEspecialidad;
    private BigInteger voluntarioMedicoId;
    private BigInteger voluntarioBasicoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pacientes_historia_c_linica);
        //eventId = getIntent().getStringExtra("idEvento");
        configView();
    }

    private void configView() {

        //identificadores rescatables
        eventId = (TextView) findViewById(R.id.txtIdEvento);

        agregar = (ImageButton) findViewById(R.id.imbAgregar);
        cancelar= (ImageButton) findViewById(R.id.ibmCancelar);
        nombre = (TextView) findViewById(R.id.txtNombre);
        detalle = (EditText) findViewById(R.id.edtDetalle);
        edad = (TextView) findViewById(R.id.txtEdad);
        telefono = (TextView) findViewById(R.id.txtTelefono);
        email = (TextView) findViewById(R.id.txtEmail);
        documento = (TextView) findViewById(R.id.txtDocumento);
        genero = (TextView) findViewById(R.id.txtGenero);
        sintomas = (EditText) findViewById(R.id.edtSintomas);

        diagnosticoPresuntivo = (Switch) findViewById(R.id.stcDiagnosticoPresuntivo);
        tratamientoFarmacologico = (Switch) findViewById(R.id.stcTratamientoFarmacologico);
        seguimiento = (Switch) findViewById(R.id.stcSeguimiento);

        agregar.setOnClickListener(this);
        cancelar.setOnClickListener(this);

        toolbar = (Toolbar) findViewById(R.id.donArToolBar);
        setSupportActionBar(toolbar);
        loadData();
    }

    private void loadData(){
        try {
            if(verificarConexion()) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://donar.azurewebsites.net/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                EventoServices eventoServices = retrofit.create(EventoServices.class);

                SharedPreferences preferencias = getSharedPreferences
                        ("ID usuario", Context.MODE_PRIVATE);
                String id = preferencias.getString("idEvento", "0");

                Call<EventoDTO> http_call = eventoServices.getEventoById(id);
                http_call.enqueue(new Callback<EventoDTO>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(Call<EventoDTO> call, Response<EventoDTO> response) {

                        if(response.isSuccessful())
                        {
                            switch (response.code()) {
                                case 200:
                                    if (response.body() != null) {
                                        EventoDTO event = (EventoDTO) response.body();
                                        eventId.setText(event.getId().toString());
                                        sintomasSave = event.getSintomas();
                                        idEspecialidad = event.getEspecialidadId();
                                        voluntarioMedicoId = event.getidVoluntarioMedico();
                                        voluntarioBasicoId = event.getidVoluntario();
                                        diagnosticoPresuntivo.setChecked(event.getDiagnosticoPresuntivo());
                                        tratamientoFarmacologico.setChecked(event.getTratamientoFarmacologico());
                                        getPaciente(event.getPacienteId());
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
                    public void onFailure(Call<EventoDTO> call, Throwable t) {
                        try {
                            throw new Exception("Ocurrio un error al llamar a la API");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
            else {
                Toast.makeText(getApplicationContext(),
                        "El dispositivo no cuenta con conexion a internet en este momento.",
                        Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), sinConexionInternet.class);
                startActivity(intent);
            }
        }
        catch (Exception ex){
            Log.e("LoadDataHC", ex.getMessage());
        }
    }

    private void getPaciente(BigInteger idPaciente){
        try {
            pacienteId = idPaciente;
            if(verificarConexion()){
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://donar.azurewebsites.net/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                String miId = "" + idPaciente;

                PacientesService ps = retrofit.create(PacientesService.class);
                Call<PacienteConsultaDTO> http_call = ps.getPacienteEspecifico2(miId);
                http_call.enqueue(new Callback<PacienteConsultaDTO>() {
                    @Override
                    public void onResponse(Call<PacienteConsultaDTO> call, Response<PacienteConsultaDTO> response) {
                        try{
                            if(response.isSuccessful()){
                                switch (response.code())
                                {
                                    case 200:
                                        if(response.body() != null)
                                        {
                                            PacienteConsultaDTO p = (PacienteConsultaDTO) response.body();
                                            nombre.setText( nombre.getText() + " " +  p.getNombrePaciente() + " " + p.getApellidoPaciente());
                                            edad.setText(edad.getText() + " " + p.getEdad().toString());
                                            documento.setText(documento.getText() + " " + p.getDni());

                                            email.setText(email.getText() + " " +  p.getEmail());
                                            telefono.setText(telefono.getText() + " " + p.getTelefonoPaciente());

                                            String generoText = "";
                                            switch (p.getGenero()){
                                                case 0:
                                                    generoText = "Masculino";
                                                    break;
                                                case 1:
                                                    generoText = "Femenino";
                                                    break;
                                                default:
                                                    generoText = "Transgenero";
                                            }

                                            genero.setText(generoText);
                                            sintomas.setText(sintomasSave);
                                            sintomas.setEnabled(false);
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
                        catch (Exception ex){
                            try {
                                throw new Exception(ex.getMessage());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<PacienteConsultaDTO> call, Throwable t) {
                        Log.e("gerPaciente", t.getMessage());
                        Toast.makeText(getApplicationContext(), "Hubo un error al llamar a la API",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }
            else
            {
                Toast.makeText(getApplicationContext(),
                        "El dispositivo no cuenta con conexion a internet en este momento.",
                        Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), sinConexionInternet.class);
                startActivity(intent);
            }
        }
        catch (Exception ex)
        {
            Log.e("getPaciente", ex.getMessage());
        }
    }

    @Override
    public void onClick(@NotNull View v) {
        switch (v.getId())
        {
            case R.id.imbAgregar:
                if(idEspecialidad != null ) {
                    EventoDTO e = formToObject();
                    if (new Evento().validar(e, true, true)) {
                        updateFullData(e);
                    } else {
                        Toast.makeText(this,
                                "Es necesario completar el detalle.",
                                Toast.LENGTH_LONG).show();
                    }
                }
                else
                    Toast.makeText(getApplicationContext(),
                            "No se puede modificar una consulta que no fue previamente analizada.",
                            Toast.LENGTH_SHORT).show();
                break;
            case R.id.ibmCancelar:
                limpiar();
                break;
        }
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
    public boolean onOptionsItemSelected(@NotNull MenuItem item) {
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

    private boolean verificarConexion() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        return (networkInfo != null && networkInfo.isConnected());
    }

    private void limpiar() {
        detalle.setText(R.string.vacio);
        diagnosticoPresuntivo.setChecked(false);
        tratamientoFarmacologico.setChecked(false);
        seguimiento.setChecked(false);
    }

    @NotNull
    private EventoDTO formToObject(){
        EventoDTO event = new EventoDTO();
        event.setId(BigInteger.valueOf(Long.parseLong(eventId.getText().toString())));
        event.setPacienteId(pacienteId);
        event.setSintomas(sintomasSave);
        event.setDetalle(detalle.getText().toString());
        event.setDiagnosticoPresuntivo(diagnosticoPresuntivo.isChecked());
        event.setTratamientoFarmacologico(tratamientoFarmacologico.isChecked());
        event.setEspecialidadId(idEspecialidad);
        Date d = new Date();
        CharSequence Fecha  = DateFormat.format("dd-MM-yyyy", d.getTime());
        event.setFecha(Fecha.toString());
        event.setidVoluntarioMedico(voluntarioMedicoId);
        event.setidVoluntario(voluntarioBasicoId);
        if(seguimiento.isChecked())
            event.setEstado(1);
        else
            event.setEstado(4);
        return event;
    }

    private void updateFullData(EventoDTO event){
        try {
            if (verificarConexion()) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://donar.azurewebsites.net/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                EventoServices eventos = retrofit.create(EventoServices.class);

                Call<Void> http_call = eventos.updateFullEvento(event);
                http_call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if(response.isSuccessful())
                        {
                            switch (response.code())
                            {
                                case 200:
                                    if(response.body()!= null)
                                    {
                                        Toast.makeText(getApplicationContext(),
                                                "Se guardo correctamente la información.",
                                                Toast.LENGTH_LONG).show();
                                    }
                                    break;
                                case 404:
                                    Toast.makeText(pacientesHistoriaCLinica.this,
                                            "404 - Recurso no encontrado",
                                            Toast.LENGTH_SHORT).show();
                                    break;
                                case 500:
                                    Toast.makeText(pacientesHistoriaCLinica.this,
                                            response.code() + "" + response.message(),
                                            Toast.LENGTH_LONG).show();
                                default:
                                    try {
                                        throw new Exception("codigo: " + response.code() + " Comuniquese con su administrador de sistemas");
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(pacientesHistoriaCLinica.this,
                                t.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
            else {
                Toast.makeText(pacientesHistoriaCLinica.this,
                        "En este momento el dispositivo no cuenta con conexion, por favor intentelo más tarde"
                        , Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this.getApplicationContext(), sinConexionInternet.class );
                startActivity(intent);
            }
        }
        catch (Exception ex)
        {
            Log.e("updateFullData", ex.getMessage());
            Toast.makeText(pacientesHistoriaCLinica.this,
                    "Ocurrio un error inesperado"
                    , Toast.LENGTH_SHORT).show();
        }
    }

}
