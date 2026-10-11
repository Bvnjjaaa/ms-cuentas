package cl.novabank.cuentas.service;

import cl.novabank.cuentas.exception.CuentaNotFoundException;
import cl.novabank.cuentas.exception.OperacionInvalidaException;
import cl.novabank.cuentas.model.Cuenta;
import cl.novabank.cuentas.repository.CuentaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CuentaService {

    private static final String TIPO_DEBITO = "DEBITO";
    private static final String TIPO_CREDITO = "CREDITO";
    private static final String ESTADO_ACTIVA = "ACTIVA";
    private static final String ESTADO_BLOQUEADA = "BLOQUEADA";

    private final CuentaRepository cuentaRepository;

    @Transactional
    public Cuenta crearCuenta(Cuenta cuenta) {
        if (cuentaRepository.existsByNumeroCuenta(cuenta.getNumeroCuenta())) {
            throw new OperacionInvalidaException(
                    "Ya existe una cuenta con el numero " + cuenta.getNumeroCuenta());
        }
        if (cuenta.getSaldo() == null || cuenta.getSaldo() < 0) {
            throw new OperacionInvalidaException("El saldo inicial no puede ser negativo");
        }
        return cuentaRepository.save(cuenta);
    }

    @Transactional(readOnly = true)
    public Cuenta obtenerPorNumero(String numeroCuenta) {
        return cuentaRepository.findByNumeroCuenta(numeroCuenta)
                .orElseThrow(() -> new CuentaNotFoundException(
                        "No existe una cuenta con el numero " + numeroCuenta));
    }

    @Transactional(readOnly = true)
    public List<Cuenta> obtenerPorRut(String rutTitular) {
        return cuentaRepository.findByRutTitular(rutTitular);
    }

    @Transactional
    public Cuenta actualizarSaldo(String numeroCuenta, Double monto, String operacion) {
        if (monto == null || monto <= 0) {
            throw new OperacionInvalidaException("El monto debe ser mayor a cero");
        }
        if (operacion == null) {
            throw new OperacionInvalidaException("La operacion es obligatoria (DEBITO o CREDITO)");
        }

        Cuenta cuenta = obtenerPorNumero(numeroCuenta);
        String operacionNormalizada = operacion.trim().toUpperCase();

        switch (operacionNormalizada) {
            case TIPO_DEBITO -> {
                if (cuenta.getSaldo() < monto) {
                    throw new OperacionInvalidaException(
                            "Saldo insuficiente para realizar el debito");
                }
                cuenta.setSaldo(cuenta.getSaldo() - monto);
            }
            case TIPO_CREDITO -> cuenta.setSaldo(cuenta.getSaldo() + monto);
            default -> throw new OperacionInvalidaException(
                    "Operacion no valida: " + operacion + ". Use DEBITO o CREDITO");
        }

        return cuentaRepository.save(cuenta);
    }

    @Transactional
    public Cuenta cambiarEstadoCuenta(String numeroCuenta, String estado) {
        if (estado == null) {
            throw new OperacionInvalidaException("El estado es obligatorio (ACTIVA o BLOQUEADA)");
        }
        String estadoNormalizado = estado.trim().toUpperCase();
        if (!ESTADO_ACTIVA.equals(estadoNormalizado) && !ESTADO_BLOQUEADA.equals(estadoNormalizado)) {
            throw new OperacionInvalidaException(
                    "Estado no valido: " + estado + ". Use ACTIVA o BLOQUEADA");
        }

        Cuenta cuenta = obtenerPorNumero(numeroCuenta);
        cuenta.setEstado(estadoNormalizado);
        return cuentaRepository.save(cuenta);
    }

    @Transactional(readOnly = true)
    public List<Cuenta> listarTodas() {
        return cuentaRepository.findAll();
    }
}
