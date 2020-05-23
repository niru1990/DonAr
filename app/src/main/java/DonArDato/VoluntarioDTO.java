package DonArDato;

import java.math.BigInteger;

public class VoluntarioDTO extends PersonaDTO{

    private BigInteger _idEvento;

    public BigInteger getidEvento(){return this._idEvento;}
    public void setidEvento(BigInteger value){this._idEvento = value;}
}
