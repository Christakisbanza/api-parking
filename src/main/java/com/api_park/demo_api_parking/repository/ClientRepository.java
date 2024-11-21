package com.api_park.demo_api_parking.repository;

import com.api_park.demo_api_parking.entity.Client;
import com.api_park.demo_api_parking.repository.projection.ClientProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    @Query("select c from Client c")
    Page<ClientProjection> findAllPageable(Pageable pageable);

    Client findByUserId(Long id);

    Optional<Client> findByCpf(String cpf);
}
