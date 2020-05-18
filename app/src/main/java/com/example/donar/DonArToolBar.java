package com.example.donar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class DonArToolBar extends AppCompatActivity {

    public TextView titulo;
    public TextView nombreUsuario;
    private Button registrar;
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_don_ar_tool_bar);

        titulo = (TextView) findViewById(R.id.txtAplicacionYModulo);
        nombreUsuario = (TextView) findViewById(R.id.txtUsuario);

    }
}
