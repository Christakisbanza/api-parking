package com.api_park.demo_api_parking.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Getter @Setter @NoArgsConstructor
@Entity
@Table(name = "users")
public class User implements Serializable {

    public enum Role{
        ROLE_ADMIN,
        ROLE_CLIENT;
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

    @Column(name = "dateCreation")
    private LocalDate dateCreation;

    @Column(name = "dateUpdate")
    private LocalDate dateUpDate;

    @Column(name = "createdBy")
    private String createdBy;

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
