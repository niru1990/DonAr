package DonArDato;

import android.app.Person;
import DonArDato.PersonaRegistroDTO;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;


public class PacienteDTO extends PersonaRegistroDTO {

    private int id;
    private Integer historialClinico;

    public PacienteDTO(String _nombre, String _apellido, Integer DNI,String _telefono,
                       String _provincia, String _pais) {
       super(_nombre,_apellido,DNI,_telefono,_provincia,_pais);

    }

    public PacienteDTO(){

    }

    public Integer getId(){return this.id;}
    public Integer getHistorialClinico(){return this.historialClinico;}

    public void setId(int value){this.id = value;}
    public void setHistorialClinico(Integer value){}
}
