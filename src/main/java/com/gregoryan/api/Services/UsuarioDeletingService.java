package com.gregoryan.api.Services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gregoryan.api.Exception.EntityDontExistException;
import com.gregoryan.api.Models.Empresa;
import com.gregoryan.api.Models.Usuario;
import com.gregoryan.api.Services.Crud.UsuarioService;
import com.gregoryan.api.Services.Interfaces.UsuarioValidateInterface;

@Service
public class UsuarioDeletingService {

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private UsuarioValidateInterface validateUsuario;

    public void delete(long idUser, Empresa empresa){
       
        Usuario usuario = usuarioService.findById(idUser).orElseThrow(() -> new EntityDontExistException("Usuário não encontrado."));
        validateUsuario.isSameEmpresaFromUserLogged(empresa, usuario.getEmpresa());
        
        usuarioService.delete(usuario);

    }
    
}
