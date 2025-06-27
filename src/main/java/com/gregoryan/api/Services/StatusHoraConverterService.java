package com.gregoryan.api.Services;

import com.gregoryan.api.Controllers.AdminController;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gregoryan.api.DTO.StatusHoraCadastroDTO;
import com.gregoryan.api.DTO.StatusHoraResponseDTO;
import com.gregoryan.api.Models.StatusHora;
import com.gregoryan.api.Interfaces.StatusHoraConverterInterface;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class StatusHoraConverterService implements StatusHoraConverterInterface{

    @Override
    public StatusHora toStatusHora(StatusHoraCadastroDTO dto) {
        StatusHora statusHora = new StatusHora();
        statusHora.setNome(dto.nome());

        return statusHora;
    }

    @Override
    public StatusHoraResponseDTO toResponseDTO(StatusHora statusHora) {
        StatusHoraResponseDTO dto = new StatusHoraResponseDTO(
            statusHora.getId(),
            statusHora.getNome());

        dto.add(linkTo(methodOn(AdminController.class).statusHoraCreate(null, null)).withRel("create").withType("POST"));
        dto.add(linkTo(methodOn(AdminController.class).statusHoraByID(dto.getId(), null)).withRel("findByID").withType("GET"));
        dto.add(linkTo(methodOn(AdminController.class).statusHoraByEmpresa(null,Pageable.unpaged())).withRel("findByEmpresa").withType("GET"));
        dto.add(linkTo(methodOn(AdminController.class).statusHoraDelete(dto.getId(), null)).withRel("delete").withType("DELETE"));

        return dto;
    }
    

}
