package com.api_park.demo_api_parking.services;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
@RequiredArgsConstructor
@Service
public class EstacionamentoServices {
    private final ClientVagaServices clientVagaServices;
    private final ClientServices clientServices;
    private final VagaServices vagaServices;
}
