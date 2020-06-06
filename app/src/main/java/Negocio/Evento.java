package Negocio;

import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;

import DonArDato.EventoDTO;

public class Evento {

    public boolean validar(@NotNull EventoDTO datos, boolean tieneEspecialidad){
        boolean valido = true;
        if(datos.getSintomas().equals(""))
            valido = false;
        if(tieneEspecialidad && datos.getEspecialidadId() == null || datos.getEspecialidadId() <= 0)
                valido = false;
        //if (tieneEspecialidad && datos.getidVoluntario() != BigInteger.valueOf(0))
        //    valido = false;
            return valido;
    }

    public boolean validar(@NotNull EventoDTO datos){
        boolean valido = true;
        if(datos.getSintomas().equals(""))
            valido = false;

        return valido;
    }

}
