package com.example.donar;

import java.util.List;

import DonArDato.TipoDeUsuarioDTO;
import retrofit2.Call;
import retrofit2.http.GET;

public interface UsuarioService {

    //Get voluntario basico
    String API_ROUTE_BASICO= "api/tipousuario/";
    @GET(API_ROUTE_BASICO)
    Call<List<TipoDeUsuarioDTO>> getTiposDeUsuario();

}
