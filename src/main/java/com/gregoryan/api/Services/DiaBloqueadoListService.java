package com.gregoryan.api.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gregoryan.api.Exception.EntityDontExistException;
import com.gregoryan.api.Models.DiaBloqueado;
import com.gregoryan.api.Models.Empresa;
import com.gregoryan.api.Services.Crud.DiaBloqueadoService;
import com.gregoryan.api.Services.Interfaces.DiaBloqueadoListInterface;

@Service
public class DiaBloqueadoListService implements DiaBloqueadoListInterface {

    @Autowired
    private DiaBloqueadoService diaBloqueadoService;
    @Autowired
    private UsuarioValidate usuarioValidate;

    @Override
    public DiaBloqueado list(long id, Empresa empresa) {
        DiaBloqueado diaBloqueado = diaBloqueadoService.findById(id)
            .orElseThrow(() -> new EntityDontExistException("Dia bloqueado não encontrado"));

        usuarioValidate.isSameEmpresaFromUserLogged(empresa, diaBloqueado.getEmpresa());

        return diaBloqueado;
    }

    @Override
    public Page<DiaBloqueado> list(Empresa empresa, Pageable pageable) {
        return diaBloqueadoService.findByEmpresa(empresa, pageable);
    }
}
