package com.gregorian.api.Interfaces;

import com.gregorian.api.DTO.HoraResposeDTO;
import com.gregorian.api.Models.Hora;

public interface HoraConverterInterface {

    HoraResposeDTO toResponseDTO(Hora hora);
}
