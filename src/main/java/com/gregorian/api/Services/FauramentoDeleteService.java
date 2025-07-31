package com.gregorian.api.Services;

import com.gregorian.api.Models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gregorian.api.Services.Crud.FaturamentoService;
import com.gregorian.api.Interfaces.FaturamentoListInterface;

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
