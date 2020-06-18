package DonArDato;

public class PacienteDTO extends PersonaRegistroDTO {

    private String historialClinico;

    public PacienteDTO(String idGoogle, String nombre, String apellido,
                       Integer tipoUsuarioId, String email, Integer genero, Integer DNI,
                       String telefono, Integer edad, Integer nacionalidadId, Integer provinciaId,
                       String historialClinico) {
        super( idGoogle,  nombre, apellido, tipoUsuarioId, genero, DNI, email, telefono, edad, nacionalidadId, provinciaId);
        this.historialClinico = historialClinico;
    }

    public PacienteDTO(){
        super();

    }



    public String getHistorialClinico(){return this.historialClinico;}

    public void setHistorialClinico(String value){}
}
