package com.gregoryan.api.Services;

import com.gregoryan.api.Components.CriptografarSenha;
import com.gregoryan.api.Controllers.AdminController;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.gregoryan.api.DTO.UsuarioCreateDTO;
import com.gregoryan.api.DTO.UsuarioEditDTO;
import com.gregoryan.api.DTO.UsuarioResponseDTO;
import com.gregoryan.api.Models.Usuario;
import com.gregoryan.api.Interfaces.UsuarioConverterInterface;
import com.gregoryan.api.Interfaces.UsuarioListInterface;
import java.text.ParseException;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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
        
        Usuario usuarioConsulta = usuarioList.list(dto.getId(), usuario);

        usuarioConsulta.setNome(dto.getNome());
        usuarioConsulta.setNascimento(dataConverter.toCalendar(dto.getNascimento()));
        usuarioConsulta.setTelefone(dto.getTelefone());
        usuarioConsulta.setEmail(dto.getEmail());
        usuarioConsulta.setEndereco(dto.getEndereco());
        usuarioConsulta.setRole(dto.getRole());
        usuarioConsulta.setStatus(dto.getStatus());
        usuarioConsulta.setAlteraNextLogon(dto.isAlteraNextLogon());

        return usuarioConsulta;
    }

    @SneakyThrows
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


        dto.add(linkTo(methodOn(AdminController.class).userById(usuario.getId(), null)).withRel("findByID").withType("GET"));
        dto.add(linkTo(methodOn(AdminController.class).userByEmpresa(Pageable.unpaged(), null)).withRel("findAll").withType("GET"));
        dto.add(linkTo(methodOn(AdminController.class).userByLogin(usuario.getLogin(), null)).withRel("findByLogin").withType("GET"));
        dto.add(linkTo(methodOn(AdminController.class).userEdit(new UsuarioEditDTO(), null)).withRel("update").withType("PUT"));
        dto.add(linkTo(methodOn(AdminController.class).userResetPassword(null, null)).withRel("resetPassword").withType("PUT"));
        dto.add(linkTo(methodOn(AdminController.class).userDelete(dto.getId(), null)).withRel("delete").withType("DELETE"));
        dto.add(linkTo(methodOn(AdminController.class).userCreate(null, null)).withRel("create").withType("POST"));

        return dto;
    }
}
