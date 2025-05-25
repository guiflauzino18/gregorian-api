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
import com.gregoryan.api.Models.Empresa;
import com.gregoryan.api.Models.UserRole;
import com.gregoryan.api.Models.Usuario;
import com.gregoryan.api.Models.Usuario.StatusUsuario;
import com.gregoryan.api.Services.Interfaces.UsuarioConverterInterface;
import com.gregoryan.api.Services.Interfaces.UsuarioListInterface;

@Service
public class UsuarioConverter implements UsuarioConverterInterface{

    @Autowired
    private DataConverter dataConverter;
    @Autowired
    private CriptografarSenha criptografarSenha;
    @Autowired
    private UsuarioListInterface usuarioList;

    @Override
    public Usuario toUsuario(UsuarioCadastroDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setNome(dto.nome());
        usuario.setSobrenome(dto.sobrenome());
        usuario.setNascimento(dataConverter.toCalendar(dto.nascimento()));
        usuario.setTelefone(dto.telefone());
        usuario.setEmail(dto.email());
        usuario.setLogin(dto.login());
        usuario.setSenha(criptografarSenha.criptografar(dto.senha()));
        usuario.setEndereco(dto.endereco());
        usuario.setRole(dto.role());
        usuario.setAlteraNextLogon(dto.alteraNextLogon());

        return usuario;

    }

    @Override
    public Usuario toUsuario(UsuarioEditDTO dto, Empresa empresa) {
        
        Usuario usuario = usuarioList.list(dto.id(), empresa);

        usuario.setNome(dto.nome());
        usuario.setNascimento(dataConverter.toCalendar(dto.nascimento()));
        usuario.setTelefone(dto.telefone());
        usuario.setEmail(dto.email());
        usuario.setEndereco(dto.endereco());
        usuario.setRole(dto.role());
        usuario.setStatus(dto.status());
        usuario.setAlteraNextLogon(dto.alteraNextLogon());

        return usuario;
    }

    @Override
    public UsuarioResponseDTO toUsuarioResponseDTO(Usuario usuario) {
        UsuarioResponseDTO dto = new UsuarioResponseDTO(
            usuario.getId(),
            usuario.getNome(),
            usuario.getSobrenome(),
            usuario.getNascimento(),
            usuario.getTelefone(),
            usuario.getEmail(),
            usuario.getLogin(),
            usuario.getEndereco(),
            usuario.getStatus(),
            usuario.isAlteraNextLogon(),
            usuario.getRole(),
            usuario.getDataRegistro(),
            usuario.getEmpresa().getNome()
        );

        return dto;
    }
}
