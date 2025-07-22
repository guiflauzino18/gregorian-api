package com.gregoryan.api.Services;

import com.gregoryan.api.Models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gregoryan.api.DTO.FaturamentoCreateDTO;
import com.gregoryan.api.Models.Faturamento;
import com.gregoryan.api.Models.Faturamento.StatusFaturamento;
import com.gregoryan.api.Services.Crud.FaturamentoService;
import com.gregoryan.api.Interfaces.DateConverterInterface;
import com.gregoryan.api.Interfaces.FaturamentoConverterInterface;

@Service
public class FaturamentoCreateService {
    @Autowired
    private FaturamentoConverterInterface faturamentoConverter;
    @Autowired
    private DateConverterInterface dateConverter;
    @Autowired
    private FaturamentoService faturamentoService;

    public Faturamento create(FaturamentoCreateDTO dto, Usuario usuario){
        var faturamento = faturamentoConverter.toFaturamento(dto, usuario);
        var data = dateConverter.getDateCurrent();
        faturamento.setData(data);
        faturamento.setEmpresa(usuario.getEmpresa());
        faturamento.setStatus(StatusFaturamento.GERADO);

        return faturamentoService.save(faturamento);
    }
}
