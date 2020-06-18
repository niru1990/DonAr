package DonArDato;

import org.jetbrains.annotations.NotNull;

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
    private void setConsultas(@NotNull ArrayList<EventoDTO> al){

        if(al.size() > 0)
            this._consultas = al;
        else
            this._consultas = new ArrayList<>();
    }

}
