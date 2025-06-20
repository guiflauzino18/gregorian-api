package com.gregoryan.api.Services;

import com.gregoryan.api.Models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gregoryan.api.Services.Crud.FaturamentoService;
import com.gregoryan.api.Interfaces.FaturamentoListInterface;

@Service
public class FauramentoDeleteService {
    @Autowired
    private FaturamentoListInterface faturamentoList;
    @Autowired
    private FaturamentoService faturamentoService;

    public void delete(long id, Usuario usuario){
        var faturamento = faturamentoList.list(id, usuario);

        faturamentoService.delete(faturamento);
    }
}
