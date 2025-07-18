package com.gregoryan.api.Services;

import com.gregoryan.api.Controllers.AdminController;
import org.springframework.stereotype.Service;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.gregoryan.api.DTO.StatusAgendaCadastroDTO;
import com.gregoryan.api.DTO.StatusAgendaResponseDTO;
import com.gregoryan.api.Models.StatusAgenda;
import com.gregoryan.api.Interfaces.StatusAgendaConverterInterface;

@Service
public class StatusAgendaConverterService implements StatusAgendaConverterInterface{

    @Override
    public StatusAgenda toStatusAgenda(StatusAgendaCadastroDTO dto) {
        StatusAgenda statusAgenda = new StatusAgenda();
        statusAgenda.setNome(dto.nome());

        return statusAgenda;
    }

    @Override
    public StatusAgendaResponseDTO toResponseDTO(StatusAgenda statusAgenda) {
        
        var dto = new StatusAgendaResponseDTO(statusAgenda.getId(), statusAgenda.getNome());
        dto.add(linkTo(methodOn(AdminController.class).statusAgendaCreate(null, null)).withRel("create").withType("POST"));
        dto.add(linkTo(methodOn(AdminController.class).statusAgendaByEmpresa(null, null)).withRel("findByEmpresa").withType("GET"));
        dto.add(linkTo(methodOn(AdminController.class).statusAgendaById(dto.getId(), null)).withRel("findByID").withType("GET"));
        dto.add(linkTo(methodOn(AdminController.class).statusAgendaDelete(dto.getId(), null)).withRel("delete").withType("DELETE"));
        return dto;
    }
    
}
