package co.edu.uniandes.csw.mueblesdelosalpes.servicios;

import java.util.HashMap;
import java.util.Map;
import javax.ws.rs.core.Response;

public final class Respuestas {

    private Respuestas() {
    }

    public static Response error(Response.Status estado, String mensaje) {
        Map<String, String> cuerpo = new HashMap<String, String>();
        cuerpo.put("error", mensaje);
        return Response.status(estado).entity(cuerpo).build();
    }

    public static Response mensaje(Response.Status estado, String mensaje) {
        Map<String, String> cuerpo = new HashMap<String, String>();
        cuerpo.put("mensaje", mensaje);
        return Response.status(estado).entity(cuerpo).build();
    }
}