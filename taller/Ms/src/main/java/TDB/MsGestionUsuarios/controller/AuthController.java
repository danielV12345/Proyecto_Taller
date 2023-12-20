package TDB.MsGestionUsuarios.controller;
import java.util.Collections;
import java.util.List;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

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
import TDB.MsGestionUsuarios.Message.MensajesParametrizados;
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
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

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
                logger.info("Autenticación exitosa. Token: {}", authToken);

                return new RespuestaGenerica("Autenticación exitosa. Token: " + authToken);
            } 
            else{
                // cuando la autenticación falle
                throw new AutenticacionException(MensajesParametrizados.MENSAJE_ERROR_AUTENTICACION);
            }     
        } catch (AutenticacionException e) {
            logger.error(MensajesParametrizados.MENSAJE_ERROR_AUTENTICACION, e.getMessage());

            return new RespuestaGenerica(e.getMessage());
        } catch (Exception e) {
            logger.error(MensajesParametrizados.MENSAJE_ERROR_AUTENTICACION, e.getMessage());
            // return new RespuestaGenerica("Error de autenticación: " + e.getMessage());
            return new RespuestaGenerica(MensajesParametrizados.MENSAJE_ERROR_AUTENTICACION + e.getMessage());

        }
       
    }
    private String generarTokenAutenticacion(UsuarioModel usuario) {
        // aqui generaremos el token con JWT
        return "asdfsjfhdskjhfkjdshfjdbsfkjbdskjfbdskjbfdsbfdsbfnbdsjfbdsjbfdsjbjdsbfjdsbfjbdsjbdsjbdfsj";
    }
    
   @PostMapping("/crearUsuario")
    public RespuestaGenerica crearusuario(@RequestBody UsuarioModel usuario) {
        try {
            // validamos antes de guardar el usuario
            //verificar si el nombre de usuario ya existe
            String username=usuario.getUsername();

            UsuarioModel existingUsuario = usuarioService.findByUsername(username);

            if (existingUsuario != null) {
                logger.info(MensajesParametrizados.MENSAJE_USUARIO_EXISTENTE);
                throw new ErrorGeneral(MensajesParametrizados.MENSAJE_USUARIO_EXISTENTE);
                // return ResponseEntity.status(400).body("Usuario ya existe");
            }
            EmpleadoModel nuevoEmpleado= empleadoRepository.save(usuario.getEmpleado());
            usuario.setEmpleado(nuevoEmpleado);

            usuarioService.guardarUsuario(usuario);
            return new RespuestaGenerica(MensajesParametrizados.MENSAJE_CREAR_USUARIO_EXITOSO);  

        } catch (ErrorGeneral e) {
            return new RespuestaGenerica(e.getMessage());
        } catch (DataIntegrityViolationException e) {
            logger.error(MensajesParametrizados.MENSAJE_ERROR_BASE_DATOS,(e.getMessage()));
            return new RespuestaGenerica(MensajesParametrizados.MENSAJE_ERROR_BASE_DATOS);
        } catch (Exception e) {
            logger.error(MensajesParametrizados.MENSAJE_ERROR_INTERNO_SERVIDOR,(e.getMessage()));
            return new RespuestaGenerica(MensajesParametrizados.MENSAJE_ERROR_INTERNO_SERVIDOR);
        }
    } 
    @GetMapping("/buscar")
    public ResponseEntity<?> buscarNombreUsuario(@RequestParam String nombreUsuario) {
        
        try {
            UsuarioModel usuario=usuarioService.findByUsername(nombreUsuario);
        if (usuario == null){
            // return ResponseEntity.notFound().build();
            logger.info(MensajesParametrizados.usuarioNoEncontrado(nombreUsuario));
            throw new ErrorGeneral(MensajesParametrizados.MENSAJE_USUARIO_NO_ENCONTRADO);
        }
        else{
            List<UsuarioModel> usuarios = Collections.singletonList(usuario);
            return ResponseEntity.ok(usuarios);   
            
        }    
        } catch (ErrorGeneral e) {
            logger.error(MensajesParametrizados.MENSAJE_ERROR,(e.getMessage()));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RespuestaGenerica(e.getMessage()));
        } catch (Exception e) {
            logger.error(MensajesParametrizados.MENSAJE_ERROR_INTERNO_SERVIDOR,(e.getMessage()));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new RespuestaGenerica(MensajesParametrizados.errorServidor(e.getMessage())));
        }
        
    }
    @PutMapping("/editarUsuario")
    public ResponseEntity<?> editarUsuario(@RequestParam int idUsuario, @RequestBody UsuarioModel usuarioActualizado) {
        try {
            // Buscar el usuario existente por su ID
            UsuarioModel usuarioExistente = usuarioService.findById(idUsuario);

            if (usuarioExistente == null) {
                // return ResponseEntity.status(404).body("Usuario no encontrado");
                logger.info(MensajesParametrizados.usuarioNoEncontradoPorId(idUsuario));
                throw new ErrorGeneral(MensajesParametrizados.usuarioNoEncontradoPorId(idUsuario));
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
            logger.error(MensajesParametrizados.MENSAJE_ERROR,e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RespuestaGenerica(e.getMessage()));
        } catch (DataIntegrityViolationException e) {
            logger.error(MensajesParametrizados.MENSAJE_ERROR_BASE_DATOS,e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RespuestaGenerica(MensajesParametrizados.errorBaseDatos(e.getMessage())));
        }catch (Exception e) {
            logger.error(MensajesParametrizados.MENSAJE_ERROR_INTERNO_SERVIDOR, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new RespuestaGenerica(MensajesParametrizados.errorServidor(e.getMessage())));
        }
    }
    @DeleteMapping("/eliminarUsuario")
    public RespuestaGenerica eliminarUsuario(@RequestParam int idUsuario) {
        try {
            // Buscar el usuario existente por su ID
            UsuarioModel usuarioExistente = usuarioService.findById(idUsuario);

            if (usuarioExistente == null) {
                logger.info(MensajesParametrizados.usuarioNoEncontradoPorId(idUsuario));
                throw  new UsuarioNoEncontradoException(MensajesParametrizados.usuarioNoEncontradoPorId(idUsuario)); 
            }

            // Eliminar el usuario de la base de datos
            usuarioService.deleteUser(idUsuario);
            return new RespuestaGenerica(MensajesParametrizados.MENSAJE_ELIMINAR_USUARIO_EXITOSO);

        }catch (UsuarioNoEncontradoException e) {
            logger.error(MensajesParametrizados.MENSAJE_USUARIO_NO_ENCONTRADO,e.getMessage());
            return new RespuestaGenerica(e.getMessage());
        }catch (Exception e) {
            logger.error(MensajesParametrizados.MENSAJE_ERROR_INTERNO_SERVIDOR,(e.getMessage()));
            return new RespuestaGenerica(MensajesParametrizados.errorServidor(MensajesParametrizados.MENSAJE_ERROR_INTERNO_SERVIDOR));
        }
    }
}
