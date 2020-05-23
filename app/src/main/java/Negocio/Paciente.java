package Negocio;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import DonArDato.PacienteDTO;

public class Paciente {

    public PacienteDTO getPaciente() {

        return new PacienteDTO();

    }

    public List<PacienteDTO> getListPaciente() {

        return new ArrayList<PacienteDTO>();
    }
}
