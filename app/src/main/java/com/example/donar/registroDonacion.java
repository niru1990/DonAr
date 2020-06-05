package com.example.donar;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import DonArDato.ItemDonacion;

public class registroDonacion extends AppCompatActivity {

    private Activity view;
    private EditText donacionVencimientoFecha;
    private EditText donacionDescripcion;
    private EditText donacionCantidad;
    private EditText donacionDestino;
    private CheckBox checkBoxVencimiento;
    private Button registrarDonacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_donacion);
        donacionVencimientoFecha = (EditText) findViewById(R.id.donacionVencimientoFecha);
        donacionDescripcion = (EditText) findViewById(R.id.donacionDescripcion);
        donacionCantidad = (EditText) findViewById(R.id.donacionCantidad);
        donacionDestino = (EditText) findViewById(R.id.donacionDestino);
        checkBoxVencimiento = (CheckBox) findViewById(R.id.donacionVencimiento);
        registrarDonacion= (Button) findViewById(R.id.btnRegistrarDonacion);
        registrarDonacion.setOnClickListener((View.OnClickListener) this);
        checkBoxVencimiento.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    donacionVencimientoFecha.setVisibility(View.VISIBLE);
                } else {
                    donacionVencimientoFecha.setVisibility(View.GONE);
                }
            }

        });


        };



    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnRegistrarDonacion:
                ItemDonacion e = new ItemDonacion(donacionVencimientoFecha.getText().toString(),donacionDestino.getText().toString(),donacionDescripcion.getText().toString());
                if (!donacionVencimientoFecha.getText().toString().equals("")){
                    e.setFechaVencimiento(parsearFecha(donacionVencimientoFecha.getText().toString()));
                }
                break;
            default:
                break;
        }
    }

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

}