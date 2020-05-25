package DonArDato;

import androidx.annotation.Nullable;

import java.math.BigInteger;
import java.util.Date;

public class EventoDTO {

    private BigInteger _id;
    private BigInteger _idPaciente;
    private Date _fecha;
    private String _sintomas;
    @Nullable
    private BigInteger _idVoluntarioMedico;
    private BigInteger _idVoluntario;
    private Integer _idEspecialidad;

    public BigInteger getId() {
        return this._id;
    }
    public void setId(BigInteger value) {
        this._id = value;
    }
    public Date getFecha(){return this._fecha;}
    public void setFecha(Date value){this._fecha = value;}
    public BigInteger getIdPaciente() {
        return this._idPaciente;
    }
    public void setIdPaciente(BigInteger value) {
        this._idPaciente = value;
    }
    public String getSintomas(){return this._sintomas;}
    public void setSintomas(String sintomas){this._sintomas = sintomas;}
    public BigInteger getidVoluntario() {
        return this._idVoluntario;
    }
    public void setidVoluntario(@Nullable BigInteger value) {
        this._idVoluntario = value;
    }
    public BigInteger getidVoluntarioMedico() {
        return this._idVoluntarioMedico;
    }
    public void setidVoluntarioMedico(@Nullable BigInteger value) {this._idVoluntarioMedico = value;}
    public Integer getIdEspecialidad() {
        return this._idEspecialidad;
    }
    public void setIdEspecialidad(@Nullable Integer value) { this._idEspecialidad = value;}

}
