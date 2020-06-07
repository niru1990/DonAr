package DonArDato;

public class PacienteDTO extends PersonaRegistroDTO {

    private Integer historialClinico;

    public PacienteDTO(String _id,String _idGoogle, String _nombre, String _apellido,
                       Integer _tipoUsuario_Id, String _email, String _genero, Integer DNI,
                       String _telefono, Integer _edad, String _pais, String _provincia,
                       Integer historialClinico) {
        super(_id, _idGoogle,  _nombre, _apellido, _tipoUsuario_Id, _genero, DNI, _email, _telefono, _edad, _pais, _provincia);
        this.historialClinico = historialClinico;
    }

    public PacienteDTO(){
        super();

    }



    public Integer getHistorialClinico(){return this.historialClinico;}

    public void setHistorialClinico(Integer value){}
}
