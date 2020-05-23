package com.example.donar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import DonArDato.PacienteDTO;
import Negocio.Paciente;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class registroGeneral extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_general);

        final Button buttonRegistrarPaciente = findViewById(R.id.btnRegistrarPaciente);
        buttonRegistrarPaciente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

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

                }catch (Exception e){
                    e.printStackTrace();
                }
            }

        });



    }
}
