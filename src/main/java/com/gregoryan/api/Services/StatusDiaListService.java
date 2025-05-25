package com.gregoryan.api.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.gregoryan.api.Exception.EntityDontExistException;
import com.gregoryan.api.Models.Empresa;
import com.gregoryan.api.Models.StatusDia;
import com.gregoryan.api.Services.Crud.StatusDiaService;
import com.gregoryan.api.Services.Interfaces.StatusDiaListInterface;

@Service
public class StatusDiaListService implements StatusDiaListInterface{

    @Autowired
    private StatusDiaService statusDiaService;
    @Autowired
    private UsuarioValidate usuarioValidate;

    

    @Override
    public Page<StatusDia> list(Empresa empresa, Pageable pageable) {
        return statusDiaService.findByEmpresa(empresa, pageable);
    }

    @Override
    public StatusDia list(long id, Empresa empresa) {
        StatusDia statusDia = statusDiaService.findById(id).orElseThrow(() -> new EntityDontExistException("Status não encontrado"));
        usuarioValidate.isSameEmpresaFromUserLogged(empresa, statusDia.getEmpresa());
        return statusDia;
    }
    
}
