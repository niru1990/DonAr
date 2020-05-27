package DonArDato;

import androidx.annotation.Nullable;

import java.math.BigInteger;

public class EventoDTO {

    private BigInteger id;
    private BigInteger PacienteId;
    //private String Fecha;
    private String Sintomas;
    private boolean Seguimiento;
    @Nullable
    private BigInteger VoluntarioMedicoId;
    private BigInteger VoluntarioBasicoId;
    private Integer EspecialidadId;

    public BigInteger getId() {
        return this.id;
    }
    public void setId(BigInteger value) {
        this.id = value;
    }
    //public String getFecha(){return this.Fecha;}
    //public void setFecha(String value){this.Fecha = value;}
    public BigInteger getPacienteId() {
        return this.PacienteId;
    }
    public void setPacienteId(BigInteger value) {
        this.PacienteId = value;
    }
    public String getSintomas(){return this.Sintomas;}
    public void setSintomas(String sintomas){this.Sintomas = sintomas;}
    public BigInteger getidVoluntario() {
        return this.VoluntarioBasicoId;
    }
    public void setidVoluntario(@Nullable BigInteger value) {
        this.VoluntarioBasicoId = value;
    }
    public BigInteger getidVoluntarioMedico() {
        return this.VoluntarioMedicoId;
    }
    public void setidVoluntarioMedico(@Nullable BigInteger value) {this.VoluntarioMedicoId = value;}
    public Integer getEspecialidadId() {
        return this.EspecialidadId;
    }
    public void setEspecialidadId(@Nullable Integer value) { this.EspecialidadId = value;}
    public void setSeguimiento(boolean value ){ this.Seguimiento = value;}

}
