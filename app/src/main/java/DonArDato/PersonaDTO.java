package DonArDato;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import Negocio.Usuario;

public class PersonaDTO extends UsuarioDTO {

    private String _nombre;
    private String _apellido;
    private Date _fechaNacimiento;
    private String _telefono;
    private Integer _edad;

    public String getNombre(){return this._nombre;}
    public void setNombre(String value){this._nombre = value;}
    public String getApellido(){return this._apellido;}
    public void setApellido(String value){this._apellido = value;}
    public Date getFechaNacimiento(){return this._fechaNacimiento;}
    public void setFechaNacimiento(Date value){this._fechaNacimiento = value;}
    public String getTelefono(){return this._telefono;}
    public void setTelefono(String value){this._telefono = value;}
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setEdad(){
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate fechaNac = LocalDate.parse(this._fechaNacimiento.toString(), fmt);
        LocalDate ahora = LocalDate.now();
        Period periodo = Period.between(fechaNac, ahora);
        this._edad  = periodo.getYears();
        /*
        System.out.printf("Tu edad es: %s años, %s meses y %s días",
                periodo.getYears(), periodo.getMonths(), periodo.getDays());
         */
    }
    public Integer getEdad(){return this._edad;}
}
