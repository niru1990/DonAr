package com.example.donar;

import Negocio.Login;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
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


}
