package TDB.MsGestionUsuarios.services;
  import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import TDB.MsGestionUsuarios.model.EmpleadoModel;
import TDB.MsGestionUsuarios.repository.IEmpleadoRepository;

@Service
public class EmpleadoService {
    @Autowired
    private IEmpleadoRepository empleadoRepository;
      public EmpleadoModel guardarempleado(EmpleadoModel empleado) {
        return empleadoRepository.save(empleado);
    }
}
