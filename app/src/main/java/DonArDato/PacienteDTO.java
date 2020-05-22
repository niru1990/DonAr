package DonArDato;

import java.util.Date;

public class PacienteDTO {

    private Integer _id;
    private String _nombre;
    private String _apellido;
    private Date _fechaNacimiento;
    private String _telefono;

    public Integer getId() {
        return this._id;
    }
    public void setId(Integer value) {
        this._id = value;
    }
    public String getNombre(){return this._nombre;}
    public void setNombre(String value){this._nombre = value;}
    public String getApellido(){return this._apellido;}
    public void setApellido(String value){this._apellido = value;}
    public Date getFechaNacimiento(){return this._fechaNacimiento;}
    public void setFechaNacimiento(Date value){this._fechaNacimiento = value;}
    public String getTelefono(){return this._telefono;}
    public void setTelefono(String value){this._telefono = value;}
}
