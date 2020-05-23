package DonArDato;

public class UsuarioDTO {

    private Integer _id;
    private String _usuario;
    private String _contraseña;

    public void setId(Integer id){this._id=id;}
    public void setUsuario(String usuario){this._usuario = usuario;}
    public void setContraseña(String contraseña){this._contraseña=contraseña;}
    public Integer getId(){return this._id;}
    public String getUsuario(){return this._usuario;}
    public String getContraseña(){return this._contraseña;}

}
