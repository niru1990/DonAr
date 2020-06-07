package com.example.donar;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import Adapters.SpinnerAdaptor;
import DonArDato.PacienteDTO;
import DonArDato.PaisDTO;
import DonArDato.ProvinciaDTO;
import DonArDato.SpinnerItem;
import DonArDato.TipoDeUsuarioDTO;
import DonArDato.VoluntarioDTO;
import Negocio.Paciente;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class registroGeneral extends AppCompatActivity implements View.OnClickListener{

    private Spinner spinnerTipoUsuario,spinnerProvincia,spinnerPais;
    private EditText campoNombre,campoApellido,campoDNI,campoMail,campoTelefono,campoEdad;
    private Button botonRegistrarse, botonSiguiente;
    private CheckBox campoTyC;
    private RadioGroup radioGroupGenero;
    private RadioButton radioButtonGenero;
    private SpinnerAdaptor adaptadorTDU,adaptadorPais,adaptadorProvincia;
    private ArrayList<SpinnerItem> misTiposDeUsuario = new ArrayList<>();
    private ArrayList<SpinnerItem> misPaises = new ArrayList<>();
    private ArrayList<SpinnerItem> misProvincias = new ArrayList<>();
    private String idTDU,idPais,idProvincia, nombrePais, nombreProvincia;
    private Integer idUsuario;
    private TextView txtProvincia;


    private AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_general);

        //Seteo variables
        configView();

    }



    private void configView() {

        campoNombre = findViewById(R.id.edtNombre);
        campoApellido = findViewById(R.id.edtApellido);
        radioGroupGenero = findViewById(R.id.generoGroup);
        //radioButtonGenero = findViewById(R.id.)
        campoMail = findViewById(R.id.edtEmail);
        campoEdad = findViewById(R.id.edtEdad);
        campoDNI = findViewById(R.id.edtDNI);
        campoTelefono = findViewById(R.id.edtTelefono);
        campoTyC = findViewById(R.id.checkBoxTerminosYcondiciones);
        txtProvincia = findViewById(R.id.txtProvincia);


        botonRegistrarse = findViewById(R.id.btnRegistrarPacienteOVoluntarioBasico);
        botonSiguiente = findViewById(R.id.btnSiguiente);

        botonRegistrarse.setOnClickListener(this);
        botonSiguiente.setOnClickListener(this);

        //Inicializar validación
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        //Valido nombre
        awesomeValidation.addValidation(this,R.id.edtNombre, RegexTemplate.NOT_EMPTY,R.string.nombre_invalido);
        //Valido apellido
        awesomeValidation.addValidation(this,R.id.edtApellido,RegexTemplate.NOT_EMPTY,R.string.apellido_invalido);
        //Valido edad
        awesomeValidation.addValidation(this,R.id.edtEdad,RegexTemplate.NOT_EMPTY,R.string.edad_invalida);
        //Valido DNI
        awesomeValidation.addValidation(this,R.id.edtDNI,RegexTemplate.NOT_EMPTY,R.string.DNI_invalido);
        //Valido teléfono
        awesomeValidation.addValidation(this,R.id.edtTelefono,"[10-13]{1}[0-9]{9}$",R.string.telefono_invalido);

        //Seteo valores a los spinners
        cargarSpinnerTipoDeUsuario();
        cargarSpinnerProvincia();
        cargarSpinnerPais();

    }

    private void cargarSpinnerTipoDeUsuario() {
        spinnerTipoUsuario = (Spinner) findViewById(R.id.spnTipoVoluntario);
        adaptadorTDU = new SpinnerAdaptor(registroGeneral.this, misTiposDeUsuario);
        spinnerTipoUsuario.setAdapter(adaptadorTDU);

        spinnerTipoUsuario.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentTDU, View view, int position, long id) {
                SpinnerItem clickItemTDU = (SpinnerItem) parentTDU.getItemAtPosition(position);
                idTDU = clickItemTDU.getIdData();
                String eds = clickItemTDU.getDescriptionData();


                if(eds.equals("Voluntario Medico"))
                {
                    botonSiguiente.setVisibility(View.VISIBLE);
                    botonRegistrarse.setVisibility(View.INVISIBLE);
                }
                else
                {
                    botonSiguiente.setVisibility(View.INVISIBLE);
                    botonRegistrarse.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //NADA, ABSOLUTAMENTE NADA
            }
        });

        try {
            //Creo llamada
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://donar.azurewebsites.net/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            UsuarioService usuarioService = retrofit.create(UsuarioService.class);
            Call<List<TipoDeUsuarioDTO>> http_call = usuarioService.getTiposDeUsuario();

            //Encolo llamda
            http_call.enqueue(new Callback<List<TipoDeUsuarioDTO>>() {
                @SuppressLint("ResourceType")
                @Override
                public void onResponse(Call<List<TipoDeUsuarioDTO>> call, Response<List<TipoDeUsuarioDTO>> response) {
                    try {
                        //Trabajo con la respuesta
                        if (response.body() != null && response.code() == 200) {
                            //Cargo valor señuelo para saber si asigno lo que corresponde
                            misTiposDeUsuario.add(new SpinnerItem("0", "Seleccione..." ));
                            //Cargo mi lista con valores de la tabla
                            for(TipoDeUsuarioDTO item: response.body()) {
                                misTiposDeUsuario.add(new SpinnerItem(item.getId(),
                                        item.getTipoDeUsuario()));
                            }
                            //Aviso al adaptor que se actualizo la información
                            adaptadorTDU.notifyDataSetChanged();
                        }
                        else {
                            throw new Exception("Código de respuesta: " + response.code());
                        }
                    }
                    catch (Exception ex)
                    {
                        Log.e("Cargar tipos de usuario", ex.getMessage());
                        try {
                            throw new Exception(ex.getMessage());
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }



                }

                @Override
                public void onFailure(Call<List<TipoDeUsuarioDTO>> call, Throwable t) {
                    Toast.makeText(registroGeneral.this,
                            "Ocurrió un error al intentar llamar a la API",
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

    private void cargarSpinnerPais() {
        spinnerPais = (Spinner) findViewById(R.id.spnNacionalidad);
        adaptadorPais = new SpinnerAdaptor(registroGeneral.this, misPaises);
        spinnerPais.setAdapter(adaptadorPais);

        spinnerPais.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentPais, View view, int positionPais, long id) {
                SpinnerItem clickItemPais = (SpinnerItem) parentPais.getItemAtPosition(positionPais);
                idPais = clickItemPais.getIdData();
                nombrePais = clickItemPais.getDescriptionData();

                if(nombrePais.equals("Argentina"))
                {
                    txtProvincia.setVisibility(View.VISIBLE);
                    spinnerProvincia.setVisibility(View.VISIBLE);

                }
                else
                {
                    txtProvincia.setVisibility(View.GONE);
                    spinnerProvincia.setVisibility(View.GONE);
;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //NADA, ABSOLUTAMENTE NADA
            }
        });


        try {
            //Creo llamada
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://donar.azurewebsites.net/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            //UsuarioService usuarioService = retrofit.create(UsuarioService.class);
            Call<List<PaisDTO>> http_call = retrofit.create(PaisService.class).getPaises();

            //Encolo llamda
            http_call.enqueue(new Callback<List<PaisDTO>>() {
                @SuppressLint("ResourceType")
                @Override
                public void onResponse(Call<List<PaisDTO>> call, Response<List<PaisDTO>> response) {
                    try {
                        //Trabajo con la respuesta
                        if (response.body() != null && response.code() == 200) {
                            //Cargo valor señuelo para saber si asigno lo que corresponde
                            misPaises.add(new SpinnerItem("0", "Seleccione..." ));
                            //Cargo mi lista con valores de la tabla
                            for(PaisDTO item: response.body()) {
                                misPaises.add(new SpinnerItem(item.getId(),
                                        item.getPais()));
                            }
                            //Aviso al adaptor que se actualizo la información
                            adaptadorPais.notifyDataSetChanged();
                        }
                        else {
                            throw new Exception("Código de respuesta: " + response.code());
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
                public void onFailure(Call<List<PaisDTO>> call, Throwable t) {
                    Toast.makeText(registroGeneral.this,
                            "Ocurrió un error al intentar llamar a la API",
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

    private void cargarSpinnerProvincia(){
        spinnerProvincia = (Spinner) findViewById(R.id.spnProvincia);
        adaptadorProvincia = new SpinnerAdaptor(registroGeneral.this, misProvincias);
        spinnerProvincia.setAdapter(adaptadorProvincia);

        spinnerProvincia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SpinnerItem clickItem = (SpinnerItem) parent.getItemAtPosition(position);
                idProvincia = clickItem.getIdData();
                nombreProvincia = clickItem.getDescriptionData();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //NADA, ABSOLUTAMENTE NADA
            }
        });


        try {
            //Creo llamada
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://donar.azurewebsites.net/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            Call<List<ProvinciaDTO>> http_call = retrofit.create(ProvinciaService.class).getProvincias();

            //Encolo llamda
            http_call.enqueue(new Callback<List<ProvinciaDTO>>() {
                @SuppressLint("ResourceType")
                @Override
                public void onResponse(Call<List<ProvinciaDTO>> call, Response<List<ProvinciaDTO>> response) {
                    try {
                        //Trabajo con la respuesta
                        if (response.body() != null && response.code() == 200) {
                            //Cargo valor señuelo para saber si asigno lo que corresponde
                            misProvincias.add(new SpinnerItem("0", "Seleccione..." ));
                            //Cargo mi lista con valores de la tabla
                            for(ProvinciaDTO item: response.body()) {
                                misProvincias.add(new SpinnerItem(item.getId(),
                                        item.getProvincia()));
                            }
                            //Aviso al adaptor que se actualizo la información
                            adaptadorProvincia.notifyDataSetChanged();
                        }
                        else {
                            throw new Exception("Código de respuesta: " + response.code());
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
                public void onFailure(Call<List<ProvinciaDTO>> call, Throwable t) {
                    Toast.makeText(registroGeneral.this,
                            "Ocurrió un error al intentar llamar a la API",
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


    @Override
    public void onClick(@NotNull View v) {

        //Chequeo validez de campos
        if(awesomeValidation.validate()) {
           if(campoTyC.isChecked()){
/*
               LoginActivity login = new LoginActivity();
               if(login.getCuentaLogueada()!=null){
                   idUsuario = Integer.valueOf(login.getCuentaLogueada().getId());
               }
*/
               //Validacion EXITOSA ;)
        switch(v.getId())
        {
            case R.id.btnRegistrarPacienteOVoluntarioBasico:

                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl("https://donar.azurewebsites.net/")
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();

                   Log.i("mensaje",idTDU);
                        switch (Integer.valueOf(idTDU)) {
                            //Paciente
                            case 1:

                                PacienteDTO paciente = new PacienteDTO(idUsuario,
                                        campoNombre.getText().toString(),
                                        campoApellido.getText().toString(), 1,
                                        campoMail.getText().toString(),
                                        getGeneroValue().getText().toString(),
                                        Integer.parseInt(campoDNI.getText().toString()),
                                        campoTelefono.getText().toString(),
                                        Integer.parseInt(campoEdad.getText().toString()),
                                        null,
                                        idProvincia,0);

                                PacientesService pacienteService = retrofit.create(PacientesService.class);

                                Call<Void> http_call_paciente = pacienteService.addPaciente(paciente);

                                http_call_paciente.enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {
                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(intent);
                                    }

                                    @Override
                                    public void onFailure(Call<Void> call, Throwable t) {
                                        Log.i("HTTP ERROR", t.getMessage());
                                    }
                                });

                                break;
                            //Voluntario
                                case 2:

                                VoluntarioDTO voluntarioDTO = new VoluntarioDTO(0,
                                        campoNombre.getText().toString(),
                                        campoApellido.getText().toString(), 2,
                                        getGeneroValue().getText().toString(),
                                        Integer.parseInt(campoDNI.getText().toString()),
                                        campoMail.getText().toString(),
                                        campoTelefono.getText().toString(),
                                        Integer.parseInt(campoEdad.getText().toString()), idPais,
                                        idProvincia);


                                VoluntariosService voluntarioBasicoService = retrofit.create(VoluntariosService.class);

                                Call<Void> http_call_voluntarioBasico = voluntarioBasicoService.addVoluntarioBasico(voluntarioDTO);

                                http_call_voluntarioBasico.enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {
                                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                        startActivity(intent);
                                    }

                                    @Override
                                    public void onFailure(Call<Void> call, Throwable t) {
                                        Log.i("HTTP ERROR", t.getMessage());
                                    }
                                });

                                    //Voluntario fundacion
                           /*
                            case 3:

                                VoluntarioDTO voluntarioDTO = new VoluntarioDTO(campoNombre.getText().toString(),
                                        campoApellido.getText().toString(),
                                        getGeneroValue().getText().toString(),
                                        Integer.parseInt(campoDNI.getText().toString()),campoTelefono.getText().toString(),
                                        spinnerProvincia.getSelectedItem().toString(),
                                        spinnerPais.getSelectedItem().toString());


                                VoluntariosService voluntarioBasicoService = retrofit.create(VoluntariosService.class);

                                Call<Void> http_call_voluntarioBasico = voluntarioBasicoService.addVoluntarioBasico(voluntarioDTO);

                                http_call_voluntarioBasico.enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {
                                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                        startActivity(intent);
                                    }

                                    @Override
                                    public void onFailure(Call<Void> call, Throwable t) {
                                        Log.i("HTTP ERROR", t.getMessage());
                                    }
                                });
*/

                                break;

                            default:
                        }

                        break;

                        //Voluntario medico
                        case R.id.btnSiguiente:
                            guardarPreferencias(v);
                            break;
                }

           }else{
               //No se checkeo el checkbox de terminos y condiciones
               Toast.makeText(getApplicationContext(), R.string.TyC_invalido, Toast.LENGTH_SHORT).show();
           }
        }else{
            //Validacion NO exitosa :(
            Toast.makeText(getApplicationContext(), "Uno o más campos incorrectos.", Toast.LENGTH_SHORT).show();
        }
    }


    public void guardarPreferencias(@NotNull View v) {

        SharedPreferences preferencias = getSharedPreferences
                ("Datos generales medico", Context.MODE_PRIVATE);

        String nombre = campoNombre.getText().toString();
        String apellido = campoApellido.getText().toString();
        String genero = getGeneroValue().getText().toString();
        String email = campoMail.getText().toString();
        String edad = campoEdad.getText().toString();
        String DNI = campoDNI.getText().toString();
        String telefono = campoTelefono.getText().toString();
        String pais = spinnerPais.getSelectedItem().toString();
        String provincia = spinnerProvincia.getSelectedItem().toString();

        SharedPreferences.Editor editor = preferencias.edit();
        editor.putString("nombre", nombre);
        editor.putString("apellido", apellido);
        editor.putString("genero", genero);
        editor.putString("email", email);
        editor.putString("Edad", edad);
        editor.putString("DNI", DNI);
        editor.putString("telefono", telefono);
        //editor.putString("nacionalidad",pais);
        editor.putString("provincia",provincia);

        editor.commit();
        Intent intent = new Intent(v.getContext(), registroMedico.class);
        startActivity(intent);
    }

    public RadioButton getGeneroValue(){
        int radioId = radioGroupGenero.getCheckedRadioButtonId();
        return radioButtonGenero = findViewById(radioId);
    }



    /*
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
       String item = parent.getItemAtPosition(position).toString();

        botonRegistrarse = findViewById(R.id.btnRegistrarPacienteOVoluntarioBasico);
        botonSiguiente = findViewById(R.id.btnSiguiente);

        if(item.equals("Voluntario Medico"))
        {
            botonSiguiente.setVisibility(View.VISIBLE);
            botonRegistrarse.setVisibility(View.INVISIBLE);
        }
        else
        {
            botonSiguiente.setVisibility(View.INVISIBLE);
            botonRegistrarse.setVisibility(View.VISIBLE);
        }



    }
*/




}
