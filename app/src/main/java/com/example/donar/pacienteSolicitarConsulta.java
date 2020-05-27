package com.example.donar;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
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

import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;
import java.util.Date;

import DonArDato.EventoDTO;
import DonArDato.PacienteDTO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import Negocio.Evento;

public class pacienteSolicitarConsulta extends AppCompatActivity implements View.OnClickListener{

    private TextView nombre;
    private TextView apellido;
    private  TextView telefono;
    private TextView detalle;
    private TextView sintomas;
    private TextView id;
    private TextView edad;
    private Button solicitar;
    private Toolbar toolbar;

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
            id = (TextView) findViewById(R.id.txtIdSolicitud);
            edad = (TextView) findViewById(R.id.txtEdad);
            sintomas = (TextView) findViewById(R.id.txtSintomasYMedicamentos);
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
        catch (Exception ex)
        {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this.getApplicationContext(), sinConexionInternet.class );
            startActivity(intent);
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
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
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

            default:
                //Aqui la accion del usuario no fue reconocida
                return super.onOptionsItemSelected(item);
        }
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
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://donar.azurewebsites.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //PacientesService pacientesService = retrofit.create(PacientesService.class);
        PacientesService pacientesService = retrofit.create(PacientesService.class);
        Call<PacienteDTO> http_call = pacientesService.getPacienteEspecifico("1"); ///TODO cambiar el 1 por el id del usuario desde el xml
        http_call.enqueue(new Callback<PacienteDTO>() {
            @Override
            public void onResponse(Call<PacienteDTO> call, Response<PacienteDTO> response) {
                try {
                    if (response.body() != null) {
                        PacienteDTO paciente = (PacienteDTO) response.body();
                        nombre.setText( nombre.getText() +"\n"+ paciente.getNombre());
                        //apellido.setText(apellido.getText() +"\n"+ paciente.getApellido());
                        telefono.setText(telefono.getText() +"\n"+ paciente.getTelefono());
                        edad.setText(edad.getText() +"\n"+  Integer.valueOf(paciente.getEdad()).toString() );
                    } else {
                        Log.e("NotUser", "No se encuentra un usuario logueado para poder avanzar," +
                                " por favor vuelva a loguearse.");
                        throw new Exception("No hay usuario logueado");
                    }
                }
                catch (Exception ex)
                {
                    try {
                        throw new Exception(ex.getMessage());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<PacienteDTO> call, Throwable t) {
                Log.e("CALL API FAIL", "Hubo un problema al llamar a la API.");
            }
        });
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
        CharSequence s  = DateFormat.format("MMMM d, yyyy ", d.getTime());

        e.setId(BigInteger.valueOf(0)); ///TODO: Revisar con Kevin si le mando un 0 o que
        e.setPacienteId(BigInteger.valueOf(1));///TODO: Poner acá el id del usuario tomandolo del XML
        e.setSintomas(detalle.getText().toString());
        //e.setFecha(s.toString());
        e.setEspecialidadId(null);
        e.setidVoluntarioMedico(null);
        e.setidVoluntario(null);
        e.setSeguimiento(false);

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
        try {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://us-central1-be-tp3-a.cloudfunctions.net/")
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
}
