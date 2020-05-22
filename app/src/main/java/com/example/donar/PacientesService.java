package com.example.donar;

import java.util.List;

import DonArDato.PacienteDTO;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PacientesService {

    /**
     * Definicion de ruta para GET (Read all)
     */
    String API_ROUTE= "app/api/read/{id}";

    /**
     * Metodo abstracto para utilizar HTTP.GET
     * @return
     */
    @GET(API_ROUTE)
    Call<PacienteDTO> getPaciente(@Path("id") String id);

}
