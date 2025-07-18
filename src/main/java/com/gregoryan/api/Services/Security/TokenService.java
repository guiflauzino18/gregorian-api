package com.gregoryan.api.Services.Security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.gregoryan.api.Models.Empresa;
import com.gregoryan.api.Models.Usuario;
import com.gregoryan.api.Services.Crud.UsuarioService;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    public String generateToken(Usuario usuario){
        Algorithm algorithm = Algorithm.HMAC256(secret);
        String token = JWT.create()
            .withIssuer("auth-api")
            .withSubject(usuario.getLogin())
            .withExpiresAt(genExpirationDate())
            .sign(algorithm);

        return token;
    }

    public String validateToken(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                .withIssuer("auth-api")
                .build()
                .verify(token)
                .getSubject();

        } catch (JWTVerificationException e) {
            return e.getMessage();
        }
    }

        private Instant genExpirationDate(){
        return LocalDateTime.now().plusHours(8).toInstant(ZoneOffset.of("-03:00"));
    }

        public String recoverToken(HttpServletRequest request){
        var authHeader = request.getHeader("Authorization");
        if(authHeader == null) return null;
        return authHeader.replace("Bearer ", "");
    }

    public Empresa getEmpresaFromToken(HttpServletRequest request, UsuarioService service){
        
        return service.findByLogin(validateToken(recoverToken(request))).get().getEmpresa();
    }

    public Usuario getUserLogado(HttpServletRequest request, UsuarioService service){
        return service.findByLogin(validateToken(recoverToken(request))).get();
    }
}
