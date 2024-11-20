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

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;


@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "vagas")
public class Vaga implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code", nullable = false, unique = true, length = 4)
    private String code;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusVaga statusVaga;

    public enum StatusVaga{
        LIVRE, OCUPADA;
    }

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
        Vaga vaga = (Vaga) o;
        return Objects.equals(id, vaga.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
