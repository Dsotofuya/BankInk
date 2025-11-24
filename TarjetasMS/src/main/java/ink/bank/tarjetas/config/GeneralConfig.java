package ink.bank.tarjetas.config;

import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/** Configuración general para JPA y escaneo de componentes. */
@Configuration
@EnableJpaRepositories(basePackages = "ink.bank.tarjetas.jpa.repository")
@EntityScan(basePackages = "ink.bank.tarjetas.jpa.entity")
@ComponentScan(basePackages = "ink.bank.tarjetas")
public class GeneralConfig {}
