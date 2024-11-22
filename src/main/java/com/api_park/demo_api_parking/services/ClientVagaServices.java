package com.api_park.demo_api_parking.services;


import com.api_park.demo_api_parking.entity.ClientVaga;
import com.api_park.demo_api_parking.exception.EntityNotFoundException;
import com.api_park.demo_api_parking.repository.ClientVagaRepository;
import com.api_park.demo_api_parking.repository.projection.ClienteVagaProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ClientVagaServices {

    private final ClientVagaRepository clientVagaRepository;

    @Transactional
    public ClientVaga save(ClientVaga clientVaga){
        return clientVagaRepository.save(clientVaga);
    }

    @Transactional
    public ClientVaga findByRecibo(String recibo){
        return clientVagaRepository.findByReciboAndDataSaidaIsNull(recibo).orElseThrow(
                () -> new EntityNotFoundException(String.format("Recibo: %s não encontrado", recibo))
        );
    }

    @Transactional
    public long getTotalDeVezesEstacionamentoCompleto(String cpf){
        return clientVagaRepository.countByClientCpfAndDataSaidaIsNotNull(cpf);
    }

    @Transactional
    public Page<ClienteVagaProjection> findAllByCpf(String cpf, Pageable pageable){
        return clientVagaRepository.findAllByCpf(cpf, pageable);
    }
}
