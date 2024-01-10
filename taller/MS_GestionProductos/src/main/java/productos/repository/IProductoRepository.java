package productos.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import productos.model.ProductoModel;


public interface IProductoRepository extends JpaRepository<ProductoModel, Integer> {

}
