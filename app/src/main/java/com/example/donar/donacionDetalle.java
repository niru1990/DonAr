package com.example.donar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import DonArDato.DonacionDTO;
import Service.DonacionesService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class donacionDetalle extends AppCompatActivity implements View.OnClickListener{
private Toolbar toolbar;
private TextView info_detalle;
private TextView info_cantidad;
private TextView info_destino;
private TextView info_fecha_vencimiento;
private Button recibir_donacion;
private Button entregar_donacion;
private Button cambiar_destino;
private DonacionDTO donacionDTO=null;
private String idDonacion;
private ImageView imageView;
private LinearLayout destinoNuevo;
private EditText destinoTexto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donacion_detalle);
        toolbar = (Toolbar) findViewById(R.id.donArToolBar);
        setSupportActionBar(toolbar);
        info_detalle=(TextView) findViewById(R.id.info_detalle);
        info_cantidad=(TextView) findViewById(R.id.info_cantidad);
        info_destino=(TextView) findViewById(R.id.info_destino);
        imageView=(ImageView)findViewById(R.id.imagenQR);
        destinoNuevo=(LinearLayout)findViewById(R.id.destinoNuevo);
        destinoTexto=(EditText)findViewById(R.id.destinoTexto);
        recibir_donacion=(Button) findViewById(R.id.recibirDonacion);
        entregar_donacion=(Button) findViewById(R.id.entregarDonacion);
        cambiar_destino=(Button) findViewById(R.id.cambiarDestino);
        recibir_donacion.setOnClickListener(this);
        entregar_donacion.setOnClickListener(this);
        cambiar_destino.setOnClickListener(this);
        info_fecha_vencimiento=(TextView) findViewById(R.id.info_fecha_vencimiento);
        final SharedPreferences sharedPreferences=getSharedPreferences("ID usuario", Context.MODE_PRIVATE);
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
                            crearQR(donacionDTO.getDonacion_id().toString());
                            if (donacionDTO.getFechaVencimiento()==null){
                                info_fecha_vencimiento.setText("No tiene");
                            }else{
                                info_fecha_vencimiento.setText(donacionDTO.getFechaVencimiento());
                            }
                            switch (donacionDTO.getEstado()){
                                case "en camino":
                                    if (String.valueOf(donacionDTO.getId()).equals(sharedPreferences.getString("ID", "0"))){
                                        entregar_donacion.setVisibility(View.VISIBLE);
                                    }
                                        break;
                                case "entregado":
                                    recibir_donacion.setVisibility(View.VISIBLE);
                                    break;
                                case "recibido":
                                    if (!String.valueOf(donacionDTO.getId()).equals(sharedPreferences.getString("ID", "0"))){
                                        destinoNuevo.setVisibility(View.VISIBLE);
                                    }
                                    break;
                            }

                        }
                        break;
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
    public void crearQR(String text){
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(text, BarcodeFormat.QR_CODE,500,500);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            imageView.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.recibirDonacion:
                final SharedPreferences sharedPreferences=getSharedPreferences("ID usuario", Context.MODE_PRIVATE);
                donacionDTO.setId(Integer.valueOf(sharedPreferences.getString("ID", "0")));
                donacionDTO.setEstado("recibido");
                modificarDonacion();
                break;
            case R.id.entregarDonacion:
                donacionDTO.setEstado("entregado");
                modificarDonacion();
                break;
            case R.id.cambiarDestino:
                if (destinoTexto.getText().toString().isEmpty()) {
                    destinoTexto.setError("Ingrese el Destino");
                }else {
                    donacionDTO.setDestino(destinoTexto.getText().toString());
                    donacionDTO.setEstado("en camino");
                    modificarDonacion();
                }
                break;
        }
    }
    public void modificarDonacion(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://donar.azurewebsites.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        DonacionesService donacionesServic= retrofit.create(DonacionesService.class);
        Call<DonacionDTO> donacionDTOCall= donacionesServic.updateDonacion(donacionDTO);
        donacionDTOCall.enqueue(new Callback<DonacionDTO>() {
            @Override
            public void onResponse(Call<DonacionDTO> call, Response<DonacionDTO> response) {
                if(response.isSuccessful()){
                    switch (response.code())
                    {
                        case 200:
                            recreate();
                            break;
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
