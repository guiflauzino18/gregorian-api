package com.gregoryan.api.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gregoryan.api.DTO.DiaBloqueadoCadastroDTO;
import com.gregoryan.api.DTO.DiaBloqueadoEditDTO;
import com.gregoryan.api.DTO.DiaBloqueadoResponseDTO;
import com.gregoryan.api.Models.DiaBloqueado;
import com.gregoryan.api.Models.Empresa;
import com.gregoryan.api.Services.Crud.DiaBloqueadoService;
import com.gregoryan.api.Services.Interfaces.DiaBloqueadoConverterInterface;
import com.gregoryan.api.Services.Interfaces.DiaBloqueadoListInterface;

@Service
public class DiaBloqueadoConverterService implements DiaBloqueadoConverterInterface{

    @Autowired
    private DataConverter dataConverter;
    @Autowired
    private DiaBloqueadoListInterface diaBloqueadoList;

    @Override
    public DiaBloqueado toDiaBloqueado(DiaBloqueadoCadastroDTO dto) {
        DiaBloqueado diaBloqueado = new DiaBloqueado();
        diaBloqueado.setNome(dto.nome());
        diaBloqueado.setDia(dataConverter.toCalendar(dto.dia()));

        return diaBloqueado;
    }

    @Override
    public DiaBloqueado toDiaBloqueado(DiaBloqueadoEditDTO dto, Empresa empresa) {
        DiaBloqueado diaBloqueado = diaBloqueadoList.list(dto.id(), empresa);
        diaBloqueado.setNome(dto.nome());
        diaBloqueado.setDia(dataConverter.toCalendar(dto.dia()));

        return diaBloqueado;
    }

    @Override
    public DiaBloqueadoResponseDTO toResponseDTO(DiaBloqueado diaBloqueado) {
        return new DiaBloqueadoResponseDTO(
            diaBloqueado.getId(),
            diaBloqueado.getNome(),
            diaBloqueado.getDia());
    }
    
}
