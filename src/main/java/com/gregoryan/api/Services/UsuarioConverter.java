package com.gregoryan.api.Services;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gregoryan.api.DTO.UsuarioCadastroDTO;
import com.gregoryan.api.DTO.UsuarioEditDTO;
import com.gregoryan.api.DTO.UsuarioResponseDTO;
import com.gregoryan.api.Models.Usuario;

@Service
public class UsuarioConverter {

    @Autowired
    private DataConverter dataConverter;

    public Usuario cadastroDTOToModel(UsuarioCadastroDTO dto){
        Usuario usuario = new Usuario();
        BeanUtils.copyProperties(dto, usuario);

        return usuario;
    }

    public Usuario EditTOToModel(UsuarioEditDTO dto, Usuario usuario){

        usuario.setNome(dto.nome());
        usuario.setSobrenome(dto.sobrenome());
        usuario.setNascimento(dataConverter.getDateOfBirth(dto.nascimento()));
        usuario.setTelefone(dto.telefone());
        usuario.setEmail(dto.email());
        usuario.setStatus(dto.status());
        usuario.setEndereco(dto.endereco());
        usuario.setRole(dto.role());
        usuario.setAlteraNextLogon(dto.alteraNextLogon());

        return usuario;
    }

    public UsuarioResponseDTO toResponseDTO(Usuario usuario){
        UsuarioResponseDTO dto = new UsuarioResponseDTO(usuario.getId(),usuario.getNome(), usuario.getSobrenome(), usuario.getNascimento(), 
        usuario.getTelefone(), usuario.getEmail(), usuario.getLogin(), usuario.getEndereco(), usuario.getStatus(), usuario.isAlteraNextLogon(),
        usuario.getRole(), usuario.getDataRegistro(), usuario.getEmpresa().getNome());

        return dto;
    }


    
}
