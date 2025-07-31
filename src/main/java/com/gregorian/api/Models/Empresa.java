package com.gregorian.api.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Calendar;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_empresa")
public class Empresa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, length = 16)
    private long cnpj;

    @Column(nullable = false)
    private String endereco;

    @Column(nullable = false)
    private String telefone;

    private String responsavel;

    @Column(nullable = false)
    private Calendar dataRegistro;

    @OneToMany(mappedBy = "empresa", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("empresa")
    private List<Usuario> usuarios;

    @ManyToOne
    @JoinColumn(name = "status_empresa_fk", referencedColumnName = "id")
    private StatusEmpresa StatusEmpresa;

    @ManyToOne
    @JoinColumn(name = "plano_empresa_fk", referencedColumnName = "id")
    private PlanoEmpresa PlanoEmpresa;
    

}
