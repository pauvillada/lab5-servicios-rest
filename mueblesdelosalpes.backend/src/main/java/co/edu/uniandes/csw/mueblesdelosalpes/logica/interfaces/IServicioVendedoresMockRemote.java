package co.edu.uniandes.csw.mueblesdelosalpes.logica.interfaces;

import co.edu.uniandes.csw.mueblesdelosalpes.dto.Vendedor;
import co.edu.uniandes.csw.mueblesdelosalpes.excepciones.OperacionInvalidaException;
import java.util.List;
import javax.ejb.Remote;

@Remote
public interface IServicioVendedoresMockRemote
{
    public void agregarVendedor(Vendedor vendedor) throws OperacionInvalidaException;

    public void eliminarVendedor(long id) throws OperacionInvalidaException;

    public List<Vendedor> getVendedores();

    public void actualizarVendedor(Vendedor vendedor);
}