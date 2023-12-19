package TDB.MsGestionUsuarios.services;
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

}
