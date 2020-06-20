package DonArDato;

public class ReporteGeneroDTO {

    private Integer cantMasculinos;
    private Integer cantFemeninos;
    private Integer cantOtros;

    public ReporteGeneroDTO(Integer cantMasculinos, Integer cantFemeninos, Integer cantOtros) {
        this.cantMasculinos = cantMasculinos;
        this.cantFemeninos = cantFemeninos;
        this.cantOtros = cantOtros;
    }

}
