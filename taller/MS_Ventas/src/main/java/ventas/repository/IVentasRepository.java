package ventas.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import ventas.model.VentasModel;

public interface IVentasRepository extends JpaRepository<VentasModel, Integer> {
    
}
