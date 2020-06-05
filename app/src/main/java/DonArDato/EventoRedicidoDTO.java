package DonArDato;

import java.math.BigInteger;

public class EventoRedicidoDTO {

    private BigInteger id;
    private String nombrePaciente;
    private String apellidoPaciente;
    private String nombreMedico;
    private String apellidoMedico;
    private String fecha;

    public BigInteger getId(){return this.id;}
    public String getNombrePaciente(){return this.nombrePaciente;}
    public String getApellidoPaciente() {return this.apellidoPaciente;}
    public String getNombreMedico(){return this.nombreMedico;}
    public String getApellidoMedico(){return this.apellidoMedico;}
    public String getFecha(){return this.fecha;}

    public void setId (BigInteger value){this.id = value;}
    public void setNombrePaciente (String value){this.nombrePaciente = value;}
    public void setApellidoPaciente (String value){this.apellidoPaciente = value;}
    public void setNombreMedico (String value){this.nombreMedico = value;}
    public void setApellidoMedico (String value){this.apellidoMedico=value;}
    public void setFecha (String value){this.fecha = value;}

}
