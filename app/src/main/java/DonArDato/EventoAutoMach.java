package DonArDato;

import android.widget.ImageButton;

public class EventoAutoMach {

    private String idEvento;
    private String nombre;
    private String apellido;
    private String nombreMedico;
    private String fecha;

    private boolean buttonsOn;

    public EventoAutoMach(String idEvento, String nombrePaciente, String apellidoPaciente, String nombreMedico){
        this.idEvento = idEvento;
        this.nombre = nombrePaciente;
        this.apellido = apellidoPaciente;
        if(nombreMedico == null)
            this.nombreMedico = "";
        else
            this.nombreMedico = nombreMedico;
        this.buttonsOn = true;
    }

    public EventoAutoMach(String idEvento, String nombrePaciente, String apellidoPaciente, String nombreMedico, String fecha,
                          boolean buttons){
        this.idEvento = idEvento;
        this.nombre = nombrePaciente;
        this.apellido = apellidoPaciente;
        if(nombreMedico == null)
            this.nombreMedico = "";
        else
            this.nombreMedico = nombreMedico;
        this.fecha = fecha;
        this.buttonsOn = buttons;
    }

    public String getIdEvento(){return  this.idEvento;}
    public String getNombre(){return this.nombre;}
    public String getApellido(){return this.apellido;}
    public String getNombreMedico(){return this.nombreMedico;}
    public boolean getButtonsOn(){return this.buttonsOn;}

}
