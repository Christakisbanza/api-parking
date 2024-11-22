package com.api_park.demo_api_parking.repository.projection;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public interface ClienteVagaProjection {
     String getPlaca();
     String getMarca();
     String getModelo();
     String getCor();
     String getClientCpf();
     String getRecibo();
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
     LocalDateTime getDataEntrada();
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
     LocalDateTime gatDataSaida();
     String gatVagaCode();
     BigDecimal getValor();
     BigDecimal getDesconto();
}
