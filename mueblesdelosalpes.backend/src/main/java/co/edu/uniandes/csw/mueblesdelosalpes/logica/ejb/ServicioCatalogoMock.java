package co.edu.uniandes.csw.mueblesdelosalpes.logica.ejb;

import co.edu.uniandes.csw.mueblesdelosalpes.dto.Mueble;
import co.edu.uniandes.csw.mueblesdelosalpes.excepciones.OperacionInvalidaException;
import co.edu.uniandes.csw.mueblesdelosalpes.logica.interfaces.IServicioCatalogoMockLocal;
import co.edu.uniandes.csw.mueblesdelosalpes.logica.interfaces.IServicioCatalogoMockRemote;
import co.edu.uniandes.csw.mueblesdelosalpes.logica.interfaces.IServicioPersistenciaMockLocal;
import co.edu.uniandes.csw.mueblesdelosalpes.persistencia.mock.ServicioPersistenciaMock;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;

@Stateless
public class ServicioCatalogoMock implements IServicioCatalogoMockRemote, IServicioCatalogoMockLocal
{
    private IServicioPersistenciaMockLocal persistencia;

    public ServicioCatalogoMock()
    {
        persistencia = new ServicioPersistenciaMock();
    }

    @Override
    public void agregarMueble(Mueble mueble)
    {
        try
        {
            persistencia.create(mueble);
        }
        catch (OperacionInvalidaException ex)
        {
            Logger.getLogger(ServicioCatalogoMock.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void eliminarMueble(long id)
    {
        Mueble m = (Mueble) persistencia.findById(Mueble.class, id);
        try
        {
            persistencia.delete(m);
        }
        catch (OperacionInvalidaException ex)
        {
            Logger.getLogger(ServicioCatalogoMock.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void removerEjemplarMueble(long id)
    {
        List<Mueble> muebles = persistencia.findAll(Mueble.class);
        for (Mueble mueble : muebles)
        {
            if (mueble.getReferencia() == id)
            {
                mueble.setCantidad(mueble.getCantidad() - 1);
                persistencia.update(mueble);
                break;
            }
        }
    }

    @Override
    public List<Mueble> darMuebles()
    {
        return persistencia.findAll(Mueble.class);
    }

    @Override
    public void actualizarMueble(Mueble mueble)
    {
        persistencia.update(mueble);
    }
}