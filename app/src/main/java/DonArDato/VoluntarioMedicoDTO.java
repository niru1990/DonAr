package DonArDato;

public class VoluntarioMedicoDTO extends PersonaRegistroDTO
        {

    private int especialidadId;
    private String matricula;
    private String seguro;
    private String inicioJornada;
    private String finJornada;

            public VoluntarioMedicoDTO(Integer _id, String _nombre, String _apellido,
                                       Integer _tipoUsuario_Id, String _genero, Integer DNI, String email,
                                       String _telefono, Integer _edad, String _pais,
                                       String _provincia, int especialidadId, String matricula,
                                       String seguro, String inicioJornada, String finJornada) {
                super(_id, _nombre, _apellido, _tipoUsuario_Id, _genero, DNI, email, _telefono, _edad, _pais,
                        _provincia);
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
