package com.gregoryan.api.Services;


import java.time.LocalTime;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gregoryan.api.DTO.DiaCadastroDTO;
import com.gregoryan.api.DTO.DiaEditDTO;
import com.gregoryan.api.DTO.DiaResponseDTO;
import com.gregoryan.api.DTO.StatusDiaResponseDTO;
import com.gregoryan.api.Exception.EntityDontExistException;
import com.gregoryan.api.Models.Dias;
import com.gregoryan.api.Services.Crud.DiasService;
import com.gregoryan.api.Services.Interfaces.DiaConverterInterface;
import com.gregoryan.api.Services.Interfaces.StatusDiaConverterInterface;

@Service
public class DiaConverterService implements DiaConverterInterface{

    @Autowired
    private DataConverter dataConverter;
    @Autowired
    private DiasService diasService;
    @Autowired
    private StatusDiaConverterInterface statusDiaConverter;

    @Override
    public Dias toDia(DiaCadastroDTO dto) {
        Dias dia = new Dias();

        //dia.setId(dto.id());
        dia.setNome(dto.nome());
        dia.setIntervaloSessaoInMinutes(dto.intervaloSessaoInMinutes());
        dia.setDuracaoSessaoInMinutes(dto.duracaoSessaoInMinutes());
        dia.setInicio(dataConverter.getHour(dto.inicio()));
        dia.setFim(dataConverter.getHour(dto.fim()));

        return dia;
    }

    @Override
    public Dias toDia(DiaEditDTO dto) {
        
        Dias dia = diasService.findById(dto.idDia()).orElseThrow(() -> new EntityDontExistException("Dia não encontrado"));

        dia.setIntervaloSessaoInMinutes(dto.intervaloSessaoInMinutes());
        dia.setDuracaoSessaoInMinutes(dto.duracaoSessaoInMinutes());
        dia.setInicio(dataConverter.getHour(dto.inicio()));
        dia.setFim(dataConverter.getHour(dto.fim()));

        return dia;
    }

    @Override
    public DiaResponseDTO toResponseDTO(Dias dia) {

        StatusDiaResponseDTO statusDia = statusDiaConverter.toResponseDTO(dia.getStatusDia());

        return new DiaResponseDTO(dia.getId(), dia.getNome(), dia.getIntervaloSessaoInMinutes(),
                                  dia.getDuracaoSessaoInMinutes(), statusDia, dia.getInicio(), dia.getFim());
    }
    
}
