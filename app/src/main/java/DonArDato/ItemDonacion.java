package DonArDato;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;

public class ItemDonacion {
    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    private BigInteger _id;
    private Date fechaIngreso;
    private Date fechaVencimiento;
    private Date fechaEgreso;
    private ArrayList<ItemDonacion> items;
    private String destino;
    private int lote;
    private String tipo;

    public ItemDonacion(String fechaVencimiento, String destino, String tipo) {
        //this.fechaVencimiento = fechaVencimiento;
        this.destino = destino;
        this.tipo = tipo;
    }

    public BigInteger get_id() {
        return _id;
    }

    public void set_id(BigInteger _id) {
        this._id = _id;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public Date getFechaEgreso() {
        return fechaEgreso;
    }

    public void setFechaEgreso(Date fechaEgreso) {
        this.fechaEgreso = fechaEgreso;
    }

    public ArrayList<ItemDonacion> getItems() {
        return items;
    }

    public void setItems(ArrayList<ItemDonacion> items) {
        this.items = items;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public int getLote() {
        return lote;
    }

    public void setLote(int lote) {
        this.lote = lote;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
