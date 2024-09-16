package presupuesto.repository;

import java.util.List;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import presupuesto.entity.PresupuestoMensual;


@ApplicationScoped
public class PresupuestoMensualRepository implements PanacheRepository<PresupuestoMensual> {
    
    public List<PresupuestoMensual> findByMontoRange(Integer rangoInicial, Integer rangoFinal) {
        return list("saldoInicial BETWEEN ?1 AND ?2", rangoInicial, rangoFinal);
    }

    public List<PresupuestoMensual> findHighestPresupuesto() {
        return list("saldoInicial = (SELECT MAX(p.saldoInicial) FROM PresupuestoMensual p)");
    }
}
