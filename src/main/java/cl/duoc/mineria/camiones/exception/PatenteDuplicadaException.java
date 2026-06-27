package cl.duoc.mineria.camiones.exception;

public class PatenteDuplicadaException extends RuntimeException {
    public PatenteDuplicadaException(String mensaje) {
        super(mensaje);
    }
}