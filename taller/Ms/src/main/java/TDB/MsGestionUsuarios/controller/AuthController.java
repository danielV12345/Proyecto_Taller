package TDB.MsGestionUsuarios.controller;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import TDB.MsGestionUsuarios.Excepciones.AutenticacionException;
import TDB.MsGestionUsuarios.Excepciones.ErrorGeneral;
import TDB.MsGestionUsuarios.Excepciones.UsuarioNoEncontradoException;
import TDB.MsGestionUsuarios.RespuestaGenerica.RespuestaGenerica;
import TDB.MsGestionUsuarios.model.EmpleadoModel;
import TDB.MsGestionUsuarios.model.UsuarioModel;
import TDB.MsGestionUsuarios.repository.IEmpleadoRepository;
import TDB.MsGestionUsuarios.services.UsuarioService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private IEmpleadoRepository empleadoRepository;


    @PostMapping("/login")
    public RespuestaGenerica login(@RequestBody UsuarioModel loginRequest){
        try {
             String username=loginRequest.getUsername();
            String password=loginRequest.getPassword();

            UsuarioModel usuario=usuarioService.findByUsername(username);
        
            if(usuario!=null && usuario.getPassword().equals(password)){
                String authToken = generarTokenAutenticacion(usuario);
                return new RespuestaGenerica("Autenticación exitosa. Token: " + authToken);
            } 
            else{
                // cuando la autenticación falle
                throw new AutenticacionException("Error de autenticación. Verifica tu nombre de usuario y contraseña.");
            }     
        } catch (AutenticacionException e) {
            return new RespuestaGenerica(e.getMessage());
        } catch (Exception e) {
            return new RespuestaGenerica("Error de autenticación: " + e.getMessage());
        }
       
    }
    private String generarTokenAutenticacion(UsuarioModel usuario) {
        // aqui generaremos el token con JWT
        
        return "TOKEN GENERADO CORRECTAMENTE";
    }
    
   @PostMapping("/crearUsuario")
    public RespuestaGenerica crearusuario(@RequestBody UsuarioModel usuario) {
        try {
            // validamos antes de guardar el usuario
            //verificar si el nombre de usuario ya existe
            String username=usuario.getUsername();

            UsuarioModel existingUsuario = usuarioService.findByUsername(username);

            if (existingUsuario != null) {
                throw new ErrorGeneral("El usuario ya existe");
                // return ResponseEntity.status(400).body("Usuario ya existe");
            }
            EmpleadoModel nuevoEmpleado= empleadoRepository.save(usuario.getEmpleado());
            usuario.setEmpleado(nuevoEmpleado);

            UsuarioModel nuevoUsuario= usuarioService.guardarUsuario(usuario);
            return new RespuestaGenerica("Usuario creado exitosamente. : " + nuevoUsuario.getUsername()+
            "\n nombre: "+nuevoEmpleado.getNombres());  

        } catch (ErrorGeneral e) {
            return new RespuestaGenerica(e.getMessage());
        } catch (DataIntegrityViolationException e) {
            return new RespuestaGenerica("Error en la base de datos " + e.getMessage());
        } catch (Exception e) {
            return new RespuestaGenerica("Error al crear usuario: " + e.getMessage());
        }
    } 
    @GetMapping("/buscar")
    public ResponseEntity<?> buscarNombreUsuario(@RequestParam String nombreUsuario) {
        
        try {
            UsuarioModel usuario=usuarioService.findByUsername(nombreUsuario);
        if (usuario == null){
            // return ResponseEntity.notFound().build();
            throw new ErrorGeneral("Usuario no encontrado: " + nombreUsuario);
        }
        else{
            List<UsuarioModel> usuarios = Collections.singletonList(usuario);
            return ResponseEntity.ok(usuarios);   
            
        }    
        } catch (ErrorGeneral e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RespuestaGenerica(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new RespuestaGenerica("Error interno del servidor: " + e.getMessage()));
        }
        
    }
    @PutMapping("/editarUsuario")
    public ResponseEntity<?> editarUsuario(@RequestParam int idUsuario, @RequestBody UsuarioModel usuarioActualizado) {
        try {
            // Buscar el usuario existente por su ID
            UsuarioModel usuarioExistente = usuarioService.findById(idUsuario);

            if (usuarioExistente == null) {
                // return ResponseEntity.status(404).body("Usuario no encontrado");
                throw new ErrorGeneral("Usuario no encontrado: "+usuarioExistente);
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
            String mensaje="Usuario editado exitosamente";
            return ResponseEntity.ok(new RespuestaGenerica(mensaje));
            
        } catch (ErrorGeneral e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RespuestaGenerica(e.getMessage()));
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RespuestaGenerica("Error en la base de datos: " + e.getMessage()));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new RespuestaGenerica("Error interno del servidor: " + e.getMessage()));
        }
    }
    @DeleteMapping("/eliminarUsuario")
    public RespuestaGenerica eliminarUsuario(@RequestParam int idUsuario) {
        try {
            // Buscar el usuario existente por su ID
            UsuarioModel usuarioExistente = usuarioService.findById(idUsuario);

            if (usuarioExistente == null) {
                throw  new UsuarioNoEncontradoException("Usuario no encontrado"); 
            }

            // Eliminar el usuario de la base de datos
            usuarioService.deleteUser(idUsuario);

            return new RespuestaGenerica("Usuario eliminado exitosamente");

        }catch (UsuarioNoEncontradoException e) {
            return new RespuestaGenerica(e.getMessage());
        }catch (Exception e) {
            return new RespuestaGenerica("Error al eliminar usuario: " + e.getMessage());
        }
    }
}
