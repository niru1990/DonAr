package Service;

import DonArDato.PacienteConsultaDTO;
import DonArDato.PacienteDTO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface PacientesService {

    //Registrar paciente
    String API_ROUTE_ADD_ITEM = "api/Paciente/";
    @POST(API_ROUTE_ADD_ITEM)
    Call<Integer> addPaciente(@Body PacienteDTO paciente);

    //Get paciente
    String API_ROUTE_GET_ESPECIFIC= "api/Paciente/{id}";
    @GET(API_ROUTE_GET_ESPECIFIC)
    Call<PacienteDTO> getPacienteEspecifico(@Path("id") String id);

    @GET(API_ROUTE_GET_ESPECIFIC)
    Call<PacienteConsultaDTO> getPacienteEspecifico2(@Path("id") String id);


}
