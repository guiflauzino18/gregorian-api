package com.gregoryan.api.Services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gregoryan.api.DTO.UsuarioResponseDTO;
import com.gregoryan.api.Exception.UsuarioDontExistException;
import com.gregoryan.api.Models.Empresa;
import com.gregoryan.api.Models.Usuario;
import com.gregoryan.api.Services.Crud.UsuarioService;
import com.gregoryan.api.Services.Interfaces.UsuarioListInterface;

@Service
public class UsuarioListService implements UsuarioListInterface{

    @Autowired
    private UsuarioService service;
    @Autowired
    private UsuarioConverter usuarioConverter;

    @Override
    public UsuarioResponseDTO list(long id) {

        Usuario usuario = service.findById(id).orElseThrow(() -> new UsuarioDontExistException("Usuário não encontrado"));
        UsuarioResponseDTO dto = usuarioConverter.toResponseDTO(usuario);

        return dto;
    }

    @Override
    public Page<UsuarioResponseDTO> list(Empresa empresa, Pageable pageable) {

        List<Usuario> usuario = service.findByEmpresa(empresa, pageable).getContent();

        // Cria List<UsuarioResponseDTO> de List<Usuario>
        List<UsuarioResponseDTO> listDTO = usuario.stream().map(item -> {
            UsuarioResponseDTO dto = usuarioConverter.toResponseDTO(item);
            return dto;

        }).collect(Collectors.toList());

        return new PageImpl<>(listDTO);
                
    }

    @Override
    public UsuarioResponseDTO list(String login) {
        Usuario usuario = service.findByLogin(login).orElseThrow(() -> new UsuarioDontExistException("Usuário não encontrado"));
        UsuarioResponseDTO dto = usuarioConverter.toResponseDTO(usuario);

        return dto;
    }

    @Override
    public Page<UsuarioResponseDTO> list(Pageable pageable) {
        List<Usuario> usuarios = service.findAll(pageable).getContent();

        List<UsuarioResponseDTO> dtoList = usuarios.stream().map(item -> {
            UsuarioResponseDTO dto = usuarioConverter.toResponseDTO(item);
            return dto;
        }).collect(Collectors.toList());

        return new PageImpl<>(dtoList);

    }
    
}
