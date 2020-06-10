package DonArDato;

public class VoluntarioMedicoDTO extends PersonaRegistroDTO
        {

    private Integer especialidadId;
    private String matricula;
    private String seguro;
    private String inicioJornada;
    private String finJornada;

            public VoluntarioMedicoDTO( String idGoogle, String nombre, String apellido,
                                       Integer tipoUsuarioId, Integer genero, Integer DNI, String email,
                                       String telefono, Integer edad, Integer nacionalidadId,
                                       Integer provinciaId, int especialidadId, String matricula,
                                       String seguro, String inicioJornada, String finJornada) {
                super( idGoogle, nombre, apellido, tipoUsuarioId, genero, DNI, email,
                        telefono, edad, nacionalidadId, provinciaId);
                this.especialidadId = especialidadId;
                this.matricula = matricula;
                this.seguro = seguro;
                this.inicioJornada = inicioJornada;
                this.finJornada = finJornada;
            }

            public VoluntarioMedicoDTO(int especialidadId, String matricula, String seguro, String inicioJornada, String finJornada) {
                this.especialidadId = especialidadId;
                this.matricula = matricula;
                this.seguro = seguro;
                this.inicioJornada = inicioJornada;
                this.finJornada = finJornada;
            }
        }
