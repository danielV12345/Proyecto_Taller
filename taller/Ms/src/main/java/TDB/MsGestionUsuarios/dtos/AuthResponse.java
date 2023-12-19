package TDB.MsGestionUsuarios.dtos;


import java.io.Serializable;
 
public class AuthResponse implements Serializable {

    private final String token;
    private final String nombres;
    private final String apellidos;

    private static final long serialVersionUID = 1L;

    public AuthResponse(String token, String nombres, String apellidos) {
        this.token = token;
        this.nombres = nombres;
        this.apellidos = apellidos;
    }

    public String getToken() {
        return token;
    }

    public String getNombres() {
        return nombres;
    }

    public String getApellidos() {
        return apellidos;
    }
}
