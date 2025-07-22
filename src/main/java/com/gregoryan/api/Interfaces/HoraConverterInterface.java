package com.gregoryan.api.Interfaces;

import com.gregoryan.api.DTO.HoraResposeDTO;
import com.gregoryan.api.Models.Hora;

public interface HoraConverterInterface {

    HoraResposeDTO toResponseDTO(Hora hora);
}
