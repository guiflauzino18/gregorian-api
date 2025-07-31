package com.gregorian.api.Interfaces;

import com.gregorian.api.DTO.PacienteCadastroDTO;
import com.gregorian.api.DTO.PacienteEditDTO;
import com.gregorian.api.Models.Paciente;

public interface PacienteConverterInterface {
    public Paciente toPaciente(PacienteCadastroDTO dto);
    public Paciente toPaciente(PacienteEditDTO dto);
}
