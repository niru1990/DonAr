package Service;

import java.util.List;

import DonArDato.PaisDTO;
import retrofit2.Call;
import retrofit2.http.GET;

public interface PaisService {

    //Get voluntario basico
    String API_ROUTE_BASICO= "api/Nacionalidad/";
    @GET(API_ROUTE_BASICO)
    Call<List<PaisDTO>> getPaises();

}
