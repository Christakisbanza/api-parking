package com.api_park.demo_api_parking.services;


import com.api_park.demo_api_parking.entity.Client;
import com.api_park.demo_api_parking.exception.CpfUniqueViolationException;
import com.api_park.demo_api_parking.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Service
public class ClientServices {

    private final ClientRepository clientRepository;

    @Transactional
    public Client save(Client client){
        try {
            return clientRepository.save(client);
        }catch (DataIntegrityViolationException e){
            throw new CpfUniqueViolationException(String.format("CPF: %s não já existe no sistema", client.getCpf()));
        }
    }
}
