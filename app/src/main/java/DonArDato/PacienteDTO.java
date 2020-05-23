package DonArDato;

import java.math.BigInteger;
import java.util.ArrayList;

public class PacienteDTO extends PersonaDTO{

    private BigInteger _idHistoriaClinica;

    public BigInteger getIdHistoriaClinica(){return this._idHistoriaClinica;}
    public void setIdHistoriaClinica(BigInteger value){ this._idHistoriaClinica = value;}

}
