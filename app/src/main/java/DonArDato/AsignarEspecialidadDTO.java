package DonArDato;

import java.math.BigInteger;

public class AsignarEspecialidadDTO {

    private BigInteger eventoId;
    private Integer especialidadId;
    public AsignarEspecialidadDTO(BigInteger id, Integer especialidad){
        this.eventoId = id;
        this.especialidadId = especialidad;
    }
    public BigInteger getEventoId(){return this.eventoId;}
    public Integer getEspecialidadId(){return this.especialidadId;}
}
