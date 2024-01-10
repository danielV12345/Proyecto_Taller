package productos.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import productos.model.CategoriaModel;
import productos.services.CategoriaService;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {
     @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    public List<CategoriaModel> obtenerTodasLasCategorias() {
        return categoriaService.getAllCategorias();
    }

    @GetMapping("/{id}")
    public Optional<CategoriaModel> obtenerCategoriaPorId(@PathVariable Integer id) {
        return categoriaService.getCategoriaById(id);
    }

    @PostMapping
    public CategoriaModel crearCategoria(@RequestBody CategoriaModel categoria) {
        return categoriaService.registrarCategoria(categoria);
    }
    
}
