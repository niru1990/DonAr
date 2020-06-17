package DonArDato;

public class AsociacionDTO extends PersonaRegistroDTO {

    public AsociacionDTO(String _idGoogle, String nombre, String apellido, Integer tipoUsuarioId,
                         Integer genero, Integer DNI, String email, String telefono, Integer edad,
                         Integer nacionalidadId, Integer provinciaId) {
        super(_idGoogle, nombre, apellido, tipoUsuarioId, genero, DNI, email, telefono, edad,
                nacionalidadId, provinciaId);
    }

}
