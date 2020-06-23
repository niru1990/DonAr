package com.example.donar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

public class donacionLeer extends AppCompatActivity  {

    private TextView barcodeInfo;
    private SurfaceView vistaCamara;
    private BarcodeDetector barcodeDetector;
    private CameraSource cameraSource;
    private SurfaceHolder surfaceHolder;
    public static final int PERMISSION_CAMERA = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        validarCamara();
        setContentView(R.layout.activity_donacion_leer);
        barcodeInfo = (TextView) findViewById(R.id.code_info);
        vistaCamara = (SurfaceView) findViewById(R.id.vista_camara);
        vistaCamara.setZOrderMediaOverlay(true);
        surfaceHolder = vistaCamara.getHolder();
        configurarCamara();
    }

    private void savePreferences(String key, String value) {
        SharedPreferences preferencias = getSharedPreferences
                ("ID usuario", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = preferencias.edit();
        editor.putString(key, value);
        editor.commit();
    }

    @Override
    public void onResume() {
        super.onResume();
        configurarCamara();
    }

    public void configurarCamara() {
        barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.QR_CODE)
                .build();
        if (!barcodeDetector.isOperational()) {
            Toast.makeText(getApplicationContext(), "La camara no funciona", Toast.LENGTH_LONG).show();
        }
        cameraSource = new CameraSource.Builder(this, barcodeDetector)
                .setFacing(CameraSource.CAMERA_FACING_BACK)
                .setRequestedFps(24)
                .setAutoFocusEnabled(true)
                .setRequestedPreviewSize(1940, 1024)
                .build();

        vistaCamara.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                try {
                    if (ContextCompat.checkSelfPermission(donacionLeer.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        cameraSource.start(vistaCamara.getHolder());
                    } else {
                        Toast.makeText(getApplicationContext(), "FALLO", Toast.LENGTH_LONG).show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                cameraSource.stop();
            }
        });
        barcodeDetector.setProcessor(new Detector.Processor<com.google.android.gms.vision.barcode.Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<com.google.android.gms.vision.barcode.Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                if (barcodes.size() > 0) {
                    savePreferences("idDonacion", barcodes.valueAt(0).displayValue);
                    Intent intent = new Intent(getApplicationContext(), donacionDetalle.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    public void validarCamara() {
        if (ContextCompat.checkSelfPermission(donacionLeer.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, PERMISSION_CAMERA);
        }
    }
}
