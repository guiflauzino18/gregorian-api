package com.gregorian.api.Interfaces;

import com.gregorian.api.DTO.UsuarioCreateDTO;
import com.gregorian.api.DTO.UsuarioEditDTO;
import com.gregorian.api.DTO.UsuarioResponseDTO;
import com.gregorian.api.Models.Usuario;

public interface UsuarioConverterInterface {
    Usuario toUsuario(UsuarioCreateDTO dto);
    Usuario toUsuario(UsuarioEditDTO dto, Usuario usuario);
    UsuarioResponseDTO toUsuarioResponseDTO(Usuario usuario);
}
