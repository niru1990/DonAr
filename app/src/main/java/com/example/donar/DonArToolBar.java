package com.example.donar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

public class DonArToolBar extends AppCompatActivity implements IToolBar{

    public TextView titulo;
    public TextView nombreUsuario;
    public TextView idUser;
    public Button registrar;
    public Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_don_ar_tool_bar);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.donArToolBar);
        setSupportActionBar(myToolbar);
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
    public void dataChangeToolbar(String textoTitulo, String usuario, String idUsuario, boolean verBotones) {
        titulo.setText("DonAr - " + textoTitulo);
        nombreUsuario.setText(usuario);
        idUser.setText(idUsuario);
        idUser.setVisibility(View.INVISIBLE);
        mostrarBotones(verBotones);
    }

    @Override
    public void mostrarBotones(boolean mostrar) {
        if(mostrar)
        {
            registrar.setVisibility(View.VISIBLE);
            login.setVisibility(View.VISIBLE);
        }
        else
        {
            registrar.setVisibility(View.INVISIBLE);
            login.setVisibility(View.INVISIBLE);
        }
    }





}
