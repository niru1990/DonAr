package Negocio;

import DonArDato.PersonaDTO;

public class Persona {

    private int _id;
    private int _idUsuario;

    public void setId(Integer id){this._id=id;}
    public void setIdUsuario(int usuario){this._idUsuario = usuario;}
    public int getId(){return this._id;}
    public int getIdUsuario(){return this._idUsuario;}

}
