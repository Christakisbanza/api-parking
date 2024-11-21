package com.api_park.demo_api_parking.services;


import com.api_park.demo_api_parking.entity.Client;
import com.api_park.demo_api_parking.entity.ClientVaga;
import com.api_park.demo_api_parking.entity.Vaga;
import com.api_park.demo_api_parking.util.EstacionamentoUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class EstacionamentoServices {
    private final ClientVagaServices clientVagaServices;
    private final ClientServices clientServices;
    private final VagaServices vagaServices;

    @Transactional
    public ClientVaga checkIn(ClientVaga clientVaga){
        Client client = clientServices.findByCpf(clientVaga.getClient().getCpf());
        clientVaga.setClient(client);

        Vaga vaga = vagaServices.findVagaLivre();
        vaga.setStatus(Vaga.StatusVaga.OCUPADA);

        clientVaga.setVaga(vaga);
        clientVaga.setDataEntrada(LocalDateTime.now());
        clientVaga.setRecibo(EstacionamentoUtils.gerarRecibo());

        return clientVagaServices.save(clientVaga);
    }
}
