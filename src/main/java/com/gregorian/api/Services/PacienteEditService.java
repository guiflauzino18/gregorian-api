package com.gregorian.api.Services;

import com.gregorian.api.Models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gregorian.api.DTO.PacienteEditDTO;
import com.gregorian.api.Services.Crud.PacienteService;
import com.gregorian.api.Interfaces.PacienteConverterInterface;
import com.gregorian.api.Interfaces.PacienteListInterface;
import com.gregorian.api.Interfaces.PacienteValidateInterface;

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

