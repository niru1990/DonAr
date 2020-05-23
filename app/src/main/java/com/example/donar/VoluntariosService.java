package com.example.donar;

import java.util.List;

import DonArDato.VoluntarioDTO;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface VoluntariosService {

    //Voluntarios basicos o voluntarios no medicos.
    /**
     * Definicion de ruta para GET (Read all)
     */
    String API_ROUTE_Voluntario= "api/voluntariobasico/{id}";

    /**
     * Metodo abstracto para utilizar HTTP.GET
     * @return PacienteDTO
     */
    @GET(API_ROUTE_Voluntario)
    Call<VoluntarioDTO> getVoluntario(@Path("id") String id);

    /**
     * Definicion de ruta para GET (Read all)
     */
    String API_ROUTE_Voluntarios= "api/voluntariobasico/";

    /**
     * Metodo abstracto para utilizar HTTP.GET
     * @return PacienteDTO
     */
    @GET(API_ROUTE_Voluntarios)
    Call<List<VoluntarioDTO>> getVoluntario();

    //Voluntarios medicos

    //Voluntarios Organizaciones
}
