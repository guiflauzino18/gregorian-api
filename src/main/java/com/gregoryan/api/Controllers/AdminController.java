package com.gregoryan.api.Controllers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.TimeZone;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import com.gregoryan.api.DTO.AgendaCadastroDTO;
import com.gregoryan.api.DTO.StatusAgendaCadastroDTO;
import com.gregoryan.api.DTO.planoPacienteCadastroDTO;
import com.gregoryan.api.Models.Agenda;
import com.gregoryan.api.Models.Dias;
import com.gregoryan.api.Models.PlanoPaciente;
import com.gregoryan.api.Models.Profissional;
import com.gregoryan.api.Models.StatusAgenda;
import com.gregoryan.api.Models.Usuario;
import com.gregoryan.api.Services.Crud.AgendaService;
import com.gregoryan.api.Services.Crud.PlanoPacienteService;
import com.gregoryan.api.Services.Crud.ProfissionalService;
import com.gregoryan.api.Services.Crud.StatusAgendaService;
import com.gregoryan.api.Services.Crud.StatusDiaService;
import com.gregoryan.api.Services.Crud.UsuarioService;
import com.gregoryan.api.Services.Security.TokenService;


@RestController
@RequestMapping("/api/admin")
public class AdminController {
    
    @Autowired
    private PlanoPacienteService planoPacienteService;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private StatusAgendaService statusAgendaService;
    @Autowired
    private ProfissionalService profissionalService;
    @Autowired
    private AgendaService agendaService;
    @Autowired
    private StatusDiaService statusDiaService;
    

    // ================================= PLANO PACIENTE ==================================

    @PostMapping("/planopaciente/cadastro")
    public ResponseEntity<PlanoPaciente> planoPacienteCadastro(@RequestBody planoPacienteCadastroDTO planoPacienteDTO, HttpServletRequest request){ 
        PlanoPaciente planoPaciente = new PlanoPaciente();

        BeanUtils.copyProperties(planoPacienteDTO, planoPaciente);

        Calendar now = Calendar.getInstance(TimeZone.getTimeZone("GMT"), new Locale("pt-BR"));

        planoPaciente.setDataRegistro(now);

        Usuario usuario = usuarioService.findByLogin(tokenService.validateToken(tokenService.recoverToken(request))).get();
        planoPaciente.setEmpresa(usuario.getEmpresa());

        planoPaciente.setStatus(PlanoPaciente.PLANO_STATUS_ATIVO);

        return new ResponseEntity<PlanoPaciente>(planoPacienteService.save(planoPaciente), HttpStatus.CREATED);
        
    }

    // ================================= Usuários dos Sistema =================================

    @PostMapping("/usuario/cadastro")
    public ResponseEntity<Object> saveUsuario(@RequestBody @Valid Usuario usuario, HttpServletRequest request) {
        
        if (usuarioService.existByLogin(usuario.getLogin())){
            return new ResponseEntity<>("Conflito: Usuário já existe!", HttpStatus.CONFLICT);
        }

        Calendar now = Calendar.getInstance(TimeZone.getTimeZone("GMT"), new Locale("pt-BR"));
        usuario.setDataRegistro(now);
        String encryptedPassword = new BCryptPasswordEncoder().encode(usuario.getSenha());
        usuario.setSenha(encryptedPassword);
        Optional<Usuario> logado = usuarioService.findByLogin(tokenService.validateToken(tokenService.recoverToken(request)));
        usuario.setEmpresa(logado.get().getEmpresa());

        return new ResponseEntity<>(usuarioService.save(usuario), HttpStatus.CREATED);
        
    }

    // =============================================== AGENDA ======================================
    @PostMapping("/agenda/cadastro")
    public ResponseEntity<Object> agendaCadastro(@RequestBody @Valid AgendaCadastroDTO agendaDTO, HttpServletRequest request){

        if(agendaService.existsByNome(agendaDTO.nome()))
            return new ResponseEntity<>("Conflito: Nome de agenda já existe!", HttpStatus.CONFLICT);
        
        Agenda agenda = new Agenda();

        StatusAgenda statusAgenda = new StatusAgenda();
        if (statusAgendaService.findByNome("Ativo").isPresent()) 
            agenda.setStatusAgenda(statusAgenda);

        Usuario logado = usuarioService.findByLogin(tokenService.validateToken(tokenService.recoverToken(request))).get();
        agenda.setEmpresa(logado.getEmpresa());

        List<Dias> dias = new ArrayList<Dias>();
        for (String nome : agendaDTO.dias()) {
            Dias dia = new Dias();
            dia.setNome(nome);
            dia.setStatusDia(statusDiaService.findByNome("Ativo").get());
            dias.add(dia);
        }
        agenda.setDias(dias);

        agendaService.save(agenda);

        Profissional profissional = profissionalService.findById(agendaDTO.profissionalID()).get();
        profissional.setAgenda(agenda);
        profissionalService.save(profissional);

        return new ResponseEntity<>(agenda, HttpStatus.CREATED);

    }

    @PostMapping("/agenda/status/cadastro")
    public ResponseEntity<Object> statusAgendaCadstro(@RequestBody @Valid StatusAgendaCadastroDTO statusAgendaDTO){
        if (statusAgendaService.existsByNome(statusAgendaDTO.nome())) 
            return new ResponseEntity<>("Conflito: Nome já existe!", HttpStatus.CONFLICT);
        

        StatusAgenda statusAgenda = new StatusAgenda();
        statusAgenda.setNome(statusAgendaDTO.nome());
        
        return new ResponseEntity<>(statusAgendaService.save(statusAgenda), HttpStatus.CREATED);
    }
    

}
