package com.gregoryan.api.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gregoryan.api.DTO.UsuarioResponseDTO;
import com.gregoryan.api.Exception.AcessoNegadoException;
import com.gregoryan.api.Exception.EntityDontExistException;
import com.gregoryan.api.Models.Usuario;
import com.gregoryan.api.Services.Crud.UsuarioService;
import com.gregoryan.api.Interfaces.UsuarioConverterInterface;
import com.gregoryan.api.Interfaces.UsuarioListInterface;
import com.gregoryan.api.Services.Security.TokenService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
public class IndexController {
    
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private UsuarioListInterface usuarioList;
    @Autowired
    private UsuarioConverterInterface usuarioConverter;

    @GetMapping("/index")
    @Operation(summary = "Dados index", description = "Retorna dados necessários para carregamento do index")
    @ApiResponse(responseCode = "200", description = "Retorna dados com sucesse")
    @ApiResponse(responseCode = "403", description = "Usuário sem permissão para esta operação")
    @ApiResponse(responseCode = "404", description = "Dados não encontrados")
    public ResponseEntity<Object> indexController(HttpServletRequest request){

        try{
            Usuario usuario = tokenService.getUserLogado(request, usuarioService);
            UsuarioResponseDTO responseDTO = usuarioConverter.toUsuarioResponseDTO(usuario);
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);

        }catch(AcessoNegadoException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);

        }catch(EntityDontExistException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
