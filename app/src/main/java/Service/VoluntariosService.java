package Service;

import DonArDato.PacienteDTO;
import DonArDato.VoluntarioDTO;
import DonArDato.VoluntarioMedicoDTO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface VoluntariosService {

    //Get voluntario basico
    String API_ROUTE_BASICO= "api/voluntariobasico/{id}";
    @GET(API_ROUTE_BASICO)
    Call<PacienteDTO> getVoluntarioBasico(@Path("id") String id);


    //Registrar voluntario basico
    String API_ROUTE_ADD_ITEM_BASICO = "api/voluntariobasico";
    @POST(API_ROUTE_ADD_ITEM_BASICO)
    Call<Integer> addVoluntarioBasico(@Body VoluntarioDTO voluntarioBasico);

    //Get voluntario medico
    String API_ROUTE_MEDICO= "api/voluntariomedico/{id}";
    @GET(API_ROUTE_MEDICO)
    Call<PacienteDTO> getVoluntarioMedico(@Path("id") String id);


    //Registrar voluntario medico
    String API_ROUTE_ADD_ITEM_MEDICO = "api/voluntariomedico";
    @POST(API_ROUTE_ADD_ITEM_MEDICO)
    Call<Integer> addVoluntarioMedico(@Body VoluntarioMedicoDTO voluntarioMedico);

}
