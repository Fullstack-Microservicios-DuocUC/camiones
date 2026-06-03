package cl.duoc.mineria.camiones.exception;

public class CamionNotFoundException extends RuntimeException {

    public CamionNotFoundException(String mensaje) {
        super(mensaje);
    }
}
