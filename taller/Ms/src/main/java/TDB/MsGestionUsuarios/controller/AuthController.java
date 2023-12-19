package TDB.MsGestionUsuarios.controller;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    @GetMapping("/buscar")
    public ResponseEntity<List<UsuarioModel>> buscarNombreUsuario(@RequestParam String nombreUsuario) {
        
        UsuarioModel usuario=usuarioService.findByUsername(nombreUsuario);
        if (usuario == null){
            return ResponseEntity.notFound().build();
        }
        else{
            List<UsuarioModel> usuarios = Collections.singletonList(usuario);
            return ResponseEntity.ok(usuarios);   
            
        }
    }
    @PutMapping("/editarUsuario")
    public ResponseEntity<String> editarUsuario(@RequestParam int idUsuario, @RequestBody UsuarioModel usuarioActualizado) {
        try {
            // Buscar el usuario existente por su ID
            UsuarioModel usuarioExistente = usuarioService.findById(idUsuario);

            if (usuarioExistente == null) {
                return ResponseEntity.status(404).body("Usuario no encontrado");
            }

            // Actualizar los campos del usuario existente con los nuevos valores
            usuarioExistente.setUsername(usuarioActualizado.getUsername());
            usuarioExistente.setPassword(usuarioActualizado.getPassword());
            usuarioExistente.setFechaCreacion(usuarioActualizado.getFechaCreacion());

            EmpleadoModel empleadoExistente = usuarioExistente.getEmpleado();

           if (empleadoExistente != null && usuarioActualizado.getEmpleado() != null) {
                EmpleadoModel empleadoActualizado = usuarioActualizado.getEmpleado();
                empleadoExistente.setIdTienda(empleadoActualizado.getIdTienda());
                empleadoExistente.setNombres(empleadoActualizado.getNombres());
                empleadoExistente.setApellidos(empleadoActualizado.getApellidos());
                empleadoExistente.setEmail(empleadoActualizado.getEmail());
                empleadoExistente.setTelefono(empleadoActualizado.getTelefono());
                empleadoExistente.setFechaContratacion(empleadoActualizado.getFechaContratacion());
                empleadoExistente.setSueldo(empleadoActualizado.getSueldo());
                empleadoExistente.setIdCargo(empleadoActualizado.getIdCargo());
            }
            // Guardamos en la base de datos
            usuarioService.guardarUsuario(usuarioExistente);
            return ResponseEntity.ok("Usuario editado exitosamente");
            
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al editar usuario: " + e.getMessage());
        }
    }
}
