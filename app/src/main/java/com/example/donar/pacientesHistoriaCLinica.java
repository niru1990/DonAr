package com.example.donar;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

public class pacientesHistoriaCLinica extends DonArToolBar implements View.OnClickListener {

    private ImageButton agregar;
    private ImageButton cancelar;

    private TextView id;
    private TextView nombre;
    private TextView apellido;
    private EditText detalle;

    private ListView historial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pacientes_historia_c_linica);
        configView();
    }

    private void configView() {
        agregar = (ImageButton) findViewById(R.id.imbAgregar);
        cancelar= (ImageButton) findViewById(R.id.ibmCancelar);
        nombre = (TextView) findViewById(R.id.txtNombre);
        apellido = (TextView) findViewById(R.id.txtApellido);
        id = (TextView) findViewById(R.id.txtIdHistorial);
        detalle = (EditText) findViewById(R.id.edtDetalle);
        historial = (ListView) findViewById(R.id.lstHistorial);

        agregar.setOnClickListener(this);
        cancelar.setOnClickListener(this);

        dataChangeToolbar("Pacientes -  Consulta", "NICO", "0", false);
    }

    @Override
    public void onClick(@NotNull View v) {
        switch (v.getId())
        {
            case R.id.imbAgregar:
                break;
            case R.id.ibmCancelar:
                break;
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void dataChangeToolbar(String titulo, String usuario, String idUsuario, boolean verBotones) {
        super.dataChangeToolbar(titulo, usuario, idUsuario, verBotones);
    }
}
