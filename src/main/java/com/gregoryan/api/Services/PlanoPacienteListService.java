package com.gregoryan.api.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gregoryan.api.Exception.EntityDontExistException;
import com.gregoryan.api.Models.Empresa;
import com.gregoryan.api.Models.PlanoPaciente;
import com.gregoryan.api.Services.Crud.PlanoPacienteService;
import com.gregoryan.api.Services.Interfaces.PlanoPacienteListInterface;

@Service
public class PlanoPacienteListService implements PlanoPacienteListInterface{

    @Autowired
    private PlanoPacienteService planoPacienteService;
    @Autowired
    private UsuarioValidate usuarioValidate;

    @Override
    public PlanoPaciente list(long id, Empresa empresa) {
        var planoPaciente = planoPacienteService.findById(id).orElseThrow(() -> new EntityDontExistException("Plano não encontrado"));
        usuarioValidate.isSameEmpresaFromUserLogged(empresa, planoPaciente.getEmpresa());

        return planoPaciente;
    }

    @Override
    public Page<PlanoPaciente> list(Empresa empresa, Pageable pageable) {
        return planoPacienteService.findByEmpresa(empresa, pageable);
    }    
}
