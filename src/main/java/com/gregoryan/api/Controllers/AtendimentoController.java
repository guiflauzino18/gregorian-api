package com.gregoryan.api.Controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.gregoryan.api.Models.Paciente;
import com.gregoryan.api.DTO.PacienteSaveDTO;
import com.gregoryan.api.Models.Usuario;
import com.gregoryan.api.Services.Crud.PacienteService;
import com.gregoryan.api.Services.Crud.UsuarioService;
import com.gregoryan.api.Services.Security.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/atendimento")
public class AtendimentoController {

    @Autowired
    private PacienteService pacienteService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private UsuarioService usuarioService;
    
    
    // ================================= Paciente ==========================
        //================================== PACIENTE =======================================

    @GetMapping("/paciente/list")
    public ResponseEntity<Page<Paciente>> findAll(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable){
        return new ResponseEntity<>(pacienteService.findAll(pageable), HttpStatus.OK);
    }

    @PostMapping("/paciente/cadastro")
    public ResponseEntity<Object> pacienteCadastro(@RequestBody @Valid PacienteSaveDTO pacienteDTO, HttpServletRequest request){
        if(pacienteService.existsByCpf(pacienteDTO.cpf())) return new ResponseEntity<>("Conflito: CPF já existe!", HttpStatus.CONFLICT);

        var paciente = new Paciente();
        BeanUtils.copyProperties(pacienteDTO, paciente);

        Usuario logado = usuarioService.findByLogin(tokenService.validateToken(tokenService.recoverToken(request))).get();
        paciente.setEmpresa(logado.getEmpresa());
        
        Calendar now = Calendar.getInstance(TimeZone.getTimeZone("GMT"), new Locale("pt-BR"));
        paciente.setDataRegistro(now);

        return new ResponseEntity<>(pacienteService.save(paciente), HttpStatus.CREATED);

    }
    
    
}
