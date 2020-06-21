package com.example.donar;

import androidx.appcompat.app.AppCompatActivity;

import DonArDato.ReporteVoluntariosTipo;
import Service.ReportesServices;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.view.PieChartView;
import lecho.lib.hellocharts.model.SliceValue;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class ReporteGraficoTorta extends AppCompatActivity {

    private PieChartView pieChartView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte_grafico_torta);
        pieChartView = findViewById(R.id.chart);
        selectedFontData();
    }

    private void selectedFontData(){
        String v = getIntent().getStringExtra("reporteSolicitado");

        switch (v){
            case "voluntariosPorTipo":
                getVoluntariosPorTipo();
                break;
            case "MedicosXEspecialidad":
                getMedicosPorEspecialidad();
                break;
            default:
        }
    }

    public void getMedicosPorEspecialidad() {
        try {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://donar.azurewebsites.net/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            ReportesServices reportes = retrofit.create(ReportesServices.class);
            Call<ReporteVoluntariosTipo> http_call = reportes.getVoluntariosPorTipo();
            http_call.enqueue(new Callback<ReporteVoluntariosTipo>() {
                @Override
                public void onResponse(Call<ReporteVoluntariosTipo> call, Response<ReporteVoluntariosTipo> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            ReporteVoluntariosTipo r = (ReporteVoluntariosTipo) response.body();

                            ArrayList<SliceValue> pieData = new ArrayList<>();
                            HashMap<String, Integer> mapa = r.getMedicosPorEspecialidad();
                            for (String key : mapa.keySet()) {

                                String text = key +  " " + mapa.get(key);
                                Integer value = mapa.get(key);
                                pieData.add(new SliceValue(value,
                                        Color.parseColor(getRandomColor()))
                                        .setLabel(text));
                            }
                            graficar(pieData, "Medicos por especialidad");
                            //pieData.clear();
                        }
                    }
                    else
                        this.onFailure(call, new Exception("codigo: " + response.code()));
                }

                @Override
                public void onFailure(Call<ReporteVoluntariosTipo> call, Throwable t) {
                    Toast.makeText(getApplicationContext(),
                            "No fue posible comunicarse con la API, " + t.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            });
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(),
                    "No fue posible comunicarse con la API, " + ex.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }


    public void getVoluntariosPorTipo() {
        try {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://donar.azurewebsites.net/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            ReportesServices reportes = retrofit.create(ReportesServices.class);
            Call<ReporteVoluntariosTipo> http_call = reportes.getVoluntariosPorTipo();
            http_call.enqueue(new Callback<ReporteVoluntariosTipo>() {
                @Override
                public void onResponse(Call<ReporteVoluntariosTipo> call, Response<ReporteVoluntariosTipo> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            ReporteVoluntariosTipo r = (ReporteVoluntariosTipo) response.body();
                            ArrayList<SliceValue> pieData = new ArrayList<>();
                            pieData.add(new SliceValue(r.getCantVoluntariosBasicos(),
                                    Color.parseColor("#63BD9A"))
                                    .setLabel("Voluntarios " + r.getCantVoluntariosBasicos()));
                            pieData.add(new SliceValue(r.getCantVoluntariosMedicos(),
                                    Color.parseColor("#6D91C7"))
                                    .setLabel("Medicos " + r.getCantVoluntariosMedicos()));
                            pieData.add(new SliceValue(r.getCantVoluntariosAsociacion(),
                                    Color.parseColor("#F5DE9D"))
                                    .setLabel("Asociación " + r.getCantVoluntariosAsociacion()));
                            graficar(pieData, "Voluntarios por tipo");
                        }
                    }
                    else
                        this.onFailure(call, new Exception("codigo: " + response.code()));
                }

                @Override
                public void onFailure(Call<ReporteVoluntariosTipo> call, Throwable t) {
                    Toast.makeText(getApplicationContext(),
                            "No fue posible comunicarse con la API, " + t.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            });
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(),
                    "No fue posible comunicarse con la API, " + ex.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }

    private void graficar(ArrayList pieData, String titulo){

        List data = pieData;
        try {
            PieChartData pieChartData = new PieChartData(data);
            pieChartData.setHasLabels(true).setValueLabelTextSize(14);
            pieChartData.setHasCenterCircle(true).setCenterText1(titulo).setCenterText1FontSize(20).setCenterText1Color(Color.parseColor("#000000"));
            pieChartView.setPieChartData(pieChartData);
        }
        catch (Exception ex) {
            Toast.makeText(getApplicationContext(),
                    ex.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }

    @NotNull
    private String getRandomColor(){
        Random rand = new Random();
        int myRandomNumber = rand.nextInt(0x1000000) + 0x10; // Generates a random number between 0x10 and 0x20
        String result = "#" +  Integer.toHexString(myRandomNumber); // Random hex number in result
        return result.toUpperCase();
    }

}
