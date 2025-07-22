package com.gregoryan.api.Interfaces;

import com.gregoryan.api.DTO.UsuarioCreateDTO;
import com.gregoryan.api.DTO.UsuarioEditDTO;
import com.gregoryan.api.DTO.UsuarioResponseDTO;
import com.gregoryan.api.Models.Usuario;

public interface UsuarioConverterInterface {
    Usuario toUsuario(UsuarioCreateDTO dto);
    Usuario toUsuario(UsuarioEditDTO dto, Usuario usuario);
    UsuarioResponseDTO toUsuarioResponseDTO(Usuario usuario);
}
