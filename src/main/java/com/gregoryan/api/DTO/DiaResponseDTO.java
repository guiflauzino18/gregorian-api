package com.gregoryan.api.DTO;

import java.time.LocalTime;

public record DiaResponseDTO(
    long id,
    String nome,
    long intervaloSessaoInMinutes,
    long duracaoSessaoInMinutes,
    StatusDiaResponseDTO status,
    LocalTime inicio,
    LocalTime fim

) {}
