
package co.edu.uniandes.csw.mueblesdelosalpes.logica.interfaces;
import co.edu.uniandes.csw.mueblesdelosalpes.dto.Usuario;
import co.edu.uniandes.csw.mueblesdelosalpes.excepciones.AutenticacionException;
import javax.ejb.Remote;

@Remote
public interface IServicioSeguridadMockRemote
{
    public Usuario ingresar(String nombre, String contraseña) throws AutenticacionException;
}
