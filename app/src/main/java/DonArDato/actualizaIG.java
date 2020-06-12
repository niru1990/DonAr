package DonArDato;

public class actualizaIG {

    private String mail;
    private String idGoogle;

    public actualizaIG(String mail, String idGoogle) {
        this.idGoogle = idGoogle;
        this.mail = mail;
    }

    public String getIdGoogle(){return this.idGoogle;}
    public String getMail(){return this.mail;}
}
