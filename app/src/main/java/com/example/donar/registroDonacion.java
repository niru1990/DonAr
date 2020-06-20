package com.example.donar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.jetbrains.annotations.NotNull;

import DonArDato.DonacionDTO;
import Service.DonacionesService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class registroDonacion extends AppCompatActivity implements View.OnClickListener {

    private Activity view;
    private EditText donacionVencimientoFecha;
    private EditText donacionDescripcion;
    private EditText donacionCantidad;
    private EditText donacionDestino;
    private CheckBox checkBoxVencimiento;
    private Button registrarDonacion;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_donacion);
        toolbar = (Toolbar) findViewById(R.id.donArToolBar);
        setSupportActionBar(toolbar);
        donacionVencimientoFecha = (EditText) findViewById(R.id.donacionVencimientoFecha);
        donacionDescripcion = (EditText) findViewById(R.id.donacionDescripcion);
        donacionCantidad = (EditText) findViewById(R.id.donacionCantidad);
        donacionDestino = (EditText) findViewById(R.id.donacionDestino);
        checkBoxVencimiento = (CheckBox) findViewById(R.id.donacionVencimiento);
        registrarDonacion= (Button) findViewById(R.id.btnRegistrarDonacion);
        registrarDonacion.setOnClickListener(this);
        donacionVencimientoFecha.setVisibility(View.INVISIBLE);
       checkBoxVencimiento.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               if (buttonView.isChecked()) {
                  donacionVencimientoFecha.setVisibility(View.VISIBLE);
               }
               else
               {
                   donacionVencimientoFecha.setVisibility(View.INVISIBLE);
               }
           }

       });

    }



    @Override
    public void onClick(@NotNull View view) {
        switch (view.getId()){
            case R.id.btnRegistrarDonacion:
                if (validarRegistro()){

                    SharedPreferences preferencias = getSharedPreferences
                            ("ID usuario", Context.MODE_PRIVATE);


                    String idUsuario= preferencias.getString("ID", "0");

                    DonacionDTO donacionDTO = new DonacionDTO(donacionDestino.getText().toString(),
                            Integer.parseInt(donacionCantidad.getText().toString()),
                            donacionDescripcion.getText().toString(), Integer.valueOf(idUsuario));
                    if (checkBoxVencimiento.isChecked()){
                        donacionDTO.setFechaVencimiento(donacionVencimientoFecha.getText().toString());
                    }else{
                        donacionDTO.setFechaVencimiento("");
                    }
                    Toast.makeText(this,"Verificado",Toast.LENGTH_LONG).show();

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("https://donar.azurewebsites.net/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    DonacionesService donacionesServic= retrofit.create(DonacionesService.class);
                    Call<Integer> donacionDTOCall= donacionesServic.addDonacion(donacionDTO);
                    donacionDTOCall.enqueue(new Callback<Integer>() {
                        @Override
                        public void onResponse(Call<Integer> call, Response<Integer> response) {
                            if(response.isSuccessful()){
                                switch (response.code())
                                {
                                    case 200:
                                        if(response.isSuccessful()){
                                            Intent intent = new Intent(getApplicationContext(), donacionMain.class);
                                            startActivity(intent);
                                        }
                                    default:
                                        try {
                                            throw new Exception(response.code() + " " + response.message());
                                        }
                                        catch (Exception ep) {
                                            ep.printStackTrace();
                                        }
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<Integer> call, Throwable t) {
                            if (t !=null){
                                Log.i("HTTP ERROR", t.getMessage());
                            }
                        }
                    });

    }
                break;
            default:
                break;
    }
    }

    public boolean validarRegistro() {
        boolean validado=true;
        if (donacionDescripcion.getText().toString().isEmpty()){
            donacionDescripcion.setError("Describa la donación");
            validado=false;
        }
        if (donacionCantidad.getText().toString().isEmpty()){
            donacionCantidad.setError("Ingrese la Cantidad");
            validado=false;
        }
        if (donacionDestino.getText().toString().isEmpty()){
            donacionDestino.setError("Ingrese el Destino");
            validado=false;
        }
        if (checkBoxVencimiento.isChecked()&&donacionVencimientoFecha.getText().toString().isEmpty()){
            donacionVencimientoFecha.setError("Ingrese la fecha de Vencimiento");
            validado=false;
        }
return validado;
    }
/*
    public Date parsearFecha(String date){
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Date myDate = new Date();
        try {
            myDate = df.parse(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return myDate;
    }

 */

}