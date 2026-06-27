package cl.duoc.mineria.camiones.exception;

import java.time.Instant;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    public GlobalExceptionHandler() {
        System.out.println("GlobalExceptionHandler (Módulo Camiones) REGISTRADO DE FORMA AUTOMÁTICA");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidationErrors(MethodArgumentNotValidException ex) {
        System.out.println("[Camiones Error] - Datos del camión inválidos u omitidos por el cliente");

        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
            HttpStatus.BAD_REQUEST,
            "Error de validación en los datos del camión"
        );

        problem.setTitle("Validation Error");
        problem.setProperty("timestamp", Instant.now());

        Map<String, String> errors = ex.getBindingResult().getFieldErrors().stream()
        .collect(Collectors.toMap(
            FieldError::getField,
            fieldError -> fieldError.getDefaultMessage() != null ? fieldError.getDefaultMessage() : "Error de validación",
            (existente, nuevo) -> existente
        ));

        problem.setProperty("errores", errors);
        return problem;
    }

    @ExceptionHandler(CamionNotFoundException.class)
    public ProblemDetail handleCamionNotFound(CamionNotFoundException ex) {
        System.out.println("[Camiones Warning] - Camión solicitado no encontrado: " + ex.getMessage());

        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
            HttpStatus.NOT_FOUND,
            ex.getMessage()
        );

        problem.setTitle("Truck not Found");
        problem.setProperty("timestamp", Instant.now());
        return problem;
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleGeneralException(Exception ex) {
        System.out.println("CRÍTICO - Excepción no controlada en el módulo de camiones: " + ex.getClass().getName());
        ex.printStackTrace();

        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
            HttpStatus.INTERNAL_SERVER_ERROR,
            "Ocurrió un error inesperado dentro del servidor en el módulo de camiones"
        );

        problem.setTitle("Internal Server Error");
        problem.setProperty("timestamp", Instant.now());
        problem.setProperty("tipoExcepcion", ex.getClass().getSimpleName());
        problem.setProperty("mensajeCorto", ex.getMessage());
        return problem;
    }

    @ExceptionHandler(PatenteDuplicadaException.class)
    public ProblemDetail handlePatenteDuplicada(PatenteDuplicadaException ex) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage());
        problem.setTitle("Patente Duplicada");
        problem.setProperty("timestamp", Instant.now());
        return problem;
    }
}
