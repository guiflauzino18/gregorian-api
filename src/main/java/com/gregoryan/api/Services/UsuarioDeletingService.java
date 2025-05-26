package com.gregoryan.api.Services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gregoryan.api.Models.Empresa;
import com.gregoryan.api.Models.Usuario;
import com.gregoryan.api.Services.Crud.UsuarioService;
import com.gregoryan.api.Services.Interfaces.UsuarioListInterface;

@Service
public class UsuarioDeletingService {

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private UsuarioListInterface usuarioList;

    public void delete(long idUser, Empresa empresa){
       
        Usuario usuario = usuarioList.list(idUser, empresa);
        usuarioService.delete(usuario);

    }
    
}
