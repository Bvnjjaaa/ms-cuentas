package cl.novabank.cuentas.repository;

import cl.novabank.cuentas.model.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CuentaRepository extends JpaRepository<Cuenta, Long> {

    Optional<Cuenta> findByNumeroCuenta(String numeroCuenta);

    List<Cuenta> findByRutTitular(String rutTitular);

    boolean existsByNumeroCuenta(String numeroCuenta);
}
