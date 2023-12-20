package TDB.MsGestionUsuarios.Message;

public class MensajesParametrizados {
    public static final String MENSAJE_AUTENTICACION_EXITOSA = "Autenticación exitosa. Token:";
    public static final String MENSAJE_ERROR_AUTENTICACION = "Error de autenticación. Verifica tu nombre de usuario y contraseña.";
    public static final String MENSAJE_CREAR_USUARIO_EXITOSO = "Usuario creado exitosamente.";
    public static final String MENSAJE_USUARIO_EXISTENTE = "El usuario ya existe";
    public static final String MENSAJE_USUARIO_NO_ENCONTRADO = "Usuario no encontrado:";
    public static final String MENSAJE_USUARIO_NO_ENCONTRADO_ID = "Usuario no encontrado: ";
    public static final String MENSAJE_USUARIO_EDITADO_EXITOSO = "Usuario editado exitosamente";
    public static final String MENSAJE_ERROR_BASE_DATOS = "Error en la base de datos: ";
    public static final String MENSAJE_ERROR_INTERNO_SERVIDOR = "Error interno del servidor: ";
    public static final String MENSAJE_ELIMINAR_USUARIO_EXITOSO = "Usuario eliminado exitosamente";
    public static final String MENSAJE_ERROR = "Error ";


    public static String usuarioNoEncontrado(String nombreUsuario) {
        return String.format(MENSAJE_USUARIO_NO_ENCONTRADO, nombreUsuario);
    }
    public static String usuarioNoEncontradoPorId(int idUsuario) {
        return MENSAJE_USUARIO_NO_ENCONTRADO_ID.replace("{}", String.valueOf(idUsuario));
    }

    public static String errorBaseDatos(String mensajeError) {
        return MENSAJE_ERROR_BASE_DATOS.replace("{}", mensajeError);
    }

    public static String errorServidor(String mensajeError) {
        return MENSAJE_ERROR_INTERNO_SERVIDOR.replace("{}", mensajeError);
    }

}
