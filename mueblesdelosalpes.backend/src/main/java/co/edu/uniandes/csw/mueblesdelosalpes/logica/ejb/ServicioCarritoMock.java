package co.edu.uniandes.csw.mueblesdelosalpes.logica.ejb;

import co.edu.uniandes.csw.mueblesdelosalpes.dto.Mueble;
import co.edu.uniandes.csw.mueblesdelosalpes.dto.RegistroVenta;
import co.edu.uniandes.csw.mueblesdelosalpes.dto.Usuario;
import co.edu.uniandes.csw.mueblesdelosalpes.logica.interfaces.IServicioCarritoMockLocal;
import co.edu.uniandes.csw.mueblesdelosalpes.logica.interfaces.IServicioCarritoMockRemote;
import co.edu.uniandes.csw.mueblesdelosalpes.logica.interfaces.IServicioPersistenciaMockLocal;
import co.edu.uniandes.csw.mueblesdelosalpes.persistencia.mock.ServicioPersistenciaMock;
import java.util.ArrayList;
import java.util.Date;
import javax.ejb.Singleton;

@Singleton
public class ServicioCarritoMock implements IServicioCarritoMockRemote, IServicioCarritoMockLocal
{
    private IServicioPersistenciaMockLocal persistencia;
    private ArrayList<Mueble> inventario;
    private double precioTotalInventario = 0.0;
    private int totalUnidades = 0;

    public ServicioCarritoMock()
    {
        inventario = new ArrayList<Mueble>();
        persistencia = new ServicioPersistenciaMock();
    }

    @Override
    public ArrayList<Mueble> getInventario()
    {
        return inventario;
    }

    @Override
    public void setInventario(ArrayList<Mueble> inventario)
    {
        this.inventario = inventario;
    }

    @Override
    public double getPrecioTotalInventario()
    {
        return precioTotalInventario;
    }

    @Override
    public int getTotalUnidades()
    {
        return totalUnidades;
    }

    @Override
    public void comprar(Usuario usuario)
    {
        for (Mueble mueble : inventario)
        {
            Mueble editar = (Mueble) persistencia.findById(Mueble.class, mueble.getReferencia());
            editar.setCantidad(editar.getCantidad() - mueble.getCantidad());
            RegistroVenta compra = new RegistroVenta(new Date(System.currentTimeMillis()), mueble, mueble.getCantidad(), null, usuario);
            usuario.agregarRegistro(compra);
            persistencia.update(usuario);
            persistencia.update(editar);
        }
        limpiarLista();
    }

    @Override
    public void agregarItem(Mueble mueble)
    {
        boolean found = false;
        for (Mueble item : inventario)
        {
            if (item.getReferencia() == mueble.getReferencia())
            {
                item.incrementarCantidad();
                found = true;
                break;
            }
        }
        if (!found)
        {
            inventario.add(mueble);
            mueble.incrementarCantidad();
        }
        recalcularInventarioTotal();
    }

    @Override
    public void removerItem(Mueble mueble, boolean removerCero)
    {
        Mueble foundItem = null;
        for (Mueble item : inventario)
        {
            if (item.getReferencia() == mueble.getReferencia())
            {
                item.reducirCantidad();
                foundItem = item;
                break;
            }
        }
        if (removerCero && foundItem != null && foundItem.getCantidad() <= 0)
        {
            inventario.remove(foundItem);
        }
        recalcularInventarioTotal();
    }

    @Override
    public void recalcularInventarioTotal()
    {
        precioTotalInventario = 0;
        totalUnidades = 0;
        for (Mueble item : inventario)
        {
            precioTotalInventario += item.getPrecio() * item.getCantidad();
            totalUnidades += item.getCantidad();
        }
    }

    @Override
    public void limpiarLista()
    {
        inventario.clear();
    }
}