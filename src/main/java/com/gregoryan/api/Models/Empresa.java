package com.gregoryan.api.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private String nome;
    private String cnpj;
    private String endereco;
    private String telefone;
    private String responsavel;

    @OneToOne
    @JoinColumn(name = "status_empresa_fk", referencedColumnName = "id")
    private StatusEmpresa StatusEmpresa;

    @OneToOne
    @JoinColumn(name = "plano_empresa_fk", referencedColumnName = "id")
    private PlanoEmpresa PlanoEmpresa;
}
