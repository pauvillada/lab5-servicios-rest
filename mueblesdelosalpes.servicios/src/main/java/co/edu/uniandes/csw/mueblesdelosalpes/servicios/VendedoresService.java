package co.edu.uniandes.csw.mueblesdelosalpes.servicios;

import co.edu.uniandes.csw.mueblesdelosalpes.dto.Vendedor;
import co.edu.uniandes.csw.mueblesdelosalpes.excepciones.OperacionInvalidaException;
import co.edu.uniandes.csw.mueblesdelosalpes.logica.interfaces.IServicioVendedoresMockLocal;
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

@Path("/Vendedores")
@Stateless
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class VendedoresService {

    @EJB
    private IServicioVendedoresMockLocal vendedoresEjb;

    @GET
    @Path("vendedores/")
    public List<Vendedor> getVendedores() {
        return vendedoresEjb.getVendedores();
    }

    @GET
    @Path("vendedores/{id}")
    public Response getVendedor(@PathParam("id") long id) {
        Vendedor v = buscar(id);
        if (v == null) {
            return Respuestas.error(Response.Status.NOT_FOUND, "No existe el vendedor " + id);
        }
        return Response.ok(v).build();
    }

    @POST
    @Path("vendedores/")
    public Response agregarVendedor(Vendedor vendedor) {
        try {
            vendedoresEjb.agregarVendedor(vendedor);
            return Response.status(Response.Status.CREATED).entity(vendedor).build();
        } catch (OperacionInvalidaException e) {
            return Respuestas.error(Response.Status.BAD_REQUEST, e.getMessage());
        }
    }

    @PUT
    @Path("vendedores/{id}")
    public Response actualizarVendedor(@PathParam("id") long id, Vendedor vendedor) {
        if (buscar(id) == null) {
            return Respuestas.error(Response.Status.NOT_FOUND, "No existe el vendedor " + id);
        }
        vendedor.setIdentificacion(id);
        vendedoresEjb.actualizarVendedor(vendedor);
        return Response.ok(vendedor).build();
    }

    @DELETE
    @Path("vendedores/{id}")
    public Response eliminarVendedor(@PathParam("id") long id) {
        if (buscar(id) == null) {
            return Respuestas.error(Response.Status.NOT_FOUND, "No existe el vendedor " + id);
        }
        try {
            vendedoresEjb.eliminarVendedor(id);
            return Response.noContent().build();
        } catch (OperacionInvalidaException e) {
            return Respuestas.error(Response.Status.BAD_REQUEST, e.getMessage());
        }
    }

    private Vendedor buscar(long id) {
        for (Vendedor v : vendedoresEjb.getVendedores()) {
            if (v.getIdentificacion() == id) {
                return v;
            }
        }
        return null;
    }
}