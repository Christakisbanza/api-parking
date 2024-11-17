package com.api_park.demo_api_parking.entity;

import jakarta.persistence.*;
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


@EntityListeners(AuditingEntityListener.class)
@Getter @Setter @NoArgsConstructor
@Entity
@Table(name = "users")
public class User implements Serializable {

    @Getter
    public enum Role{
        ROLE_ADMIN("Admin"),
        ROLE_CLIENT("Client");

        private final String role;

        Role(String role){
            this.role = role;
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, length = 25)
    private Role role = Role.ROLE_CLIENT;

    @Column(name = "name", nullable = false, unique = true, length = 100)
    private String userName;

    @Column(name = "password", nullable = false, length = 200)
    private String passWord;

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
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                '}';
    }
}
