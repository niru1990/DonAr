package com.example.donar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.btnRegistrarMedico:

                VoluntarioMedicoDTO voluntarioMedico = new VoluntarioMedicoDTO();
                //Log.i("MARCA", marca.getText().toString());
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("")
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
