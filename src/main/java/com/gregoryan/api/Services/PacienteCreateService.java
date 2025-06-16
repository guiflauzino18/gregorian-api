package com.gregoryan.api.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gregoryan.api.DTO.PacienteCadastroDTO;
import com.gregoryan.api.Models.Empresa;
import com.gregoryan.api.Models.Paciente;
import com.gregoryan.api.Services.Crud.PacienteService;
import com.gregoryan.api.Services.Interfaces.PacienteConverterInterface;
import com.gregoryan.api.Services.Interfaces.PacienteValidateInterface;
import com.gregoryan.api.Services.Interfaces.PlanoPacienteListInterface;

@Service
public class PacienteCreateService {
    
    @Autowired
    private PacienteConverterInterface pacienteConverter;
    @Autowired
    private PacienteService pacienteService;
    @Autowired
    private PacienteValidateInterface pacienteValidate;
    @Autowired
    private PlanoPacienteListInterface planoPacienteList;
    @Autowired
    private EnderecoCreateService enderecoCreate;


    public Paciente create(PacienteCadastroDTO dto, Empresa empresa){

        var paciente = pacienteConverter.toPaciente(dto);
        pacienteValidate.validate(paciente);
        var plano = planoPacienteList.list(dto.idPlanoPaciente(), empresa);
        var endereco = enderecoCreate.create(dto.endereco());
        paciente.setPlanoPaciente(plano);
        paciente.setEndereco(endereco);

        return pacienteService.save(paciente);

    }
}