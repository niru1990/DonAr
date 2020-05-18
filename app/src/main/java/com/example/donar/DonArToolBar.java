package com.example.donar;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

public class DonArToolBar extends AppCompatActivity implements IToolBar, View.OnClickListener{

    public TextView titulo;
    public TextView nombreUsuario;
    public TextView idUsuario;
    public Button registrar;
    public Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_don_ar_tool_bar);

        titulo = (TextView) findViewById(R.id.txtAplicacionYModulo);
        nombreUsuario = (TextView) findViewById(R.id.txtUsuario);
        idUsuario = (TextView) findViewById(R.id.txtId);

        registrar = (Button) findViewById(R.id.btnRegistro);
        login = (Button) findViewById(R.id.btnLogin);

        registrar.setOnClickListener(this);
        login.setOnClickListener(this);

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void dataChangeToolbar(String textoTitulo, String usuario, String idUsuario, boolean verBotones) {
        titulo.setText("DonAr - " + textoTitulo);
        nombreUsuario.setText(usuario);
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


    @Override
    public void onClick(@NotNull View v) {
        String s;
        switch (v.getId())
        {
            case R.id.btnRegistro:
                s = "Intento ingresar";
                break;
            case R.id.btnLogin:
                s= "Ingreso";
                break;
            default:
                break;
        }
    }
}
