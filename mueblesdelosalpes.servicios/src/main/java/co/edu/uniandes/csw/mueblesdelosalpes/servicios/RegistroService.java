package co.edu.uniandes.csw.mueblesdelosalpes.servicios;

import co.edu.uniandes.csw.mueblesdelosalpes.dto.RegistroVenta;
import co.edu.uniandes.csw.mueblesdelosalpes.dto.Usuario;
import co.edu.uniandes.csw.mueblesdelosalpes.excepciones.OperacionInvalidaException;
import co.edu.uniandes.csw.mueblesdelosalpes.logica.interfaces.IServicioRegistroMockLocal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/Registro")
@Stateless
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RegistroService {

    @EJB
    private IServicioRegistroMockLocal registroEjb;

    @GET
    @Path("clientes/")
    public List<Usuario> getClientes() {
        return registroEjb.darClientes();
    }

    @GET
    @Path("clientes/{login}")
    public Response getCliente(@PathParam("login") String login) {
        Usuario u = buscar(login);
        if (u == null) {
            return Respuestas.error(Response.Status.NOT_FOUND, "No existe el cliente " + login);
        }
        return Response.ok(u).build();
    }

    @POST
    @Path("clientes/")
    public Response registrar(Usuario usuario) {
        if (usuario.getCompras() == null) {
            usuario.setCompras(new ArrayList<RegistroVenta>());
        }
        try {
            registroEjb.registrar(usuario);
            return Response.status(Response.Status.CREATED).entity(usuario).build();
        } catch (OperacionInvalidaException e) {
            return Respuestas.error(Response.Status.BAD_REQUEST, e.getMessage());
        }
    }

    @PUT
    @Path("clientes/{login}")
    public Response actualizar(@PathParam("login") String login, Usuario usuario) {
        usuario.setLogin(login);
        try {
            registroEjb.actualizarCliente(usuario);
            return Response.ok(usuario).build();
        } catch (OperacionInvalidaException e) {
            return Respuestas.error(Response.Status.NOT_FOUND, e.getMessage());
        }
    }

    @DELETE
    @Path("clientes/{login}")
    public Response eliminar(@PathParam("login") String login) {
        if (buscar(login) == null) {
            return Respuestas.error(Response.Status.NOT_FOUND, "No existe el cliente " + login);
        }
        try {
            registroEjb.eliminarCliente(login);
            return Response.noContent().build();
        } catch (OperacionInvalidaException e) {
            return Respuestas.error(Response.Status.BAD_REQUEST, e.getMessage());
        }
    }

    private Usuario buscar(String login) {
        for (Usuario u : registroEjb.darClientes()) {
            if (u.getLogin().equals(login)) {
                return u;
            }
        }
        return null;
    }
}