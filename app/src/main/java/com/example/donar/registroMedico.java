package com.example.donar;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import Adapters.SpinnerAdaptor;
import DonArDato.EspecialidadDTO;
import DonArDato.PaisDTO;
import DonArDato.SpinnerItem;
import DonArDato.VoluntarioMedicoDTO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class registroMedico extends AppCompatActivity implements View.OnClickListener{

    private AwesomeValidation awesomeValidation;
    private Spinner spinnerEspecialidades;
    private SpinnerAdaptor especialidadesAdaptor;
    private ArrayList<SpinnerItem> misEspecialidades = new ArrayList<>();
    private Button botonRegistrarse,botonIngreso, botonSalida;
    private String idEspecialidad;
    private TextView  textoHorarioIngreso, textoHorarioSalida;
    private EditText campoMatricula, campoSeguro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_voluntario_medico);

        configView();

    }

    private void configView() {
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        //awesomeValidation.addValidation(this,R.id.edtMatricula, RegexTemplate.NOT_EMPTY,R.string.matricula_invalida);
        //awesomeValidation.addValidation(this,R.id.edtSeguro, RegexTemplate.NOT_EMPTY,R.string.seguro_invalido);


        campoMatricula = findViewById(R.id.edtMatricula);
        campoSeguro = findViewById(R.id.edtSeguro);
        textoHorarioIngreso = findViewById(R.id.ingresoTextView);
        textoHorarioSalida = findViewById(R.id.salidaTextView);
        botonRegistrarse = findViewById(R.id.btnRegistrarMedico);
        botonIngreso = findViewById(R.id.btnHorarioIngreso);
        botonSalida = findViewById(R.id.btnHorarioSalida);
        botonRegistrarse.setOnClickListener(this);
        botonIngreso.setOnClickListener(this);
        botonSalida.setOnClickListener(this);



        cargarSpinnerEspecialidades();
    }

    private void cargarSpinnerEspecialidades() {
            spinnerEspecialidades = (Spinner) findViewById(R.id.spnEspecialidad);
            especialidadesAdaptor = new SpinnerAdaptor(registroMedico.this, misEspecialidades);
            spinnerEspecialidades.setAdapter(especialidadesAdaptor);

            spinnerEspecialidades.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentPais, View view, int positionPais, long id) {
                    SpinnerItem clickItemEspecialidades = (SpinnerItem) parentPais.getItemAtPosition(positionPais);
                    idEspecialidad = clickItemEspecialidades.getIdData();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    //NADA, ABSOLUTAMENTE NADA
                }
            });


            try {
                //Creo llamada
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://donar.azurewebsites.net/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                Call<List<EspecialidadDTO>> http_call = retrofit.create(EspecialidadServices.class).getEspecialidades();

                //Encolo llamda
                http_call.enqueue(new Callback<List<EspecialidadDTO>>() {
                    @SuppressLint("ResourceType")
                    @Override
                    public void onResponse(Call<List<EspecialidadDTO>> call, Response<List<EspecialidadDTO>> response) {
                        try {
                            //Trabajo con la respuesta
                            if (response.body() != null && response.code() == 200) {
                                //Cargo valor señuelo para saber si asigno lo que corresponde
                                misEspecialidades.add(new SpinnerItem("0", "Seleccione..." ));
                                //Cargo mi lista con valores de la tabla
                                for(EspecialidadDTO item: response.body()) {
                                    misEspecialidades.add(new SpinnerItem(item.getId(),
                                            item.getEspecialidad()));
                                }
                                //Aviso al adaptor que se actualizo la información
                                especialidadesAdaptor.notifyDataSetChanged();
                            }
                            else {
                                throw new Exception("Código de respuesta: " + response.code());
                            }
                        }
                        catch (Exception ex)
                        {
                            try {
                                throw new Exception(ex.getMessage());
                            }
                            catch (Exception e) {
                                e.printStackTrace();
                            }
                        }



                    }

                    @Override
                    public void onFailure(Call<List<EspecialidadDTO>> call, Throwable t) {
                        Toast.makeText(registroMedico.this,
                                "Ocurrió un error al intentar llamar a la API.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
            catch (Exception ex)
            {
                Toast.makeText(this,
                        "Por favor, verifique su conexión a internet.",
                        Toast.LENGTH_SHORT).show();
            }


    }

    @Override
    public void onClick(View v) {
        Intent intent;


            switch (v.getId()) {
                case R.id.btnRegistrarMedico:
                    if(awesomeValidation.validate()) {
                    SharedPreferences preferences = getSharedPreferences("Datos generales medico",
                            Context.MODE_PRIVATE);

                    //Cargo campos del formulario anterior, guardados en el XML, mediante Shared
                    //Preferences
                    String nombre = preferences.getString("nombre", "No posee nombre");
                    String apellido = preferences.getString("apellido", "No posee apellido");
                    String genero = preferences.getString("genero","No posee genero");
                    String email = preferences.getString("email", "No posee email");
                    String edad = preferences.getString("edad","No posee edad");
                    String DNI = preferences.getString("DNI", "No posee DNI");
                    String telefono = preferences.getString("telefono", "No posee teléfono");
                    String pais = preferences.getString("pais","No posee país");
                    String provincia = preferences.getString("provincia","No posee provincia");


                    VoluntarioMedicoDTO voluntarioMedico = new VoluntarioMedicoDTO(null, nombre,
                            apellido, 3, genero, Integer.valueOf(DNI), email,
                            telefono, Integer.valueOf(edad), Integer.valueOf(pais), Integer.valueOf(provincia),
                            Integer.valueOf(idEspecialidad), campoMatricula.getText().toString(),
                            campoSeguro.getText().toString(),
                            textoHorarioIngreso.getText().toString(),
                            textoHorarioSalida.getText().toString());

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("https://donar.azurewebsites.net/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    VoluntariosService voluntariosService = retrofit.create(VoluntariosService.class);
                    Call<Void> http_call = voluntariosService.addVoluntarioMedico(voluntarioMedico);

                    http_call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Log.i("HTTP ERROR", t.getMessage());
                        }
                    });

                    intent = new Intent(v.getContext(), MainActivity.class);
                    }else{
                        //Validacion NO exitosa :(
                        Toast.makeText(getApplicationContext(), "Uno o más campos incorrectos.", Toast.LENGTH_SHORT).show();
                    }
                    break;

                case R.id.btnHorarioIngreso:
                    setHorarioIngreso();

                    break;
                case R.id.btnHorarioSalida:
                    setHorarioSalida();

                    break;

                default:
                    intent = new Intent(v.getContext(), registroGeneral.class);
                    break;
            }

    }

    public void setHorarioIngreso(){
        Calendar calendar = Calendar.getInstance();
        int HORA = calendar.get(Calendar.HOUR);
        int MINUTO = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    String stringTiempo =  "Horario de Ingreso: "+ hourOfDay + ":" + minute + " hs.";
                    textoHorarioIngreso.setText(stringTiempo);
            }
        }, HORA, MINUTO, false);
        timePickerDialog.show();
    }

    public void setHorarioSalida(){
        Calendar calendar = Calendar.getInstance();
        int HORA = calendar.get(Calendar.HOUR);
        int MINUTO = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String stringTiempo =  "Horario de Salida: "+ hourOfDay + ":" + minute + " hs.";
                textoHorarioSalida.setText(stringTiempo);
            }
        }, HORA, MINUTO, false);
        timePickerDialog.show();
    }






}
