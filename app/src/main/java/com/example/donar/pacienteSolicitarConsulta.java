package com.example.donar;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import Negocio.Paciente;
import DonArDato.PacienteDTO;

public class pacienteSolicitarConsulta extends DonArToolBar implements View.OnClickListener{//AppCompatActivity{

    private TextView nombre;
    private TextView apellido;
    private TextView detalle;
    private TextView id;
    private TextView usuario;
    private Button solicitar;
    //Button login;
    //Button registrar;

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
        usuario = (TextView) findViewById(R.id.txtUsuario);
        //login = (Button) findViewById(R.id.btnLogin);//.setOnClickListener(this);
        //registrar = (Button) findViewById(R.id.btnRegistro);

        dataChangeToolbar("Pacientes -  Consulta", usuario.getText().toString(),
                id.getText().toString(), false);
        solicitar.setOnClickListener(this);
        //login.setOnClickListener(this);
        //registrar.setOnClickListener(this);
        loadData();
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
    @SuppressLint("SetTextI18n")
    @Override
    public void dataChangeToolbar(String titulo, String usuario, String idUsuario, boolean verBotones) {
        super.dataChangeToolbar(titulo, usuario, idUsuario, verBotones);
    }


    private void guardar(){
        //Acá va el SAVE
    }
}
