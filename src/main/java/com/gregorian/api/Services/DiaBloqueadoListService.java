package com.gregorian.api.Services;

import com.gregorian.api.Models.Usuario;
import com.gregorian.api.Components.UsuarioValidateIsNotYourProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gregorian.api.Exception.EntityDontExistException;
import com.gregorian.api.Models.DiaBloqueado;
import com.gregorian.api.Models.Empresa;
import com.gregorian.api.Services.Crud.DiaBloqueadoService;
import com.gregorian.api.Interfaces.DiaBloqueadoListInterface;

@Service
public class DiaBloqueadoListService implements DiaBloqueadoListInterface {

    @Autowired
    private DiaBloqueadoService diaBloqueadoService;
    @Autowired
    private UsuarioValidateIsNotYourProperties usuarioValidateIsNotYourPropertiesEmpresa;

    @Override
    public DiaBloqueado list(long id, Usuario usuario) {
        DiaBloqueado diaBloqueado = diaBloqueadoService.findById(id)
            .orElseThrow(() -> new EntityDontExistException("Dia bloqueado não encontrado"));

        usuarioValidateIsNotYourPropertiesEmpresa.validate(usuario, diaBloqueado.getEmpresa());

        return diaBloqueado;
    }

    @Override
    public Page<DiaBloqueado> list(Empresa empresa, Pageable pageable) {
        return diaBloqueadoService.findByEmpresa(empresa, pageable);
    }
}
