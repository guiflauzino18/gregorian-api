package com.gregorian.api.DTO;

public record UsuarioResetSenhaDTO(
        long id,
        String senha,
        boolean alteraNextLogon

    ) {}
