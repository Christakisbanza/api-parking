package com.api_park.demo_api_parking.services;


import com.api_park.demo_api_parking.entity.ClientVaga;
import com.api_park.demo_api_parking.repository.ClientVagaRepository;
import lombok.RequiredArgsConstructor;
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
}
