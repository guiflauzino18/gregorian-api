package com.gregorian.api.Services;

import com.gregorian.api.Models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gregorian.api.DTO.DiaCadastroDTO;
import com.gregorian.api.DTO.DiaEditDTO;
import com.gregorian.api.DTO.DiaResponseDTO;
import com.gregorian.api.DTO.StatusDiaResponseDTO;
import com.gregorian.api.Models.Dias;
import com.gregorian.api.Interfaces.DiaConverterInterface;
import com.gregorian.api.Interfaces.DiaListInterface;
import com.gregorian.api.Interfaces.StatusDiaConverterInterface;

@Service
public class DiaConverterService implements DiaConverterInterface{

    @Autowired
    private DateConverterService dataConverter;
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
    public Dias toDia(DiaEditDTO dto, Usuario usuario) {
        
        Dias dia = diaList.list(dto.idDia(), usuario);
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
