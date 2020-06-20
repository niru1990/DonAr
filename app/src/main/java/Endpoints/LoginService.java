package Endpoints;

import DonArDato.ResponseData;
import DonArDato.actualizaIG;
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
    String API_ROUTE_SAVE_ITEM = "api/Login/";
    @Headers("Content-Type: application/json")
    @PUT(API_ROUTE_SAVE_ITEM)
    Call<Login> updateData(@Body actualizaIG aic);

    //Get
    String API_ROUTE_BASICO= "api/Login/{correo}";
    @GET(API_ROUTE_BASICO)
    Call<ResponseData> checkCorreo(@Path("correo") String correo);
}
