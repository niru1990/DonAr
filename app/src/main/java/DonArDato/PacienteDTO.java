package DonArDato;

import java.math.BigInteger;
import java.util.ArrayList;

public class PacienteDTO {

    private String nombre;
    //private String apellido;
    private String dni;
    private String telefono;
    private String email;
    private String usuario;
    private String contraseña;
    private int edad;
    private int id;
    private Integer historialClinico;

    public String getNombre(){return this.nombre;}
    //public String getApellido(){return this.apellido;}
    public String getDni(){return this.dni;}
    public String getTelefono(){return this.telefono;}
    public String getEmail(){return this.email;}
    public String getUsuario(){return this.usuario;}
    public String getContraseña(){return this.contraseña;}
    public int getEdad(){return this.edad;}
    public int getId(){return this.id;}
    public Integer getHistorialClinico(){return this.historialClinico;}

    public void setNombre(String value){this.nombre = value;}
    //public void setApellido(String value){this.apellido = value;}
    public void setDNI(String value){this.dni =value;}
    public void setTelefono(String value){this.telefono = value;}
    public void setEmail(String value){this.email =value;}
    public void setUsuario(String value){this.usuario = value;}
    public void setContraseña(String value){this.contraseña = value;}
    public void setEdad(int value){this.edad = value;}
    public void setId(int value){this.id = value;}
    public void setHistorialClinico(Integer value){}
}
