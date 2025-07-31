package com.gregorian.api.Interfaces;

import com.gregorian.api.DTO.EnderecoEditDTO;
import com.gregorian.api.Models.Endereco;

public interface EnderecoConverterInterface {
   public Endereco toEndereco(EnderecoEditDTO dto); 
}
