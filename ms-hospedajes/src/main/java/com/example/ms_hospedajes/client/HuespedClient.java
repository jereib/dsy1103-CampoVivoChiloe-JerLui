package com.example.ms_hospedajes.client;

import com.example.ms_hospedajes.dto.HuespedDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-huespedes", url = "http://localhost:8082")
public interface HuespedClient {
    @GetMapping("/api/v1/huespedes/{id}")
    HuespedDTO obtenerHuesped(@PathVariable Long id);
}
