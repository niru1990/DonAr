package Negocio;

import android.util.Log;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.Toast;

import com.example.donar.VoluntariosService;

import java.util.ArrayList;
import java.util.List;

import DonArDato.VoluntarioDTO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Voluntario {
    /*
     * Testeo de la llamada a la api
    public ArrayList<VoluntarioDTO> voluntarios = new ArrayList<>();
    ListAdapter adaptador;
    public void obtenerListadoVoluntarios() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://donar.azurewebsites.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        VoluntariosService voluntariosService =retrofit.create(VoluntariosService.class);

        Call<List<VoluntarioDTO>> http_call = voluntariosService.getVoluntario();
        http_call.enqueue(new Callback<List<VoluntarioDTO>>() {
            @Override
            public void onResponse(Call<List<VoluntarioDTO>> call, Response<List<VoluntarioDTO>> response) {
                voluntarios.clear();
                if(response.code() == 200) {
                    if(response.body()!=null) {
                        for (VoluntarioDTO v : response.body()) {
                            voluntarios.add(v);
                        }

                        ((BaseAdapter) adaptador).notifyDataSetChanged();
                    }
                }
                else
                {
                    //Toast.makeText(this., "", Toast.LENGTH_SHORT).show();
                    //throw new Exception("El codigo de respuesta fue : " +  response.code().ToString());
                }
            }

            @Override
            public void onFailure(Call<List<VoluntarioDTO>> call, Throwable t) {
                Log.e("CALL API FAIL", "Hubo un problema al llamar a la API.");
            }
        });

    }
     */

}
