package com.gregoryan.api.Services;

import org.springframework.stereotype.Service;

import com.gregoryan.api.DTO.StatusDiaResponseDTO;
import com.gregoryan.api.Models.StatusDia;
import com.gregoryan.api.Services.Interfaces.StatusDiaConverterInterface;

@Service
public class StatusDiaConverterService implements StatusDiaConverterInterface{

    @Override
    public StatusDiaResponseDTO toResponseDTO(StatusDia statusDia) {
        
        return new StatusDiaResponseDTO(statusDia.getId(), statusDia.getNome());
    }
    
}
