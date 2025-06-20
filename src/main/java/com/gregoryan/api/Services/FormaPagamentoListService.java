package com.gregoryan.api.Services;

import com.gregoryan.api.Models.Usuario;
import com.gregoryan.api.Interfaces.UsuarioValidateInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gregoryan.api.Exception.EntityDontExistException;
import com.gregoryan.api.Models.Empresa;
import com.gregoryan.api.Models.FormaPagamento;
import com.gregoryan.api.Services.Crud.FormaPagamentoService;
import com.gregoryan.api.Interfaces.FormaPagamentoListInterface;

@Service
public class FormaPagamentoListService implements FormaPagamentoListInterface{

    @Autowired
    private FormaPagamentoService formaPagamentoService;
    @Autowired
    private UsuarioValidateInterface usuarioValidate;

    @Override
    public FormaPagamento list(long id, Usuario usuario) {
        var formaPagamento = formaPagamentoService.findById(id).orElseThrow(() -> new EntityDontExistException("Forma de Pagamento não encontrado"));
        usuarioValidate.validate(usuario, formaPagamento.getEmpresa());
        return formaPagamento;
    }

    @Override
    public Page<FormaPagamento> list(Empresa empresa, Pageable pageable) {
        return formaPagamentoService.findByEmpresa(empresa, pageable);
    }
    
}
