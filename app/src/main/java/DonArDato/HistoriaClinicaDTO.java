package DonArDato;

import java.math.BigInteger;
import java.util.ArrayList;

public class HistoriaClinicaDTO {

    private BigInteger _id;
    private BigInteger _idPaciente;
    private ArrayList<EventoDTO> _consultas;

    public BigInteger getId() {
        return this._id;
    }
    public void setId(BigInteger value) {
        this._id = value;
    }
    public BigInteger getIdPaciente() {
        return this._idPaciente;
    }
    public void setIdPaciente(BigInteger value) {
        this._idPaciente = value;
    }
    public ArrayList<EventoDTO> getConsultas(){
        if(this._consultas == null)
            this.setConsultas(new ArrayList<EventoDTO>());
        return this._consultas;
    }
    public void setConsultas(ArrayList al){ this._consultas = al;}

}
