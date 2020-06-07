package com.example.donar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.graphics.Color;
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

import org.jetbrains.annotations.NotNull;

//Negocio
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import Adapters.SpinnerAdaptor;
import DonArDato.EspecialidadDTO;
import DonArDato.EventoDTO;
import DonArDato.PacienteDTO;
import DonArDato.SpinnerItem;
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
    private TextView especialidadText;
    private EditText sintomas;
    private Spinner especialidad;
    private Button asignar;
    private Toolbar toolbar;

    private String idEspecialidad;

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
        especialidadText = (TextView) findViewById(R.id.txtEspecialidad);
        sintomas = (EditText) findViewById(R.id.medtSintomasYMedicamentos);
        //Boton
        asignar = (Button) findViewById(R.id.btnAsignar);
        asignar.setOnClickListener(this);


        //Conseguir la información
        loadData();

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
                Toast.makeText(pacienteAsignarEspecialidad.this,
                        "La especialidad seleccionada fue: " + eds,
                        Toast.LENGTH_LONG).show();
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
            cargarSpinner(especialidad);
            //Obtener evento y cargarlo. Esto obliga a obtener el paciente.
//            getEvento();
            //getPacienteData(idPaciente.getText().toString());
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
        getMenuInflater().inflate(R.menu.toolbar_menu_logueado, menu);
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

    @NotNull
    private EventoDTO formToObject() {
        EventoDTO event = new EventoDTO();
        event.setId(BigInteger.valueOf(Long.parseLong(id.getText().toString())));
        event.setPacienteId(BigInteger.valueOf(Long.parseLong(idPaciente.getText().toString())));
        //event.getidVoluntario();//Tomarlo del Xml
        event.setEspecialidadId(Integer.valueOf(idEspecialidad));
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
                Call<Void> http_call = eventos.updateEvento(event.getId(), event.getEspecialidadId());;

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
                            //Cargo valor señuelo para saber si asigno lo que corresponde
                            misEspecialidades.add(new SpinnerItem("0", "Seleccione..." ));
                            //Cargo mi lista con valores de la tabla
                            for(EspecialidadDTO esp: response.body()) {
                                misEspecialidades.add(new SpinnerItem(esp.getId(),
                                        esp.getEspecialidad()));
                            }
                            //Aviso al adaptor que se actualizo la información
                            miAdaptador.notifyDataSetChanged();
                        }
                        else {
                            throw new Exception("codigo de respuesta: " + response.code());
                        }
                    }
                    catch (Exception ex)
                    {
                        Log.e("Cargar especialidades", ex.getMessage());
                        try {
                            throw new Exception(ex.getMessage());
                        }
                        catch (Exception e) {
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

    private void getEvento() throws Exception {
        try {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://donar.azurewebsites.net/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            EventoServices eventoServices = retrofit.create(EventoServices.class);
            String idEvento = getIntent().getStringExtra("idEvento");
            Call<EventoDTO> http_call = eventoServices.getEventoById(idEvento);
            //Call<EventoDTO> http_call = eventoServices.getEventoById("1");//TODO: Modificar despues de hacer el automach
            http_call.enqueue(new Callback<EventoDTO>() {
                @Override
                public void onResponse(Call<EventoDTO> call, Response<EventoDTO> response){
                    try {
                        if(response.code() == 200){
                            if(response.isSuccessful() && response.body() != null) {
                                EventoDTO event = (EventoDTO)response.body();
                                idPaciente.setText(event.getPacienteId().toString());

                                Log.i("idPaciente", idPaciente.getText().toString());

                                idPaciente.setVisibility(View.INVISIBLE);
                                sintomas.setText(event.getSintomas());

                                //Handler
                            }
                        }
                        else {
                            throw new Exception((Integer.valueOf(response.code())).toString() +
                                    ": error " + response.message());
                        }
                    }
                    catch (Exception ex) {
                        Log.e("errors", ex.getMessage());
                    }
                }


                @Override
                public void onFailure(Call<EventoDTO> call, Throwable t) {
                    Log.i("error123", "ocurrio un errro al llamar a la API");
                    Toast.makeText(pacienteAsignarEspecialidad.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    //apellido.setText(t.getMessage());
                }
            });

        }
        catch (Exception ex){
            Log.i("errorCall", "ocurrio un errro al llamar a la API");
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            throw new Exception(ex.getMessage());
        }
    }

    private void getPacienteData(String paciente) {
        try {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://donar.azurewebsites.net/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            //PacientesService pacientesService = retrofit.create(PacientesService.class);
            PacientesService pacientesService = retrofit.create(PacientesService.class);
            Call<PacienteDTO> http_call = pacientesService.getPacienteEspecifico(paciente); ///TODO cambiar el 1 por el id del usuario desde el xml
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
        catch (Exception ex){
            Log.i("error", "ocurrio un errro al llamar a la API");
        }
    }
}
