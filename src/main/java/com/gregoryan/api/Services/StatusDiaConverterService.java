package com.gregoryan.api.Services;

import com.gregoryan.api.Controllers.AdminController;
import com.gregoryan.api.Models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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
        
        var dto = new StatusDiaResponseDTO(statusDia.getId(), statusDia.getNome());

        dto.add(linkTo(methodOn(AdminController.class).statusDiaCreate(null, null)).withRel("create").withType("POST"));
        dto.add(linkTo(methodOn(AdminController.class).statusDiaEdit(null, null)).withRel("update").withType("PUT"));
        dto.add(linkTo(methodOn(AdminController.class).statusDiaByEmpresa(null, null)).withRel("findByEmpresa").withType("GET"));
        dto.add(linkTo(methodOn(AdminController.class).statusDiaByID(dto.getId(), null)).withRel("findByID").withType("GET"));
        dto.add(linkTo(methodOn(AdminController.class).statusDiaDelete(dto.getId(), null)).withRel("delete").withType("DELETE"));
        return dto;
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
