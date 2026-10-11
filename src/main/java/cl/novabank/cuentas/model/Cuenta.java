package cl.novabank.cuentas.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cuentas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cuenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "numero_cuenta", nullable = false, unique = true)
    private String numeroCuenta;

    @NotBlank
    @Column(name = "rut_titular", nullable = false)
    private String rutTitular;

    @NotBlank
    @Column(name = "tipo_cuenta", nullable = false)
    private String tipoCuenta;

    @NotNull
    @Column(nullable = false)
    private Double saldo;

    @NotBlank
    @Column(nullable = false)
    private String estado;
}
