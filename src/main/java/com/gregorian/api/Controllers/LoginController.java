package com.gregorian.api.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gregorian.api.DTO.LoginRequestDTO;
import com.gregorian.api.DTO.LoginResponseDTO;
import com.gregorian.api.DTO.UsuarioResetSenhaDTO;
import com.gregorian.api.Models.Usuario;
import com.gregorian.api.Repositorys.UsuarioRepository;
import com.gregorian.api.Services.Crud.UsuarioService;
import com.gregorian.api.Services.Security.TokenService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class LoginController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    TokenService tokenService;
    @Autowired
    private UsuarioService usuarioService;


    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginRequestDTO loginRequest){

        var usernamePassword = new UsernamePasswordAuthenticationToken(loginRequest.login(), loginRequest.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        Usuario usuario = (Usuario) auth.getPrincipal();

        if (usuario.getStatus() != Usuario.StatusUsuario.ATIVO ) {
            return new ResponseEntity<>("Usuário Bloqueado!", HttpStatus.FORBIDDEN);
        }

        var token = tokenService.generateToken((Usuario) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token, usuario.getId()));
    }

    @PutMapping("/login/usuario/alterasenha")
    public ResponseEntity<Object> usuarioAlteraPass(@RequestBody @Valid UsuarioResetSenhaDTO usuarioDTO){
        if(usuarioService.findById(usuarioDTO.id()).isPresent()){
            Usuario usuario = usuarioService.findById(usuarioDTO.id()).get();
            String encryptedPassword = new BCryptPasswordEncoder().encode(usuarioDTO.senha());
            usuario.setSenha(encryptedPassword);
            usuario.setAlteraNextLogon(usuarioDTO.alteraNextLogon());

            return new ResponseEntity<>(usuarioService.save(usuario), HttpStatus.OK);
        } else return new ResponseEntity<>("Usuário não encontrado!", HttpStatus.NOT_FOUND);
    }

}
