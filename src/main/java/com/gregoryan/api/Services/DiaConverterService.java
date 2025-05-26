package com.gregoryan.api.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gregoryan.api.DTO.DiaCadastroDTO;
import com.gregoryan.api.DTO.DiaEditDTO;
import com.gregoryan.api.DTO.DiaResponseDTO;
import com.gregoryan.api.DTO.StatusDiaResponseDTO;
import com.gregoryan.api.Models.Dias;
import com.gregoryan.api.Models.Empresa;
import com.gregoryan.api.Services.Interfaces.DiaConverterInterface;
import com.gregoryan.api.Services.Interfaces.DiaListInterface;
import com.gregoryan.api.Services.Interfaces.StatusDiaConverterInterface;

@Service
public class DiaConverterService implements DiaConverterInterface{

    @Autowired
    private DataConverter dataConverter;
    @Autowired
    private DiaListInterface diaList;
    @Autowired
    private StatusDiaConverterInterface statusDiaConverter;

    @Override
    public Dias toDia(DiaCadastroDTO dto) {
        Dias dia = new Dias();

        dia.setNome(dto.nome());
        dia.setIntervaloSessaoInMinutes(dto.intervaloSessaoInMinutes());
        dia.setDuracaoSessaoInMinutes(dto.duracaoSessaoInMinutes());
        dia.setInicio(dataConverter.getHour(dto.inicio()));
        dia.setFim(dataConverter.getHour(dto.fim()));

        return dia;
    }

    @Override
    public Dias toDia(DiaEditDTO dto, Empresa empresa) {
        
        Dias dia = diaList.list(dto.idDia(), empresa);
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

    @Override
    public DiaEditDTO toEditDTO(Dias dia) {
        System.out.println(dia.getFim());
        return new DiaEditDTO(
            dia.getId(),
            dia.getIntervaloSessaoInMinutes(),
            dia.getDuracaoSessaoInMinutes(),
            dia.getStatusDia().getId(),
            dia.getInicio().toString(),
            dia.getFim().toString());

    }

    @Override
    public DiaEditDTO toEditDTO(DiaCadastroDTO dto, Dias dias) {
        DiaEditDTO diaEditDTO = new DiaEditDTO(dto.id(),
        dto.intervaloSessaoInMinutes(),
        dto.duracaoSessaoInMinutes(),
        dias.getStatusDia().getId(),
        dto.inicio(),
        dto.fim());

        return diaEditDTO;

        //long id, String nome, long intervaloSessaoInMinutes, long duracaoSessaoInMinutes, String inicio, String fim
    }
    
}
