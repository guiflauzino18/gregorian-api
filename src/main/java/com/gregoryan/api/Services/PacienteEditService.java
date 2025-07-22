package com.gregoryan.api.Services;

import com.gregoryan.api.Models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gregoryan.api.DTO.PacienteEditDTO;
import com.gregoryan.api.Services.Crud.PacienteService;
import com.gregoryan.api.Interfaces.PacienteConverterInterface;
import com.gregoryan.api.Interfaces.PacienteListInterface;
import com.gregoryan.api.Interfaces.PacienteValidateInterface;

@Service
public class PacienteEditService {

    @Autowired
    private PacienteConverterInterface pacienteConverter;
    @Autowired
    private PlanoPacienteListService planoPacienteList;
    @Autowired
    private PacienteService pacienteService;
    @Autowired
    private PacienteValidateInterface pacienteValidate;
    @Autowired
    private PacienteListInterface pacienteList;

    public void edit(PacienteEditDTO dto, Usuario usuario){

        var paciente = pacienteList.list(dto.id(), usuario);
        var pacienteNew = pacienteConverter.toPaciente(dto);

        paciente.setNome(pacienteNew.getNome());
        paciente.setSobrenome(pacienteNew.getSobrenome());
        paciente.setTelefone(pacienteNew.getTelefone());
        paciente.setEmail(pacienteNew.getEmail());
        paciente.setCpf(pacienteNew.getCpf());
        paciente.setSexo(pacienteNew.getSexo());
        
        pacienteValidate.validate(paciente);
        var plano = planoPacienteList.list(dto.idPlanoPaciente(), usuario);
        paciente.setPlanoPaciente(plano);

        pacienteService.save(paciente);
    }
}

