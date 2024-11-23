package com.api_park.demo_api_parking.repository;

import com.api_park.demo_api_parking.entity.ClientVaga;
import com.api_park.demo_api_parking.repository.projection.ClienteVagaProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientVagaRepository extends JpaRepository<ClientVaga, Long> {

    Optional<ClientVaga> findByReciboAndDataSaidaIsNull(String recibo);

    long countByClientCpfAndDataSaidaIsNotNull(String cpf);

    Page<ClienteVagaProjection> findAllByCpf(String cpf, Pageable pageable);

    Page<ClienteVagaProjection> findAllByClientUserId(Long id, Pageable pageable);
}
