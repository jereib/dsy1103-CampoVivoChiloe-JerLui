package com.example.api_gateway;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Prueba de integración que verifica que el contexto de Spring
 * se cargue correctamente en el API Gateway.
 */
@SpringBootTest
class ApiGatewayApplicationTests {

    @Autowired
    private ApplicationContext applicationContext;

    /**
     * Verifica que el contexto de la aplicación se haya levantado
     * y que los beans estén disponibles.
     */
    @Test
    void contextLoads() {
        assertNotNull(applicationContext);
    }

}
