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
import android.widget.EditText;
import android.widget.Spinner;

import org.jetbrains.annotations.NotNull;

import DonArDato.PacienteDTO;
import DonArDato.VoluntarioDTO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class registroGeneral extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener{

    private Spinner spinnerTipoUsuario;
    private EditText campoNombre,campoApellido,campoDNI,campoMail,campoTelefono,campoNacionalidad,campoEdad;
    private Button botonRegistrarse, botonSiguiente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_general);

        /*
        Seteo los valores del spinner de tipo de usuario
         */
        spinnerTipoUsuario = (Spinner) findViewById(R.id.spnTipoVoluntario);
        spinnerTipoUsuario.setOnItemSelectedListener(this);

        String [] tiposUsuarios = {"","Paciente","Voluntario Básico","Voluntario Médico"};

        ArrayAdapter <String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, tiposUsuarios);

        spinnerTipoUsuario.setAdapter(adapter);

        campoNombre = (EditText) findViewById(R.id.edtNombre);
        campoApellido = (EditText) findViewById(R.id.edtApellido);
        campoEdad = (EditText) findViewById(R.id.edtEdad);
        campoDNI = (EditText) findViewById(R.id.edtDNI);
        campoTelefono = (EditText) findViewById(R.id.edtTelefono);
        campoMail = (EditText) findViewById(R.id.edtMail);
        campoNacionalidad = (EditText) findViewById(R.id.edtNacionalidad);

        botonRegistrarse = (Button) findViewById(R.id.btnRegistrarPacienteOVoluntarioBasico);
        botonSiguiente = (Button) findViewById(R.id.btnSiguiente);

        botonRegistrarse.setOnClickListener(this);
        botonSiguiente.setOnClickListener(this);
    }


    @Override
    public void onClick(@NotNull View v) {
        Log.i("ID toolbar", String.valueOf(v.getId()));
        //Intent intent;
        Spinner spinner = findViewById(R.id.spnTipoVoluntario);

        switch(v.getId())
        {
            case R.id.btnRegistrarPacienteOVoluntarioBasico:

                if(spinner.getSelectedItem().toString().equals("Paciente")){
                PacienteDTO paciente = new PacienteDTO();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://donar.azurewebsites.net/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                PacientesService pacienteService = retrofit.create(PacientesService.class);

                Call<Void> http_call = pacienteService.addPaciente(paciente);

                http_call.enqueue(new Callback<Void>() {
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

            }
                else {

                VoluntarioDTO voluntarioBasico = new VoluntarioDTO();
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://donar.azurewebsites.net/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                VoluntariosService voluntarioBasicoService = retrofit.create(VoluntariosService.class);

                Call<Void> http_call = voluntarioBasicoService.addVoluntarioBasico(voluntarioBasico);

                http_call.enqueue(new Callback<Void>() {
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
            }

                break;

            case R.id.btnSiguiente:
                guardarPreferencias(v);
                break;
        }

    }


    public void guardarPreferencias(View v) {

        SharedPreferences preferencias = getSharedPreferences
                ("Datos usuario general", Context.MODE_PRIVATE);

        String nombre = campoNombre.getText().toString();
        String apellido = campoApellido.getText().toString();
        String edad = campoEdad.getText().toString();
        String DNI = campoDNI.getText().toString();
        String telefono = campoTelefono.getText().toString();
        String mail = campoMail.getText().toString();
        String password = campoNacionalidad.getText().toString();


        SharedPreferences.Editor editor = preferencias.edit();
        editor.putString("nombre",nombre);
        editor.putString("apellido",apellido);
        editor.putString("DNI",DNI);
        editor.putString("mail",mail);
        editor.putString("telefono",telefono);

        editor.commit();
        Intent intent = new Intent(v.getContext(), registroMedico.class);
        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
       String item = parent.getItemAtPosition(position).toString();

        botonRegistrarse = (Button) findViewById(R.id.btnRegistrarPacienteOVoluntarioBasico);
        botonSiguiente = (Button) findViewById(R.id.btnSiguiente);

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




     /*
    private void guardarIdEnXML() {

        SharedPreferences preferencias = getSharedPreferences
                ("Datos usuario general", Context.MODE_PRIVATE);

        String nombre = campoNombre.getText().toString();
        String apellido = campoApellido.getText().toString();
        String DNI = campoDNI.getText().toString();
        String mail = campoMail.getText().toString();
        String telefono = campoTelefono.getText().toString();

        SharedPreferences.Editor editor = preferencias.edit();
        editor.putString("nombre",nombre);
        editor.putString("apellido",apellido);
        editor.putString("DNI",DNI);
        editor.putString("mail",mail);
        editor.putString("telefono",telefono);

        editor.commit();

    */
}
