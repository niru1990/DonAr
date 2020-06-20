package DonArDato;


import java.util.HashMap;

public class ReporteVoluntariosTipo {

    private Integer cantVoluntariosBasicos;
    private Integer cantVoluntariosMedicos;
    private Integer cantVoluntariosAsociacion;
    private HashMap<String, Integer> medicosPorEspecialidad;

    public ReporteVoluntariosTipo(Integer basico, Integer medico, Integer asociación,
                                  HashMap<String, Integer> mpe){
        this.cantVoluntariosBasicos = basico;
        this.cantVoluntariosMedicos = medico;
        this.cantVoluntariosAsociacion = asociación;
        this.medicosPorEspecialidad = mpe;
    }

    public Integer getCantVoluntariosBasicos(){return this.cantVoluntariosBasicos;}
    public Integer getCantVoluntariosMedicos(){return this.cantVoluntariosMedicos;}
    public Integer getCantVoluntariosAsociacion(){return this.cantVoluntariosAsociacion;}
    public HashMap<String, Integer> getMedicosPorEspecialidad(){return this.medicosPorEspecialidad;}

}
