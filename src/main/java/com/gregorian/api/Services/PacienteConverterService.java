package com.gregorian.api.Services;

import org.springframework.stereotype.Service;

import com.gregorian.api.DTO.PacienteCadastroDTO;
import com.gregorian.api.DTO.PacienteEditDTO;
import com.gregorian.api.Models.Paciente;
import com.gregorian.api.Interfaces.PacienteConverterInterface;

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