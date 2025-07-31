package com.gregorian.api.Services;

import com.gregorian.api.Models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gregorian.api.DTO.PacienteCadastroDTO;
import com.gregorian.api.Models.Paciente;
import com.gregorian.api.Services.Crud.PacienteService;
import com.gregorian.api.Interfaces.PacienteConverterInterface;
import com.gregorian.api.Interfaces.PacienteValidateInterface;
import com.gregorian.api.Interfaces.PlanoPacienteListInterface;

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


    public Paciente create(PacienteCadastroDTO dto, Usuario usuario){

        var paciente = pacienteConverter.toPaciente(dto);
        pacienteValidate.validate(paciente);
        var plano = planoPacienteList.list(dto.idPlanoPaciente(), usuario);
        var endereco = enderecoCreate.create(dto.endereco());
        paciente.setPlanoPaciente(plano);
        paciente.setEndereco(endereco);

        return pacienteService.save(paciente);

    }
}