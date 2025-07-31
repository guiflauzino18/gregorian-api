package com.gregorian.api.Services;

import com.gregorian.api.Models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gregorian.api.DTO.FaturamentoCreateDTO;
import com.gregorian.api.Models.Faturamento;
import com.gregorian.api.Models.Faturamento.StatusFaturamento;
import com.gregorian.api.Services.Crud.FaturamentoService;
import com.gregorian.api.Interfaces.DateConverterInterface;
import com.gregorian.api.Interfaces.FaturamentoConverterInterface;

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
