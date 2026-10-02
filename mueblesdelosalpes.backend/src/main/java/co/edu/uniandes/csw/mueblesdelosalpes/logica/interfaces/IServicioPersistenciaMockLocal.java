package co.edu.uniandes.csw.mueblesdelosalpes.logica.interfaces;

import co.edu.uniandes.csw.mueblesdelosalpes.excepciones.OperacionInvalidaException;
import java.util.List;
import javax.ejb.Local;

@Local
public interface IServicioPersistenciaMockLocal
{
    public void create(Object obj) throws OperacionInvalidaException;

    public void update(Object obj);

    public void delete(Object obj) throws OperacionInvalidaException;

    public List findAll(Class c);

    public Object findById(Class c, Object id);
}