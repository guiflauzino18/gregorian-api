package com.gregoryan.api.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gregoryan.api.DTO.ProfissionalCadastroDTO;
import com.gregoryan.api.DTO.ProfissionalEditDTO;
import com.gregoryan.api.DTO.ProfissionalListDTO;
import com.gregoryan.api.DTO.ProfissionalResponseDTO;
import com.gregoryan.api.Models.Profissional;
import com.gregoryan.api.Models.Usuario;
import com.gregoryan.api.Services.Crud.AgendaService;
import com.gregoryan.api.Interfaces.ProfissionalConverterInterface;
import com.gregoryan.api.Interfaces.ProfissionalListInterface;
import com.gregoryan.api.Interfaces.UsuarioListInterface;

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
        return new ProfissionalResponseDTO(
            profissional.getId(), profissional.getTitulo(),
            profissional.getRegistro(),
            profissional.getUsuario().getNome(),
            profissional.getUsuario().getSobrenome(),
            profissional.getUsuario().getLogin(),
            profissional.getUsuario().getEmpresa().getNome(),
            profissional.getStatus(),
             null);
    }

    @Override
    public ProfissionalListDTO toListDTO(Profissional profissional) {
        return new ProfissionalListDTO(profissional.getUsuario().getNome(),  profissional.getId());
    }
    
}
