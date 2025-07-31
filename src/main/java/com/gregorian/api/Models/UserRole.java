package com.gregorian.api.Models;

public enum UserRole {
    
    GESTOR("gestor"),
    ADMIN("admin"),
    FATURAMENTO("faturamento"),
    AGENDAMENTO("agendamento"),
    ATENDIMENTO("atendimento"),
    PROFISSIONAL("profissional");

    private String role;

    UserRole(String role){
        this.role = role;
    }

    public String getRole(){
        return role;
    }
}
