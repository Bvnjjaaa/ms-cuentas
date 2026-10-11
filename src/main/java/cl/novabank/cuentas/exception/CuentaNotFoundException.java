package cl.novabank.cuentas.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CuentaNotFoundException extends RuntimeException {

    public CuentaNotFoundException(String message) {
        super(message);
    }
}
