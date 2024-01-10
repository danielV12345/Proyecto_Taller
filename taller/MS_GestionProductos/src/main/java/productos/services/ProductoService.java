package productos.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import productos.model.ProductoModel;
import productos.repository.IProductoRepository;

@Service
public class ProductoService{
    @Autowired
    private IProductoRepository productoRepository;

    public List<ProductoModel> getAllProductos() {
        return productoRepository.findAll();
    }

    public Optional<ProductoModel> getProductoById(Integer idProducto) {
        return productoRepository.findById(idProducto);
    }

    public ProductoModel registrarProducto(ProductoModel producto) {
        return productoRepository.save(producto);
    }
}
