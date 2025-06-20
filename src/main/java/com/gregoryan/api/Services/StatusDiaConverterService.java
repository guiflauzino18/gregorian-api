package com.gregoryan.api.Services;

import com.gregoryan.api.Models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gregoryan.api.DTO.StatusDiaCadastroDTO;
import com.gregoryan.api.DTO.StatusDiaEditDTO;
import com.gregoryan.api.DTO.StatusDiaResponseDTO;
import com.gregoryan.api.Models.StatusDia;
import com.gregoryan.api.Interfaces.StatusDiaConverterInterface;
import com.gregoryan.api.Interfaces.StatusDiaListInterface;

@Service
public class StatusDiaConverterService implements StatusDiaConverterInterface{

    @Autowired
    private StatusDiaListInterface statusDiaList;

    @Override
    public StatusDiaResponseDTO toResponseDTO(StatusDia statusDia) {
        
        return new StatusDiaResponseDTO(statusDia.getId(), statusDia.getNome());
    }

    @Override
    public StatusDia toStatusDia(StatusDiaCadastroDTO dto) {
        StatusDia statusDia = new StatusDia();
        statusDia.setNome(dto.nome());
        return statusDia;
    }

    @Override
    public StatusDia toStatusDia(StatusDiaEditDTO dto, Usuario usuario) {
        StatusDia statusDia = statusDiaList.list(dto.id(), usuario);
        statusDia.setNome(dto.nome());

        return statusDia;
    }
    
}
