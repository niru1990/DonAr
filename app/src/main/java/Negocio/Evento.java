package Negocio;

import org.jetbrains.annotations.NotNull;

import DonArDato.EventoDTO;

public class Evento {

    public boolean validar(@NotNull EventoDTO datos, boolean tieneEspecialidad){
        boolean valido = true;
        if(datos.getSintomas().equals(""))
            valido = false;

        if(tieneEspecialidad && datos.getIdEspecialidad() == null || datos.getIdEspecialidad() <= 0)
            valido = false;

        return valido;
    }

}
