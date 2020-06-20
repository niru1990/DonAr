package Endpoints;

import java.util.List;

import DonArDato.ProvinciaDTO;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ProvinciaService {

    //Get voluntario basico
    String API_ROUTE_BASICO= "api/provincia/";
    @GET(API_ROUTE_BASICO)
    Call<List<ProvinciaDTO>> getProvincias();

}
