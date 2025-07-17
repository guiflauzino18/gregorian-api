package com.gregoryan.api.mocks;

import com.gregoryan.api.Models.UserRole;
import com.gregoryan.api.Models.Usuario;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MockUser {

    public Usuario mockEntity(){
        return mockEntity(10);
    }

    private Usuario mockEntity(Integer number){
        Usuario usuario = new Usuario();
        usuario.setId(number);
        usuario.setNome("Usuario Teste "+number);
        usuario.setSobrenome("Sobrenome Teste "+number);
        usuario.setEndereco(String.format("Rua %d, %d - Centro", number, number));
        usuario.setNascimento(Calendar.getInstance());
        usuario.setEmail(String.format("teste%d@email.com", number));
        usuario.setTelefone("DD 9 9988-7766");
        usuario.setLogin("teste."+number);
        usuario.setSenha("senhateste"+number);
        usuario.setStatus(Usuario.StatusUsuario.ATIVO);
        usuario.setAlteraNextLogon(false);
        usuario.setRole(UserRole.ADMIN);
        usuario.setDataRegistro(Calendar.getInstance());

        return usuario;
    }

    public List<Usuario> mockEntityList(){
        List<Usuario> users = new ArrayList<Usuario>();
        for (int i = 0; i<=15; i++){
            users.add(mockEntity(i));
        }

        return users;
    }
}