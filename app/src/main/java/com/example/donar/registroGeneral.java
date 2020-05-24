package com.example.donar;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
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

public class registroGeneral extends AppCompatActivity implements View.OnClickListener{

    EditText campoNombre,campoApellido,campoDNI,campoMail,campoTelefono;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_general);

        /*
        Seteo los valores del spinner de tipo de usuario
         */
        Spinner spinnerTipoUsuario =  findViewById(R.id.spnTipoVoluntario);
        String [] tiposUsuarios = {"Paciente","Voluntario Básico","Voluntario Médico"};
        ArrayAdapter <String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, tiposUsuarios);
        spinnerTipoUsuario.setAdapter(adapter);

        campoNombre = (EditText) findViewById(R.id.edtNombre);
        campoApellido = (EditText) findViewById(R.id.edtApellido);
        campoDNI = (EditText) findViewById(R.id.edtDNI);
        campoMail = (EditText) findViewById(R.id.edtMail);
        campoTelefono = (EditText) findViewById(R.id.edtTelefono);



    }


    @Override
    public void onClick(@NotNull View v) {
        Log.i("ID toolbar", String.valueOf(v.getId()));
        //super.onClick(v);
        Intent intent;
        Spinner spinner = findViewById(R.id.spnTipoVoluntario);

        switch(v.getId())
        {
            case R.id.btnRegistrarPacienteOVoluntarioBasico:

                guardarPreferencias();

                if(spinner.getSelectedItem().toString().equals("Paciente")){
                PacienteDTO paciente = new PacienteDTO();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://donar.azurewebsites.net/api/paciente")
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
                        .baseUrl("https://donar.azurewebsites.net/api/voluntariobasico")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                VoluntariosService voluntarioBasicoService = retrofit.create(VoluntariosService.class);

                Call<Void> http_call = voluntarioBasicoService.addVoluntarioBasico(voluntarioBasico);

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

                intent = new Intent(v.getContext(), MainActivity.class);
                break;

            case R.id.btnSiguiente:
                guardarPreferencias();
                intent = new Intent(v.getContext(), registroMedico.class);
                break;

            default:
                intent = new Intent(v.getContext(), registroGeneral.class);
                break;
        }
        startActivity(intent);
    }

    private void guardarPreferencias() {

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
    }

}
