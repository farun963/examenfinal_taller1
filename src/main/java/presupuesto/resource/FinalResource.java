package presupuesto.resource;

import java.util.List;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import presupuesto.entity.Cliente;
import presupuesto.entity.PresupuestoMensual;
import presupuesto.repository.ClienteRepository;
import presupuesto.repository.PresupuestoMensualRepository;

@Path("/final")
@Produces(MediaType.APPLICATION_JSON)
public class FinalResource {

    @Inject
    PresupuestoMensualRepository presupuestoMensualRepository;

    @Inject
    ClienteRepository clienteRepository;

    @GET
    @Path("/presupuestos/count")
    public Response contarPresupuestos() {
        long total = presupuestoMensualRepository.count();
        return Response.ok(total).build();
    }

    @GET
    @Path("/presupuestos-mensual/{rangoInicial}/{rangoFinal}")
    public Response filtrarPresupuestosPorRango(@PathParam("rangoInicial") Integer rangoInicial,
                                                @PathParam("rangoFinal") Integer rangoFinal) {
        List<PresupuestoMensual> presupuestos = presupuestoMensualRepository.findByMontoRange(rangoInicial, rangoFinal);
        if (presupuestos.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND)
                           .entity("No se encontraron presupuestos en el rango especificado.")
                           .build();
        }
        return Response.ok(presupuestos).build();
    }

    @GET
    @Path("/presupuesto-mensual/mayor-presupuesto")
    public Response obtenerPresupuestoMasAlto() {
        List<PresupuestoMensual> presupuestosMasAltos = presupuestoMensualRepository.findHighestPresupuesto();
        if (presupuestosMasAltos.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND)
                           .entity("No se encontraron presupuestos.")
                           .build();
        }
        return Response.ok(presupuestosMasAltos).build();
    }

    @GET
    @Path("/clientes/buscar")
    public Response buscarClientes(@QueryParam("nombres") String nombres,
                                   @QueryParam("apellidos") String apellidos) {
        List<Cliente> clientes = clienteRepository.buscarPorNombresOApellidos(nombres, apellidos);
        if (clientes.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND)
                           .entity("No se encontraron clientes con los criterios especificados.")
                           .build();
        }
        return Response.ok(clientes).build();
    }
}