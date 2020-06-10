package DonArDato;

import java.math.BigInteger;

public class VoluntarioDTO extends PersonaRegistroDTO{

    public VoluntarioDTO( String idGoogle, String nombre, String apellido,
                         Integer tipoUsuario_Id, Integer genero, Integer DNI,String email,
                         String telefono, Integer edad, Integer nacionalidadId, Integer provinciaId) {
        super( idGoogle, nombre, apellido, tipoUsuario_Id,genero, DNI, email, telefono,
                edad, nacionalidadId, provinciaId);

    }


/*
    public BigInteger getidEvento(){return this._idEvento;}
    public void setidEvento(BigInteger value){this._idEvento = value;}
    */

}
