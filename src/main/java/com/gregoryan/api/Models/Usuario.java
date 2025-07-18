package com.gregoryan.api.Models;

import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tbl_usuario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Usuario implements UserDetails {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String sobrenome;

    @Column(nullable = false)
    private Calendar nascimento;

    private String telefone;
    private String email;

    @Column(nullable = false, unique = true, length = 64)
    private String login;

    @Column(nullable = false, length = 255)
    private String senha;

    private String endereco;

    private StatusUsuario status;

    private boolean alteraNextLogon;

    @Column(nullable = false)
    private UserRole role;

    @Column(nullable = false)
    private Calendar dataRegistro;

    @ManyToOne
    @JoinColumn(name = "empresa_fk")
    private Empresa empresa;

    public enum StatusUsuario {
        ATIVO(1), INATIVO(0), BLOQUEADO(2);

        private final int status;

        private StatusUsuario(int valor){
            this.status = valor;
        }

        public int getStatus(){
            return status;
        }

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.role == UserRole.GESTOR){
            return List.of(new SimpleGrantedAuthority("ROLE_GESTOR"),
                            new SimpleGrantedAuthority("ROLE_ADMIN"),
                            new SimpleGrantedAuthority("ROLE_FATURAMENTO"),
                            new SimpleGrantedAuthority("ROLE_AGENDAMENTO"),
                            new SimpleGrantedAuthority("ROLE_ATENDIMENTO"),
                            new SimpleGrantedAuthority("ROLE_PROFISSIONAL"));
        
        } else if (this.role == UserRole.ADMIN){
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"),
                            new SimpleGrantedAuthority("ROLE_FATURAMENTO"),
                            new SimpleGrantedAuthority("ROLE_AGENDAMENTO"),
                            new SimpleGrantedAuthority("ROLE_ATENDIMENTO"),
                            new SimpleGrantedAuthority("ROLE_PROFISSIONAL"));

        } else if(this.role == UserRole.FATURAMENTO){
            return List.of(new SimpleGrantedAuthority("ROLE_FATURAMENTO"),
                            new SimpleGrantedAuthority("ROLE_AGENDAMENTO"),
                            new SimpleGrantedAuthority("ROLE_ATENDIMENTO") );

        } else if (this.role == UserRole.AGENDAMENTO){
            return List.of(new SimpleGrantedAuthority("ROLE_AGENDAMENTO"),
                            new SimpleGrantedAuthority("ROLE_ATENDIMENTO") );

        } else if (this.role == UserRole.PROFISSIONAL){
            return List.of(new SimpleGrantedAuthority("ROLE_PROFISSIONAL"));

        } else if (this.role == UserRole.PROFISSIONAL){
            return List.of(new SimpleGrantedAuthority("ROLE_PROFISSIONAL"));

        } else return List.of(new SimpleGrantedAuthority("ROLE_ATENDIMENTO"));
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.senha;
    }

}
