package com.example.donar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;

import org.jetbrains.annotations.NotNull;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton donaciones;
    private ImageButton voluntarios;
    private ImageButton pacientes;
    private ImageButton reportes;
    private Toolbar toolbar;
    private boolean active;

    private String tipoUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        obtenerPreferencias();
        configView();
    }

    private void configView() {
        try {
            if (verificarConexion()) {
                donaciones = (ImageButton) findViewById(R.id.imbDonaciones);
                voluntarios = (ImageButton) findViewById(R.id.imbVoluntarios);
                pacientes = (ImageButton) findViewById(R.id.imbPacientes);
                reportes = (ImageButton) findViewById(R.id.imbReportes);

                donaciones.setOnClickListener(this);
                voluntarios.setOnClickListener(this);
                pacientes.setOnClickListener(this);
                reportes.setOnClickListener(this);
                //active = true;
                active = isSignedIn();

                if (active) {
                    donaciones.setImageResource(R.mipmap.boton_donaciones);
                    pacientes.setImageResource(R.mipmap.boton_pacientes);

                    if(tipoUsuario.equals("2") || tipoUsuario.equals("3"))
                        voluntarios.setImageResource(R.mipmap.boton_voluntarios);
                    //if(tipoUsuario.equals("4"))
                        reportes.setImageResource(R.mipmap.boton_reportes);

                } else {
                    donaciones.setImageResource(R.mipmap.boton_donaciones_gris);
                    voluntarios.setImageResource(R.mipmap.boton_voluntarios_gris);
                    pacientes.setImageResource(R.mipmap.boton_pacientes_gris);
                    reportes.setImageResource(R.mipmap.boton_reportes_gris);

                }
                toolbar = (Toolbar) findViewById(R.id.donArToolBar);
                setSupportActionBar(toolbar);
            } else {
                Intent intent = new Intent(this.getApplicationContext(), sinConexionInternet.class);
            }
        }
        catch (Exception ex)
        {
            Log.i("Main error", ex.getMessage());
            finish();
        }
    }

    /**
     * Aqui creamos las opciones de Menu con el toolbar.
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if(isSignedIn()) {
            getMenuInflater().inflate(R.menu.toolbar_menu2, menu);
        }else{
            getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        }
        return true;
    }

    /**
     * Aqui tenemos las opciones para cada item del menu.
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(@NotNull MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.action_login_oculto:
                intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_registro_oculto:
                intent = new Intent(getApplicationContext(), registroGeneral.class);
                startActivity(intent);
                return true;
            case R.id.action_cerrarSesion:
                signOut();
                return true;
            case R.id.action_cerrarSesion_OUT:
                signOutAndOut();
            default:
                return super.onOptionsItemSelected(item);//Aqui la accion del usuario no fue reconocida
        }
    }

    private boolean verificarConexion() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        return (networkInfo != null && networkInfo.isConnected());
    }

    private boolean isSignedIn() {
        return GoogleSignIn.getLastSignedInAccount(this) != null;
    }

    private void signOut() {
        GoogleSignInOptions gso = new GoogleSignInOptions.
                Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).
                build();

        GoogleSignInClient googleSignInClient=GoogleSignIn.getClient(this,gso);
        googleSignInClient.signOut();
        finish();
        startActivity(getIntent());
    }

    private void signOutAndOut() {
        GoogleSignInOptions gso = new GoogleSignInOptions.
                Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).
                build();

        GoogleSignInClient googleSignInClient=GoogleSignIn.getClient(this,gso);
        googleSignInClient.signOut();
        //startActivity(getIntent());
        Salida();
    }

    @SuppressLint("NewApi")
    public void Salida() {
        finishAffinity();
        finish();
    }

    @Override
    public void onClick(@NotNull View v) {
        Intent intent;

        if(active){
            switch(v.getId())
            {
                case R.id.imbDonaciones:
                        intent = new Intent(v.getContext(), donacionMain.class); //prueba
                    break;
                case R.id.imbPacientes:
                    if(tipoUsuario.equals("1"))
                        intent = new Intent(v.getContext(), pacienteSolicitarConsulta.class);
                    else
                        intent = new Intent(v.getContext(), historial_de_consultas.class);
                    break;
                case R.id.imbReportes:
                    intent = new Intent(v.getContext(), reportesMain.class);
                    /*
                    if(tipoUsuario.equals("4"))
                        intent = new Intent(v.getContext(), reportesMain.class);
                    else {
                        Toast.makeText(getApplicationContext(),
                                R.string.NoTieneAcceso,
                                Toast.LENGTH_SHORT).show();
                        intent = new Intent(v.getContext(), MainActivity.class);
                    }
                     */
                    break;
                case R.id.imbVoluntarios:
                    if(tipoUsuario.equals("2") || tipoUsuario.equals("3"))
                        intent = new Intent(v.getContext(), voluntariosAutoMach.class);
                    else {
                        Toast.makeText(getApplicationContext(),
                                R.string.NoTieneAcceso,
                                Toast.LENGTH_SHORT).show();
                        intent = new Intent(v.getContext(), MainActivity.class);
                    }
                    break;
                default:
                    intent = new Intent(v.getContext(), registroGeneral.class);
                    break;
            }
        }
        else
            intent = new Intent(v.getContext(), registroGeneral.class);
        startActivity(intent);

    }

    private void obtenerPreferencias(){
        SharedPreferences preferencias = getSharedPreferences
                ("ID usuario", Context.MODE_PRIVATE);
        tipoUsuario = preferencias.getString("tipo", "0");
    }
}
