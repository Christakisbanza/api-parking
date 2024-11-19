package com.api_park.demo_api_parking.entity;

import jakarta.persistence.*;
import lombok.*;
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
@Table(name = "clientes")
public class Client implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", nullable = false, length = 100)
    private String name;

    @Column(name = "cpf", nullable = false, unique = true, length = 11)
    private String cpf;

    @OneToOne
    @JoinColumn(name = "id_user", nullable = false)
    private User user;

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
        Client client = (Client) o;
        return Objects.equals(id, client.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

}
