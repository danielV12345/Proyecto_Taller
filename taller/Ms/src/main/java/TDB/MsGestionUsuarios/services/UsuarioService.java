package TDB.MsGestionUsuarios.services;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import TDB.MsGestionUsuarios.model.UsuarioModel;
import TDB.MsGestionUsuarios.repository.IUsuarioRepository;

@Service
public class UsuarioService {
    @Autowired
    private IUsuarioRepository usuarioRepository;
    public UsuarioModel findByUsername(String username){
        return usuarioRepository.findByUsername(username).
        orElse(null);

    }
    public UsuarioModel guardarUsuario(UsuarioModel usuario) {
        return usuarioRepository.save(usuario);
    }
    
    public List<UsuarioModel> findByUsernameContaining(String username) {
       return usuarioRepository.findByUsernameContaining(username);
     }

     public UsuarioModel findById(int idUsuario){
        return usuarioRepository.findById(idUsuario).orElse(null);
     }

     public void deleteUser(int idUsuario){
        usuarioRepository.deleteById(idUsuario);
     }

}
