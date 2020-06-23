package Service;

import java.util.List;

import DonArDato.DonacionDTO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface DonacionesService {

    //Get donaciones
    String API_ROUTE_GET_ALL = "api/donacion/";
    @GET (API_ROUTE_GET_ALL)
    Call<List<DonacionDTO>>getDonaciones();

    //Get historial donacion
    String API_ROUTE_GET_HISTORY="api/donacion/historicodonacion/{id}";
    @GET (API_ROUTE_GET_HISTORY)
    Call<List<DonacionDTO>>getHistoricoDonacion (@Path("id") String id);

    //actualizar donacion
    String API_ROUTE_UPDATE_ITEM= "api/donacion/";
    @PUT(API_ROUTE_UPDATE_ITEM)
    Call<DonacionDTO> updateDonacion(@Body DonacionDTO donacionDTO);

    //Registrar donacion
    String API_ROUTE_ADD_ITEM = "api/donacion";
    @POST(API_ROUTE_ADD_ITEM)
    Call<Integer> addDonacion(@Body DonacionDTO donacionDTO);

    //Get donacion con id
    String API_ROUTE_GET_WITH_ID= "api/donacion/{id}";
    @GET(API_ROUTE_GET_WITH_ID)
    Call<DonacionDTO> getDonacionID(@Path("id") String id);

    //Get donaciones de usuario
    String API_ROUTE_GET_FROM_ID= "api/donacion/donacionesporusuario/{id}";
    @GET(API_ROUTE_GET_FROM_ID)
    Call<List<DonacionDTO>>getDonacionesUsuario(@Path("id") String id);

}


