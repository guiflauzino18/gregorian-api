package com.gregorian.api.Services;

import com.gregorian.api.Models.Usuario;
import com.gregorian.api.Interfaces.UsuarioValidateInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gregorian.api.Exception.EntityDontExistException;
import com.gregorian.api.Models.Empresa;
import com.gregorian.api.Models.Faturamento;
import com.gregorian.api.Services.Crud.FaturamentoService;
import com.gregorian.api.Interfaces.FaturamentoListInterface;

@Service

public class FaturamentoListService implements FaturamentoListInterface{

    @Autowired
    private FaturamentoService faturamentoService;
    @Autowired
    private UsuarioValidateInterface usuarioValidate;

    @Override
    public Faturamento list(long id, Usuario usuario) {
        var faturamento = faturamentoService.findById(id).orElseThrow(() -> new EntityDontExistException("Faturamento não encontrado"));
        usuarioValidate.validate(usuario, faturamento.getEmpresa());
        return faturamento;

    }

    @Override
    public Page<Faturamento> list(Empresa empresa, Pageable pageable) {
        return faturamentoService.findByEmpresa(empresa, pageable);
    }
    
}
