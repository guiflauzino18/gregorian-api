package com.gregoryan.api.Interfaces;

import com.gregoryan.api.DTO.PacienteCadastroDTO;
import com.gregoryan.api.DTO.PacienteEditDTO;
import com.gregoryan.api.Models.Paciente;

public interface PacienteConverterInterface {
    public Paciente toPaciente(PacienteCadastroDTO dto);
    public Paciente toPaciente(PacienteEditDTO dto);
}
