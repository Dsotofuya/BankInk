package ink.bank.transacciones.config;

import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/** Configuración general para JPA y escaneo de componentes. */
@Configuration
@EnableJpaRepositories(basePackages = "ink.bank.transacciones.jpa.repository")
@EntityScan(basePackages = "ink.bank.transacciones.jpa.entity")
@ComponentScan(basePackages = "ink.bank.transacciones")
public class GeneralConfig {}
