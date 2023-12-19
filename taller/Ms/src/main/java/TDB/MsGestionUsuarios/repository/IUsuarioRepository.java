package TDB.MsGestionUsuarios.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import TDB.MsGestionUsuarios.model.UsuarioModel;

public interface IUsuarioRepository extends JpaRepository<UsuarioModel, Integer>{
    Optional<UsuarioModel> findByUsername(String username);
    List<UsuarioModel> findByUsernameContaining(String username);
    
} 
