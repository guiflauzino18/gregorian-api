package com.gregoryan.api.Services;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gregoryan.api.DTO.AgendaCadastroDTO;
import com.gregoryan.api.DTO.AgendaConfigDTO;
import com.gregoryan.api.DTO.AgendaResponseDTO;
import com.gregoryan.api.Exception.AgendaDontExistException;
import com.gregoryan.api.Models.Agenda;
import com.gregoryan.api.Services.Crud.AgendaService;
import com.gregoryan.api.Services.Interfaces.AgendaConverterInterface;

@Service
public class AgendaConverter implements AgendaConverterInterface {

    @Autowired
    private AgendaService agendaService;

    public AgendaResponseDTO toAgendaResponseDTO(Agenda agenda){
        AgendaResponseDTO dto = new AgendaResponseDTO(agenda.getId(),agenda.getNome(), agenda.getStatusAgenda(),
            agenda.getEmpresa().getNome(), agenda.getProfissional().getId(),
            agenda.getProfissional().getUsuario().getNome(), agenda.getDias());


        return dto;
    }
    
    public Agenda toAgenda(AgendaCadastroDTO dto){
        Agenda agenda = new Agenda();

        agenda.setNome(dto.nome());

        return agenda;
    }



    @Override
    public Agenda toAgenda(AgendaConfigDTO dto) {
        
        Agenda agenda = agendaService.findById(dto.idAgenda()).orElseThrow(() -> new AgendaDontExistException("Agenda não encontrada"));

        return agenda;
    }



    

}
