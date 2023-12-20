package TDB.MsGestionUsuarios.Excepciones;

public class AutenticacionException extends RuntimeException{
    public AutenticacionException(String mensaje) {
        super(mensaje);
    }
}
