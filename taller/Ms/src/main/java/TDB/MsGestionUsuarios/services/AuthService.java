package TDB.MsGestionUsuarios.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import TDB.MsGestionUsuarios.model.UsuarioModel;
import TDB.MsGestionUsuarios.repository.IUsuarioRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private IUsuarioRepository usuarioRepository;
    
    public List<UsuarioModel> getAllUsuarios() {
        return (List<UsuarioModel>) usuarioRepository.findAll();
    }

    public Optional<UsuarioModel> getUsuarioById(int idUsuario) {
        return usuarioRepository.findById(idUsuario);
    }

    public UsuarioModel registrarUsuario(UsuarioModel usuario) {
        return usuarioRepository.save(usuario);
    }
    
}
