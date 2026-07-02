package com.example.api_gateway;

import org.junit.jupiter.api.Test;

/**
 * Prueba que la aplicación se pueda iniciar llamando al método main.
 * Verifica que no haya errores al levantar el contexto de Spring.
 */
class ApiGatewayApplicationMainTest {

    /**
     * Ejecuta el método main con opciones que evitan que el servidor web
     * se quede escuchando, solo para confirmar que arranca sin problemas.
     */
    @Test
    void applicationStartsViaMain() {
        ApiGatewayApplication.main(new String[]{
            "--spring.main.web-application-type=none",
            "--server.port=0"
        });
    }
}
