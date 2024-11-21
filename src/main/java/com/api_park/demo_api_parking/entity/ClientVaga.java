package com.api_park.demo_api_parking.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;


@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "clients_vagas")
public class ClientVaga {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero_recibo", nullable = false, unique = true, length = 15)
    private String recibo;
    @Column(name = "placa", nullable = false, length = 8)
    private String placa;
    @Column(name = "marca", nullable = false, length = 45)
    private String marca;
    @Column(name = "modelo", nullable = false, length = 45)
    private String modelo;
    @Column(name = "cor", nullable = false, length = 45)
    private String cor;
    @Column(name = "data_entrada", nullable = false)
    private LocalDateTime dataEntrada;
    @Column(name = "data_saida")
    private LocalDateTime dataSaida;
    @Column(name = "valor",columnDefinition = "decimal(7,2)")
    private BigDecimal valor;
    @Column(name = "desconto", columnDefinition = "decimal(7,2)")
    private BigDecimal desconto;

    @ManyToOne
    @JoinColumn(name = "id_clients", nullable = false)
    private Client client;
    @ManyToOne
    @JoinColumn(name = "id_vagas", nullable = false)
    private Vaga vaga;


    @CreatedDate
    @Column(name = "dateCreation")
    private LocalDate dateCreation;

    @LastModifiedDate
    @Column(name = "dateUpdate")
    private LocalDate dateUpDate;

    @CreatedBy
    @Column(name = "createdBy")
    private String createdBy;

    @LastModifiedBy
    @Column(name = "updatedBy")
    private String upDatedBy;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientVaga that = (ClientVaga) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
