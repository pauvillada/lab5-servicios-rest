package co.edu.uniandes.csw.mueblesdelosalpes.logica.interfaces;

import co.edu.uniandes.csw.mueblesdelosalpes.dto.Mueble;
import java.util.List;
import javax.ejb.Local;

@Local
public interface IServicioCatalogoMockLocal
{
    public void agregarMueble(Mueble mueble);

    public void eliminarMueble(long id);

    public List<Mueble> darMuebles();

    public void removerEjemplarMueble(long id);

    public void actualizarMueble(Mueble mueble);
}