package com.api_park.demo_api_parking.util;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EstacionamentoUtils {

    public static String gerarRecibo(){
        LocalDateTime localDateTime = LocalDateTime.now();
        String recibo = localDateTime.toString().substring(0,19);
        return recibo
                .replace("-", "")
                .replace(":", "")
                .replace("T", "-");
    }

}
