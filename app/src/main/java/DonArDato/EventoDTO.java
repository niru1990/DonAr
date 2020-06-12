package DonArDato;

import androidx.annotation.Nullable;

import java.math.BigInteger;

public class EventoDTO {

    private BigInteger id;
    private BigInteger pacienteId;
    private String fecha;
    private String sintomas;
    private String detalle;
    private boolean diagnosticoPresuntivo;
    private boolean tratamientoFarmacologico;
    @Nullable
    private BigInteger voluntarioMedicoId;
    @Nullable
    private BigInteger voluntarioBasicoId;
    @Nullable
    private Integer especialidadId;
    @Nullable
    private Integer estado;

    //Getters
    public BigInteger getId() {
        return this.id;
    }
    public String getFecha(){return this.fecha;}
    public BigInteger getPacienteId() {
        return this.pacienteId;
    }
    public String getSintomas(){return this.sintomas;}
    public BigInteger getidVoluntario() {
        return this.voluntarioBasicoId;
    }
    public BigInteger getidVoluntarioMedico() {
        return this.voluntarioMedicoId;
    }
    public Integer getEspecialidadId() {
        return this.especialidadId;
    }
    public String getDetalle(){return  this.detalle;}
    public boolean getDiagnosticoPresuntivo(){return this.diagnosticoPresuntivo;}
    public boolean getTratamientoFarmacologico(){return this.tratamientoFarmacologico;}
    public Integer getEstado(){return this.estado;}

    //Setters
    public void setId(BigInteger value) {
        this.id = value;
    }
    public void setFecha(String value){this.fecha = value;}
    public void setPacienteId(BigInteger value) {
        this.pacienteId = value;
    }
    public void setSintomas(String sintomas){this.sintomas = sintomas;}
    public void setidVoluntario(@Nullable BigInteger value) {
        this.voluntarioBasicoId = value;
    }
    public void setidVoluntarioMedico(@Nullable BigInteger value) {this.voluntarioMedicoId = value;}
    public void setEspecialidadId(@Nullable Integer value) { this.especialidadId = value;}
    public void setDetalle(String detalle){this.detalle = detalle;}
    public void setDiagnosticoPresuntivo(boolean diagnosticoPresuntivo){ this.diagnosticoPresuntivo = diagnosticoPresuntivo;}
    public void setTratamientoFarmacologico(boolean tratamientoFarmacologico) {this.tratamientoFarmacologico = tratamientoFarmacologico;}
    public void setEstado(Integer estado){this.estado = estado;}
}
