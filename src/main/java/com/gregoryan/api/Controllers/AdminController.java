package com.gregoryan.api.Controllers;

import java.time.LocalTime;
import java.util.Calendar;
import java.util.Locale;
import java.util.Optional;
import java.util.TimeZone;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import com.gregoryan.api.DTO.AgendaCadastroDTO;
import com.gregoryan.api.DTO.AgendaConfigDTO;
import com.gregoryan.api.DTO.AgendaEditDTO;
import com.gregoryan.api.DTO.DiaCadastroDTO;
import com.gregoryan.api.DTO.DiaEditDTO;
import com.gregoryan.api.DTO.HorasEditDTO;
import com.gregoryan.api.DTO.StatusAgendaCadastroDTO;
import com.gregoryan.api.DTO.UsuarioCadastroDTO;
import com.gregoryan.api.DTO.UsuarioEditDTO;
import com.gregoryan.api.DTO.planoPacienteCadastroDTO;
import com.gregoryan.api.DTO.usuarioResetSenhaDTO;
import com.gregoryan.api.Models.Agenda;
import com.gregoryan.api.Models.Dias;
import com.gregoryan.api.Models.Horas;
import com.gregoryan.api.Models.PlanoPaciente;
import com.gregoryan.api.Models.Profissional;
import com.gregoryan.api.Models.StatusAgenda;
import com.gregoryan.api.Models.StatusDia;
import com.gregoryan.api.Models.StatusHora;
import com.gregoryan.api.Models.Usuario;
import com.gregoryan.api.Services.Crud.AgendaService;
import com.gregoryan.api.Services.Crud.DiasService;
import com.gregoryan.api.Services.Crud.HorasService;
import com.gregoryan.api.Services.Crud.PlanoPacienteService;
import com.gregoryan.api.Services.Crud.ProfissionalService;
import com.gregoryan.api.Services.Crud.StatusAgendaService;
import com.gregoryan.api.Services.Crud.StatusDiaService;
import com.gregoryan.api.Services.Crud.StatusHoraService;
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
    @Autowired
    private StatusHoraService statusHoraService;
    @Autowired
    private DiasService diasService;
    @Autowired
    private HorasService horasService;
    

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

    //Cadastro de Usuário
    @PostMapping("/usuario/cadastro")
    public ResponseEntity<Object> saveUsuario(@RequestBody @Valid UsuarioCadastroDTO usuarioDTO, HttpServletRequest request) {
        
        if (usuarioService.existByLogin(usuarioDTO.login())){
            return new ResponseEntity<>("Conflito: Usuário já existe!", HttpStatus.CONFLICT);
        }

        Usuario usuario = new Usuario();
        BeanUtils.copyProperties(usuarioDTO, usuario);

        Calendar now = Calendar.getInstance(TimeZone.getTimeZone("GMT"), new Locale("pt-BR"));
        usuario.setDataRegistro(now);

        String encryptedPassword = new BCryptPasswordEncoder().encode(usuarioDTO.senha());
        usuario.setSenha(encryptedPassword);
        
        Optional<Usuario> logado = usuarioService.findByLogin(tokenService.validateToken(tokenService.recoverToken(request)));
        usuario.setEmpresa(logado.get().getEmpresa());

        usuario.setStatus(Usuario.STATUS_ATIVO);

        Calendar nascimento = Calendar.getInstance();
        int ano = Integer.parseInt(usuarioDTO.nascimento().split("-")[0]);
        int mes = Integer.parseInt(usuarioDTO.nascimento().split("-")[1]);
        int dia = Integer.parseInt(usuarioDTO.nascimento().split("-")[2]);
        nascimento.set(ano,mes,dia);
        usuario.setNascimento(nascimento);


        return new ResponseEntity<>(usuarioService.save(usuario), HttpStatus.CREATED);
        
    }

    //Exclusão de usuário
    @DeleteMapping("/usuario/exclui/{id}")
    public ResponseEntity<Object> deleteUsuario(@PathVariable long id){
        Optional<Usuario> usuario = usuarioService.findById(id);
        if(usuario.isPresent()){
            usuarioService.delete(usuario.get());
            return new ResponseEntity<>("Usuário "+usuario.get().getNome()+" excluído do sistema!", HttpStatus.OK);
        } else return new ResponseEntity<>("Usuário não encontrado!", HttpStatus.NOT_FOUND);
    }

    //List Usuario por Empresa
    @GetMapping("/usuario/list")
    public ResponseEntity<Page<Usuario>> usuarioListByEmpresa(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable, HttpServletRequest request){
        Usuario logado = usuarioService.findByLogin(tokenService.validateToken(tokenService.recoverToken(request))).get();
        return new ResponseEntity<>(usuarioService.findByEmpresa(logado.getEmpresa(), pageable), HttpStatus.OK);
    }

    //Edição de Usuário
    @PutMapping("/usuario/edit")
    public ResponseEntity<Object> editUsuario(@RequestBody @Valid UsuarioEditDTO usuarioDTO){
        Optional<Usuario> usuario = usuarioService.findById(usuarioDTO.id());
        if (usuario.isPresent()){

            Calendar nascimento = Calendar.getInstance(TimeZone.getTimeZone("GMT"), new Locale("pt-BR"));
            int ano = Integer.parseInt(usuarioDTO.nascimento().split("-")[0]);
            int mes = Integer.parseInt(usuarioDTO.nascimento().split("-")[1]);
            int dia = Integer.parseInt(usuarioDTO.nascimento().split("-")[2]);
            nascimento.set(ano, mes, dia);
            usuario.get().setNome(usuarioDTO.nome());
            usuario.get().setSobrenome(usuarioDTO.sobrenome());
            usuario.get().setNascimento(nascimento);
            usuario.get().setTelefone(usuarioDTO.telefone());
            usuario.get().setEmail(usuarioDTO.email());
            usuario.get().setStatus(usuarioDTO.status());
            usuario.get().setEndereco(usuarioDTO.endereco());
            usuario.get().setRole(usuarioDTO.role());
            
            return new ResponseEntity<>(usuarioService.save(usuario.get()), HttpStatus.OK);
        } else return new ResponseEntity<Object>("Usuário não encontrado!", HttpStatus.NOT_FOUND);
        
    }

    //Reset de Senha
    @PutMapping("/usuario/resetsenha")
    public ResponseEntity<Object> usuarioResetPass(@RequestBody @Valid usuarioResetSenhaDTO usuarioDTO){
        if(usuarioService.findById(usuarioDTO.id()).isPresent()){
            Usuario usuario = usuarioService.findById(usuarioDTO.id()).get();
            String encryptedPassword = new BCryptPasswordEncoder().encode(usuarioDTO.senha());
            usuario.setSenha(encryptedPassword);
            usuario.setAlteraNextLogon(usuarioDTO.alteraNextLogon() == 1 ? true : false);

            return new ResponseEntity<>(usuarioService.save(usuario), HttpStatus.OK);
        } else return new ResponseEntity<>("Usuário não encontrado!", HttpStatus.NOT_FOUND);
    }



    // =============================================== AGENDA ======================================

    //Cadastro de Agenda
    @PostMapping("/agenda/cadastro")
    public ResponseEntity<Object> agendaCadastro(@RequestBody @Valid AgendaCadastroDTO agendaDTO, HttpServletRequest request){

        if(agendaService.existsByNome(agendaDTO.nome()))
            return new ResponseEntity<>("Conflito: Nome de agenda já existe!", HttpStatus.CONFLICT);
        
        Agenda agenda = new Agenda();

        
        if (statusAgendaService.findByNome("Ativo").isPresent()) 
            agenda.setStatusAgenda(statusAgendaService.findByNome("Ativo").get());

        Usuario logado = usuarioService.findByLogin(tokenService.validateToken(tokenService.recoverToken(request))).get();
        agenda.setEmpresa(logado.getEmpresa());

        agenda.setNome(agendaDTO.nome());

        return new ResponseEntity<>(agendaService.save(agenda), HttpStatus.CREATED);

    }

    //List Agendas
    @GetMapping("/agenda/list")
    public ResponseEntity<Page<Agenda>> agendaList(
        @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
        HttpServletRequest request){
        
        Usuario usuario = usuarioService.findByLogin(tokenService.validateToken(tokenService.recoverToken(request))).get();
        return new ResponseEntity<>(agendaService.findByEmpresa(usuario.getEmpresa(), pageable), HttpStatus.OK);
    }

    //Exclui Agenda
    @DeleteMapping("/agenda/delete/{id}")
    public ResponseEntity<String> agendaDelete(@PathVariable long id){
        Optional<Agenda> agenda = agendaService.findById(id);
        if (agenda.isPresent()) {
            try {
                agendaService.delete(agenda.get());
                return new ResponseEntity<String> ("Agenda excluída do sistema!", HttpStatus.OK);

            } catch (Exception e) {
                return new ResponseEntity<>("Nâo é possível excluir essa Agenda!\n"+e, HttpStatus.FORBIDDEN);
            }
            
        } else return new ResponseEntity<>("Agenda não encontrada!", HttpStatus.NOT_FOUND);
    }

    //Configura Agenda
    @PutMapping("/agenda/config")
    public ResponseEntity<Object> agendaConfig(@RequestBody @Valid AgendaConfigDTO agendaDTO){
        Optional<StatusHora> statusHora = statusHoraService.findByNome("Ativo");
        Optional<StatusDia> statusDia = statusDiaService.findByNome("Ativo");
        Optional<Agenda> agenda = agendaService.findById(agendaDTO.idAgenda());

        if (!agenda.isPresent()) return new ResponseEntity<>("Agenda não encontrada!", HttpStatus.OK);

        for (DiaCadastroDTO diaDTO : agendaDTO.dias()) {
            Dias dia = new Dias();
            dia.setNome(diaDTO.nome());
            
            dia.setDuracaoSessaoInMinutes(diaDTO.duracaoSessaoInMinutes());
            dia.setIntervaloSesssaoInMinutes(diaDTO.intervaloSessaoInMinutes());

            LocalTime inicio = LocalTime.of(Integer.parseInt(diaDTO.inicio().split(":")[0]), Integer.parseInt(diaDTO.inicio().split(":")[1]));
            LocalTime fim = LocalTime.of(Integer.parseInt(diaDTO.fim().split(":")[0]), Integer.parseInt(diaDTO.fim().split(":")[1]));
            dia.setInicio(inicio);
            dia.setFim(fim);

            if (statusDia.isPresent()) dia.setStatusDia(statusDia.get());      
            
            if (statusHora.isPresent()) dia.createHoras(statusHora.get(), horasService);
            
            agenda.get().getDias().add(dia);
        }

        agendaService.save(agenda.get());

        Optional<Profissional> profissional = profissionalService.findById(agendaDTO.idProfissional());
        if (profissional.isPresent()) {
            profissional.get().setAgenda(agenda.get());
            profissionalService.save(profissional.get());
        }
        return new ResponseEntity<>(agenda.get(), HttpStatus.OK);
    }

    //Edita Agenda
    @PutMapping("/agenda/edit")
    public ResponseEntity<Object> agendaEdit(@RequestBody @Valid AgendaEditDTO agendaDTO){
        if(!agendaService.findById(agendaDTO.idAgenda()).isPresent()) 
            return new ResponseEntity<>("Agenda não encontrada!", HttpStatus.NOT_FOUND);

        Agenda agenda = agendaService.findById(agendaDTO.idAgenda()).get();
        agenda.setNome(agendaDTO.nome());
        if (statusAgendaService.findById(agendaDTO.idStatusAgenda()).isPresent()) 
            agenda.setStatusAgenda(statusAgendaService.findById(agendaDTO.idStatusAgenda()).get());

        for (DiaEditDTO diaDTO : agendaDTO.diaDTO()) {
            Optional<Dias> dia = diasService.findById(diaDTO.idDia());
            if (dia.isPresent()) {
                long duracaoSessaoBeforeEdit = dia.get().getDuracaoSessaoInMinutes();
                long intervaloSessaoBeforeEdit = dia.get().getIntervaloSesssaoInMinutes();
                LocalTime inicioBeforeEdit = dia.get().getInicio();
                LocalTime fimBeforeEdit = dia.get().getFim();

                dia.get().setDuracaoSessaoInMinutes(diaDTO.duracaoSessaoInMinutes());
                dia.get().setIntervaloSesssaoInMinutes(diaDTO.intervaloSessaoInMinutes());
                Optional<StatusDia> statusDia = statusDiaService.findById(diaDTO.idStatusDIa());
                if (statusDia.isPresent()) dia.get().setStatusDia(statusDia.get());
                LocalTime inicio = LocalTime.of(Integer.parseInt(diaDTO.inicio().split(":")[0]), Integer.parseInt(diaDTO.inicio().split(":")[0]));
                LocalTime fim = LocalTime.of(Integer.parseInt(diaDTO.inicio().split(":")[1]), Integer.parseInt(diaDTO.inicio().split(":")[1]));
                dia.get().setInicio(inicio);
                dia.get().setFim(fim);

                if (duracaoSessaoBeforeEdit != dia.get().getDuracaoSessaoInMinutes() ||
                    intervaloSessaoBeforeEdit != dia.get().getIntervaloSesssaoInMinutes()||
                    !inicio.equals(inicioBeforeEdit) || !fim.equals(fimBeforeEdit)) {
                        
                        StatusHora statusHora = statusHoraService.findByNome("Ativo").get();
                        dia.get().createHoras(statusHora, horasService);
                }

                diasService.save(dia.get());

                for (HorasEditDTO horaDTO : diaDTO.horaDTO()) {
                    Optional<Horas> hora = horasService.findById(horaDTO.idHora());
                    if (hora.isPresent()){
                        Optional<StatusHora> statusHora = statusHoraService.findById(horaDTO.idHora());
                        if (statusHora.isPresent()) hora.get().setStatusHora(statusHora.get());

                        horasService.save(hora.get());
                    }
                }
            }
        }

        return new ResponseEntity<>(agendaService.save(agenda),HttpStatus.OK);
    }

    //Cadastro de Status Agenda
    @PostMapping("/agenda/status/cadastro")
    public ResponseEntity<Object> statusAgendaCadstro(@RequestBody @Valid StatusAgendaCadastroDTO statusAgendaDTO){
        if (statusAgendaService.existsByNome(statusAgendaDTO.nome())) 
            return new ResponseEntity<>("Conflito: Nome já existe!", HttpStatus.CONFLICT);
        

        StatusAgenda statusAgenda = new StatusAgenda();
        statusAgenda.setNome(statusAgendaDTO.nome());
        
        return new ResponseEntity<>(statusAgendaService.save(statusAgenda), HttpStatus.CREATED);
    }
    

}
