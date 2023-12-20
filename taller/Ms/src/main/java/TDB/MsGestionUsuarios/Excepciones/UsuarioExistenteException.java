package TDB.MsGestionUsuarios.Excepciones;

public class UsuarioExistenteException extends RuntimeException{
    public UsuarioExistenteException(String mensaje){
        super(mensaje);
    }

}
