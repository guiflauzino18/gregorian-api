package com.gregoryan.api.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gregoryan.api.Models.Usuario;
import com.gregoryan.api.Services.Crud.UsuarioService;
import com.gregoryan.api.Services.Security.TokenService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
public class IndexController {
    
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private TokenService tokenService;

    @GetMapping("/index")
    public ResponseEntity<Object> indexController(HttpServletRequest request){
        Usuario usuario = usuarioService.findByLogin(tokenService.validateToken(tokenService.recoverToken(request))).get();

        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }
}
