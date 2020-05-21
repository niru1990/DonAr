package com.example.donar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

public class pacienteAsignarEspecialidad extends DonArToolBar implements View.OnClickListener {

    private TextView id;
    private TextView nombre;
    private TextView apellido;
    private TextView telefono;
    private EditText sintomas;
    private Spinner especialidad;
    private Button asignar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paciente_asignar_especialidad);
        configView();
    }

    private void configView()
    {
        id = (TextView) findViewById(R.id.txtIdConsulta);
        nombre = (TextView) findViewById(R.id.txtNombre);
        apellido = (TextView) findViewById(R.id.txtApellido);
        telefono = (TextView) findViewById(R.id.txtTelefono);
        sintomas = (EditText) findViewById(R.id.medtSintomasYMedicamentos);
        especialidad = (Spinner) findViewById(R.id.spnEspecialidad);
        asignar = (Button) findViewById(R.id.btnAsignar);
        asignar.setOnClickListener(this);
        cargarSpinner(especialidad);
    }

    @Override
    public void onClick(@NotNull View v) {
        //super.onClick(v);
        switch (v.getId())
        {
            case R.id.btnAsignar:
                actualizar();
                break;
            default:
                break;
        }
    }

    private void actualizar(){

    }

    private void cargarSpinner(Spinner spinner){

    }
}
