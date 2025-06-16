package com.gregoryan.api.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gregoryan.api.DTO.AgendaCadastroDTO;
import com.gregoryan.api.DTO.AgendaEditDTO;
import com.gregoryan.api.DTO.AgendaResponseDTO;
import com.gregoryan.api.Models.Agenda;
import com.gregoryan.api.Models.Empresa;
import com.gregoryan.api.Services.Interfaces.AgendaConverterInterface;
import com.gregoryan.api.Services.Interfaces.AgendaListInterface;
import com.gregoryan.api.Services.Interfaces.ProfissionalListInterface;
import com.gregoryan.api.Services.Interfaces.StatusAgendaConverterInterface;
import com.gregoryan.api.Services.Interfaces.StatusAgendaListInterface;

@Service
public class AgendaConverter implements AgendaConverterInterface {

    @Autowired
    private StatusAgendaConverterInterface statusAgendaConverter;
    @Autowired
    private AgendaListInterface agendaList;
    @Autowired
    private StatusAgendaListInterface statusAgendaList;
    @Autowired
    private ProfissionalListInterface profissionalList;

    public AgendaResponseDTO toAgendaResponseDTO(Agenda agenda){
        AgendaResponseDTO dto = new AgendaResponseDTO(
            agenda.getId(),
            agenda.getNome(),
            statusAgendaConverter.toResponseDTO(agenda.getStatusAgenda()),
            agenda.getEmpresa().getNome(), agenda.getProfissional().getId(),
            agenda.getProfissional().getUsuario().getNome(),
            agenda.getDias());
        return dto;
    }
    
    public Agenda toAgenda(AgendaCadastroDTO dto){
        Agenda agenda = new Agenda();

        agenda.setNome(dto.nome());

        return agenda;
    }

    @Override
    public AgendaResponseDTO toResponseDTO(Agenda agenda) {
        
        return new AgendaResponseDTO(
            agenda.getId(),
            agenda.getNome(),
            statusAgendaConverter.toResponseDTO(agenda.getStatusAgenda()),
            agenda.getEmpresa().getNome(),
            agenda.getProfissional().getId(),
            agenda.getProfissional().getUsuario().getNome(),
            agenda.getDias());
    }

    @Override
    public Agenda toAgenda(AgendaEditDTO dto, Empresa empresa) {

        Agenda agenda = agendaList.list(dto.idAgenda(), empresa);
        var status = statusAgendaList.list(dto.idStatusAgenda(), empresa);
        agenda.setStatusAgenda(status);
        var profissional = profissionalList.list(dto.idProfissional(), empresa);
        agenda.setProfissional(profissional);
        agenda.setId(dto.idAgenda());
        agenda.setNome(dto.nome());

        return agenda;

    }

    //long idAgenda, String nome, long idStatusAgenda, long idProfissional


    

}
