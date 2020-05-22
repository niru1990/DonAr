package com.example.donar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class pacientesMain extends DonArToolBar implements View.OnClickListener {

    private ImageButton historiaClinica;
    private ImageButton asignarEspecialidad;
    private ImageButton solicitarConsulta;

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
    }


    @Override
    public void onClick(View v) {
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
}
