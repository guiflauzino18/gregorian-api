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
import java.util.Calendar;

@Entity
@Table(name = "tbl_faturamento_empresa")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FaturamentoEmpresa {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(name = "empresa_fk", referencedColumnName = "id")
    Empresa empresa;

    @OneToOne
    @JoinColumn(name = "plano_empresa_fk", referencedColumnName = "id")
    PlanoEmpresa planoEmpresa;

    private float desconto;
    
    private Calendar data;
}


