package com.example.donar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

//Negocio
import java.lang.reflect.Array;
import java.util.ArrayList;

import DonArDato.VoluntarioDTO;
import Negocio.Voluntario;

public class pacienteAsignarEspecialidad extends AppCompatActivity implements View.OnClickListener {

    private TextView id;
    private TextView nombre;
    private TextView apellido;
    private TextView telefono;
    private EditText sintomas;
    private Spinner especialidad;
    private Button asignar;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paciente_asignar_especialidad);
        configView();
    }

    private void configView() {
        id = (TextView) findViewById(R.id.txtIdConsulta);
        nombre = (TextView) findViewById(R.id.txtNombre);
        apellido = (TextView) findViewById(R.id.txtApellido);
        telefono = (TextView) findViewById(R.id.txtTelefono);
        sintomas = (EditText) findViewById(R.id.medtSintomasYMedicamentos);
        especialidad = (Spinner) findViewById(R.id.spnEspecialidad);
        asignar = (Button) findViewById(R.id.btnAsignar);
        asignar.setOnClickListener(this);
        cargarSpinner(especialidad);
        toolbar = (Toolbar) findViewById(R.id.donArToolBar);
        setSupportActionBar(toolbar);

        /**
         * Testeo de la llamada a la API
        Voluntario voluntario = new Voluntario();
        voluntario.obtenerListadoVoluntarios();
        */

    }

    /**
     * Aqui creamos las opciones de Menu con el toolbar.
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    /**
     * Aqui tenemos las opciones para cada item del menu.
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(@NotNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_login:
                Toast.makeText(this, "Hago click en boton login", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.action_registro:
                Toast.makeText(this, "Haglo click en el boton registro", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.action_login_oculto:
                Toast.makeText(this, "Hago click en boton login oculto", Toast.LENGTH_LONG).show();
                return true;

            case R.id.action_registro_oculto:
                Toast.makeText(this, "Haglo click en el boton registro oculto", Toast.LENGTH_LONG).show();
                return true;

            default:
                //Aqui la accion del usuario no fue reconocida
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(@NotNull View v) {
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
