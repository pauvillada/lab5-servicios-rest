package co.edu.uniandes.csw.mueblesdelosalpes.servicios;

import co.edu.uniandes.csw.mueblesdelosalpes.dto.Mueble;
import co.edu.uniandes.csw.mueblesdelosalpes.dto.Usuario;
import co.edu.uniandes.csw.mueblesdelosalpes.excepciones.AutenticacionException;
import co.edu.uniandes.csw.mueblesdelosalpes.logica.interfaces.IServicioCarritoMockLocal;
import co.edu.uniandes.csw.mueblesdelosalpes.logica.interfaces.IServicioSeguridadMockLocal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

@Path("/CarroCompras")
@Stateless
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CarroComprasService {

    @EJB
    private IServicioCarritoMockLocal carroEjb;

    @EJB
    private IServicioSeguridadMockLocal seguridadEjb;

    @GET
    @Path("muebles/")
    public List<Mueble> getTodosLosMuebles() {
        return carroEjb.getInventario();
    }

    @GET
    @Path("total/")
    public Map<String, Object> getTotal() {
        Map<String, Object> resumen = new HashMap<String, Object>();
        resumen.put("precioTotal", carroEjb.getPrecioTotalInventario());
        resumen.put("totalUnidades", carroEjb.getTotalUnidades());
        return resumen;
    }

    @POST
    @Path("agregar/")
    public List<Mueble> agregarMuebles(List<Mueble> mb) {
        for (Mueble mueble : mb) {
            carroEjb.agregarItem(mueble);
        }
        return carroEjb.getInventario();
    }

    @PUT
    @Path("comprar/")
    public Response comprar(Usuario datos) {
        try {
            Usuario usuario = seguridadEjb.ingresar(datos.getLogin(), datos.getContraseña());
            if (carroEjb.getInventario().isEmpty()) {
                return Respuestas.error(Response.Status.BAD_REQUEST, "El carrito está vacío");
            }
            carroEjb.comprar(usuario);
            carroEjb.recalcularInventarioTotal();
            return Respuestas.mensaje(Response.Status.OK, "Compra realizada por " + usuario.getLogin());
        } catch (AutenticacionException e) {
            return Respuestas.error(Response.Status.UNAUTHORIZED, e.getMessage());
        }
    }

    @DELETE
    @Path("muebles/{referencia}")
    public Response eliminarMueble(@PathParam("referencia") long referencia) {
        for (Mueble item : carroEjb.getInventario()) {
            if (item.getReferencia() == referencia) {
                carroEjb.removerItem(item, true);
                return Response.ok(carroEjb.getInventario()).build();
            }
        }
        return Respuestas.error(Response.Status.NOT_FOUND, "El mueble " + referencia + " no está en el carrito");
    }

    @DELETE
    @Path("limpiar/")
    public Response limpiar() {
        carroEjb.limpiarLista();
        carroEjb.recalcularInventarioTotal();
        return Response.noContent().build();
    }
}