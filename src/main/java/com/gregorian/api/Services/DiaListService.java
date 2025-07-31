package com.gregorian.api.Services;

import com.gregorian.api.Components.UsuarioValidateIsNotYourProperties;
import com.gregorian.api.Models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.gregorian.api.Exception.EntityDontExistException;
import com.gregorian.api.Models.Dias;
import com.gregorian.api.Models.Empresa;
import com.gregorian.api.Services.Crud.DiasService;
import com.gregorian.api.Interfaces.DiaListInterface;

@Service
public class DiaListService implements DiaListInterface{
    
    @Autowired
    private DiasService service;
    @Autowired
    private UsuarioValidateIsNotYourProperties usuarioValidate;


    @Override
    public Dias list(long id, Usuario usuario) {
        
        Dias dia = service.findById(id).orElseThrow(() -> new EntityDontExistException("Dia não encontrado"));
        usuarioValidate.validate(usuario, dia.getEmpresa());
        return dia;
    }

    @Override
    public Page<Dias> list(Empresa empresa, Pageable pageable) {
        return service.findByEmpresa(empresa, pageable);
    }
}
