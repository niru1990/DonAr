package com.example.donar;

import java.util.List;

import DonArDato.PacienteDTO;
import Negocio.Paciente;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface PacientesService {


    //Get paciente
    String API_ROUTE= "https://donar.azurewebsites.net/api/paciente/read/{id}";
    @GET(API_ROUTE)
    Call<PacienteDTO> getPaciente(@Path("id") String id);


    //Registrar paciente
    String API_ROUTE_ADD_ITEM = "https://donar.azurewebsites.net/api/paciente/registrarPaciente";
    @POST(API_ROUTE_ADD_ITEM)
    Call<Void> addPaciente(@Body PacienteDTO paciente);
}
