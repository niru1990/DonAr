package com.example.donar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.EventLog;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

//Negocio
import java.lang.reflect.Array;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import DonArDato.EspecialidadDTO;
import DonArDato.EventoDTO;
import DonArDato.VoluntarioDTO;
import Negocio.Evento;
import Negocio.Voluntario;
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
    private EditText sintomas;
    private TextView especialidadText;
    private Spinner especialidad;
    private Button asignar;
    private Toolbar toolbar;

    private EventoDTO espinerValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paciente_asignar_especialidad);
        configView();
    }

    private void configView() {
        id = (TextView) findViewById(R.id.txtIdConsulta);
        idPaciente = (TextView) findViewById(R.id.txtIdPaciente);
        nombre = (TextView) findViewById(R.id.txtNombre);
        apellido = (TextView) findViewById(R.id.txtApellido);
        telefono = (TextView) findViewById(R.id.txtTelefono);
        sintomas = (EditText) findViewById(R.id.medtSintomasYMedicamentos);
        especialidad = (Spinner) findViewById(R.id.spnEspecialidad);
        especialidadText = (TextView) findViewById(R.id.txtEspecialidad);
        asignar = (Button) findViewById(R.id.btnAsignar);
        asignar.setOnClickListener(this);
        cargarSpinner(especialidad);
        especialidad.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                espinerValue = (EventoDTO) parent.getSelectedItem();
            }
        });
        toolbar = (Toolbar) findViewById(R.id.donArToolBar);
        setSupportActionBar(toolbar);

        /**
         * Testeo de la llamada a la API
        Voluntario voluntario = new Voluntario();
        voluntario.obtenerListadoVoluntarios();
        */

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
    public boolean onOptionsItemSelected(@NotNull MenuItem item) {
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

    private EventoDTO formToObject() {
        EventoDTO event = new EventoDTO();
        event.setId(BigInteger.valueOf(Long.parseLong(id.getText().toString())));
        event.setIdPaciente(BigInteger.valueOf(Long.parseLong(idPaciente.getText().toString())));
        //event.getidVoluntario();//Tomarlo del Xml
        event.setIdEspecialidad(espinerValue.getIdEspecialidad());
        event.setidVoluntarioMedico(null);
        event.setSintomas(sintomas.getText().toString());
        return event;
    }

    private void actualizar() throws Exception {
        try{
            EventoDTO event =  formToObject();
            if(new Evento().validar(event, true))
            {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://donar.azurewebsites.net/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                EventoServices eventos =retrofit.create(EventoServices.class);
                ///TODO cambiar a base del endpoint
                Call<Void> http_call = eventos.updateEvento(event.getId(), event.getIdEspecialidad());;

                http_call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        try {
                            if (response.code() == 200) {
                                if (response.body() != null) {
                                    //limpiar la pantalla
                                }
                            }
                            else {
                                throw new Exception("codigo: " + response.code() + " Comuniquese con su administrador de sistemas");
                            }
                        }
                        catch (Exception ex)
                        {
                            Toast.makeText(pacienteAsignarEspecialidad.this,
                                    ex.getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(pacienteAsignarEspecialidad.this,
                                "Ocurrio un error al llamar a la API",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
            else
            {
                Toast.makeText(this, "Por favor cargue los campos obligatorios",
                        Toast.LENGTH_SHORT).show();
                especialidadText.setTextColor(Color.RED);
            }
        }
        catch (Exception ex)
        {
            throw new Exception(ex.getMessage());
        }
    }

    private void cargarSpinner(Spinner spinner){

        try {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://donar.azurewebsites.net/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            EspecialidadServices especialidadServices = retrofit.create(EspecialidadServices.class);

            Call<List<EspecialidadDTO>> http_call = especialidadServices.getEspecialidades();
            http_call.enqueue(new Callback<List<EspecialidadDTO>>() {
                @SuppressLint("ResourceType")
                @Override
                public void onResponse(Call<List<EspecialidadDTO>> call, Response<List<EspecialidadDTO>> response) {
                    try {
                        if (response.body() != null) {
                            ArrayList<EspecialidadDTO> especialidadesDto = new ArrayList<EspecialidadDTO>();
                            EspecialidadDTO ed = new EspecialidadDTO();
                            ed.setId(0);
                            ed.setEspecialidad("Seleccione una especialidad.");
                            especialidadesDto.add(ed);
                            for (EspecialidadDTO e : response.body()) {
                                especialidadesDto.add(e);
                            }

                            ArrayAdapter<EspecialidadDTO> addapter = new ArrayAdapter(getApplicationContext(),
                                    android.R.layout.simple_spinner_item,
                                    especialidadesDto);
                            especialidad.setAdapter(addapter);
                        }
                    }
                    catch (Exception ex)
                    {
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
        catch (Exception ex)
        {
            Toast.makeText(this,
                    "Por favor verifique si su conexion con internet",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
