package com.api_park.demo_api_parking.services;


import com.api_park.demo_api_parking.entity.Client;
import com.api_park.demo_api_parking.exception.CpfUniqueViolationException;
import com.api_park.demo_api_parking.exception.EntityNotFoundException;
import com.api_park.demo_api_parking.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@RequiredArgsConstructor
@Service
public class ClientServices {

    private final ClientRepository clientRepository;

    @Transactional
    public void save(Client client){
        try {
            clientRepository.save(client);
        }catch (DataIntegrityViolationException e){
            throw new CpfUniqueViolationException(String.format("CPF: %s não já existe no sistema", client.getCpf()));
        }
    }

    @Transactional(readOnly = true)
    public List<Client> findAll(){
        return clientRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Client findById(Long id){
        return clientRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Client id: %s não encontrada", id))
        );
    }
}
