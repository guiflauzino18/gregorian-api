package com.gregorian.api.Services;

import com.gregorian.api.Controllers.AdminController;
import com.gregorian.api.DTO.HoraResposeDTO;
import com.gregorian.api.Interfaces.HoraConverterInterface;
import com.gregorian.api.Models.Hora;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@Service
public class HoraConverterService implements HoraConverterInterface {
    @Override
    public HoraResposeDTO toResponseDTO(Hora hora) {
        var dto = new HoraResposeDTO(
                hora.getId(),
                hora.getInicio(),
                hora.getFim(),
                hora.getStatusHora()
        );

        dto.add(linkTo(methodOn(AdminController.class).horaEdit(null, null)).withRel("update").withType("PUT"));
        dto.add(linkTo(methodOn(AdminController.class).horaDelete(dto.getId())).withRel("delete").withType("DELETE"));
        dto.add(linkTo(methodOn(AdminController.class).horaByDia(1L, null)).withRel("findByDia").withType("GET"));
        dto.add(linkTo(methodOn(AdminController.class).horaByStatus(1L, null, Pageable.unpaged())).withRel("findByStatus").withType("GET"));

        return dto;
    }
}
