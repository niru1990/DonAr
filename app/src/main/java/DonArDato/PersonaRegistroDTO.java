package DonArDato;

public class PersonaRegistroDTO{

    //private String _id;
    private String idGoogle;
    private String nombre;
    private String apellido;
    private Integer tipoUsuarioId;
    private String email;
    private Integer genero;
    private Integer DNI;
    private String telefono;
    private Integer edad;
    private Integer nacionalidadId;
    private Integer provinciaId;

    public PersonaRegistroDTO(
            //String _id,
             String _idGoogle, String nombre, String apellido, Integer tipoUsuario_Id,
                              Integer genero, Integer DNI, String email, String telefono , Integer edad,
                              Integer nacionalidadId, Integer provinciaId) {
       // this._id = _id;
        this.idGoogle = idGoogle;
        this.nombre = nombre;
        this.apellido = apellido;
        this.tipoUsuarioId = tipoUsuarioId;
        this.genero = genero;
        this.DNI = DNI;
        this.email = email;
        this.telefono = telefono;
        this.edad = edad;
        this.nacionalidadId = nacionalidadId;
        this.provinciaId = provinciaId;
    }

    public PersonaRegistroDTO(){

    }

    //public String get_id() { return _id; }
   // public void set_id(String _id) {this._id = _id;}

    public String getNombre(){return this.nombre;}
    public void setNombre(String value){this.nombre = value;}

    public String getApellido(){return this.apellido;}
    public void setApellido(String value){this.apellido = value;}

    public Integer get_genero() {
        return genero;
    }

    public void set_genero(Integer _genero) {
        this.genero = _genero;
    }
/*
    public Date getFechaNacimiento(){return this._fechaNacimiento;}
    public void setFechaNacimiento(Date value){this._fechaNacimiento = value;}
*/
    public String getTelefono(){return this.telefono;}
    public void setTelefono(String value){this.telefono = value;}



    public Integer getDNI() { return DNI; }
    public void setDNI(Integer DNI) { this.DNI = DNI; }

    /*
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

    }
    */

    public Integer getEdad(){return this.edad;}
}
