package TDB.MsGestionUsuarios.Excepciones;

public class UsuarioNoEncontradoException extends RuntimeException{
    public UsuarioNoEncontradoException(String mensaje){
        super(mensaje);
    } 
}
