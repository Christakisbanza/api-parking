package com.api_park.demo_api_parking.web.controller.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EstacionamentoResponseDto {

    private String placa;
    private String marca;
    private String modelo;
    private String cor;
    private String clientCpf;
    private String recibo;
    private LocalDateTime dataEntrada;
    private LocalDateTime dataSaida;
    private String vagaCode;
    private BigDecimal valor;
    private BigDecimal desconto;


}
