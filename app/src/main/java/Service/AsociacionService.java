package Service;

import DonArDato.AsociacionDTO;
import DonArDato.PacienteDTO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface AsociacionService {
    //Registrar paciente
    String API_ROUTE_ADD_ITEM = "api/Asociacion/";
    @POST(API_ROUTE_ADD_ITEM)
    Call<Integer> addAsociacion(@Body AsociacionDTO paciente);

    //Get paciente
    String API_ROUTE_GET_ESPECIFIC= "api/Asociacion/{id}";
    @GET(API_ROUTE_GET_ESPECIFIC)
    Call<PacienteDTO> getAsociacionEspecifico(@Path("id") String id);

}
