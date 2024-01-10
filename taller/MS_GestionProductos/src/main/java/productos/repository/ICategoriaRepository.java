package productos.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import productos.model.CategoriaModel;

public interface ICategoriaRepository extends JpaRepository<CategoriaModel, Integer> {
    
}
