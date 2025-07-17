package com.gregoryan.api.Interfaces;

import com.gregoryan.api.DTO.EnderecoEditDTO;
import com.gregoryan.api.Models.Endereco;

public interface EnderecoConverterInterface {
   public Endereco toEndereco(EnderecoEditDTO dto); 
}
