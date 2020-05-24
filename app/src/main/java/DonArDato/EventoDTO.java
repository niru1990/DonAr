package DonArDato;

import java.math.BigInteger;
import java.util.Date;

public class EventoDTO {

    private BigInteger _id;
    private BigInteger _idPaciente;
    private BigInteger _idVoluntario;
    private BigInteger _idVoluntarioMedico;
    private Date _fecha;
    private String _sintomas;
    private Integer _idEspecialidad;


    public Integer getIdEspecialidad() {
        return this._idEspecialidad;
    }
    public void setIdEspecialidad(Integer value) { this._idEspecialidad = value;}

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
    public void setidVoluntario(BigInteger value) {
        this._idVoluntario = value;
    }
    public BigInteger getidVoluntarioMedico() {
        return this._idVoluntarioMedico;
    }
    public void setidVoluntarioMedico(BigInteger value) {
        this._idVoluntarioMedico = value;
    }

}
