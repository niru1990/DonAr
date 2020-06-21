package Service;

import DonArDato.ReporteGeneroDTO;
import DonArDato.ReporteVoluntariosTipo;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ReportesServices {

    //Get voluntario basico
    String API_ROUTE_BASICO_VOLUNTARIOS= "api//reportes/voluntarios";
    @GET(API_ROUTE_BASICO_VOLUNTARIOS)
    Call<ReporteVoluntariosTipo> getVoluntariosPorTipo();

    //Get usuarios generos
    String API_ROUTE_BASICO_GENEROS= "api/reportes/generos";
    @GET(API_ROUTE_BASICO_GENEROS)
    Call<ReporteGeneroDTO> getUsuariosGeneros();

}