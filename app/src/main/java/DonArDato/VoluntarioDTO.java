package DonArDato;

import java.math.BigInteger;

public class VoluntarioDTO extends PersonaRegistroDTO{

    public VoluntarioDTO(String _id, String _idGoogle, String _nombre, String _apellido,
                         Integer _tipoUsuario_Id, String _genero, Integer DNI,String _email,
                         String _telefono, Integer _edad, String _pais, String _provincia) {
        super(_id, _idGoogle, _nombre, _apellido, _tipoUsuario_Id, _genero, DNI, _email, _telefono,
                _edad, _pais, _provincia);

    }


    private BigInteger _idEvento;

    public BigInteger getidEvento(){return this._idEvento;}
    public void setidEvento(BigInteger value){this._idEvento = value;}
}
