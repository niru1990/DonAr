package com.example.donar;

import DonArDato.PacienteDTO;
import DonArDato.VoluntarioDTO;
import DonArDato.VoluntarioMedicoDTO;
import Negocio.Paciente;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface VoluntariosService {

    //Get voluntario basico
    String API_ROUTE_BASICO= "api/evento/getVoluntarioBasico/{id}";
    @GET(API_ROUTE_BASICO)
    Call<PacienteDTO> getVoluntarioBasico(@Path("id") String id);


    //Registrar voluntario basico
    String API_ROUTE_ADD_ITEM_BASICO = "api/evento/registrarVoluntarioBasico";
    @POST(API_ROUTE_ADD_ITEM_BASICO)
    Call<Void> addVoluntarioBasico(@Body VoluntarioDTO voluntarioBasico);

    //Get voluntario medico
    String API_ROUTE_MEDICO= "api/evento/getVoluntarioMedico/{id}";
    @GET(API_ROUTE_MEDICO)
    Call<PacienteDTO> getVoluntarioMedico(@Path("id") String id);


    //Registrar voluntario medico
    String API_ROUTE_ADD_ITEM_MEDICO = "api/evento/registrarVoluntarioMedico";
    @POST(API_ROUTE_ADD_ITEM_MEDICO)
    Call<Void> addVoluntarioMedico(@Body VoluntarioMedicoDTO voluntarioMedico);

}
