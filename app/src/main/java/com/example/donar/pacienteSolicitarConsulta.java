package com.example.donar;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import Negocio.Evento;

public class pacienteSolicitarConsulta extends AppCompatActivity implements View.OnClickListener{

    private TextView nombre,  apellido,  telefono,  detalle,  sintomas, id,  edad, email, genero;
    private Button solicitar;
    private Toolbar toolbar;
    private String idPacient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paciente_solicitar_consulta);
        configView();
    }

    private boolean verificarConexion() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        return (networkInfo != null && networkInfo.isConnected());
    }

    private void configView(){
        try {
            nombre = (TextView) findViewById(R.id.txtNombre);
            apellido = (TextView) findViewById(R.id.txtApellido);
            detalle = (TextView) findViewById(R.id.medtSintomasYMedicamentos);
            telefono = (TextView) findViewById(R.id.txtTelefono);
            email = (TextView) findViewById(R.id.txtEmail);
            id = (TextView) findViewById(R.id.txtIdSolicitud);
            edad = (TextView) findViewById(R.id.txtEdad);
            sintomas = (TextView) findViewById(R.id.txtSintomasYMedicamentos);
            genero = (TextView) findViewById(R.id.txtGenero);
            solicitar = (Button) findViewById(R.id.btnSolciitar);
            solicitar.setOnClickListener(this);
            toolbar = (Toolbar) findViewById(R.id.donArToolBar);
            setSupportActionBar(toolbar);
            if (verificarConexion()) {
                loadData();
            } else {
                throw new Exception("El dispositivo no cuenta con conexion a internet");
            }
        }
        catch (Exception ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this.getApplicationContext(), sinConexionInternet.class );
            startActivity(intent);
            finish();
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
        finish();
    }

    public void loadData(){
        try {
            getUserData();
        }
        catch (Exception ex)
        {
            Log.i("Consulta - loadData", ex.getMessage());
            Intent intent = new Intent(this.getApplicationContext(), LoginActivity.class);
        }
    }

    private void getUserData() {
        try {
            if(verificarConexion()) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://donar.azurewebsites.net/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                SharedPreferences preferencias = getSharedPreferences
                        ("ID usuario", Context.MODE_PRIVATE);
                idPacient = preferencias.getString("ID", "0");
                if (idPacient.equals("0")) {
                    throw new Exception("Es necesario volver a loguearse.");
                }
                PacientesService pacientesService = retrofit.create(PacientesService.class);
                Call<PacienteConsultaDTO> http_call = pacientesService.getPacienteEspecifico2(idPacient);
                http_call.enqueue(new Callback<PacienteConsultaDTO>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(Call<PacienteConsultaDTO> call, Response<PacienteConsultaDTO> response) {
                        try {
                            if (response.body() != null) {
                                PacienteConsultaDTO paciente = (PacienteConsultaDTO) response.body();
                                nombre.setText(nombre.getText() + " " + paciente.getNombrePaciente());
                                apellido.setText(apellido.getText() + " " + paciente.getApellidoPaciente());
                                telefono.setText(telefono.getText() + " " + paciente.getTelefonoPaciente());
                                edad.setText(edad.getText() + " " + Integer.valueOf(paciente.getEdad()).toString());
                                email.setText(email.getText() + " " + paciente.getEmail());
                                String pGenero = "";
                                switch (paciente.getGenero())
                                {
                                    case 0:
                                        pGenero = "Masculino";
                                        break;
                                    case 1:
                                        pGenero = "Femenino";
                                        break;
                                    default:
                                        pGenero = "Transgenero";
                                }
                                genero.setText(genero.getText() + " " + pGenero);
                            } else {
                                Log.e("NotUser", "No se encuentra un usuario logueado para poder avanzar," +
                                        " por favor vuelva a loguearse.");
                                throw new Exception("No hay usuario logueado");
                            }
                        } catch (Exception ex) {
                            try {
                                throw new Exception(ex.getMessage());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<PacienteConsultaDTO> call, Throwable t) {
                            Log.e("detail", t.getMessage());
                        Log.e("CALL API FAIL", "Hubo un problema al llamar a la API.");
                    }
                });
            }
            else
            {
                Intent intent = new Intent(getApplicationContext(), sinConexionInternet.class);
                startActivity(intent);
                finish();
            }
        }
        catch (Exception ex)
        {
            Toast.makeText(this.getApplicationContext(),
                    "Ocurrio un error, por favor vuelva a loguearse"
                    , Toast.LENGTH_LONG).show();
            Intent i = new Intent(this.getApplicationContext(), LoginActivity.class);
            startActivity(i);
            finish();
        }
    }

    @Override
    public void onClick(@NotNull View v) {
        //super.onClick(v);
        switch (v.getId())
        {
            case R.id.btnSolciitar:
                guardar();
                break;
            default:
                break;
        }
    }

    private void guardar(){
        //Acá va el SAVE
        EventoDTO e = formToObject();
        if(validar(e)) {
            sintomas.setTextColor(Color.BLACK);
            save(e);
        }
        else
        {
            Toast.makeText(this, "Es necesario cargar los campos obligatorios",
                    Toast.LENGTH_SHORT).show();
            sintomas.setTextColor(Color.RED);
        }
    }

    /**
     * Convierto los datos del formulario en un objeto de tipo EventoDTO
     * @return EventoDTO
     */
    @NotNull
    private EventoDTO formToObject(){
        EventoDTO e = new EventoDTO();

        Date d = new Date();
        CharSequence Fecha  = DateFormat.format("dd-MM-yyyy hh:mm:ss", d.getTime());

        e.setId(BigInteger.valueOf(0));
        e.setPacienteId( new BigInteger(idPacient) );
        e.setSintomas(detalle.getText().toString());
        e.setFecha(Fecha.toString());
        e.setEspecialidadId(null);
        e.setidVoluntarioMedico(null);
        e.setidVoluntario(null);
        //e.setSeguimiento(false);

        return e;
    }

    /**
     * Validación por reglas de negocio y campos necesarios.
     * @param eventoDTO
     * @return boolean
     */
    private boolean validar(EventoDTO eventoDTO){
        Evento event = new Evento();
        return event.validar(eventoDTO);
    }

    private void save(EventoDTO event){
        try
        {
            if(verificarConexion()) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://donar.azurewebsites.net/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                EventoServices eventoServices = retrofit.create(EventoServices.class);
                Call<Void> http_call = eventoServices.addEvento(event);

                http_call.enqueue(new Callback<Void>() {
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        try {
                            if (response.isSuccessful()) {
                                String message = "";
                                if (response.isSuccessful())
                                    message = "Su solicitud de consulta fue generada exitosamente";
                                else
                                    message = "Ocurrio algo inesperado.";

                                Toast.makeText(pacienteSolicitarConsulta.this
                                        , message
                                        , Toast.LENGTH_SHORT).show();
                                limpiar();
                                goHome();
                            } else {
                                Log.i(((Integer) response.code()).toString(), "No fue posible guardar la consulta, " +
                                        "por favor intente mas tarde");
                                throw new Exception("No fue posible guardar la consulta, " +
                                        "por favor intente mas tarde");
                            }
                        } catch (Exception ex) {
                            try {
                                throw new Exception(ex.getMessage());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(pacienteSolicitarConsulta.this,
                                "Hubo un error con la llamada a la API",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
            else
            {
                Intent intent = new Intent(getApplicationContext(), sinConexionInternet.class);
                startActivity(intent);
                finish();
            }
        }
        catch (Exception ex){
            Toast.makeText(this,
                    "Ocurrio un error, por favor contacte al responsable de sistemas",
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Limpia los campos del formulario
     */
    private void limpiar() {
        this.detalle.setText("");
    }

    private void goHome(){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }
}
