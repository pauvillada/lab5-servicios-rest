package co.edu.uniandes.csw.mueblesdelosalpes.dto;

import java.util.Date;
import org.codehaus.jackson.annotate.JsonIgnore;

public class RegistroVenta
{
    private Date fechaVenta;
    private Mueble producto;
    private int cantidad;
    private String ciudad;
    private Usuario comprador;

    public RegistroVenta()
    {
    }

    public RegistroVenta(Date fechaVenta, Mueble producto, int cantidad, String ciudad, Usuario comprador)
    {
        this.fechaVenta = fechaVenta;
        this.producto = producto;
        this.cantidad = cantidad;
        this.ciudad = ciudad;
        this.comprador = comprador;
    }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public Date getFechaVenta() { return fechaVenta; }
    public void setFechaVenta(Date fechaVenta) { this.fechaVenta = fechaVenta; }

    public Mueble getProducto() { return producto; }
    public void setProducto(Mueble producto) { this.producto = producto; }

    public String getCiudad() { return ciudad; }
    public void setCiudad(String ciudad) { this.ciudad = ciudad; }

    @JsonIgnore
    public Usuario getComprador() { return comprador; }
    public void setComprador(Usuario comprador) { this.comprador = comprador; }
}