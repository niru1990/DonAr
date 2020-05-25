package com.example.donar;

import java.util.List;

import DonArDato.EspecialidadDTO;
import DonArDato.PacienteDTO;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface EspecialidadServices {

    //Get voluntario basico
    String API_ROUTE_BASICO= "api/especialidad/";
    @GET(API_ROUTE_BASICO)
    Call<List<EspecialidadDTO>> getEspecialidades();
}
