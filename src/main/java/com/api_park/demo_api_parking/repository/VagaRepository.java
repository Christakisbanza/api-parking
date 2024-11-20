package com.api_park.demo_api_parking.repository;

import com.api_park.demo_api_parking.entity.Vaga;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VagaRepository extends JpaRepository<Vaga, Long> {

}
