package Negocio;

import java.lang.reflect.Array;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import DonArDato.PacienteDTO;

public class Paciente extends Persona{

    public PacienteDTO getNewPaciente() {
        return new PacienteDTO();
    }

    public PacienteDTO getPacienteById(BigInteger userId){
        //Recuperar la información del paciente desde la base de datos a base del ID del usuario
        return new PacienteDTO();
    }

    public List<PacienteDTO> getListPaciente() {

        return new ArrayList<PacienteDTO>();
    }
}
