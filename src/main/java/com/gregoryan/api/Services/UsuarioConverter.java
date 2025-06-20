package com.gregoryan.api.Services;

import com.gregoryan.api.Components.CriptografarSenha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gregoryan.api.DTO.UsuarioCreateDTO;
import com.gregoryan.api.DTO.UsuarioEditDTO;
import com.gregoryan.api.DTO.UsuarioResponseDTO;
import com.gregoryan.api.Models.Usuario;
import com.gregoryan.api.Interfaces.UsuarioConverterInterface;
import com.gregoryan.api.Interfaces.UsuarioListInterface;

@Service
public class UsuarioConverter implements UsuarioConverterInterface{

    @Autowired
    private DateConverterService dataConverter;
    @Autowired
    private CriptografarSenha criptografarSenha;
    @Autowired
    private UsuarioListInterface usuarioList;

    @Override
    public Usuario toUsuario(UsuarioCreateDTO dto) {
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
    public Usuario toUsuario(UsuarioEditDTO dto, Usuario usuario) {
        
        Usuario usuarioConsulta = usuarioList.list(dto.id(), usuario);

        usuarioConsulta.setNome(dto.nome());
        usuarioConsulta.setNascimento(dataConverter.toCalendar(dto.nascimento()));
        usuarioConsulta.setTelefone(dto.telefone());
        usuarioConsulta.setEmail(dto.email());
        usuarioConsulta.setEndereco(dto.endereco());
        usuarioConsulta.setRole(dto.role());
        usuarioConsulta.setStatus(dto.status());
        usuarioConsulta.setAlteraNextLogon(dto.alteraNextLogon());

        return usuarioConsulta;
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
