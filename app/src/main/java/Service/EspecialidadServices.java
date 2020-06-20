package Service;

import java.util.List;

import DonArDato.EspecialidadDTO;
import retrofit2.Call;
import retrofit2.http.GET;

public interface EspecialidadServices {

    //Get voluntario basico
    String API_ROUTE_BASICO= "api/especialidad/";
    @GET(API_ROUTE_BASICO)
    Call<List<EspecialidadDTO>> getEspecialidades();

}
