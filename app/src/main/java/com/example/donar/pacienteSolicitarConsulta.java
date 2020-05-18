package com.example.donar;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

public class pacienteSolicitarConsulta extends DonArToolBar implements IToolBar,View.OnClickListener{//AppCompatActivity{

    TextView nombre;
    TextView apellido;
    TextView detalle;
    TextView id;
    Button solicitar;
    //Button login;
    //Button registrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paciente_solicitar_consulta);
        nombre = (TextView) findViewById(R.id.txtNombre);
        apellido = (TextView) findViewById(R.id.txtApellido);
        detalle = (TextView) findViewById(R.id.medtSintomasYMedicamentos);
        id = (TextView) findViewById(R.id.txtIdSolicitud);
        solicitar = (Button) findViewById(R.id.btnSolciitar);
        //login = (Button) findViewById(R.id.btnLogin);//.setOnClickListener(this);
        //registrar = (Button) findViewById(R.id.btnRegistro);

        dataChangeToolbar("Pacientes -  Consulta", "NICO", "0", false);
        solicitar.setOnClickListener(this);
        //login.setOnClickListener(this);
        //registrar.setOnClickListener(this);
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

    @SuppressLint("SetTextI18n")
    @Override
    public void dataChangeToolbar(String titulo, String usuario, String idUsuario, boolean verBotones) {
        super.dataChangeToolbar(titulo, usuario, idUsuario, verBotones);
    }

    private void guardar(){
        //Acá va el SAVE
    }
}
