package DonArDato;

public class VoluntarioMedicoDTO extends PersonaRegistroDTO
        {

    private int especialidadId;
    private String matricula;
    private String seguro;
    private String inicioJornada;
    private String finJornada;

            public VoluntarioMedicoDTO(String _id, String _idGoogle, String _nombre, String _apellido,
                                       Integer _tipoUsuario_Id, String _genero, Integer DNI, String email,
                                       String _telefono, Integer _edad, Integer _pais,
                                       Integer _provincia, int especialidadId, String matricula,
                                       String seguro, String inicioJornada, String finJornada) {
                super(_id, _idGoogle, _nombre, _apellido, _tipoUsuario_Id, _genero, DNI, email,
                        _telefono, _edad, _pais, _provincia);
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
