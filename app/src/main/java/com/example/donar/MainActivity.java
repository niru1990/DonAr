package com.example.donar;

//import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
//import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton donaciones;
    private ImageButton voluntarios;
    private ImageButton pacientes;
    private ImageButton reportes;
    boolean active;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        configView();
    }

    private void configView() {
        donaciones = (ImageButton) findViewById(R.id.imbDonaciones);
        voluntarios = (ImageButton) findViewById(R.id.imbVoluntarios);
        pacientes = (ImageButton) findViewById(R.id.imbPacientes);
        reportes = (ImageButton) findViewById(R.id.imbReportes);

        donaciones.setOnClickListener(this);
        voluntarios.setOnClickListener(this);
        pacientes.setOnClickListener(this);
        reportes.setOnClickListener(this);

        //active = (verificarConexion() && id.getText().toString().compareTo(" ") != 0);
        active = true; //SOLO PRUEBA

        if (active) {
            donaciones.setImageResource(R.mipmap.boton_donaciones);
            voluntarios.setImageResource(R.mipmap.boton_voluntarios);
            pacientes.setImageResource(R.mipmap.boton_pacientes);
            reportes.setImageResource(R.mipmap.boton_reportes);
        }
        else {
            donaciones.setImageResource(R.mipmap.boton_donaciones_gris);
            voluntarios.setImageResource(R.mipmap.boton_voluntarios_gris);
            pacientes.setImageResource(R.mipmap.boton_pacientes_gris);
            reportes.setImageResource(R.mipmap.boton_reportes_gris);
        }

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

    @SuppressLint("SetTextI18n")
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

    private boolean verificarConexion() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        return (networkInfo != null && networkInfo.isConnected());
    }

    @Override
    public void onClick(@NotNull View v) {
        Log.i("ID toolbar", String.valueOf(v.getId()));
        //super.onClick(v);
        Intent intent;

        if(active){
            switch(v.getId())
            {
                case R.id.imbDonaciones:
                    intent = new Intent(v.getContext(), pacienteSolicitarConsulta.class); //prueba
                    break;
                case R.id.imbPacientes:
                    intent = new Intent(v.getContext(), pacientesMain.class);//prueba
                    break;
                case R.id.imbReportes:
                    intent = new Intent(v.getContext(), pacienteSolicitarConsulta.class);//prueba
                    break;
                case R.id.imbVoluntarios:
                    intent = new Intent(v.getContext(), voluntariosAutoMach.class);//prueba
                    break;
                default:
                    intent = new Intent(v.getContext(), registroGeneral.class);//prueba
                    break;
            }
        }
        else
            intent = new Intent(v.getContext(), registroGeneral.class);
        startActivity(intent);
    }
}
