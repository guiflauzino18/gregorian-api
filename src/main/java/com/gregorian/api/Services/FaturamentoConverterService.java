package com.gregorian.api.Services;

import com.gregorian.api.Models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gregorian.api.DTO.FaturamentoCreateDTO;
import com.gregorian.api.DTO.FaturamentoEditDTO;
import com.gregorian.api.Models.Faturamento;
import com.gregorian.api.Interfaces.FaturamentoConverterInterface;
import com.gregorian.api.Interfaces.FormaPagamentoListInterface;
import com.gregorian.api.Interfaces.PacienteListInterface;

@Service
public class FaturamentoConverterService implements FaturamentoConverterInterface{

    @Autowired
    private PacienteListInterface pacienteList;
    @Autowired
    private FormaPagamentoListInterface formapagamentoList;

    @Override
    public Faturamento toFaturamento(FaturamentoCreateDTO dto, Usuario usuario) {
        var faturamento = new Faturamento();
        faturamento.setValor(dto.valor());
        faturamento.setDesconto(dto.desconto());
        var paciente = pacienteList.list(dto.idPaciente(), usuario);
        var formaPagamento = formapagamentoList.list(dto.idFormaPagamento(), usuario);
        faturamento.setPaciente(paciente);
        faturamento.setFormaPagamento(formaPagamento);

        return faturamento;
    }

    @Override
    public Faturamento toFaturamento(FaturamentoEditDTO dto, Usuario usuario) {
        var faturamento = new Faturamento();
        faturamento.setValor(dto.valor());
        faturamento.setDesconto(dto.desconto());
        faturamento.setPago(dto.pago());
        var paciente = pacienteList.list(dto.idPaciente(), usuario);
        var formaPagamento = formapagamentoList.list(dto.idFormaPagamento(), usuario);
        faturamento.setPaciente(paciente);
        faturamento.setFormaPagamento(formaPagamento);

        return faturamento;
        
    }
    
}
