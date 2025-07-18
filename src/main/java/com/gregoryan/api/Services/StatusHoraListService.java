package com.gregoryan.api.Services;

import java.util.ArrayList;
import java.util.List;

import com.gregoryan.api.Components.UsuarioValidateIsNotYourProperties;
import com.gregoryan.api.Models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gregoryan.api.Exception.EntityDontExistException;
import com.gregoryan.api.Models.Empresa;
import com.gregoryan.api.Models.StatusHora;
import com.gregoryan.api.Services.Crud.StatusHoraService;
import com.gregoryan.api.Interfaces.StatusHoraListInterface;
import com.gregoryan.api.Interfaces.UsuarioValidateInterface;

@Service
public class StatusHoraListService implements StatusHoraListInterface{

    @Autowired
    private StatusHoraService statusHoraService;
    @Autowired
    private UsuarioValidateIsNotYourProperties usuarioValidate;

    @Override
    public StatusHora list(long id, Usuario usuario) {
        StatusHora statusHora = statusHoraService.findById(id).orElseThrow(() -> new EntityDontExistException("Status não encontrado"));
        usuarioValidate.validate(usuario, statusHora.getEmpresa());
        return statusHora;
    }

    @Override
    public Page<StatusHora> list(Empresa empresa, Pageable pageable) {
        
        List<StatusHora> listStatus = statusHoraService.findByEmpresa(empresa, pageable).getContent();

        //getContent retorna uma lista imutável, para acrescentar mais itens é necessário criar uma nova lista mutavel
        // que recebe os status da empresa mais os status Ativo e Bloqueado.
        List<StatusHora> statusHoras = new ArrayList<>();
        statusHoraService.findByNome("Ativo").ifPresent(statusHoras::add);
        statusHoraService.findByNome("Bloqueado").ifPresent(statusHoras::add);

        statusHoras.addAll(listStatus);

        return new PageImpl<>(statusHoras);
    }

    @Override
    public StatusHora list(String nome, Usuario usuario) {
        StatusHora statusHora = statusHoraService.findByNome(nome).orElseThrow(() -> new EntityDontExistException("Status hora não encontrado"));
        usuarioValidate.validate(usuario, statusHora.getEmpresa());

        return statusHora;
    }

    
}
