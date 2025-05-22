package com.gregoryan.api.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gregoryan.api.DTO.AgendaConfigDTO;
import com.gregoryan.api.DTO.DiaCadastroDTO;
import com.gregoryan.api.DTO.DiaEditDTO;
import com.gregoryan.api.Models.Agenda;
import com.gregoryan.api.Models.Dias;
import com.gregoryan.api.Models.Empresa;
import com.gregoryan.api.Models.Profissional;
import com.gregoryan.api.Services.Crud.AgendaService;
import com.gregoryan.api.Services.Interfaces.DiaConverterInterface;
import com.gregoryan.api.Services.Interfaces.DiaListInterface;
import com.gregoryan.api.Services.Interfaces.UsuarioValidateInterface;

@Service
public class AgendaConfigureService {

    @Autowired
    private AgendaConverter converter;
    @Autowired
    private UsuarioValidateInterface usuarioValidade;
    @Autowired
    private DiaConverterInterface diaConverter;
    @Autowired
    private DiaListInterface diaList;
    @Autowired
    private DiaCreateService diaCreate;
    @Autowired
    private DiaEditingService diaEdit;
    @Autowired
    private AgendaService agendaService;
    

    public void configure(AgendaConfigDTO agendaDTO, Empresa empresa){

        Agenda agenda = converter.toAgenda(agendaDTO); //AgendaDontExistException
        usuarioValidade.isSameEmpresaFromUserLogged(empresa, agenda.getEmpresa()); //AcessoNegadoException
        
        /*
         * Buscar o profissinal pelo ID 
         */

        /* Percorrer os dias de agendaDTO.dias
         * Para cada dia verificar se há um ID. se haver edita o dia e salva os dados
         * Se não haver cria um dia novo
         * Ao final adicionar esse dia na lista de dias da agenda
        */
        for (DiaCadastroDTO diaDTO : agendaDTO.dias()) {
            
            Dias dia;
                    
            if (diaDTO.id() != 0){
                dia = diaCreate.create(diaDTO);
            }else {
                dia = diaList.list(diaDTO.id());
            }

            DiaEditDTO diaEditDTO = new DiaEditDTO(dia.getId(), dia.getIntervaloSessaoInMinutes(),
            dia.getDuracaoSessaoInMinutes(), dia.getStatusDia().getId(), dia.getInicio().toString(), dia.getFim().toString());

            diaEdit.editar(empresa, diaEditDTO);

            agenda.getDias().add(dia);

        }
        
        /*
         * Salvar a agenda
         */
        agendaService.save(agenda);

        /*
         * Adicionar a agenda no Profissional e salvar o profissional
         */



    }
}
