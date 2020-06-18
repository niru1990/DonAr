package DonArDato;

import java.math.BigInteger;
import java.net.BindException;

public class cambiarEstado {

    private BigInteger EventoId;
    private int Estado;

    public cambiarEstado(BigInteger EventoId, int estado)
    {
        this.EventoId = EventoId;
        this.Estado = estado;
    }

    public int getEstado() {
        return this.Estado;
    }

    public BigInteger getEventoId() {
        return this.EventoId;
    }
}
