package DonArDato;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class PersonaRegistroDTO extends UsuarioDTO {

    private String _id;
    private String _idGoogle;
    private String _nombre;
    private String _apellido;
    private Integer _tipoUsuario_Id;
    private String _email;
    private String _genero;
    private Integer DNI;
    private Date _fechaNacimiento;
    private String _telefono;
    private Integer _edad;
    private String _pais;
    private String _provincia;

    public PersonaRegistroDTO(String _id, String _idGoogle, String _nombre, String _apellido, Integer _tipoUsuario_Id,
                              String _genero, Integer DNI, String _email, String _telefono , Integer _edad,
                              String _pais, String _provincia) {
        this._id = _id;
        this._idGoogle = _idGoogle;
        this._nombre = _nombre;
        this._apellido = _apellido;
        this._tipoUsuario_Id = _tipoUsuario_Id;
        this._genero = _genero;
        this.DNI = DNI;
        this._email = _email;
        this._telefono = _telefono;
        this._edad = _edad;
        this._pais = _pais;
        this._provincia = _provincia;
    }

    public PersonaRegistroDTO(){

    }

    public String get_id() { return _id; }
    public void set_id(String _id) {this._id = _id;}

    public String getNombre(){return this._nombre;}
    public void setNombre(String value){this._nombre = value;}

    public String getApellido(){return this._apellido;}
    public void setApellido(String value){this._apellido = value;}

    public String get_genero() {return _genero;}
    public void set_genero(String _genero) {this._genero = _genero;}

    public Date getFechaNacimiento(){return this._fechaNacimiento;}
    public void setFechaNacimiento(Date value){this._fechaNacimiento = value;}

    public String getTelefono(){return this._telefono;}
    public void setTelefono(String value){this._telefono = value;}

    public String get_provincia() { return _provincia; }
    public void set_provincia(String _provincia) { this._provincia = _provincia; }

    public String get_localidad() { return _pais; }
    public void set_localidad(String _localidad) { this._pais = _localidad; }

    public Integer getDNI() { return DNI; }
    public void setDNI(Integer DNI) { this.DNI = DNI; }

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
