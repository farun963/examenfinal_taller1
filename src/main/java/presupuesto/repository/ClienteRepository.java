package presupuesto.repository;

import java.util.List;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import presupuesto.entity.Cliente;



@ApplicationScoped
public class ClienteRepository implements PanacheRepository<Cliente> {
    
    public List<Cliente> buscarPorNombresOApellidos(String nombres, String apellidos) {
        if (nombres != null && apellidos != null) {
            return list("nombres LIKE ?1 AND apellidos LIKE ?2", "%" + nombres + "%", "%" + apellidos + "%");
        } else if (nombres != null) {
            return list("nombres LIKE ?1", "%" + nombres + "%");
        } else if (apellidos != null) {
            return list("apellidos LIKE ?1", "%" + apellidos + "%");
        }
        return listAll();
    }
}