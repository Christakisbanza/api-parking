package com.api_park.demo_api_parking.repository;

import com.api_park.demo_api_parking.entity.ClientVaga;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientVagaRepository extends JpaRepository<ClientVaga, Long> {

    Optional<ClientVaga> findByReciboAndDataSaidaIsNull(String recibo);

    long countByClientCpfAndDataSaidaIsNotNull(String cpf);

}
