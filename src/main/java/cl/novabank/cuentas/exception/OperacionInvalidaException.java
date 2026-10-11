package cl.novabank.cuentas.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class OperacionInvalidaException extends RuntimeException {

    public OperacionInvalidaException(String message) {
        super(message);
    }
}
