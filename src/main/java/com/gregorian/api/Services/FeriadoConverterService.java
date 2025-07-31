package com.gregorian.api.Services;

import com.gregorian.api.Controllers.AdminController;
import com.gregorian.api.Models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.gregorian.api.DTO.FeriadoCadastroDTO;
import com.gregorian.api.DTO.FeriadoEditDTO;
import com.gregorian.api.DTO.FeriadoResponseDTO;
import com.gregorian.api.Models.Feriado;
import com.gregorian.api.Interfaces.DateConverterInterface;
import com.gregorian.api.Interfaces.FeriadoConverterInterface;
import com.gregorian.api.Interfaces.FeriadoListInterface;

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

        dto.add(linkTo(methodOn(AdminController.class).feriadoCreate(null, null)).withRel("create").withType("POST"));
        dto.add(linkTo(methodOn(AdminController.class).feriadoEdit(null, null)).withRel("update").withType("PUT"));
        dto.add(linkTo(methodOn(AdminController.class).feriadoDelete(dto.getId(), null)).withRel("delete").withType("DELETE"));
        dto.add(linkTo(methodOn(AdminController.class).feriadoByEmpresa(Pageable.unpaged(), null)).withRel("findByEmpresa").withType("GET"));
        dto.add(linkTo(methodOn(AdminController.class).feriadoById(dto.getId(), null)).withRel("findByID").withType("GET"));
        dto.add(linkTo(methodOn(AdminController.class).feriadoById(dto.getId(), null)).withRel("findByID").withType("GET"));

        return dto;
    }
    
}
