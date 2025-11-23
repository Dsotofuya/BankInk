package ink.bank.prueba.config;

import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/** Configuración general para JPA y escaneo de componentes. */
@Configuration
@EnableJpaRepositories(basePackages = "ink.bank.prueba.jpa.repository")
@EntityScan(basePackages = "ink.bank.prueba.jpa.entity")
@ComponentScan(basePackages = "ink.bank.prueba")
public class GeneralConfig {}
