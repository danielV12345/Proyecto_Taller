package TDB.MsGestionUsuarios.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import TDB.MsGestionUsuarios.model.UsuarioModel;
import TDB.MsGestionUsuarios.services.UsuarioService;

import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    @Autowired
    private UsuarioService usuarioService;
    
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UsuarioModel loginRequest){

        String username=loginRequest.getUsername();
        String password=loginRequest.getPassword();

        UsuarioModel usuario=usuarioService.findByUsername(username);
        
        if(usuario!=null && usuario.getPassword().equals(password)){
            String authToken = generarTokenAutenticacion(usuario);
            return ResponseEntity.ok("Autenticación exitosa. Token: " + authToken);
        }else{
            return ResponseEntity.status(401).body("Error de autenticación. Verifica tu nombre de usuario y contraseña.");
   }
    }
    private String generarTokenAutenticacion(UsuarioModel usuario) {
        // aqui generaremos el token con JWT
        
        return "TOKEN GENERADO CORRECTAMENTE";
    }
    
    
}
