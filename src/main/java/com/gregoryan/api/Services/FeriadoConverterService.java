package com.gregoryan.api.Services;

import com.gregoryan.api.Models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gregoryan.api.DTO.FeriadoCadastroDTO;
import com.gregoryan.api.DTO.FeriadoEditDTO;
import com.gregoryan.api.DTO.FeriadoResponseDTO;
import com.gregoryan.api.Models.Feriado;
import com.gregoryan.api.Interfaces.DateConverterInterface;
import com.gregoryan.api.Interfaces.FeriadoConverterInterface;
import com.gregoryan.api.Interfaces.FeriadoListInterface;

@Service
public class FeriadoConverterService implements FeriadoConverterInterface{

    @Autowired
    private DateConverterInterface dataConverter;
    @Autowired
    private FeriadoListInterface feriadoList;

    @Override
    public Feriado toFeriado(FeriadoCadastroDTO dto) {
        Feriado feriado = new Feriado();
        feriado.setNome(dto.nome());
        feriado.setDia(dataConverter.toCalendar(dto.dia()));

        return feriado;
    }

    @Override
    public Feriado toFeriado(FeriadoEditDTO dto, Usuario usuario) {
        Feriado feriado = feriadoList.list(dto.id(), usuario);
        feriado.setNome(dto.nome());
        feriado.setDia(dataConverter.toCalendar(dto.data()));
        return feriado;
    }

    @Override
    public FeriadoResponseDTO toResponseDTO(Feriado feriado) {
        FeriadoResponseDTO dto = new FeriadoResponseDTO(
            feriado.getId(),
            feriado.getNome(),
            feriado.getDia()
        );

        return dto;
    }
    
}
