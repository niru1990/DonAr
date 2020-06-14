package DonArDato;

public class ResponseData {

    private Integer idUser;
    private Integer tipoUser;

    public ResponseData(Integer idUsuario, Integer tipoUsuario)
    {
        this.idUser = idUsuario;
        this.tipoUser = tipoUsuario;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public Integer getTipoUser() {
        return tipoUser;
    }
}
