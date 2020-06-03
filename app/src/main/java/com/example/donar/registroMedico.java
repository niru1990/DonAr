package com.example.donar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;


import DonArDato.VoluntarioMedicoDTO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class registroMedico extends AppCompatActivity implements View.OnClickListener{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_voluntario_medico);

    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.btnRegistrarMedico:

                SharedPreferences preferences = getSharedPreferences("Datos generales medico",
                        Context.MODE_PRIVATE);

                String nombre = preferences.getString("nombre","No posee nombre");
                String apellido = preferences.getString("apellido","No posee apellido");
                String DNI = preferences.getString("DNI","No posee DNI");
                String telefono = preferences.getString("telefono","No posee teléfono");


                VoluntarioMedicoDTO voluntarioMedico = new VoluntarioMedicoDTO();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://donar.azurewebsites.net/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                VoluntariosService voluntariosService = retrofit.create(VoluntariosService.class);
                Call<Void> http_call = voluntariosService.addVoluntarioMedico(voluntarioMedico);
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
            default:
                intent = new Intent(v.getContext(), registroGeneral.class);
                break;
        }
    }
}
