package com.gregoryan.api.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gregoryan.api.DTO.AgendaCadastroDTO;
import com.gregoryan.api.DTO.AgendaResponseDTO;
import com.gregoryan.api.Models.Agenda;
import com.gregoryan.api.Services.Interfaces.AgendaConverterInterface;
import com.gregoryan.api.Services.Interfaces.StatusAgendaConverterInterface;

@Service
public class AgendaConverter implements AgendaConverterInterface {

    @Autowired
    private StatusAgendaConverterInterface statusAgendaConverter;

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



    

}
