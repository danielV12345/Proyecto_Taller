package TDB.MsGestionUsuarios.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import TDB.MsGestionUsuarios.model.UsuarioModel;

@Repository
public interface IAuthRepository extends JpaRepository<UsuarioModel, Integer> {
    Optional<UsuarioModel> findByUsername(String username);
}
