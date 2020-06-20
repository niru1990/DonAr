package com.example.donar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

public class reportesMain extends AppCompatActivity implements View.OnClickListener {

    private Button generar;
    private RadioGroup reportes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportes_main);
        configView();
    }

    private void configView(){

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                PackageManager.PERMISSION_GRANTED
        );

        generar = (Button) findViewById(R.id.btnGenerar);
        reportes = (RadioGroup) findViewById(R.id.rbgReportes);

        generar.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View v) {
        try {
            switch (v.getId()) {
                case R.id.btnGenerar:
                    Intent intent = new Intent(getApplicationContext(), ReporteGraficoTorta.class);
                    intent.putExtra("reporteSolicitado", "voluntariosPorTipo");
                    startActivity(intent);
                    break;
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }


}
