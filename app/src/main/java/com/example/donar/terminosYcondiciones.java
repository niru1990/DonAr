package com.example.donar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.appcompat.app.AppCompatActivity;

public class terminosYcondiciones extends AppCompatActivity {

    private Button siguienteListo;
    private CheckBox TyCcheckbox;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terminos_y_condiciones);

        siguienteListo = findViewById(R.id.TyCsiguienteButton);
        TyCcheckbox = findViewById(R.id.checkBoxTerminosYcondiciones);

        //Si se aceptaron los términos y condiciones, aparece el boton para continuar con el
        //registro
        siguienteListo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),registroGeneral.class));
            }
        });

        TyCcheckbox.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(TyCcheckbox.isChecked()==true)
                    siguienteListo.setVisibility(View.VISIBLE);
                else
                    siguienteListo.setVisibility(View.INVISIBLE);
            }
        });



    }

}
