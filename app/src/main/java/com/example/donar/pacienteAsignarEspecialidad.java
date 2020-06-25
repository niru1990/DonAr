package com.example.donar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import org.jetbrains.annotations.NotNull;

//Negocio
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import Adapters.SpinnerAdaptor;
import DonArDato.AsignarEspecialidadDTO;
import DonArDato.EspecialidadDTO;
import DonArDato.EventoDTO;
import DonArDato.PacienteConsultaDTO;
import DonArDato.SpinnerItem;
import Service.EspecialidadServices;
import Service.EventoServices;
import Service.PacientesService;
import Negocio.Evento;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class pacienteAsignarEspecialidad extends AppCompatActivity implements View.OnClickListener {

    private TextView id;
    private TextView idPaciente;
    private TextView nombre;
    private TextView apellido;
    private TextView telefono;
    private TextView edad;
    private TextView email;
    private TextView especialidadText;
    private EditText sintomas;
    private Spinner especialidad;
    private Button asignar;
    private Toolbar toolbar;

    private String idEspecialidad;
    private String sintomasSave;

    //Listado para el spinner
    private ArrayList<SpinnerItem> misEspecialidades = new ArrayList<>();
    private SpinnerAdaptor miAdaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paciente_asignar_especialidad);
        configView();
    }

    private void configView() {
        //Textos
        id = (TextView) findViewById(R.id.txtIdConsulta);
        idPaciente = (TextView) findViewById(R.id.txtIdPaciente);
        nombre = (TextView) findViewById(R.id.txtNombre);
        apellido = (TextView) findViewById(R.id.txtApellido);
        telefono = (TextView) findViewById(R.id.txtTelefono);
        edad = (TextView) findViewById(R.id.txtEdad);
        email = (TextView) findViewById(R.id.txtEmail);
        especialidadText = (TextView) findViewById(R.id.txtEspecialidad);
        sintomas = (EditText) findViewById(R.id.medtSintomasYMedicamentos);
        //Boton
        asignar = (Button) findViewById(R.id.btnAsignar);
        asignar.setOnClickListener(this);
        //Conseguir la información
        if(verificarConexion())
            loadData();
        else
        {
            Intent intent = new Intent(this.getApplicationContext(), sinConexionInternet.class );
            startActivity(intent);
        }

        //Spinner y adaptor
        especialidad = (Spinner) findViewById(R.id.spnEspecialidad);
        miAdaptador = new SpinnerAdaptor(pacienteAsignarEspecialidad.this, misEspecialidades);
        especialidad.setAdapter(miAdaptador);
        //Asigno accion al onclick
        especialidad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SpinnerItem clickItem = (SpinnerItem) parent.getItemAtPosition(position);
                idEspecialidad = clickItem.getIdData();
                String eds = clickItem.getDescriptionData();
                //Toast.makeText(pacienteAsignarEspecialidad.this,"La especialidad seleccionada fue: " + eds, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //NADA, ABSOLUTAMENTE NADA
            }
        });
        //Toolbar
        toolbar = (Toolbar) findViewById(R.id.donArToolBar);
        setSupportActionBar(toolbar);
    }

    private void loadData(){
        try {
            if(verificarConexion()) {
                cargarSpinner(especialidad);
                //Obtener evento y cargarlo. Esto obliga a obtener el paciente.
                getEvento();
            }
            else {
                Intent intent = new Intent(this.getApplicationContext(), sinConexionInternet.class );
                startActivity(intent);
            }
        }
        catch (Exception ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
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

    @Override
    public void onClick(@NotNull View v) {
        try {
            switch (v.getId()) {
                case R.id.btnAsignar:
                    actualizar();
                    break;
                default:
                    break;
            }
        }
        catch (Exception ex)
        {
            Log.i("actualizar error", ex.getMessage());
        }
    }

    @NotNull
    private EventoDTO formToObject() {
        EventoDTO event = new EventoDTO();
        event.setId(BigInteger.valueOf(Long.parseLong(id.getText().toString())));
        event.setPacienteId(BigInteger.valueOf(Long.parseLong(idPaciente.getText().toString())));
        event.setEspecialidadId(Integer.valueOf(idEspecialidad));
        event.setidVoluntarioMedico(null);
        event.setSintomas(sintomas.getText().toString());
        return event;
    }

    private void actualizar() throws Exception {
        try{
            if(verificarConexion()) {
                EventoDTO event = formToObject();
                if (new Evento().validar(event, true)) {
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("https://donar.azurewebsites.net/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    EventoServices eventos = retrofit.create(EventoServices.class);

                    Call<EventoDTO> http_call = eventos.updateEvento(new AsignarEspecialidadDTO(
                            event.getId(),
                            event.getEspecialidadId()));

                    http_call.enqueue(new Callback<EventoDTO>() {
                        @Override
                        public void onResponse(Call<EventoDTO> call, Response<EventoDTO> response) {
                            try {
                                    switch (response.code()) {
                                        case 200:
                                            if (response.body() != null) {
                                                Toast.makeText(pacienteAsignarEspecialidad.this,
                                                        "Se asigno la especialidad correctamente.",
                                                        Toast.LENGTH_SHORT).show();

                                                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                                startActivity(i);
                                                finish();
                                            }
                                            break;
                                        case 404:
                                            Toast.makeText(pacienteAsignarEspecialidad.this,
                                                    "404 - Recurso no encontrado",
                                                    Toast.LENGTH_SHORT).show();
                                            break;
                                        case 500:
                                            Toast.makeText(pacienteAsignarEspecialidad.this,
                                                    response.code() + "" + response.message(),
                                                    Toast.LENGTH_LONG).show();
                                        default:
                                            throw new Exception("codigo: " + response.code() + " Comuniquese con su administrador de sistemas");
                                    }
                            } catch (Exception ex) {
                                Toast.makeText(pacienteAsignarEspecialidad.this,
                                        ex.getMessage(),
                                        Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<EventoDTO> call, Throwable t) {
                            Toast.makeText(pacienteAsignarEspecialidad.this,
                                    "Ocurrio un error al llamar a la API",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });

                } else {
                    Toast.makeText(this, "Por favor cargue los campos obligatorios",
                            Toast.LENGTH_SHORT).show();
                    especialidadText.setTextColor(Color.RED);
                }
            }
            else
            {
                Toast.makeText(this,
                        "El dispositivo no cuenta con conexion a internet en este momento",
                        Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this.getApplicationContext(), sinConexionInternet.class );
                startActivity(intent);
            }
        }
        catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    private void cargarSpinner(Spinner spinner){
        try {
            if(verificarConexion()) {
                //Creo llamada
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://donar.azurewebsites.net/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                EspecialidadServices especialidadServices = retrofit.create(EspecialidadServices.class);
                Call<List<EspecialidadDTO>> http_call = especialidadServices.getEspecialidades();
                //Encolo llamda
                http_call.enqueue(new Callback<List<EspecialidadDTO>>() {
                    @SuppressLint("ResourceType")
                    @Override
                    public void onResponse(Call<List<EspecialidadDTO>> call, Response<List<EspecialidadDTO>> response) {
                        try {
                            //Trabajo con la respuesta
                            if (response.body() != null && response.code() == 200) {
                                //limpio la lista
                                misEspecialidades.clear();
                                //Cargo valor señuelo para saber si asigno lo que corresponde
                                misEspecialidades.add(new SpinnerItem("0", "Seleccione..."));
                                //Cargo mi lista con valores de la tabla
                                for (EspecialidadDTO esp : response.body()) {
                                    misEspecialidades.add(new SpinnerItem(esp.getId(),
                                            esp.getEspecialidad()));
                                }
                                //Aviso al adaptor que se actualizo la información
                                miAdaptador.notifyDataSetChanged();
                            } else {
                                throw new Exception("codigo de respuesta: " + response.code());
                            }
                        } catch (Exception ex) {
                            Log.e("Cargar especialidades", ex.getMessage());
                            try {
                                throw new Exception(ex.getMessage());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<List<EspecialidadDTO>> call, Throwable t) {
                        Toast.makeText(pacienteAsignarEspecialidad.this,
                                "Ocurrio un error al intentar llamar a la API",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
            else
            {
                Toast.makeText(this,
                        "El dispositivo no cuenta con conexion a internet en este momento",
                        Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this.getApplicationContext(), sinConexionInternet.class );
                startActivity(intent);
            }
        }
        catch (Exception ex)
        {
            Toast.makeText(this,
                    "Por favor verifique si su conexion con internet",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void getEvento() throws Exception {
        try {
            if(verificarConexion()) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://donar.azurewebsites.net/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                SharedPreferences preferencias = getSharedPreferences
                        ("ID usuario", Context.MODE_PRIVATE);

                String idEvento = preferencias.getString("idEvento", "0");
                id.setText(idEvento);

                EventoServices eventoServices = retrofit.create(EventoServices.class);

                if(idEvento.equals("0"))
                {
                    Toast.makeText(getApplicationContext(),
                            "No se encontro el evento que desea obtener",
                            Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
                Call<EventoDTO> http_call = eventoServices.getEventoById(idEvento);

                http_call.enqueue(new Callback<EventoDTO>() {
                    @Override
                    public void onResponse(Call<EventoDTO> call, Response<EventoDTO> response) {
                        if (response.isSuccessful()) {
                            switch (response.code()) {
                                case 200:
                                    if (response.body() != null) {
                                        EventoDTO event = (EventoDTO) response.body();
                                        idPaciente.setText(event.getPacienteId().toString());
                                        sintomasSave = event.getSintomas();
                                        getUserData(event.getPacienteId().toString());
                                    }
                                    break;
                                default:
                                    break;
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<EventoDTO> call, Throwable t) {
                        String s = "n";
                    }
                });

            }
            else
            {
                Toast.makeText(this,
                        "El dispositivo no cuenta con conexion a internet en este momento",
                        Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this.getApplicationContext(), sinConexionInternet.class );
                startActivity(intent);
            }
        }
        catch (Exception ex){
            Toast.makeText(pacienteAsignarEspecialidad.this, ex.getMessage(), Toast.LENGTH_LONG ).show();
        }
    }

    private void getUserData(String idPacient) {
        try {
            if(verificarConexion()) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://donar.azurewebsites.net/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                SharedPreferences preferencias = getSharedPreferences
                        ("ID usuario", Context.MODE_PRIVATE);

                PacientesService pacientesService = retrofit.create(PacientesService.class);
                Call<PacienteConsultaDTO> http_call = pacientesService.getPacienteEspecifico2(idPacient);
                //Call<PacienteDTO> http_call = pacientesService.getPacienteEspecifico("1");
                http_call.enqueue(new Callback<PacienteConsultaDTO>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(Call<PacienteConsultaDTO> call, Response<PacienteConsultaDTO> response) {
                        try {
                            if (response.isSuccessful()) {
                                switch (response.code()) {
                                    case 200:
                                        if (response.body() != null) {
                                            PacienteConsultaDTO paciente = (PacienteConsultaDTO) response.body();
                                            nombre.setText(nombre.getText() + " " + paciente.getNombrePaciente());
                                            apellido.setText(apellido.getText() + " " + paciente.getApellidoPaciente());
                                            telefono.setText(telefono.getText() + " " + paciente.getTelefonoPaciente());
                                            edad.setText(edad.getText() + " " + Integer.valueOf(paciente.getEdad()).toString());
                                            email.setText(email.getText() + " " + paciente.getEmail());
                                            sintomas.setText(sintomasSave);
                                            sintomas.setEnabled(false);
                                        } else {
                                            Log.e("NotUser", "No se encuentra un usuario logueado para poder avanzar," +
                                                    " por favor vuelva a loguearse.");
                                            throw new Exception("No hay usuario logueado");
                                        }
                                        break;
                                    case 404:
                                        Toast.makeText(getApplicationContext(), "Recurso no encontrado.", Toast.LENGTH_LONG).show();
                                        break;
                                    default:
                                        throw new Exception("Codigo de error: " + response.code());
                                }
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
                Toast.makeText(this,
                        "El dispositivo no cuenta con conexion a intenret en este momento",
                        Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this.getApplicationContext(), sinConexionInternet.class );
                startActivity(intent);
            }
        }
        catch (Exception ex)
        {
            Toast.makeText(this.getApplicationContext(),
                    "Ocurrio un error, por favor vuelva a loguearse"
                    , Toast.LENGTH_LONG).show();
            Intent i = new Intent(this.getApplicationContext(), LoginActivity.class);
            startActivity(i);
        }
    }

    private boolean verificarConexion() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        return (networkInfo != null && networkInfo.isConnected());
    }
}
