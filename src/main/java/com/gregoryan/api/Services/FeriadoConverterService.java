package com.gregoryan.api.Services;

import com.gregoryan.api.Controllers.AdminController;
import com.gregoryan.api.Models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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

        dto.add(linkTo(methodOn(AdminController.class).feriadoCreate(null, null)).withRel("create").withType("POST"));
        dto.add(linkTo(methodOn(AdminController.class).feriadoEdit(null, null)).withRel("update").withType("PUT"));
        dto.add(linkTo(methodOn(AdminController.class).feriadoDelete(dto.getId(), null)).withRel("delete").withType("DELETE"));
        dto.add(linkTo(methodOn(AdminController.class).feriadoByEmpresa(Pageable.unpaged(), null)).withRel("findByEmpresa").withType("GET"));
        dto.add(linkTo(methodOn(AdminController.class).feriadoById(dto.getId(), null)).withRel("findByID").withType("GET"));
        dto.add(linkTo(methodOn(AdminController.class).feriadoById(dto.getId(), null)).withRel("findByID").withType("GET"));

        return dto;
    }
    
}
