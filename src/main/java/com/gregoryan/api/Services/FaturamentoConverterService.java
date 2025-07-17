package com.gregoryan.api.Services;

import com.gregoryan.api.Models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gregoryan.api.DTO.FaturamentoCreateDTO;
import com.gregoryan.api.DTO.FaturamentoEditDTO;
import com.gregoryan.api.Models.Faturamento;
import com.gregoryan.api.Interfaces.FaturamentoConverterInterface;
import com.gregoryan.api.Interfaces.FormaPagamentoListInterface;
import com.gregoryan.api.Interfaces.PacienteListInterface;

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
