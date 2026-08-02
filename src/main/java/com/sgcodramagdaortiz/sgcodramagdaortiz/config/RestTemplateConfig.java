package com.sgcodramagdaortiz.sgcodramagdaortiz.config;

// Importa Bean y Configuration para declarar componentes de Spring
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// Importa RestTemplate, la clase de Spring usada para
// realizar peticiones HTTP hacia servicios externos
import org.springframework.web.client.RestTemplate;

/**
 * ============================================================
 * RESTTEMPLATECONFIG.JAVA
 * SISTEMA DE GESTIÓN DE CITAS ODONTOLÓGICAS
 * ============================================================
 *
 * Configuración que registra un RestTemplate como Bean
 * de Spring, para que pueda inyectarse (mediante
 * constructor) en cualquier servicio que necesite
 * consumir una API externa.
 *
 * Se usa específicamente en ApiColombiaService.java,
 * para consumir la API pública externa "API Colombia"
 * (https://api-colombia.com), que provee la información
 * de departamentos y ciudades de Colombia.
 */
@Configuration
public class RestTemplateConfig {

    /**
     * Declara el Bean de RestTemplate.
     *
     * Al estar anotado con @Bean, Spring crea una única
     * instancia de RestTemplate y la reutiliza en toda
     * la aplicación (patrón Singleton), en lugar de que
     * cada clase cree su propia instancia con "new".
     *
     * @return instancia de RestTemplate administrada por Spring
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}