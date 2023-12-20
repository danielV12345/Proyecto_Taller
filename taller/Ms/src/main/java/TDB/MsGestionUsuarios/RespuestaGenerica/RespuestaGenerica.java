package TDB.MsGestionUsuarios.RespuestaGenerica;

public class RespuestaGenerica {
    private String mensaje;
    public RespuestaGenerica(String mensaje) {
        this.mensaje = mensaje;
    }
    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
