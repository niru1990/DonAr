package DonArDato;

import androidx.annotation.Nullable;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;

public class DonacionDTO {
    private BigInteger id;
    private String detalle;
    private int cantidad;
    @Nullable
    private Date fechaIngreso;
    private String fechaVencimiento;
    @Nullable
    private Date fechaEgreso;
    private String destino;
    @Nullable
    private ArrayList<DonacionDTO> items;

    public String getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(String fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public DonacionDTO(BigInteger id, String detalle, int cantidad, String fechaVencimiento, String destino) {
        this.id = id;
        this.detalle = detalle;
        this.cantidad = cantidad;
        this.fechaVencimiento = fechaVencimiento;
        this.destino = destino;
    }



    public DonacionDTO(String destino, int cantidad, String detalle) {
        this.destino = destino;
        this.cantidad = cantidad;
        this.detalle = detalle;
    }
@Nullable
    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }
    @Nullable
    public Date getFechaEgreso() {
        return fechaEgreso;
    }
    @Nullable
    public void setFechaEgreso(Date fechaEgreso) {
        this.fechaEgreso = fechaEgreso;
    }
    @Nullable
    public ArrayList<DonacionDTO> getItems() {
        return items;
    }
    @Nullable
    public void setItems(ArrayList<DonacionDTO> items) {
        this.items = items;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }


    public BigInteger getDonacion_id() {
        return id;
    }

    public void setDonacion_id(BigInteger donacion_id) {
        this.id = donacion_id;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}


