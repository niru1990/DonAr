package DonArDato;

import androidx.annotation.Nullable;

import java.math.BigInteger;

public class EventoDTO {

    private BigInteger id;
    private BigInteger pacienteId;
    private String fecha;
    private String sintomas;
    private boolean Seguimiento;
    @Nullable
    private BigInteger voluntarioMedicoId;
    private BigInteger voluntarioBasicoId;
    private Integer especialidadId;
    private Integer estado;



    public BigInteger getId() {
        return this.id;
    }
    public void setId(BigInteger value) {
        this.id = value;
    }
    public String getFecha(){return this.fecha;}
    public void setFecha(String value){this.fecha = value;}
    public BigInteger getPacienteId() {
        return this.pacienteId;
    }
    public void setPacienteId(BigInteger value) {
        this.pacienteId = value;
    }
    public String getSintomas(){return this.sintomas;}
    public void setSintomas(String sintomas){this.sintomas = sintomas;}
    public BigInteger getidVoluntario() {
        return this.voluntarioBasicoId;
    }
    public void setidVoluntario(@Nullable BigInteger value) {
        this.voluntarioBasicoId = value;
    }
    public BigInteger getidVoluntarioMedico() {
        return this.voluntarioMedicoId;
    }
    public void setidVoluntarioMedico(@Nullable BigInteger value) {this.voluntarioMedicoId = value;}
    public Integer getEspecialidadId() {
        return this.especialidadId;
    }
    public void setEspecialidadId(@Nullable Integer value) { this.especialidadId = value;}
    public void setSeguimiento(boolean value ){ this.Seguimiento = value;}

    public Integer getEstado(){return this.estado;}
    public void setEstado(Integer estado){this.estado = estado;}

}
