package DonArDato;

import android.widget.ImageButton;

public class EventoAutoMach {

    private String idEvento;
    private String nombre;
    private String apellido;

    public EventoAutoMach(String idEvento, String nombre, String apellido){
        this.nombre = nombre;
        this.idEvento = idEvento;
        this.apellido = apellido;
    }

    public String getIdEvento(){return  this.idEvento;}
    public String getNombre(){return this.nombre;}
    public String getApellido(){return this.apellido;}

    public void setIdEvento(String idEvento){this.idEvento = idEvento;}
    public void setNombre(String nombre){this.nombre = nombre;}
    public void setApellido(String apellido){this.apellido = apellido;}

}
