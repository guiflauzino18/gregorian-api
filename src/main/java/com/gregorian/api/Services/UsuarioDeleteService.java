package com.gregorian.api.Services;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gregorian.api.Models.Usuario;
import com.gregorian.api.Services.Crud.UsuarioService;
import com.gregorian.api.Interfaces.UsuarioListInterface;

@Service
public class UsuarioDeleteService {

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private UsuarioListInterface usuarioList;

    private Logger logger  = LoggerFactory.getLogger(UsuarioDeleteService.class);

    public void delete(long idUser, Usuario usuario){
       
        Usuario usuarioConsulta = usuarioList.list(idUser, usuario);
        usuarioService.delete(usuarioConsulta);
        logger.info("delete: %s deletou o usuário %s".formatted(usuario.getLogin(), usuarioConsulta.getLogin()));
    }
    
}
