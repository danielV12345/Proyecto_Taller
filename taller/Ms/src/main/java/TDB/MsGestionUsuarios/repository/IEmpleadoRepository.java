package TDB.MsGestionUsuarios.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import TDB.MsGestionUsuarios.model.EmpleadoModel;

public interface IEmpleadoRepository extends JpaRepository<EmpleadoModel, Integer> {

} 
