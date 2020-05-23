package com.example.donar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import org.jetbrains.annotations.NotNull;

import DonArDato.PacienteDTO;
import Negocio.Paciente;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class registroGeneral extends AppCompatActivity implements View.OnClickListener{

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
        }


    @Override
    public void onClick(@NotNull View v) {
        Log.i("ID toolbar", String.valueOf(v.getId()));
        //super.onClick(v);
        Intent intent;

        switch(v.getId())
        {
            case R.id.btnRegistrarPacienteOVoluntarioBasico:
                PacienteDTO paciente = new PacienteDTO();
                //Log.i("MARCA", marca.getText().toString());
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                PacientesService pacienteService = retrofit.create(PacientesService.class);
                Call<Void> http_call = pacienteService.addPaciente(paciente);
                //  Log.i("Paciente");

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

                intent = new Intent(v.getContext(), MainActivity.class);
                break;
            case R.id.btnSiguiente:
                intent = new Intent(v.getContext(), registroMedico.class);
                break;
            default:
                intent = new Intent(v.getContext(), registroGeneral.class);
                break;
        }
        startActivity(intent);
    }

}
