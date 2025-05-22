package com.gregoryan.api.Services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gregoryan.api.DTO.AgendaResponseDTO;
import com.gregoryan.api.Exception.AgendaDontExistException;
import com.gregoryan.api.Models.Agenda;
import com.gregoryan.api.Models.Empresa;
import com.gregoryan.api.Services.Crud.AgendaService;
import com.gregoryan.api.Services.Interfaces.AgendaListInterface;
import com.gregoryan.api.Services.Interfaces.UsuarioValidateInterface;


@Service
public class AgendaList implements AgendaListInterface{

    @Autowired
    private AgendaService agendaService;
    @Autowired
    private UsuarioValidateInterface usuarioValidade;
    @Autowired
    private AgendaConverter converter;

    //Lista agenda por ID
    @Override
    public AgendaResponseDTO list(long id, Empresa empresa) {
        
        Agenda agenda = agendaService.findById(id).orElseThrow(() -> new AgendaDontExistException("Agenda não encontrada"));
        usuarioValidade.isSameEmpresaFromUserLogged(empresa, agenda.getEmpresa());

        AgendaResponseDTO dto = converter.toAgendaResponseDTO(agenda);

        return dto;

    }

    //Lista agenda por empresa
    @Override
    public Page<AgendaResponseDTO> list(Empresa empresa, Pageable pageable) {
        List<Agenda> agendas = agendaService.findByEmpresa(empresa, pageable).getContent();

        List<AgendaResponseDTO> listDTO = agendas.stream().map(agenda -> {
            AgendaResponseDTO dto = converter.toAgendaResponseDTO(agenda);
            return dto;
            
        }).collect(Collectors.toList());

        return new PageImpl<>(listDTO);
        
    }
    
}
