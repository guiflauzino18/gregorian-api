package com.gregorian.api.Services;

import com.gregorian.api.Controllers.AdminController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.gregorian.api.DTO.ProfissionalCadastroDTO;
import com.gregorian.api.DTO.ProfissionalEditDTO;
import com.gregorian.api.DTO.ProfissionalListDTO;
import com.gregorian.api.DTO.ProfissionalResponseDTO;
import com.gregorian.api.Models.Profissional;
import com.gregorian.api.Models.Usuario;
import com.gregorian.api.Services.Crud.AgendaService;
import com.gregorian.api.Interfaces.ProfissionalConverterInterface;
import com.gregorian.api.Interfaces.ProfissionalListInterface;
import com.gregorian.api.Interfaces.UsuarioListInterface;

import java.text.ParseException;

@Service
public class ProfissionalConverterService implements ProfissionalConverterInterface{

    @Autowired
    private UsuarioListInterface usuarioList;
    @Autowired
    private ProfissionalListInterface profissionalList;
    @Autowired
    private AgendaService agendaService;


    @Override
    public Profissional toProfissional(ProfissionalCadastroDTO dto, Usuario usuario) {
        Profissional profissional = new Profissional();

        profissional.setTitulo(dto.titulo());
        profissional.setRegistro(dto.registro());
        profissional.setStatus(Profissional.StatusProfissional.ATIVO);

        Usuario usuarioDoProfissional = usuarioList.list(dto.login(), usuario); //UsuarioDontExistException
        profissional.setUsuario(usuarioDoProfissional);

        return profissional;
    }

    @Override
    public Profissional toProfissional(ProfissionalEditDTO dto, Usuario usuario) {
        Profissional profissional = profissionalList.list(dto.id(), usuario); //ProfissionalDontExitException
        profissional.setTitulo(dto.titulo());
        profissional.setRegistro(dto.registro());
        profissional.setStatus(dto.status());
        agendaService.findById(dto.idAgenda()).ifPresent(agenda -> profissional.setAgenda(agenda));

        return profissional;
    }

    @Override
    public ProfissionalResponseDTO toResponseDTO(Profissional profissional) {
        var dto = new ProfissionalResponseDTO(
            profissional.getId(), profissional.getTitulo(),
            profissional.getRegistro(),
            profissional.getUsuario().getNome(),
            profissional.getUsuario().getSobrenome(),
            profissional.getUsuario().getLogin(),
            profissional.getUsuario().getEmpresa().getNome(),
            profissional.getStatus(),
             null);

        try {
            dto.add(linkTo(methodOn(AdminController.class).profissionalCreate(null, null)).withRel("create").withType("POST"));
            dto.add(linkTo(methodOn(AdminController.class).profissionalEdit(null, null)).withRel("update").withType("PUT"));
            dto.add(linkTo(methodOn(AdminController.class).profissionalDelete(dto.getId(), null)).withRel("delete").withType("DELETE"));
            dto.add(linkTo(methodOn(AdminController.class).profissionalByEmpresa(Pageable.unpaged(), null)).withRel("findByEmpresa").withType("GET"));
            dto.add(linkTo(methodOn(AdminController.class).profissionalNameAndId(null, null)).withRel("profissionalNameAndId").withType("GET"));
            dto.add(linkTo(methodOn(AdminController.class).profissionalById(dto.getId(), null)).withRel("findByID").withType("GET"));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return dto;
    }

    @Override
    public ProfissionalListDTO toListDTO(Profissional profissional) {
        return new ProfissionalListDTO(profissional.getUsuario().getNome(),  profissional.getId());
    }
    
}
