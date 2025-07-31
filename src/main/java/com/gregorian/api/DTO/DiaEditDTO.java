package com.gregorian.api.DTO;

public record DiaEditDTO(
        long idDia,
        long intervaloSessaoInMinutes,
        long duracaoSessaoInMinutes,
        long idStatusDia,
        String inicio, String fim
) {
}
