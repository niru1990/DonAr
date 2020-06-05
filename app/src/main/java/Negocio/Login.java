package Negocio;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Login {

    @SerializedName("inicio")
    @Expose
    public int inicio;

    public int getIicio() {
        return inicio;
    }
    public void setInicio(int inicio){
        this.inicio=inicio;
    }


}
