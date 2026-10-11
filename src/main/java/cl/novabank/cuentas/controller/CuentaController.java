package cl.novabank.cuentas.controller;

import cl.novabank.cuentas.model.Cuenta;
import cl.novabank.cuentas.service.CuentaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/cuentas")
@RequiredArgsConstructor
public class CuentaController {

    private final CuentaService cuentaService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cuenta crearCuenta(@Valid @RequestBody Cuenta cuenta) {
        return cuentaService.crearCuenta(cuenta);
    }

    @GetMapping
    public List<Cuenta> listarTodas() {
        return cuentaService.listarTodas();
    }

    @GetMapping("/{numeroCuenta}")
    public Cuenta obtenerPorNumero(@PathVariable String numeroCuenta) {
        return cuentaService.obtenerPorNumero(numeroCuenta);
    }

    @GetMapping("/titular/{rut}")
    public List<Cuenta> obtenerPorTitular(@PathVariable String rut) {
        return cuentaService.obtenerPorRut(rut);
    }

    @PutMapping("/{numeroCuenta}/saldo")
    public Cuenta actualizarSaldo(@PathVariable String numeroCuenta,
                                  @RequestParam Double monto,
                                  @RequestParam String operacion) {
        return cuentaService.actualizarSaldo(numeroCuenta, monto, operacion);
    }

    @PutMapping("/{numeroCuenta}/estado")
    public Cuenta cambiarEstado(@PathVariable String numeroCuenta,
                                @RequestParam String estado) {
        return cuentaService.cambiarEstadoCuenta(numeroCuenta, estado);
    }
}
