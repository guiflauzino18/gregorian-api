package com.gregoryan.api.Models;

public enum UserRole {
    
    GESTOR("gestor"),
    ADMIN("admin"),
    FATURAMENTO("faturamento"),
    AGENDAMENTO("agendamento"),
    ATENDIMENTO("atendimento");

    private String role;

    UserRole(String role){
        this.role = role;
    }

    public String getRole(){
        return role;
    }
}
