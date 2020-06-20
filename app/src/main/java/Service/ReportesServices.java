package Service;

import DonArDato.ReporteVoluntariosTipo;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ReportesServices {

    //Get voluntario basico
    String API_ROUTE_BASICO= "api//reportes/voluntarios";
    @GET(API_ROUTE_BASICO)
    Call<ReporteVoluntariosTipo> getVoluntariosPorTipo();

}