package productos.controller;


import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import productos.model.CategoriaModel;
import productos.model.ProductoModel;
import productos.services.CategoriaService;
import productos.services.ProductoService;


@RestController
@RequestMapping("/api/productos")
public class ProductoController {
     @Autowired
    private ProductoService productoService;
    @Autowired
    private CategoriaService categoriaService;


    @GetMapping
    public List<ProductoModel> obtenerTodosLosProductos() {
        return productoService.getAllProductos();
    }

    @GetMapping("/{id}")
    public Optional<ProductoModel> obtenerProductoPorId(@PathVariable Integer id) {
        return productoService.getProductoById(id);
    }

    @PostMapping
    public ResponseEntity<?> crearProducto(@RequestBody ProductoModel productoModel) {
        try {
        Integer idCategoria = productoModel.getCategoria().getIdCategoria();

        if (idCategoria == null || !categoriaService.getCategoriaById(idCategoria).isPresent()) {
            throw new RuntimeException("La categoría con ID " + idCategoria + " no existe.");
        }

        CategoriaModel categoria = categoriaService.getCategoriaById(productoModel.getCategoria().getIdCategoria())
                .orElseThrow(() -> new RuntimeException("Categoria no encontrada con ID: " + productoModel.getCategoria().getIdCategoria()));

        productoModel.setCategoria(categoria);
        ProductoModel nuevoProducto = productoService.registrarProducto(productoModel);

        return new ResponseEntity<>(nuevoProducto, HttpStatus.CREATED);

        } catch (RuntimeException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
    }
}
