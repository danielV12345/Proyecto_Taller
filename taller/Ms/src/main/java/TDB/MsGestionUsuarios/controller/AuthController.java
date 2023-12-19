package TDB.MsGestionUsuarios.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import TDB.MsGestionUsuarios.model.EmpleadoModel;
import TDB.MsGestionUsuarios.model.UsuarioModel;
import TDB.MsGestionUsuarios.repository.IEmpleadoRepository;
import TDB.MsGestionUsuarios.services.UsuarioService;

import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private IEmpleadoRepository empleadoRepository;


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
    
   @PostMapping("/crearUsuario")
    public ResponseEntity<String> crearusuario(@RequestBody UsuarioModel usuario) {
        try {
            // validamos antes de guardar el usuario
            //verificar si el nombre de usuario ya existe
            String username=usuario.getUsername();

            UsuarioModel existingUsuario = usuarioService.findByUsername(username);

            if (existingUsuario != null) {
                return ResponseEntity.status(400).body("Usuario ya existe");
        
            }
            EmpleadoModel nuevoEmpleado= empleadoRepository.save(usuario.getEmpleado());
            usuario.setEmpleado(nuevoEmpleado);

            UsuarioModel nuevoUsuario= usuarioService.guardarUsuario(usuario);
            return ResponseEntity.ok("Usuario creado exitosamente. ID: " + nuevoUsuario.getIdUsuario()+
            "\n nombre: "+nuevoEmpleado.getNombres());  

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al crear usuario: " + e.getMessage());
         }
    } 
}
