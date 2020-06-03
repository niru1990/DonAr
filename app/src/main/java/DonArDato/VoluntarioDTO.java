package DonArDato;

import java.math.BigInteger;

public class VoluntarioDTO extends PersonaRegistroDTO{

    public VoluntarioDTO(String _nombre, String _apellido, Integer DNI,String _telefono,
                       String _provincia, String _localidad) {
        super(_nombre,_apellido,DNI,_telefono,_provincia,_localidad);

    }

    private BigInteger _idEvento;

    public BigInteger getidEvento(){return this._idEvento;}
    public void setidEvento(BigInteger value){this._idEvento = value;}
}
