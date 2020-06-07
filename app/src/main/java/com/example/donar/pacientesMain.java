package com.example.donar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

public class pacientesMain extends DonArToolBar implements View.OnClickListener {

    private ImageButton historiaClinica;
    private ImageButton asignarEspecialidad;
    private ImageButton solicitarConsulta;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pacientes_main);
        configView();
    }

    private void configView(){
        asignarEspecialidad = (ImageButton) findViewById(R.id.imbAsignarEspecialidad);
        historiaClinica = (ImageButton) findViewById(R.id.imbHistoriaClinica);
        solicitarConsulta = (ImageButton) findViewById(R.id.imbSolicitarConsulta);
        asignarEspecialidad.setOnClickListener(this);
        solicitarConsulta.setOnClickListener(this);
        historiaClinica.setOnClickListener(this);

        toolbar = (Toolbar) findViewById(R.id.donArToolBar);
        setSupportActionBar(toolbar);
    }


    @Override
    public void onClick(@NotNull View v) {
        Intent intent;
            switch(v.getId())
            {
                case R.id.imbAsignarEspecialidad:
                    intent = new Intent(v.getContext(), pacienteAsignarEspecialidad.class);
                    break;
                case R.id.imbHistoriaClinica:
                    intent = new Intent(v.getContext(), pacientesHistoriaCLinica.class);
                    break;
                case R.id.imbSolicitarConsulta:
                    intent = new Intent(v.getContext(), pacienteSolicitarConsulta.class);
                    break;
                default:
                    intent = new Intent(v.getContext(), MainActivity.class);
                    break;
            }
        startActivity(intent);
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
}
