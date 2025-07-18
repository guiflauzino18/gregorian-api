package com.gregoryan.api.Services;

import com.gregoryan.api.Models.Usuario;
import com.gregoryan.api.Interfaces.UsuarioValidateInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gregoryan.api.Exception.EntityDontExistException;
import com.gregoryan.api.Models.Empresa;
import com.gregoryan.api.Models.PlanoPaciente;
import com.gregoryan.api.Services.Crud.PlanoPacienteService;
import com.gregoryan.api.Interfaces.PlanoPacienteListInterface;

@Service
public class PlanoPacienteListService implements PlanoPacienteListInterface{

    @Autowired
    private PlanoPacienteService planoPacienteService;
    @Autowired
    private UsuarioValidateInterface usuarioValidate;

    @Override
    public PlanoPaciente list(long id, Usuario usuario) {
        var planoPaciente = planoPacienteService.findById(id).orElseThrow(() -> new EntityDontExistException("Plano não encontrado"));
        usuarioValidate.validate(usuario, planoPaciente.getEmpresa());

        return planoPaciente;
    }

    @Override
    public Page<PlanoPaciente> list(Empresa empresa, Pageable pageable) {
        return planoPacienteService.findByEmpresa(empresa, pageable);
    }    
}
