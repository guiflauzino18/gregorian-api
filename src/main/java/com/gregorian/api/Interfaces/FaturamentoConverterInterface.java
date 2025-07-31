package com.gregorian.api.Interfaces;

import com.gregorian.api.DTO.FaturamentoCreateDTO;
import com.gregorian.api.DTO.FaturamentoEditDTO;
import com.gregorian.api.Models.Faturamento;
import com.gregorian.api.Models.Usuario;

public interface FaturamentoConverterInterface {
    public Faturamento toFaturamento(FaturamentoCreateDTO dto, Usuario usuario);
    public Faturamento toFaturamento(FaturamentoEditDTO dto, Usuario usuario);
}
