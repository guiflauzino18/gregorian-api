package com.gregoryan.api.Controllers;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.TimeZone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import com.gregoryan.api.Models.PlanoPaciente;
import com.gregoryan.api.Models.Usuario;
import com.gregoryan.api.Repositorys.PlanoPacienteRepository;
import com.gregoryan.api.Services.Crud.UsuarioService;
import com.gregoryan.api.Services.Security.TokenService;


@RestController
@RequestMapping("/api/admin")
public class AdminController {
    
    @Autowired
    private PlanoPacienteRepository planoPacienteRepository;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private TokenService tokenService;
    

    // ================================= PLANO PACIENTE ==================================

    @PostMapping("/plano/cadastro")
    public ResponseEntity<String> cadastroPlano(@RequestBody PlanoPaciente planoPaciente){

        planoPacienteRepository.save(planoPaciente);

        return new ResponseEntity<String>("Plano Cadastrado com Sucesso!", HttpStatus.OK);

    }

    @GetMapping("/plano/list")
    public ResponseEntity<List<PlanoPaciente>> allPlanoPaciente(){

        List<PlanoPaciente> planos = planoPacienteRepository.findAll();

        return new ResponseEntity<List<PlanoPaciente>>(planos, HttpStatus.OK);
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
    

}
