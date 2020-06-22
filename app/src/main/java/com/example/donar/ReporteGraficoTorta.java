package com.example.donar;

import androidx.appcompat.app.AppCompatActivity;

import DonArDato.ReporteGeneroDTO;
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
import android.widget.TextView;
import android.util.Log;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class ReporteGraficoTorta extends AppCompatActivity {

    private PieChartView pieChartView;
    private TextView reporteTitle;
    private ArrayList<SliceValue> pieData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte_grafico_torta);

        pieChartView = findViewById(R.id.chart);
        reporteTitle = findViewById(R.id.reporteTitle);
        if(pieData == null)
            pieData = new ArrayList<>();
        else
            pieData.clear();

        selectedFontData();
    }

    private void selectedFontData(){
        String v = getIntent().getStringExtra("reporteSolicitado");

        switch (v){
            //Composicion de voluntarios
            case "voluntariosPorTipo":
                getVoluntariosPorTipo();
                break;

            //Identidad de genero
            case "generosUsuarios":
                getUsuariosPorGenero();
                break;
            case "RangoHetario":
                getRangosHetarios();
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

            final ReportesServices reportes = retrofit.create(ReportesServices.class);
            Call<ReporteVoluntariosTipo> http_call = reportes.getVoluntariosPorTipo();
            http_call.enqueue(new Callback<ReporteVoluntariosTipo>() {
                @Override
                public void onResponse(Call<ReporteVoluntariosTipo> call, Response<ReporteVoluntariosTipo> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            ReporteVoluntariosTipo r = (ReporteVoluntariosTipo) response.body();

                          //  ArrayList<SliceValue> pieData = new ArrayList<>();
                            HashMap<String, Integer> mapa = r.getMedicosPorEspecialidad();
                            for (String key : mapa.keySet()) {

                                String text = key +  " " + mapa.get(key);
                                Integer value = mapa.get(key);
                                pieData.add(new SliceValue(value,
                                        Color.parseColor(getRandomColor()))
                                        .setLabel(text));
                            }
                            graficar(pieData, "");
                            reporteTitle.setTextSize(32);
                            reporteTitle.setText("Medicos por especialidad");
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
                            ReporteVoluntariosTipo r = response.body();
                            //ArrayList<SliceValue> pieData = new ArrayList<>();
                            pieData.add(new SliceValue(r.getCantVoluntariosBasicos(),
                                    Color.parseColor("#63BD9A"))
                                    .setLabel("Voluntarios: " + r.getCantVoluntariosBasicos()));
                            pieData.add(new SliceValue(r.getCantVoluntariosMedicos(),
                                    Color.parseColor("#6D91C7"))
                                    .setLabel("Médicos: " + r.getCantVoluntariosMedicos()));
                            pieData.add(new SliceValue(r.getCantVoluntariosAsociacion(),
                                    Color.parseColor("#F5DE9D"))
                                    .setLabel("Asociación: " + r.getCantVoluntariosAsociacion()));
                            reporteTitle.setText("Voluntarios por Tipo");
                            graficar(pieData, "");

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

    public void getRangosHetarios(){
        try {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://donar.azurewebsites.net/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            ReportesServices reportes = retrofit.create(ReportesServices.class);
            Call<List<Integer>> http_call = reportes.getRangosHetarios();
            http_call.enqueue(new Callback<List<Integer>>() {
                @Override
                public void onResponse(Call<List<Integer>> call, Response<List<Integer>> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            ArrayList<Integer> r = (ArrayList<Integer>) response.body();
                            //ArrayList<SliceValue> pieData = new ArrayList<>();
                            String text = " ";
                            for(int i = 0; i < r.size(); i ++){
                                switch (i)
                                {
                                    case 0:
                                        text = "0-5 : ";
                                        break;
                                    case 1:
                                        text = "6-14 : ";
                                        break;
                                    case 2:
                                        text = "15-19 : ";
                                        break;
                                    case 3:
                                        text = "20-44 : ";
                                        break;
                                    case 4:
                                        text = "45-64 : ";
                                        break;
                                    case 5:
                                        text = "65+ : ";
                                        break;
                                }

                                pieData.add(new SliceValue(r.get(i),
                                        Color.parseColor(getRandomColor()))
                                        .setLabel(text + r.get(i)));
                            }
                            graficar(pieData, "");
                            reporteTitle.setText("Rangos hetarios");
                        }
                    }
                    else
                        this.onFailure(call, new Exception("codigo: " + response.code()));
                }

                @Override
                public void onFailure(Call<List<Integer>> call, Throwable t) {
                    Toast.makeText(getApplicationContext(),
                            "No fue posible comunicarse con la API, " + t.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            });
        }
        catch (Exception ex) {
            Toast.makeText(getApplicationContext(),
                    "No fue posible comunicarse con la API, " + ex.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }

    public void getUsuariosPorGenero() {
        try {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://donar.azurewebsites.net/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            ReportesServices reportes = retrofit.create(ReportesServices.class);
            Call<ReporteGeneroDTO> http_call = reportes.getUsuariosGeneros();
            http_call.enqueue(new Callback<ReporteGeneroDTO>() {
                @Override
                public void onResponse(Call<ReporteGeneroDTO> call, Response<ReporteGeneroDTO> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            ReporteGeneroDTO reporteGeneros = response.body();
                            //ArrayList<SliceValue> pieData = new ArrayList<>();
                            pieData.add(new SliceValue(reporteGeneros.getCantMasculinos(),
                                    Color.parseColor("#63BD9A"))
                                    .setLabel("Masculinos: " + reporteGeneros.getCantMasculinos()));
                            pieData.add(new SliceValue(reporteGeneros.getCantFemeninos(),
                                    Color.parseColor("#6D91C7"))
                                    .setLabel("Femeninos: " + reporteGeneros.getCantFemeninos()));
                            pieData.add(new SliceValue(reporteGeneros.getCantOtros(),
                                    Color.parseColor("#F5DE9D"))
                                    .setLabel("Otro: " + reporteGeneros.getCantOtros()));
                            reporteTitle.setText("Usuarios por Género");
                            graficar(pieData, "");
                        }
                    }
                    else
                        this.onFailure(call, new Exception("codigo: " + response.code()));
                }

                @Override
                public void onFailure(Call<ReporteGeneroDTO> call, Throwable t) {
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
            pieChartData.setHasCenterCircle(true).setCenterText1(titulo).setCenterText1FontSize(20).
                    setCenterText1Color(Color.parseColor("#000000"));
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
        if(result.length()< 7){
            for(int i = result.length(); i < 7; i++)//Completo la cantidad de digitos requerida
                result=result+"0";
        }
        return result.toUpperCase();
    }

}
