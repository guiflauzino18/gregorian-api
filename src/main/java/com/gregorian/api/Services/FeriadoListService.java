package com.gregorian.api.Services;

import com.gregorian.api.Models.Usuario;
import com.gregorian.api.Components.UsuarioValidateIsNotYourProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gregorian.api.Exception.EntityDontExistException;
import com.gregorian.api.Models.Empresa;
import com.gregorian.api.Models.Feriado;
import com.gregorian.api.Services.Crud.FeriadoService;
import com.gregorian.api.Interfaces.FeriadoListInterface;

@Service
public class FeriadoListService implements FeriadoListInterface {
    @Autowired
    private FeriadoService feriadoService;
    @Autowired
    private UsuarioValidateIsNotYourProperties usuarioValidateIsNotYourPropertiesEmpresa;

    @Override
    public Feriado list(long id, Usuario usuario) {
        Feriado feriado = feriadoService.findById(id).orElseThrow(() -> new EntityDontExistException("Feriado não encontrado"));
        usuarioValidateIsNotYourPropertiesEmpresa.validate(usuario, feriado.getEmpresa());

        return feriado;
    }
    @Override
    public Page<Feriado> list(Empresa empresa, Pageable pageable) {
        return feriadoService.findByEmpresa(empresa, pageable);
    }




}
