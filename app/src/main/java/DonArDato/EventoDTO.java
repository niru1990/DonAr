package DonArDato;

import java.math.BigInteger;
import java.util.Date;

public class EventoDTO {

    private BigInteger _id;
    private BigInteger _idPaciente;
    private Date _fecha;
    private String _sintomas;

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

}
