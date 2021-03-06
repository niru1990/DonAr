package DonArDato;

import androidx.annotation.Nullable;

import java.math.BigInteger;
import java.util.Date;

public class DonacionDTO {
    private BigInteger id;
    private String detalle;
    private int cantidad;
    private String fechaVencimiento;
    private String destino;
    private int idUsuario;
    private String estado;//en camino, entregado, recibido
    @Nullable
    private String fechaCambio;

    public String getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(String fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public DonacionDTO(BigInteger id, String detalle, int cantidad, String fechaVencimiento, String destino, int idUsuario, String estado) {
        this.id = id;
        this.detalle = detalle;
        this.cantidad = cantidad;
        this.fechaVencimiento = fechaVencimiento;
        this.destino = destino;
        this.idUsuario = idUsuario;
        this.estado = estado;
    }

    public DonacionDTO(String destino, int cantidad, String detalle, int idUsuario) {
        this.destino = destino;
        this.cantidad = cantidad;
        this.detalle = detalle;
        this.idUsuario = idUsuario;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
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

    public int getId() {
        return idUsuario;
    }

    public void setId(int id) {
        this.idUsuario = id;
    }

    @Nullable
    public String getFechaCambio() {
        return fechaCambio;
    }

    public void setFechaCambio(@Nullable String fechaCambio) {
        this.fechaCambio = fechaCambio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}


