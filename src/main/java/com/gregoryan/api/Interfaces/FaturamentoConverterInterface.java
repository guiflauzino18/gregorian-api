package com.gregoryan.api.Interfaces;

import com.gregoryan.api.DTO.FaturamentoCreateDTO;
import com.gregoryan.api.DTO.FaturamentoEditDTO;
import com.gregoryan.api.Models.Empresa;
import com.gregoryan.api.Models.Faturamento;
import com.gregoryan.api.Models.Usuario;

public interface FaturamentoConverterInterface {
    public Faturamento toFaturamento(FaturamentoCreateDTO dto, Usuario usuario);
    public Faturamento toFaturamento(FaturamentoEditDTO dto, Usuario usuario);
}
