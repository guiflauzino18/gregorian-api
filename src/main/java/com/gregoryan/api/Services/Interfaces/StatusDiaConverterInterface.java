package com.gregoryan.api.Services.Interfaces;

import com.gregoryan.api.DTO.StatusDiaResponseDTO;
import com.gregoryan.api.Models.StatusDia;

public interface StatusDiaConverterInterface {
    StatusDiaResponseDTO toResponseDTO(StatusDia statusDia);
}
