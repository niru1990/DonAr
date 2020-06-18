package com.example.donar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.barcode.Barcode;

import DonArDato.DonacionDTO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.donar.donacionMain.REQUEST_CODE_QR;

public class donacionDetalle extends AppCompatActivity implements View.OnClickListener{
private Toolbar toolbar;
private TextView info_detalle;
private TextView info_cantidad;
private TextView info_destino;
private TextView info_fecha_vencimiento;
private Button recibir_donacion;
private DonacionDTO donacionDTO=null;
private Bitmap bitmap;
private String idDonacion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donacion_detalle);
        toolbar = (Toolbar) findViewById(R.id.donArToolBar);
        setSupportActionBar(toolbar);
        info_detalle=(TextView) findViewById(R.id.info_detalle);
        info_cantidad=(TextView) findViewById(R.id.info_cantidad);
        info_destino=(TextView) findViewById(R.id.info_destino);
        recibir_donacion=(Button) findViewById(R.id.recibirDonacion);
        recibir_donacion.setOnClickListener(this);
        info_fecha_vencimiento=(TextView) findViewById(R.id.info_fecha_vencimiento);
        SharedPreferences sharedPreferences=getSharedPreferences("ID usuario", Context.MODE_PRIVATE);
        idDonacion=sharedPreferences.getString("idDonacion", "0");
        if (idDonacion!=null){

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://donar.azurewebsites.net/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            DonacionesService donacionesServic= retrofit.create(DonacionesService.class);
            Call<DonacionDTO> donacionDTOCall= donacionesServic.getDonacionID(idDonacion);
            donacionDTOCall.enqueue(new Callback<DonacionDTO>() {
                @Override
                public void onResponse(Call<DonacionDTO> call, Response<DonacionDTO> response) {
                    if(response.isSuccessful()){
                        switch (response.code())
                        {
                            case 200:
                        if(response.body()!=null){
                            donacionDTO=(DonacionDTO) response.body();
                            info_detalle.setText(donacionDTO.getDetalle());
                            info_cantidad.setText(String.valueOf(donacionDTO.getCantidad()));
                            info_destino.setText(donacionDTO.getDestino());
                            if (donacionDTO.getFechaVencimiento()==null){
                                info_fecha_vencimiento.setText("No tiene");
                            }else{
                                info_fecha_vencimiento.setText(donacionDTO.getFechaVencimiento());
                            }
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
                public void onFailure(Call<DonacionDTO> call, Throwable t) {
                    Toast.makeText(donacionDetalle.this, t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });

        }
    }
/*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_QR && resultCode == RESULT_OK) {
            if (data != null) {
                final Barcode barcode = data.getParcelableExtra("barcode");
                String QR = barcode.displayValue;
                lectura_QR.setText(QR);
            }
        }
    }

 */
    /*

    private Bitmap getBitmapFromString(String stringPicture) {
        byte[] decodedString = Base64.decode(stringPicture, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedByte;
    }
 */
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.recibirDonacion:
                Toast.makeText(donacionDetalle.this, "toco el boton", Toast.LENGTH_LONG).show();
        }
    }
}
