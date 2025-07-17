package com.gregoryan.api.Services;

import com.gregoryan.api.Controllers.AdminController;
import com.gregoryan.api.Models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.gregoryan.api.DTO.DiaBloqueadoCadastroDTO;
import com.gregoryan.api.DTO.DiaBloqueadoEditDTO;
import com.gregoryan.api.DTO.DiaBloqueadoResponseDTO;
import com.gregoryan.api.Models.DiaBloqueado;
import com.gregoryan.api.Interfaces.DiaBloqueadoConverterInterface;
import com.gregoryan.api.Interfaces.DiaBloqueadoListInterface;

@Service
public class DiaBloqueadoConverterService implements DiaBloqueadoConverterInterface{

    @Autowired
    private DateConverterService dataConverter;
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
    public DiaBloqueado toDiaBloqueado(DiaBloqueadoEditDTO dto, Usuario usuario) {
        DiaBloqueado diaBloqueado = diaBloqueadoList.list(dto.id(), usuario);
        diaBloqueado.setNome(dto.nome());
        diaBloqueado.setDia(dataConverter.toCalendar(dto.dia()));

        return diaBloqueado;
    }

    @Override
    public DiaBloqueadoResponseDTO toResponseDTO(DiaBloqueado diaBloqueado) {
        var dto = new DiaBloqueadoResponseDTO(
            diaBloqueado.getId(),
            diaBloqueado.getNome(),
            diaBloqueado.getDia());

        dto.add(linkTo(methodOn(AdminController.class).diaBloqueadoCreate(null, null)).withRel("create").withType("POST"));
        dto.add(linkTo(methodOn(AdminController.class).diaBloqueadoEdit(null, null)).withRel("update").withType("PUT"));
        dto.add(linkTo(methodOn(AdminController.class).diaBloqueadoDelete(dto.getId(), null)).withRel("delete").withType("DELETE"));
        dto.add(linkTo(methodOn(AdminController.class).diaBloqueadoByEmpresa(Pageable.unpaged(), null)).withRel("findByEmpresa").withType("GET"));
        dto.add(linkTo(methodOn(AdminController.class).diaBloqueadoByID(dto.getId(), null)).withRel("findByID").withType("GET"));

        return dto;
    }
    
}
