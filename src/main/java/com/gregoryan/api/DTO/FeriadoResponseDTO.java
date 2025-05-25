package com.gregoryan.api.DTO;

import java.util.Calendar;

public record FeriadoResponseDTO(
    long id,
    String nome,
    Calendar dia
) {}
