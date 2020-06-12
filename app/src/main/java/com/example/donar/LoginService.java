package com.example.donar;

import Negocio.Login;

import DonArDato.actualizaIG;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface LoginService {

    //Editar
    String API_ROUTE_SAVE_ITEM = "/api/Login/{correo}";
    @FormUrlEncoded
    @PUT(API_ROUTE_SAVE_ITEM)
    Call<Login> updateIDGoogle(
            @Path("correo") String correo,
            @Field("IdGoogle") String IdGoogle
    );

    //Editar con Objeto
    String API_ROUTE_UPDATE_DATA = "api/Login/";
    @Headers("Content-Type: application/json")
    @PUT(API_ROUTE_UPDATE_DATA)
    Call<Login> updateData(@Body actualizaIG aic);

}
