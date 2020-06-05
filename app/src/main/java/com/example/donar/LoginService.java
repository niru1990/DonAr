package com.example.donar;

import Negocio.Login;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface LoginService {

    //Get
    String API_ROUTE_BASICO= "api/Login/{correo}";
    @GET(API_ROUTE_BASICO)
    Call<Login> checkCorreo(@Path("correo") String correo);


}
