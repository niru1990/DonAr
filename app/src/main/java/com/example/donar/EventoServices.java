
package com.example.donar;

import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.common.reflect.Parameter;

import java.math.BigInteger;
import java.util.List;

import DonArDato.AsignarEspecialidadDTO;
import DonArDato.EventoDTO;
import DonArDato.EventoReducidoDTO;
import DonArDato.cambiarEstado;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface EventoServices {

    //Registrar eventos
    String API_ROUTE_ADD_ITEM = "api/evento/";
    @POST(API_ROUTE_ADD_ITEM)
    Call<Void> addEvento(@Body EventoDTO evento);

    //Actualizar eventos
    String API_ROUTE_UPDATE= "api/evento/asignarEspecialidad/";
    @Headers("Content-Type: application/json")
    @PUT(API_ROUTE_UPDATE)
    //@FormUrlEncoded
    Call<EventoDTO> updateEvento(@Body AsignarEspecialidadDTO actualizador);

    //Obtener evento por id de voluntario
    String API_ROUTE_GET_BY_VoluntarioID = "api/evento/eventosporvoluntario/{id}";
    @GET(API_ROUTE_GET_BY_VoluntarioID)
    Call<List<EventoDTO>> getEventoByVoluntarioId(@Path("id") String id);

    //Obtengo evento reducido
    String API_ROUTE_GET_EVENT_REDUCED_BY_ID = "api/evento/eventoDTO/{id}";
    @GET(API_ROUTE_GET_EVENT_REDUCED_BY_ID)
    Call<EventoReducidoDTO> getEventoReducidoById(@Path("id") String id);

    //Obtener evento por id
    String API_ROUTE_GET_BY_ID = "api/evento/{id}";
    @GET(API_ROUTE_GET_BY_ID)
    Call<EventoDTO> getEventoById(@Path("id") String id);

    //Aceptar eventos
    String API_ROUT_MODIFY_STATUS= "api/evento/modificarestado ";
    @Headers("Content-Type: application/json")
    @PUT(API_ROUT_MODIFY_STATUS)
    //@FormUrlEncoded
    Call<EventoDTO> modifyStatus (@Body cambiarEstado c);


}