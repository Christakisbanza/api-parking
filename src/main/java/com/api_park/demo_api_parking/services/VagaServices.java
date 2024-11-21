package com.api_park.demo_api_parking.services;


import com.api_park.demo_api_parking.entity.Vaga;
import com.api_park.demo_api_parking.exception.CodeUniqueViolationException;
import com.api_park.demo_api_parking.exception.EntityNotFoundException;
import com.api_park.demo_api_parking.repository.VagaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.api_park.demo_api_parking.entity.Vaga.StatusVaga.LIVRE;

@RequiredArgsConstructor
@Service
public class VagaServices {

    private final VagaRepository vagaRepository;


    @Transactional
    public Vaga save(Vaga vaga){
        try{
            return vagaRepository.save(vaga);
        }catch (DataIntegrityViolationException e){
            throw new CodeUniqueViolationException(String.format("Vaga com código %s já cadastrada", vaga.getCode()));
        }
    }

    @Transactional(readOnly = true)
    public Vaga findByCode(String code){
        return vagaRepository.findByCode(code).orElseThrow(
                () -> new EntityNotFoundException(String.format("Vaga com código %s não encontrada", code))
        );
    }

    @Transactional(readOnly = true)
    public Vaga findVagaLivre(){
        return vagaRepository.findFirstByStatus(LIVRE).orElseThrow(
                () -> new EntityNotFoundException("Neng=huma vaga livre foi encontrada")
        );
    }

}
