package com.example.donar;

import java.math.BigInteger;
import java.util.List;

import DonArDato.EventoDTO;
import DonArDato.PacienteDTO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface EventoServices {

    //Registrar eventos
    String API_ROUTE_ADD_ITEM = "api/evento/";
    @POST(API_ROUTE_ADD_ITEM)
    Call<Void> addEvento(@Body EventoDTO evento);

    //Actualizar eventos
    String API_ROUTE_UPDATE= "api/update/{id}";
    @PUT(API_ROUTE_UPDATE)
    @FormUrlEncoded
    Call<Void> updateEvento(@Path("evento") BigInteger id,
                          @Field("idEspecialidad") Integer idEspecialidad);

    //Obtener evento por id
    String API_ROUTE_GET_BY_ID = "api/evento/{id}";
    @GET(API_ROUTE_GET_BY_ID)
    Call<EventoDTO> getEventoById(@Path("id") String id);


    //Obtener evento por id de voluntario
    String API_ROUTE_GET_BY_VoluntarioID = "api/evento/{id}";
    @GET(API_ROUTE_GET_BY_VoluntarioID)
    Call<List<EventoDTO>> getEventoByVoluntarioId(@Path("id") String id);

    //Obtener eventos
    String API_ROUT_GET_EVENTS = "api/evento";
    @GET(API_ROUT_GET_EVENTS)
    Call<EventoDTO> getEventos();
}