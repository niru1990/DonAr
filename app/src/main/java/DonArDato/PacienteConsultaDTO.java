package DonArDato;

public class PacienteConsultaDTO {

    private Integer id;
    private String historialClinico;
    private String email;
    private Integer tipoUsuarioId;
    private int genero;
    private String nombre;
    private String apellido;
    private int dni;
    private int edad;
    private String telefono;
    private int nacionalidadId;
    private boolean terminosyCondiciones;


    public Integer getPacienteId(){return this.id;}
    public Integer getDni(){return this.dni;}
    public Integer getEdad(){return this.edad;}
    public Integer getNacionalidadId(){return this.nacionalidadId;}

    public boolean getTerminosYCondiciones(){return this.terminosyCondiciones;}

    public String getHistorialClinico(){return this.historialClinico;}
    public String getEmail(){return this.email;}
    public String getNombrePaciente(){return this.nombre;}
    public String getApellidoPaciente(){return this.apellido;}
    public String getTelefonoPaciente(){return this.telefono;}


    public void  setPacienteId(Integer id){this.id = id ;}
    public void  setDni(Integer dni){this.dni = dni;}
    public void  setEdad(Integer edad){this.edad = edad;}
    public void setNacionalidadId(Integer nacionalidadId){this.nacionalidadId = nacionalidadId;}

    public void setTerminosyCondiciones(boolean terms){this.terminosyCondiciones = terms;}

    public void setHistorialClinico(String historialClinico){this.historialClinico = historialClinico;}
    public void setEmail(String email){this.email = email;}
    public void setNombrePaciente(String nombre){this.nombre = nombre;}
    public void setApellidoPaciente(String apellido){this.apellido = apellido;}
    public void setTelefonoPaciente(String telefono){this.telefono = telefono;}

}

