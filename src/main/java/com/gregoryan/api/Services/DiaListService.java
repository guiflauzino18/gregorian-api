package com.gregoryan.api.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.gregoryan.api.Exception.EntityDontExistException;
import com.gregoryan.api.Models.Dias;
import com.gregoryan.api.Models.Empresa;
import com.gregoryan.api.Services.Crud.DiasService;
import com.gregoryan.api.Services.Interfaces.DiaListInterface;

@Service
public class DiaListService implements DiaListInterface{
    
    @Autowired
    private DiasService service;
    @Autowired
    private UsuarioValidate usuarioValidate;


    @Override
    public Dias list(long id, Empresa empresa) {
        
        Dias dia = service.findById(id).orElseThrow(() -> new EntityDontExistException("Dia não encontrado"));
        usuarioValidate.isSameEmpresaFromUserLogged(empresa, dia.getEmpresa());
        return dia;
    }

    @Override
    public Page<Dias> list(Empresa empresa, Pageable pageable) {
        return service.findByEmpresa(empresa, pageable);
    }
}
