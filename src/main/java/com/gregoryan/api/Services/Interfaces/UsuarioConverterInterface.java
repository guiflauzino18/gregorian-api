package com.gregoryan.api.Services.Interfaces;

import com.gregoryan.api.DTO.UsuarioCadastroDTO;
import com.gregoryan.api.DTO.UsuarioEditDTO;
import com.gregoryan.api.DTO.UsuarioResponseDTO;
import com.gregoryan.api.Models.Empresa;
import com.gregoryan.api.Models.Usuario;

public interface UsuarioConverterInterface {
    Usuario toUsuario(UsuarioCadastroDTO dto);
    Usuario toUsuario(UsuarioEditDTO dto, Empresa empresa);
    UsuarioResponseDTO toUsuarioResponseDTO(Usuario usuario);
}
