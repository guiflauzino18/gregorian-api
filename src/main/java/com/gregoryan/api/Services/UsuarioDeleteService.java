package com.gregoryan.api.Services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gregoryan.api.Models.Usuario;
import com.gregoryan.api.Services.Crud.UsuarioService;
import com.gregoryan.api.Interfaces.UsuarioListInterface;

@Service
public class UsuarioDeleteService {

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private UsuarioListInterface usuarioList;

    public void delete(long idUser, Usuario usuario){
       
        Usuario usuarioConsulta = usuarioList.list(idUser, usuario);
        usuarioService.delete(usuarioConsulta);

    }
    
}
