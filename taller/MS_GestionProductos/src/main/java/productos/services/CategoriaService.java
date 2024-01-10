package productos.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import productos.model.CategoriaModel;
import productos.repository.ICategoriaRepository;


@Service
public class CategoriaService {
    @Autowired
    private ICategoriaRepository categoriaRepository;

    public List<CategoriaModel> getAllCategorias() {
        return categoriaRepository.findAll();
    }

    public Optional<CategoriaModel> getCategoriaById(Integer idCategoria) {
        return categoriaRepository.findById(idCategoria);
    }

    public CategoriaModel registrarCategoria(CategoriaModel categoria) {
        return categoriaRepository.save(categoria);
    }
}
