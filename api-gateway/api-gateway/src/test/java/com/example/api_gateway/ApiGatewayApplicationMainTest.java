package com.example.api_gateway;

import org.junit.jupiter.api.Test;

class ApiGatewayApplicationMainTest {

    @Test
    void applicationStartsViaMain() {
        ApiGatewayApplication.main(new String[]{
            "--spring.main.web-application-type=none",
            "--server.port=0"
        });
    }
}
