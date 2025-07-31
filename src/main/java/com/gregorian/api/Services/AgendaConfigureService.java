package com.gregorian.api.Services;

import com.gregorian.api.Models.Agenda;
import com.gregorian.api.Models.Dias;
import com.gregorian.api.Models.Profissional;
import com.gregorian.api.Models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gregorian.api.DTO.AgendaConfigDTO;
import com.gregorian.api.DTO.DiaCadastroDTO;
import com.gregorian.api.DTO.DiaEditDTO;
import com.gregorian.api.Services.Crud.AgendaService;
import com.gregorian.api.Services.Crud.ProfissionalService;
import com.gregorian.api.Interfaces.AgendaListInterface;
import com.gregorian.api.Interfaces.DiaConverterInterface;
import com.gregorian.api.Interfaces.DiaListInterface;
import com.gregorian.api.Interfaces.UsuarioValidateInterface;

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
    private DiaEditService diaEdit;
    @Autowired
    private AgendaService agendaService;
    @Autowired
    private ProfissionalListService profissionalList;
    @Autowired
    private ProfissionalService profissionalService;
    @Autowired
    private AgendaListInterface agendaList;
    

    public void configure(AgendaConfigDTO agendaDTO, Usuario usuario){

        Agenda agenda = agendaList.list(agendaDTO.idAgenda(), usuario);
        Profissional profissinal = profissionalList.list(agendaDTO.idProfissional(), usuario);

        /* Percorrer os dias de agendaDTO.dias
         * Para cada dia verificar se há um ID. se haver edita o dia e salva os dados
         * Se não haver cria um dia novo
         * Ao final adicionar esse dia na lista de dias da agenda
        */
        for (DiaCadastroDTO diaDTO : agendaDTO.dias()) {
            
            Dias dia;
            if (diaDTO.id() == 0){
                dia = diaCreate.create(diaDTO, usuario);
                DiaEditDTO diaEditDTO = diaConverter.toEditDTO(dia);
                diaEdit.editar(usuario, diaEditDTO);

            }else {
                dia = diaList.list(diaDTO.id(), usuario);
                DiaEditDTO diaEditDTO = diaConverter.toEditDTO(diaDTO, dia);
                diaEdit.editar(usuario, diaEditDTO);
            }

            
            agenda.getDias().add(dia);

        }
        
        /*
         * Salvar a agenda
         */
        agendaService.save(agenda);

        /*
         * Adicionar a agenda no Profissional e salvar o profissional
         */
        profissinal.setAgenda(agenda);
        profissionalService.save(profissinal);
         
    }
}
