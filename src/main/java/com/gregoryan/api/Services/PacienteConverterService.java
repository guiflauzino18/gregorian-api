package com.gregoryan.api.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gregoryan.api.DTO.PacienteCadastroDTO;
import com.gregoryan.api.DTO.PacienteEditDTO;
import com.gregoryan.api.Models.Paciente;
import com.gregoryan.api.Services.Interfaces.PacienteConverterInterface;
import com.gregoryan.api.Services.Interfaces.PlanoPacienteListInterface;

@Service
public class PacienteConverterService implements PacienteConverterInterface{


    @Override
    public Paciente toPaciente(PacienteCadastroDTO dto) {
        var paciente = new Paciente();
        paciente.setNome(dto.nome());
        paciente.setSobrenome(dto.sobrenome());
        paciente.setTelefone(dto.telefone());
        paciente.setEmail(dto.email());
        paciente.setCpf(dto.cpf());
        paciente.setSexo(dto.sexo());

        return paciente;
        
    }

    @Override
    public Paciente toPaciente(PacienteEditDTO dto) {
        var paciente = new Paciente();
        //paciente.setId(dto.id());
        paciente.setNome(dto.nome());
        paciente.setSobrenome(dto.sobrenome());
        paciente.setTelefone(dto.telefone());
        paciente.setEmail(dto.email());
        paciente.setCpf(dto.cpf());
        paciente.setSexo(dto.sexo());
        return paciente;
    }
    
}

/*    long id,
    String nome,
    String sobrenome,
    long telefone,
    String email,
    long cpf,
    String sexo,
    long idPlanoPaciente */