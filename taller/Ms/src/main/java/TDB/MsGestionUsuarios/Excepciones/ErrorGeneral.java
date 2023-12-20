package TDB.MsGestionUsuarios.Excepciones;

public class ErrorGeneral extends RuntimeException{
    public ErrorGeneral(String mensaje){
        super(mensaje);
    }
}
