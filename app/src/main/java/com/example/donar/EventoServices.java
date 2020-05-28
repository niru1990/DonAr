package com.example.donar;

import java.math.BigInteger;

import DonArDato.EventoDTO;
import DonArDato.PacienteDTO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface EventoServices {

    //Registrar eventos
    String API_ROUTE_ADD_ITEM = "api/evento/";
    @POST(API_ROUTE_ADD_ITEM)
    Call<Void> addEvento(@Body EventoDTO evento);

    //Actualizar eventos
    String API_ROUTE_UPDATE= "app/api/update/{id}";
    @PUT(API_ROUTE_UPDATE)
    @FormUrlEncoded
    Call<Void> updateEvento(@Path("evento") BigInteger id,
                          @Field("idEspecialidad") Integer idEspecialidad);
}