package co.edu.uniandes.csw.mueblesdelosalpes.logica.ejb;

import co.edu.uniandes.csw.mueblesdelosalpes.dto.Usuario;
import co.edu.uniandes.csw.mueblesdelosalpes.excepciones.AutenticacionException;
import co.edu.uniandes.csw.mueblesdelosalpes.logica.interfaces.IServicioPersistenciaMockLocal;
import co.edu.uniandes.csw.mueblesdelosalpes.logica.interfaces.IServicioSeguridadMockLocal;
import co.edu.uniandes.csw.mueblesdelosalpes.logica.interfaces.IServicioSeguridadMockRemote;
import co.edu.uniandes.csw.mueblesdelosalpes.persistencia.mock.ServicioPersistenciaMock;
import javax.ejb.Stateless;

@Stateless
public class ServicioSeguridadMock implements IServicioSeguridadMockLocal, IServicioSeguridadMockRemote
{
    private IServicioPersistenciaMockLocal persistencia;

    public ServicioSeguridadMock()
    {
        persistencia = new ServicioPersistenciaMock();
    }

    @Override
    public Usuario ingresar(String nombre, String contraseña) throws AutenticacionException
    {
        Usuario u = (Usuario) persistencia.findById(Usuario.class, nombre);
        if (u == null)
        {
            throw new AutenticacionException("El nombre de usuario proporcionado no pertenece a ninguna cuenta.");
        }
        if (u.getLogin().equals(nombre) && u.getContraseña().equals(contraseña))
        {
            return u;
        }
        throw new AutenticacionException("La contraseña no es válida.");
    }
}