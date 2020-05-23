package com.example.donar;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.jetbrains.annotations.NotNull;

import Negocio.Paciente;
import DonArDato.PacienteDTO;

public class pacienteSolicitarConsulta extends AppCompatActivity implements View.OnClickListener{

    private TextView nombre;
    private TextView apellido;
    private TextView detalle;
    private TextView id;
    private Button solicitar;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paciente_solicitar_consulta);
        configView();
    }

    private void configView(){
        nombre = (TextView) findViewById(R.id.txtNombre);
        apellido = (TextView) findViewById(R.id.txtApellido);
        detalle = (TextView) findViewById(R.id.medtSintomasYMedicamentos);
        id = (TextView) findViewById(R.id.txtIdSolicitud);
        solicitar = (Button) findViewById(R.id.btnSolciitar);
        solicitar.setOnClickListener(this);
        loadData();
        toolbar = (Toolbar) findViewById(R.id.donArToolBar);
        setSupportActionBar(toolbar);
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
    public boolean onOptionsItemSelected(MenuItem item) {
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

    public void loadData(){
        Paciente p = new Paciente();
        p.getPaciente();
    }

    @Override
    public void onClick(@NotNull View v) {
        //super.onClick(v);
        switch (v.getId())
        {
            case R.id.btnSolciitar:
                guardar();
                break;
            default:
                break;
        }
    }


    private void guardar(){
        //Acá va el SAVE
    }
}
