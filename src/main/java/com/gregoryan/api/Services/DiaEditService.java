package com.gregoryan.api.Services;

import com.gregoryan.api.Models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gregoryan.api.DTO.DiaEditDTO;
import com.gregoryan.api.Models.Dias;
import com.gregoryan.api.Models.StatusHora;
import com.gregoryan.api.Services.Crud.DiasService;
import com.gregoryan.api.Services.Crud.HorasService;
import com.gregoryan.api.Interfaces.DiaConverterInterface;
import com.gregoryan.api.Interfaces.DiaListInterface;
import com.gregoryan.api.Interfaces.StatusHoraListInterface;
@Service
public class DiaEditService {
    
    @Autowired
    private DiasService service;
    @Autowired
    private DiaConverterInterface diaConverter;
    @Autowired
    private DiaListInterface diaList;
    @Autowired
    private HorasService horasService;
    @Autowired
    private StatusHoraListInterface statusHoraList;

    public void editar(Usuario usuario, DiaEditDTO dto){

        Dias diaAtual = diaList.list(dto.idDia(), usuario); //Somente para ter valores originais e comparar se houve alteração para recriar as horas
        Dias dia = diaConverter.toDia(dto, usuario);
        /*
         * É necessário verificar os valoes antigos de tempo com o novo para verificar se vai ser necessário recriar as horas.
         */
        if (dia.recriarHora(diaAtual, dia)){

            StatusHora statusHora = statusHoraList.list("Ativo", usuario);
            dia.createHoras(statusHora, horasService);
        }

        System.out.println("-------------------\n"+dia.getFim()+diaAtual.getFim());

        service.save(dia);
        

    }

}
