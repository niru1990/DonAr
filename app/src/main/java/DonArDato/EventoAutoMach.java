package DonArDato;

import android.widget.ImageButton;

public class EventoAutoMach {

    private String idEvento;
    private String nombre;
    private String apellido;

    public EventoAutoMach(String idEvento, String nombrePaciente, String apellidoPaciente){
        this.idEvento = idEvento;
        this.nombre = nombrePaciente;
        this.apellido = apellidoPaciente;
    }

    public String getIdEvento(){return  this.idEvento;}
    public String getNombre(){return this.nombre;}
    public String getApellido(){return this.apellido;}

}
