package com.example.ms_hospedajes.dto;

import lombok.Data;

@Data
public class SocioDTO {
    private Long id;
    private String socio;
    private String predio;
    private Integer capacidad;
}
