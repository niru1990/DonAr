package com.example.donar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;

import org.jetbrains.annotations.NotNull;

import DonArDato.PacienteDTO;
import DonArDato.VoluntarioDTO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class registroGeneral extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener{

    private Spinner spinnerTipoUsuario,spinnerProvincia,spinnerNacionalidad;
    private EditText campoNombre,campoApellido,campoDNI,campoMail,campoTelefono,campoNacionalidad,campoEdad;
    private Button botonRegistrarse, botonSiguiente;
    private CheckBox campoTyC;

    private AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_general);


        //Seteo los valores del spinner de tipo de usuario
        spinnerTipoUsuario = (Spinner) findViewById(R.id.spnTipoVoluntario);

        spinnerTipoUsuario.setOnItemSelectedListener(this);

        String [] tiposUsuarios = {"<Seleccione tipo de usuario>","Paciente","Voluntario","Voluntario Médico"};

        ArrayAdapter <String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, tiposUsuarios);

        spinnerTipoUsuario.setAdapter(adapter);

        campoNombre = findViewById(R.id.edtNombre);
        campoApellido = findViewById(R.id.edtApellido);
        campoEdad = findViewById(R.id.edtEdad);
        campoDNI = findViewById(R.id.edtDNI);
        campoTelefono = findViewById(R.id.edtTelefono);
        spinnerNacionalidad = findViewById(R.id.spnNacionalidad);
        spinnerProvincia = findViewById(R.id.spnProvincia);
        campoTyC = findViewById(R.id.checkBoxTerminosYcondiciones);

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


    }


    @Override
    public void onClick(@NotNull View v) {
        Log.i("ID toolbar", String.valueOf(v.getId()));
        //Intent intent;
        Spinner spinner = findViewById(R.id.spnTipoVoluntario);
        String tipoDeUsuario = spinner.getSelectedItem().toString();

        //Chequeo validez de campos
        if(awesomeValidation.validate()) {
           if(campoTyC.isChecked()){

               //Validacion EXITOSA ;)
        switch(v.getId())
        {
            case R.id.btnRegistrarPacienteOVoluntarioBasico:

                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl("https://donar.azurewebsites.net/")
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();

                        switch (tipoDeUsuario) {
                            case "Paciente":

                                PacienteDTO paciente = new PacienteDTO(campoNombre.getText().toString(),
                                        campoApellido.getText().toString(),Integer.parseInt(campoDNI.getText().toString()),campoTelefono.getText().toString(),
                                        spinnerProvincia.getSelectedItem().toString(),
                                        spinnerNacionalidad.getSelectedItem().toString());

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
                            case "Voluntario":

                                VoluntarioDTO voluntarioDTO = new VoluntarioDTO(campoNombre.getText().toString(),
                                        campoApellido.getText().toString(),Integer.parseInt
                                        (campoDNI.getText().toString()),campoTelefono.getText().toString(),
                                        spinnerProvincia.getSelectedItem().toString(),
                                        spinnerNacionalidad.getSelectedItem().toString());


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

                                break;

                            default:
                                Toast.makeText(getApplicationContext(), R.string.tipo_usuario_invalido, Toast.LENGTH_SHORT).show();
                        }

                        break;

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


    public void guardarPreferencias(View v) {

        SharedPreferences preferencias = getSharedPreferences
                ("Datos generales medico", Context.MODE_PRIVATE);

        String nombre = campoNombre.getText().toString();
        String apellido = campoApellido.getText().toString();
        String edad = campoEdad.getText().toString();
        String DNI = campoDNI.getText().toString();
        String telefono = campoTelefono.getText().toString();
        String nacionalidad = spinnerNacionalidad.getSelectedItem().toString();
        String provincia = spinnerProvincia.getSelectedItem().toString();

        SharedPreferences.Editor editor = preferencias.edit();
        editor.putString("nombre",nombre);
        editor.putString("apellido",apellido);
        editor.putString("Edad",edad);
        editor.putString("DNI",DNI);
        editor.putString("telefono",telefono);
        editor.putString("nacionalidad",telefono);
        editor.putString("provincia",telefono);

        editor.commit();
        Intent intent = new Intent(v.getContext(), registroMedico.class);
        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
       String item = parent.getItemAtPosition(position).toString();

        botonRegistrarse = findViewById(R.id.btnRegistrarPacienteOVoluntarioBasico);
        botonSiguiente = findViewById(R.id.btnSiguiente);

        if(item.equals("Voluntario Médico"))
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

    }



}
