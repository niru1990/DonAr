package Service;

import java.util.List;

import DonArDato.ReporteVoluntariosTipo;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ReportesServices {

    //Get voluntario basico
    String API_ROUTE_BASICO= "api//reportes/voluntarios";
    @GET(API_ROUTE_BASICO)
    Call<ReporteVoluntariosTipo> getVoluntariosPorTipo();

    //Get voluntario basico
    String API_ROUTE_RH= "api//reportes/rangoetario";
    @GET(API_ROUTE_RH)
    Call<List<Integer>> getRangosHetarios();

}