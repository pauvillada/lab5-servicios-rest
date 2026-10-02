package co.edu.uniandes.csw.mueblesdelosalpes.logica.ejb;

import co.edu.uniandes.csw.mueblesdelosalpes.dto.Vendedor;
import co.edu.uniandes.csw.mueblesdelosalpes.excepciones.OperacionInvalidaException;
import co.edu.uniandes.csw.mueblesdelosalpes.logica.interfaces.IServicioPersistenciaMockLocal;
import co.edu.uniandes.csw.mueblesdelosalpes.logica.interfaces.IServicioVendedoresMockLocal;
import co.edu.uniandes.csw.mueblesdelosalpes.logica.interfaces.IServicioVendedoresMockRemote;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

@Stateful
public class ServicioVendedoresMock implements IServicioVendedoresMockRemote, IServicioVendedoresMockLocal
{
    @EJB
    private IServicioPersistenciaMockLocal persistencia;

    public ServicioVendedoresMock()
    {
    }

    @Override
    public void agregarVendedor(Vendedor vendedor) throws OperacionInvalidaException
    {
        persistencia.create(vendedor);
    }

    @Override
    public void eliminarVendedor(long id) throws OperacionInvalidaException
    {
        Vendedor v = (Vendedor) persistencia.findById(Vendedor.class, id);
        persistencia.delete(v);
    }

    @Override
    public List<Vendedor> getVendedores()
    {
        return persistencia.findAll(Vendedor.class);
    }

    @Override
    public void actualizarVendedor(Vendedor vendedor)
    {
        persistencia.update(vendedor);
    }
}