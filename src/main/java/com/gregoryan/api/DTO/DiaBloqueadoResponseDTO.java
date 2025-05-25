package com.gregoryan.api.DTO;

import java.util.Calendar;

public record DiaBloqueadoResponseDTO(
    long id,
    String nome,
    Calendar dia
) {}
