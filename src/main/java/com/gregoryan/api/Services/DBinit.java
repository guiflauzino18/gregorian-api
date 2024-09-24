package com.gregoryan.api.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.gregoryan.api.Models.StatusAgenda;
import com.gregoryan.api.Models.StatusDia;
import com.gregoryan.api.Models.StatusHora;
import com.gregoryan.api.Services.Crud.StatusAgendaService;
import com.gregoryan.api.Services.Crud.StatusDiaService;
import com.gregoryan.api.Services.Crud.StatusHoraService;
import jakarta.annotation.PostConstruct;

@Component
public class DBinit {
    
    @Autowired
    private StatusAgendaService statusAgendaService;
    @Autowired
    private StatusDiaService statusDiaService;
    @Autowired
    private StatusHoraService statusHoraService;

    @PostConstruct
    public void criaStatusSeNaoExistir(){

        //Cria Status da Agenda Ativo
        statusAgendaAtivo();

        //Cria Status do Dia para Ativo
        statusDiaAtivo();

        //Cria Status da Hora para Ativo
        statusHoraAtivo();

    }

    private void statusAgendaAtivo(){
        //Cria Status Ativo para Agenda na Inicialização;
        if (!statusAgendaService.existsByNome("Ativo")){
            StatusAgenda statusAgenda = new StatusAgenda();
            statusAgenda.setNome("Ativo");
            statusAgendaService.save(statusAgenda);
        }
    }

    private void statusDiaAtivo(){
        //Cria Status Ativo para Dia na Inicialização;
        if (!statusDiaService.existsByNome("Ativo")){
            StatusDia statusDia = new StatusDia();
            statusDia.setNome("Ativo");
            statusDiaService.save(statusDia);
        }
    }

    private void statusHoraAtivo(){
        //cria status Ativo para Hora na Incialização
        if (!statusAgendaService.existsByNome("Ativo")){
            StatusHora statusHora = new StatusHora();
            statusHora.setNome("Nome");
            statusHoraService.save(statusHora);
        }
    }

}
