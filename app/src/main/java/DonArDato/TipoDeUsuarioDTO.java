package DonArDato;

public class TipoDeUsuarioDTO {

    private String id;
    private String nombre;

    public String getId(){return  this.id;}
    public String getTipoDeUsuario(){return this.nombre;}
    public void setId(String id){this.id = id;}
    public void setTipoDeUsuario(String tipoDeUsuario){this.nombre = tipoDeUsuario;}
}
