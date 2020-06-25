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

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import Adapters.SpinnerAdaptor;
import DonArDato.EspecialidadDTO;
import DonArDato.SpinnerItem;
import DonArDato.VoluntarioMedicoDTO;
import Negocio.Preferencias;
import Service.EspecialidadServices;
import Service.VoluntariosService;
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
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_voluntario_medico);

        configView();

    }

    private void configView() {
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this,R.id.edtMatricula, RegexTemplate.NOT_EMPTY,R.string.matricula_invalida);
        awesomeValidation.addValidation(this,R.id.edtSeguro, RegexTemplate.NOT_EMPTY,R.string.seguro_invalido);

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
//
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
    public void onClick(@NotNull View v) {
        Intent intent;
            switch (v.getId()) {
                case R.id.btnRegistrarMedico:
                    if(awesomeValidation.validate() && validarCampos()) {

                        Preferencias p = new Preferencias(getSharedPreferences("Datos generales medico",
                                Context.MODE_PRIVATE));

                        p.obtenerPreferencia("nombre", "No posee nombre");
                    //Cargo campos del formulario anterior, guardados en el XML, mediante Shared
                    //Preferences
                    String nombre = p.obtenerPreferencia("nombre", "No posee nombre");
                    String apellido = p.obtenerPreferencia("apellido", "No posee apellido");
                    String genero = p.obtenerPreferencia("genero","No posee genero");
                    String email = p.obtenerPreferencia("email", "No posee email");
                    String edad = p.obtenerPreferencia("edad","-1");
                    String DNI =p.obtenerPreferencia("DNI", "No posee DNI");
                    String telefono =p.obtenerPreferencia("telefono", "No posee teléfono");
                    String pais = p.obtenerPreferencia("pais","-1");
                    String provincia = p.obtenerPreferencia("provincia","0");

                    String horaIngreso = "";
                    horaIngreso = textoHorarioIngreso.getText().toString();
                    horaIngreso = ajustarFecha(horaIngreso);
                    String horaSalida = "";
                    horaSalida = textoHorarioIngreso.getText().toString();
                    horaSalida = ajustarFecha(horaSalida);


                    VoluntarioMedicoDTO voluntarioMedico = new VoluntarioMedicoDTO(
                            null,
                            nombre,
                            apellido,
                            3,
                            Integer.valueOf(genero),
                            Integer.valueOf(DNI),
                            email,
                            telefono,
                            Integer.valueOf(edad),
                            Integer.valueOf(pais),
                            Integer.valueOf(provincia),
                            Integer.valueOf(idEspecialidad),
                            campoMatricula.getText().toString(),
                            campoSeguro.getText().toString(),
                            horaIngreso,
                            horaSalida
                    );

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("https://donar.azurewebsites.net/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    VoluntariosService voluntariosService = retrofit.create(VoluntariosService.class);
                    Call<Integer> http_call = voluntariosService.addVoluntarioMedico(voluntarioMedico);

                    http_call.enqueue(new Callback<Integer>() {
                        @Override
                        public void onResponse(Call<Integer> call, Response<Integer> response) {
                            Toast.makeText(getApplicationContext(),
                                    "¡Te has registrado exitosamente!",
                                    Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(intent);
                        }

                        @Override
                        public void onFailure(Call<Integer> call, Throwable t) {
                            Log.i("HTTP ERROR", t.getMessage());
                            Toast.makeText(getApplicationContext(),
                                    "Algo malo ocurrio! muy malo!! y tu tienes la culpa!",
                                    Toast.LENGTH_LONG).show();
                        }
                    });

                    intent = new Intent(v.getContext(), LoginActivity.class);
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

    private boolean validarCampos() {
        String horaIngresoString = textoHorarioIngreso.getText().toString().
                replace(":","");
        String horaSalidaString = textoHorarioSalida.getText().toString().
                replace(":","");

        if(!campoMatricula.getText().toString().substring(0,2).equals("MP") &&
                !campoMatricula.getText().toString().substring(0,2).equals("MN") ){
            Toast.makeText(getApplicationContext(), R.string.matricula_iniciales_invalidas,
                    Toast.LENGTH_SHORT).show();
            return false;
        }

//
        if(campoMatricula.getText().toString().length() != 8 ){
            Toast.makeText(getApplicationContext(), R.string.matricula_cantidad_caracteres_invalidos,
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        if(!horaIngresoString.matches(".*\\d.*")){
            Toast.makeText(getApplicationContext(), R.string.horario_ingreso_vacio,
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        if(!horaSalidaString.matches(".*\\d.*")){
            Toast.makeText(getApplicationContext(), R.string.horario_salida_vacio,
                    Toast.LENGTH_SHORT).show();
            return false;
        }

         int horaIngreso = Integer.parseInt(ajustarFecha(textoHorarioIngreso.getText().toString()).
                 replace(":",""));
         int horaSalida = Integer.parseInt(ajustarFecha(textoHorarioSalida.getText().toString()).
                 replace(":",""));

         if(horaIngreso>horaSalida){
             Toast.makeText(getApplicationContext(), R.string.horarios_incoherentes,
                     Toast.LENGTH_SHORT).show();
             return false;
         }

         if(horaIngreso<90000 || horaSalida<90000 ||  horaIngreso>190000 || horaSalida>190000){
             Toast.makeText(getApplicationContext(), R.string.horarios_fuera_rango,
                     Toast.LENGTH_SHORT).show();
             return false;
         }

        if(idEspecialidad.equals("0")){
            Toast.makeText(getApplicationContext(), R.string.especialidad_invalida,
                    Toast.LENGTH_SHORT).show();
            return false;
        }

         return true;
    }


    public void setHorarioIngreso(){
        Calendar calendar = Calendar.getInstance();
        int HORA = calendar.get(Calendar.HOUR);
        int MINUTO = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    String stringTiempo =   hourOfDay + ":" + minute + "hs.";
                    textoHorarioIngreso.setText(stringTiempo);
            }
        }, HORA, MINUTO, false);
        timePickerDialog.show();
    }//

    public void setHorarioSalida(){
        Calendar calendar = Calendar.getInstance();
        int HORA = calendar.get(Calendar.HOUR);
        int MINUTO = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String stringTiempo =  hourOfDay + ":" + minute + "hs.";
                textoHorarioSalida.setText(stringTiempo);
            }
        }, HORA, MINUTO, false);
        timePickerDialog.show();
    }

    //Doy formato a la hora para que no pinche el automach
    @NotNull
    private String ajustarFecha(@NotNull String fecha) {
        String finale = "";
        String[] a= fecha.split(":");
        String horas = "";
        String minutos = "";
        String segundos = "00";

        if(a[0].length() != 2)
            horas = a[0] = "0" + a[0];
        else
            horas = a[0];

        if(a[1].length() > 2)
        {
            minutos = a[1].substring(0, a[1].length()-3);
            if(minutos.length() != 2)
                minutos = "0" + minutos;
        }
        else
        {
            if(a[1].length() == 1)
                minutos = "0" + minutos;
        }

        finale = horas + ":" +  minutos + ":" + segundos;
         return finale;
    }

}
