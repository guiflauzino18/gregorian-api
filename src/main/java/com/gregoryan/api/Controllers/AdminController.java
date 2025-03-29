package com.gregoryan.api.Controllers;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.TimeZone;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.stream.Collectors;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import com.gregoryan.api.DTO.AgendaCadastroDTO;
import com.gregoryan.api.DTO.AgendaConfigDTO;
import com.gregoryan.api.DTO.AgendaEditDTO;
import com.gregoryan.api.DTO.DiaCadastroDTO;
import com.gregoryan.api.DTO.DiaEditDTO;
import com.gregoryan.api.DTO.FeriadoCadastroDTO;
import com.gregoryan.api.DTO.FeriadoEditDTO;
import com.gregoryan.api.DTO.HorasEditDTO;
import com.gregoryan.api.DTO.ProfissionalCadastroDTO;
import com.gregoryan.api.DTO.ProfissionalEditDTO;
import com.gregoryan.api.DTO.ProfissionalListDTO;
import com.gregoryan.api.DTO.StatusAgendaCadastroDTO;
import com.gregoryan.api.DTO.UsuarioCadastroDTO;
import com.gregoryan.api.DTO.UsuarioEditDTO;
import com.gregoryan.api.DTO.planoPacienteCadastroDTO;
import com.gregoryan.api.DTO.usuarioResetSenhaDTO;
import com.gregoryan.api.Models.Agenda;
import com.gregoryan.api.Models.DiaBloqueado;
import com.gregoryan.api.Models.Dias;
import com.gregoryan.api.Models.Empresa;
import com.gregoryan.api.Models.Feriado;
import com.gregoryan.api.Models.Horas;
import com.gregoryan.api.Models.PlanoPaciente;
import com.gregoryan.api.Models.Profissional;
import com.gregoryan.api.Models.StatusAgenda;
import com.gregoryan.api.Models.StatusDia;
import com.gregoryan.api.Models.StatusHora;
import com.gregoryan.api.Models.Usuario;
import com.gregoryan.api.Services.Crud.AgendaService;
import com.gregoryan.api.Services.Crud.DiaBloqueadoService;
import com.gregoryan.api.Services.Crud.DiasService;
import com.gregoryan.api.Services.Crud.FeriadoService;
import com.gregoryan.api.Services.Crud.HorasService;
import com.gregoryan.api.Services.Crud.PlanoPacienteService;
import com.gregoryan.api.Services.Crud.ProfissionalService;
import com.gregoryan.api.Services.Crud.StatusAgendaService;
import com.gregoryan.api.Services.Crud.StatusDiaService;
import com.gregoryan.api.Services.Crud.StatusHoraService;
import com.gregoryan.api.Services.Crud.UsuarioService;
import com.gregoryan.api.Services.Security.TokenService;

import ch.qos.logback.core.joran.util.beans.BeanUtil;


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
    @Autowired
    private FeriadoService feriadoService;
    @Autowired
    private DiaBloqueadoService diaBloqueadoService;

    

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
    public ResponseEntity<Object> saveUsuario(@RequestBody @Valid UsuarioCadastroDTO usuarioDTO, HttpServletRequest request) throws ParseException {
        
        if (usuarioService.existByLogin(usuarioDTO.login())){
            return new ResponseEntity<>("Conflito: Usuário já existe!", HttpStatus.CONFLICT);
        }

        Usuario usuario = new Usuario();
        BeanUtils.copyProperties(usuarioDTO, usuario);

        Calendar now = Calendar.getInstance(TimeZone.getTimeZone("GMT-3:00"), new Locale("pt-BR"));
        usuario.setDataRegistro(now);

        String encryptedPassword = new BCryptPasswordEncoder().encode(usuarioDTO.senha());
        usuario.setSenha(encryptedPassword);
        
        Optional<Usuario> logado = usuarioService.findByLogin(tokenService.validateToken(tokenService.recoverToken(request)));
        usuario.setEmpresa(logado.get().getEmpresa());

        usuario.setStatus(Usuario.STATUS_ATIVO);

        Calendar nascimento = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        nascimento.setTime(dateFormat.parse(usuarioDTO.nascimento()));
        usuario.setNascimento(nascimento);

        return new ResponseEntity<>(usuarioService.save(usuario), HttpStatus.CREATED);
        
    }

    //Exclusão de usuário
    @DeleteMapping("/usuario/exclui/{id}")
    public ResponseEntity<Object> deleteUsuario(@PathVariable long id, HttpServletRequest request){

        //REcupera Usuário logado e empresa
        Optional<Usuario> usuarioLogado = usuarioService.findByLogin(tokenService.validateToken(tokenService.recoverToken(request)));

        //Cria usuário com ID recebido da Request
        Optional<Usuario> usuario = usuarioService.findById(id);

        //Exclui somente se usuário existir e se Empresa for a mesma empresa do usário logado
        if(usuario.isPresent() && usuario.get().getEmpresa().getId() == usuarioLogado.get().getEmpresa().getId()){

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
    public ResponseEntity<Object> editUsuario(@RequestBody @Valid UsuarioEditDTO usuarioDTO, HttpServletRequest request) throws ParseException{
        //Recupera Usuário logado e Empresa
        Optional<Usuario> usuarioLogado = usuarioService.findByLogin(tokenService.validateToken(tokenService.recoverToken(request)));

        Optional<Usuario> usuario = usuarioService.findById(usuarioDTO.id());

        //SOmente edita se usuário existir e se pertencer a mesma empresa do usuário logado
        if (usuario.isPresent() && usuario.get().getEmpresa().getId() == usuarioLogado.get().getEmpresa().getId()){

            Calendar nascimento = Calendar.getInstance();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            nascimento.setTime(dateFormat.parse(usuarioDTO.nascimento()));

            usuario.get().setNome(usuarioDTO.nome());
            usuario.get().setSobrenome(usuarioDTO.sobrenome());
            usuario.get().setNascimento(nascimento);
            usuario.get().setTelefone(usuarioDTO.telefone());
            usuario.get().setEmail(usuarioDTO.email());
            usuario.get().setStatus(usuarioDTO.status());
            usuario.get().setEndereco(usuarioDTO.endereco());
            usuario.get().setRole(usuarioDTO.role());
            usuario.get().setAlteraNextLogon(usuarioDTO.alteraNextLogon());
            
            return new ResponseEntity<>(usuarioService.save(usuario.get()), HttpStatus.OK);

        } else return new ResponseEntity<Object>("Usuário não encontrado!", HttpStatus.NOT_FOUND);
        
    }

    //Reset de Senha
    @PutMapping("/usuario/resetsenha")
    public ResponseEntity<Object> usuarioResetPass(@RequestBody @Valid usuarioResetSenhaDTO usuarioDTO, HttpServletRequest request){
        Optional<Usuario> usuarioLogado = usuarioService.findByLogin(tokenService.validateToken(tokenService.recoverToken(request)));

        Optional<Usuario> usuario = usuarioService.findById(usuarioDTO.id());

        //Somente reseta senha se Empresa do Usuário for a mesma empresa do Usuário logado
        if(usuario.isPresent() && usuario.get().getEmpresa().getId() == usuarioLogado.get().getEmpresa().getId()){
            
            String encryptedPassword = new BCryptPasswordEncoder().encode(usuarioDTO.senha());
            usuario.get().setSenha(encryptedPassword);
            usuario.get().setAlteraNextLogon(usuarioDTO.alteraNextLogon());

            return new ResponseEntity<>(usuarioService.save(usuario.get()), HttpStatus.OK);
        } else return new ResponseEntity<>("Usuário não encontrado!", HttpStatus.NOT_FOUND);
    }

    //Find By ID
    @GetMapping("/usuario")
    public ResponseEntity<Object> usuarioById(@RequestParam long id){
        Optional<Usuario> usuario = usuarioService.findById(id);
        if (usuario.isPresent()) return new ResponseEntity<>(usuario.get(), HttpStatus.OK);
            else return new ResponseEntity<>("Usuário não encontrado!", HttpStatus.NOT_FOUND);
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

    //Busca Agenda por ID
    @GetMapping("/agenda/{id}")
    public ResponseEntity<Object> agendaById(@PathVariable long id , HttpServletRequest request){
        System.out.println(id);
        Optional<Agenda> agenda = agendaService.findById(id);
        Usuario usuarioLogado = usuarioService.findByLogin(tokenService.validateToken(tokenService.recoverToken(request))).get();

        //Somente busca agenda se agenda pertencer à empresa do usuário
        if (!agenda.isPresent() || agenda.get().getEmpresa().getId() == usuarioLogado.getEmpresa().getId() )
            return new ResponseEntity<> ("Agenda não encontrada!", HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(agenda.get(), HttpStatus.OK);
    }

    //Exclui Agenda
    @DeleteMapping("/agenda/delete/{id}")
    public ResponseEntity<String> agendaDelete(@PathVariable long id, HttpServletRequest request){

        Usuario usuarioLogado = usuarioService.findByLogin(tokenService.validateToken(tokenService.recoverToken(request))).get();

        Optional<Agenda> agenda = agendaService.findById(id);

        //Somente deleta agenda se Empresa da Agenda for a mesma do Usuário logado.
        if (agenda.isPresent() && agenda.get().getEmpresa().getId() == usuarioLogado.getEmpresa().getId()) {
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
    public ResponseEntity<Object> agendaConfig(@RequestBody @Valid AgendaConfigDTO agendaDTO, HttpServletRequest request){
        Optional<StatusHora> statusHora = statusHoraService.findByNome("Ativo");
        Optional<StatusDia> statusDia = statusDiaService.findByNome("Ativo");
        Optional<Agenda> agenda = agendaService.findById(agendaDTO.idAgenda());

        Usuario usuarioLogado = usuarioService.findByLogin(tokenService.validateToken(tokenService.recoverToken(request))).get();

        //Se agenda não existe ou empresa da agenda é diferente da empresa do usuário logado retorno 404
        if (!agenda.isPresent() || agenda.get().getEmpresa().getId() != usuarioLogado.getEmpresa().getId()) 
            return new ResponseEntity<>("Agenda não encontrada!", HttpStatus.OK);

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
    public ResponseEntity<Object> agendaEdit(@RequestBody @Valid AgendaEditDTO agendaDTO, HttpServletRequest request){

        Usuario usuarioLogado = usuarioService.findByLogin(tokenService.validateToken(tokenService.recoverToken(request))).get();

        //Se Agenda Não existe e empresa da Agenda é diferente da Empresa do usuário logado retorna 404
        if(!agendaService.findById(agendaDTO.idAgenda()).isPresent() || agendaService.findById(agendaDTO.idAgenda()).get().getEmpresa().getId() != usuarioLogado.getEmpresa().getId()) 
            return new ResponseEntity<>("Agenda não encontrada!", HttpStatus.NOT_FOUND);

        Agenda agenda = agendaService.findById(agendaDTO.idAgenda()).get();
        agenda.setNome(agendaDTO.nome());
        if (statusAgendaService.findById(agendaDTO.idStatusAgenda()).isPresent()) 
            agenda.setStatusAgenda(statusAgendaService.findById(agendaDTO.idStatusAgenda()).get());

        Optional<Profissional> profissional = profissionalService.findById(agendaDTO.idProfissional());
        if (profissional.isPresent())
            agenda.setProfissional(profissional.get());

        return new ResponseEntity<>(agendaService.save(agenda),HttpStatus.OK);
    }

    //Edita Dias da Agenda
    @PutMapping("/agenda/edit/dia")
    public ResponseEntity<Object> agendaEditDia(@RequestBody @Valid DiaEditDTO diaDTO){

        Optional<Dias> dia = diasService.findById(diaDTO.idDia());

        if (dia.isPresent()) {
            long duracaoSessaoBeforeEdit = dia.get().getDuracaoSessaoInMinutes();
            long intervaloSessaoBeforeEdit = dia.get().getIntervaloSesssaoInMinutes();
            LocalTime inicioBeforeEdit = dia.get().getInicio();
            LocalTime fimBeforeEdit = dia.get().getFim();

            dia.get().setDuracaoSessaoInMinutes(diaDTO.duracaoSessaoInMinutes());
            dia.get().setIntervaloSesssaoInMinutes(diaDTO.intervaloSessaoInMinutes());
            Optional<StatusDia> statusDia = statusDiaService.findById(diaDTO.idStatusDia());
            if (statusDia.isPresent()) dia.get().setStatusDia(statusDia.get());
            LocalTime inicio = LocalTime.of(Integer.parseInt(diaDTO.inicio().split(":")[0]), Integer.parseInt(diaDTO.inicio().split(":")[1]));
            LocalTime fim = LocalTime.of(Integer.parseInt(diaDTO.fim().split(":")[0]), Integer.parseInt(diaDTO.fim().split(":")[1]));
            dia.get().setInicio(inicio);
            dia.get().setFim(fim);

            
            if (duracaoSessaoBeforeEdit != dia.get().getDuracaoSessaoInMinutes() ||
                intervaloSessaoBeforeEdit != dia.get().getIntervaloSesssaoInMinutes()||
                !inicio.equals(inicioBeforeEdit) || !fim.equals(fimBeforeEdit)) {
                    
                StatusHora statusHora = statusHoraService.findByNome("Ativo").get();

                dia.get().createHoras(statusHora, horasService);
            }
            
            return new ResponseEntity<>(diasService.save(dia.get()), HttpStatus.OK);

        } else return new ResponseEntity<>("Dia não encontrado!", HttpStatus.NOT_FOUND);
    
    }

    //Edit Horas do dia da Agenda
    @PutMapping("/agenda/edit/horas")
    public ResponseEntity<Object> agendaEditHoras(@RequestBody @Valid HorasEditDTO horaDTO){
        
        Optional<Horas> hora = horasService.findById(horaDTO.idHora());
        if (hora.isPresent()){
            Optional<StatusHora> statusHora = statusHoraService.findById(horaDTO.idStatusHora());

            if (statusHora.isPresent()) hora.get().setStatusHora(statusHora.get());

            return new ResponseEntity<>(horasService.save(hora.get()), HttpStatus.OK);

        } else return new ResponseEntity<>("Hora não encontrada!", HttpStatus.NOT_FOUND);
                

    }

    @DeleteMapping("/agenda/horas/delete/{id}")
    public ResponseEntity<Object> agendaHorasDelete(@PathVariable long id){
        Optional<Horas> hora = horasService.findById(id);
        if(!hora.isPresent()) return new ResponseEntity<>("Hora não encontrada!", HttpStatus.NOT_FOUND);
        
        horasService.delete(hora.get());
        return new ResponseEntity<>("Hora deletada da Agenda", HttpStatus.OK);
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
    


    //=============================================== PROFISSIONAL =======================================================

    //Cadastro de Profissional
    @PostMapping("/profissional/cadastro")
    public ResponseEntity<Object> profissionalCadastro(@RequestBody @Valid ProfissionalCadastroDTO profissionalDTO, HttpServletRequest request){
        Usuario usuarioLogado = usuarioService.findByLogin(tokenService.validateToken(tokenService.recoverToken(request))).get();

        Optional<Usuario> usuario = usuarioService.findByLogin(profissionalDTO.login());

        //Se usuario não existe a empresa do usuario do profissional for diferente da empresa do usuario logado retorn 404
        if (!usuario.isPresent() || usuario.get().getEmpresa().getId() != usuarioLogado.getEmpresa().getId()) return new ResponseEntity<>("Usuário não encontrado!", HttpStatus.NOT_FOUND);

        Profissional profissional = new Profissional();
        BeanUtils.copyProperties(profissionalDTO, profissional);
        profissional.setUsuario(usuario.get());

        return new ResponseEntity<>(profissionalService.save(profissional), HttpStatus.CREATED);
    }

    //Editar Profissional
    @PutMapping("/profissional/edit")
    public ResponseEntity<Object> profissionalEditar(@RequestBody @Valid ProfissionalEditDTO profissionalDTO) throws Exception{
        Optional<Profissional> profissional = profissionalService.findById(profissionalDTO.id());

        Usuario usuarioLogado = usuarioService.findByLogin(tokenService.validateToken(tokenService.recoverToken(null))).get();

        //Se profissional não existir ou empresa do usuario do profissional for diferente da empresa do usuario logado retorna 404
        if (!profissional.isPresent() || profissional.get().getUsuario().getEmpresa().getId() != usuarioLogado.getEmpresa().getId()) 
            return new ResponseEntity<>("Profissional não encontrado!", HttpStatus.NOT_FOUND);

        profissional.get().setTitulo(profissionalDTO.titulo());
        profissional.get().setRegistro(profissionalDTO.registro());

        Optional<Agenda> agenda = agendaService.findById(profissionalDTO.idAgenda());
        if (agenda.isPresent()) profissional.get().setAgenda(agenda.get());

        if (profissionalDTO.idAgenda() == -1) profissional.get().setAgenda(null);

        return new ResponseEntity<>(profissionalService.save(profissional.get()), HttpStatus.OK);
    }

    //Excluir Profissional
    @DeleteMapping("/profissional/delete/{id}")
    public ResponseEntity<Object> profissionalDelete(@PathVariable long id, HttpServletRequest request){
        Optional<Profissional> profissional = profissionalService.findById(id);

        Usuario usuarioLogado = usuarioService.findByLogin(tokenService.validateToken(tokenService.recoverToken(request))).get();

        if (profissional.isPresent() && profissional.get().getUsuario().getEmpresa().getId() == usuarioLogado.getEmpresa().getId()) {
            profissionalService.delete(profissional.get());
            return new ResponseEntity<>("Profissional excluído do sistema!", HttpStatus.OK);
        }

        return new ResponseEntity<>("Profissional não encontrado!", HttpStatus.NOT_FOUND);
    }

    //Lista Profissoinal por empresa
    @GetMapping("/profissional/list")
    public ResponseEntity<Page<Profissional>> profissionalListByEmpresa(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable, HttpServletRequest request){
        Usuario usuario = usuarioService.findByLogin(tokenService.validateToken(tokenService.recoverToken(request))).get();
        return new ResponseEntity<>(profissionalService.findByEmpresa(usuario.getEmpresa().getId(), pageable), HttpStatus.OK);
    }

        //Lista Profissoinal por empresa
        @GetMapping("/profissionais")
        public ResponseEntity<List<ProfissionalListDTO>> profissionalList(@PageableDefault(page = 0, size = 1000, sort = "id", direction = Sort.Direction.DESC) Pageable pageable, HttpServletRequest request){
            Usuario usuario = usuarioService.findByLogin(tokenService.validateToken(tokenService.recoverToken(request))).get();

            Page<Profissional> page = profissionalService.findByEmpresa(usuario.getEmpresa().getId(), pageable);

            List<ProfissionalListDTO> profissionais = page.getContent().stream().map(profissional -> {
                ProfissionalListDTO dto = new ProfissionalListDTO(profissional.getUsuario().getNome(), profissional.getId());
                return dto;

            }).collect(Collectors.toList());

            return new ResponseEntity<>(profissionais, HttpStatus.OK);
        }


    @GetMapping("/profissional/findbyId")
    public ResponseEntity<Object> profissionalFindById(@RequestParam long id){
        Optional<Profissional> profissional = profissionalService.findById(id);
        if (profissional.isPresent()) return new ResponseEntity<>(profissional.get(), HttpStatus.OK);
            else return new ResponseEntity<>("Profissional não encontrado!", HttpStatus.NOT_FOUND);
        
    }


    // ========================================== FERIADOS =============================================================
    @PostMapping("/feriado/cadastro")
    public ResponseEntity<Object> feriadoCadastro(@RequestBody @Valid FeriadoCadastroDTO feriadoDTO, HttpServletRequest request){
        
        Feriado feriado = new Feriado();
        BeanUtils.copyProperties(feriadoDTO, feriado);

        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT-3:00"), new Locale("pt-BR"));
        String ano = feriadoDTO.dia().split("-")[0];
        String mes = feriadoDTO.dia().split("-")[1];
        String dia = feriadoDTO.dia().split("-")[2];
        calendar.set(Integer.parseInt(ano), Integer.parseInt(mes), Integer.parseInt(dia));
        feriado.setDia(calendar);

        Usuario usuario = usuarioService.findByLogin(tokenService.validateToken(tokenService.recoverToken(request))).get();
        feriado.setEmpresa(usuario.getEmpresa());
        return new ResponseEntity<>(feriadoService.save(feriado), HttpStatus.CREATED);
    }

    @PutMapping("/feriado/edit")
    public ResponseEntity<Object> feriadoEdit(@RequestBody @Valid FeriadoEditDTO feriadoDTO){

        Usuario usuarioLogado = usuarioService.findByLogin(tokenService.validateToken(tokenService.recoverToken(null))).get();

        Optional<Feriado> feriado = feriadoService.findById(feriadoDTO.id());

        //SOmente edita feriado se Empresa do feriado for a memsa do usuario logado.
        if (feriado.isPresent() && feriado.get().getEmpresa().getId() == usuarioLogado.getEmpresa().getId()){
            Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT-3:00"), new Locale("pt-BR"));
            int ano = Integer.parseInt(feriadoDTO.data().split("-")[0]);
            int mes = Integer.parseInt(feriadoDTO.data().split("-")[1]);
            int dia = Integer.parseInt(feriadoDTO.data().split("-")[2]); 
            calendar.set(ano, mes, dia);

            feriado.get().setNome(feriadoDTO.nome());
            feriado.get().setDia(calendar);

            return new ResponseEntity<>(feriadoService.save(feriado.get()), HttpStatus.OK);
        
        } else return new ResponseEntity<>("Feriado não encontrado!", HttpStatus.NOT_FOUND);
        
    }

    @DeleteMapping("/feriado/delete/{id}")
    public ResponseEntity<Object> feriadoDelete(@PathVariable (name = "id") long id, HttpServletRequest request){
        Usuario usuarioLogado = usuarioService.findByLogin(tokenService.validateToken(tokenService.recoverToken(request))).get();

        Optional<Feriado> feriado = feriadoService.findById(id);

        if (feriado.isPresent() && feriado.get().getEmpresa().getId() == usuarioLogado.getEmpresa().getId()) {
            
            feriadoService.delete(feriado.get());
            return new ResponseEntity<>("Feriado deletado do sistema!", HttpStatus.NOT_FOUND);

        }    else return new ResponseEntity<>("Feriado não encontrado!", HttpStatus.NOT_FOUND);
    }


    @GetMapping("/feriado/list")
    public ResponseEntity<Page<Feriado>> feriadoListByEmpresa(
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) 
            Pageable pageable, HttpServletRequest request){
                Usuario usuario = usuarioService.findByLogin(tokenService.validateToken(tokenService.recoverToken(request))).get();
                Page<Feriado> feriado = feriadoService.findByEmpresa(usuario.getEmpresa(), pageable);

                return new ResponseEntity<>(feriado, HttpStatus.OK);
            }


    // ======================================================= DIAS BLOQUEADOS ==============================================
    @PostMapping("diabloqueado/cadastro")
    public ResponseEntity<Object> diaBloqueadoCadastro(@RequestBody @Valid DiaBloqueado diaBloqueado, HttpServletRequest request){
        Usuario usuario = usuarioService.findByLogin(tokenService.validateToken(tokenService.recoverToken(request))).get();
        diaBloqueado.setEmpresa(usuario.getEmpresa());
        return new ResponseEntity<>(diaBloqueadoService.save(diaBloqueado), HttpStatus.CREATED);
    }

    @PutMapping("/diabloqueado/edit")
    public ResponseEntity<Object> diaBloqueadoEdit(@RequestBody @Valid DiaBloqueado diaBloqueado){

        return new ResponseEntity<>(diaBloqueadoService.save(diaBloqueado), HttpStatus.OK);
    }

    @DeleteMapping("/diabloqueado/delete/{id}")
    public ResponseEntity<Object> diaBloqueadoDelete(@PathVariable(name = "id") long id){
        Optional<DiaBloqueado> diaBloqueado = diaBloqueadoService.findById(id);

        if (diaBloqueado.isPresent()){
            diaBloqueadoService.delete(diaBloqueado.get());
            return new ResponseEntity<>("Dia Bloqueado deletado do sistema!", HttpStatus.OK);

        } else return new ResponseEntity<>("Dia Bloqueado não encontrado!", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/diabloqueado/list")
    public ResponseEntity<Object> diaBloqueadoListByEmpresa(
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
            HttpServletRequest request){

        Usuario usuario = usuarioService.findByLogin(tokenService.validateToken(tokenService.recoverToken(request))).get();
        return new ResponseEntity<>(diaBloqueadoService.findByEmpresa(usuario.getEmpresa(), pageable), HttpStatus.OK);
    
    }
        

}

 