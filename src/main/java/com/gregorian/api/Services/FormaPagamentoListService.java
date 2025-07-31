package com.gregorian.api.Services;

import com.gregorian.api.Models.Usuario;
import com.gregorian.api.Interfaces.UsuarioValidateInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gregorian.api.Exception.EntityDontExistException;
import com.gregorian.api.Models.Empresa;
import com.gregorian.api.Models.FormaPagamento;
import com.gregorian.api.Services.Crud.FormaPagamentoService;
import com.gregorian.api.Interfaces.FormaPagamentoListInterface;

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
