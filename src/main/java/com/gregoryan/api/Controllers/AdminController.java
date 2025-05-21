package com.gregoryan.api.Controllers;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.TimeZone;
import java.text.ParseException;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.gregoryan.api.DTO.AgendaReplicaDiaDTO;
import com.gregoryan.api.DTO.AgendaResponseDTO;
import com.gregoryan.api.DTO.DiaCadastroDTO;
import com.gregoryan.api.DTO.DiaEditDTO;
import com.gregoryan.api.DTO.FeriadoCadastroDTO;
import com.gregoryan.api.DTO.FeriadoEditDTO;
import com.gregoryan.api.DTO.HorasEditDTO;
import com.gregoryan.api.DTO.ProfissionalCadastroDTO;
import com.gregoryan.api.DTO.ProfissionalEditDTO;
import com.gregoryan.api.DTO.ProfissionalListDTO;
import com.gregoryan.api.DTO.ProfissionalResponseDTO;
import com.gregoryan.api.DTO.StatusAgendaCadastroDTO;
import com.gregoryan.api.DTO.StatusDiaCadastroDTO;
import com.gregoryan.api.DTO.StatusDiaEditDTO;
import com.gregoryan.api.DTO.StatusDiaResponseDTO;
import com.gregoryan.api.DTO.StatusHoraCadastroDTO;
import com.gregoryan.api.DTO.StatusHoraResponseDTO;
import com.gregoryan.api.DTO.UsuarioCadastroDTO;
import com.gregoryan.api.DTO.UsuarioEditDTO;
import com.gregoryan.api.DTO.planoPacienteCadastroDTO;
import com.gregoryan.api.DTO.UsuarioResetSenhaDTO;
import com.gregoryan.api.Exception.AcessoNegadoException;
import com.gregoryan.api.Exception.UsuarioDontExistException;
import com.gregoryan.api.Exception.UsuarioExisteException;
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
import com.gregoryan.api.Services.UsuarioDeletingService;
import com.gregoryan.api.Services.UsuarioEditingService;
import com.gregoryan.api.Services.UsuarioRegistrationService;
import com.gregoryan.api.Services.UsuarioResetSenha;
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
import com.gregoryan.api.Services.Interfaces.UsuarioListInterface;
import com.gregoryan.api.Services.Security.TokenService;


@RestController
@RequestMapping("/api/admin")
public class AdminController {
    
    @Autowired
    private PlanoPacienteService planoPacienteService;
 
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

    // Injetor relacionado a Usuarios
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private UsuarioRegistrationService usuarioRegistration;
    @Autowired
    private UsuarioDeletingService usuarioDeleting;
    @Autowired
    private UsuarioListInterface usuarioListing;
    @Autowired
    private UsuarioEditingService usuarioEditing;
    @Autowired
    private UsuarioResetSenha resetSenha;

    

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
        
        try {

            Empresa empresa = tokenService.getEmpresaFromToken(request, usuarioService);
            
            Usuario usuario = usuarioRegistration.cadastrar(usuarioDTO, empresa);
            
            return new ResponseEntity<>(usuario, HttpStatus.CREATED);
        }catch (UsuarioExisteException e){

            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
            
        }
        
    }

    //Exclusão de usuário
    @DeleteMapping("/usuario/exclui/{id}")
    public ResponseEntity<Object> deleteUsuario(@PathVariable long id, HttpServletRequest request){

        try{

            Empresa empresa = tokenService.getEmpresaFromToken(request, usuarioService);
            usuarioDeleting.deleteUser(id, empresa);
            return new ResponseEntity<>("Usuário excluído do sistema", HttpStatus.OK);

        } catch(UsuarioDontExistException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (AcessoNegadoException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    //List Usuario por Empresa
    @GetMapping("/usuario/list")
    public ResponseEntity<Object> usuarioListByEmpresa(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable, HttpServletRequest request){
        
        try{
            Empresa empresa = tokenService.getEmpresaFromToken(request, usuarioService);
            return new ResponseEntity<>(usuarioListing.list(empresa, pageable), HttpStatus.OK);

        }catch (UsuarioDontExistException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // Lista Usuario por ID
    @GetMapping("/usuario")
    public ResponseEntity<Object> usuarioListById(@RequestParam long id, HttpServletRequest request){

        try {
            return new ResponseEntity<>(usuarioListing.list(id), HttpStatus.OK);

        }catch(UsuarioDontExistException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
            
    }

    // Lista Usuario por Login
    @GetMapping("/usuario/findlogin")
    public ResponseEntity<Object> usuarioListByLogin(@RequestParam String login){

        System.out.println("-------------------------\n"+login);

        try {
            return new ResponseEntity<>(usuarioListing.list(login), HttpStatus.OK);

        }catch(UsuarioDontExistException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
            
    }


    //Edição de Usuário
    @PutMapping("/usuario/edit")
    public ResponseEntity<Object> editUsuario(@RequestBody @Valid UsuarioEditDTO usuarioDTO, HttpServletRequest request) throws ParseException{

        try {
            
            Empresa empresa = tokenService.getEmpresaFromToken(request, usuarioService);
            usuarioEditing.editar(usuarioDTO, empresa);
            return new ResponseEntity<>("Usuario Editado", HttpStatus.OK);
        }catch (UsuarioDontExistException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);

        }catch (AcessoNegadoException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
        
    }

    //Reset de Senha
    @PutMapping("/usuario/resetsenha")
    public ResponseEntity<Object> usuarioResetPass(@RequestBody @Valid UsuarioResetSenhaDTO usuarioDTO, HttpServletRequest request){

        
        try {

            Empresa empresa = tokenService.getEmpresaFromToken(request, usuarioService);
            resetSenha.reset(usuarioDTO, empresa);

            return new ResponseEntity<>("Senha Resetada", HttpStatus.OK);

        }catch(UsuarioDontExistException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);

        }catch(AcessoNegadoException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
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

        
        Optional<Profissional> profissional = profissionalService.findById(agendaDTO.idProfissional());

        if (!profissional.isPresent())
            return new ResponseEntity<>("Profissional não encontrado!", HttpStatus.NOT_FOUND);

        agenda.setProfissional(profissional.get());

        Usuario logado = usuarioService.findByLogin(tokenService.validateToken(tokenService.recoverToken(request))).get();
        agenda.setEmpresa(logado.getEmpresa());

        agenda.setNome(agendaDTO.nome());

        return new ResponseEntity<>(agendaService.save(agenda), HttpStatus.CREATED);

    }

    //List Agendas
    @GetMapping("/agenda/list")
    public ResponseEntity<Page<AgendaResponseDTO>> agendaList(
        @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
        HttpServletRequest request){
        
        Usuario usuario = usuarioService.findByLogin(tokenService.validateToken(tokenService.recoverToken(request))).get();

        Page<Agenda> page = agendaService.findByEmpresa(usuario.getEmpresa(), pageable);

        Page<AgendaResponseDTO> agendas = page.map(agenda -> {

            AgendaResponseDTO dto = new AgendaResponseDTO(agenda.getId(), agenda.getNome(), agenda.getStatusAgenda(), agenda.getEmpresa().getNome(),
            agenda.getProfissional().getId(), agenda.getProfissional().getUsuario().getNome(), agenda.getDias());

            return dto;
        });


        return new ResponseEntity<>(agendas, HttpStatus.OK);
    }

    //Busca Agenda por ID
    @GetMapping("/agenda/{id}")
    public ResponseEntity<Object> agendaById(@PathVariable long id , HttpServletRequest request){
        System.out.println(id);
        Optional<Agenda> agenda = agendaService.findById(id);
        Usuario usuarioLogado = usuarioService.findByLogin(tokenService.validateToken(tokenService.recoverToken(request))).get();

        //Somente busca agenda se agenda pertencer à empresa do usuário
        if (!agenda.isPresent() || agenda.get().getEmpresa().getId() != usuarioLogado.getEmpresa().getId() )
            return new ResponseEntity<> ("Agenda não encontrada!", HttpStatus.NOT_FOUND);

        //Cria DTO e passa valores de agenda para o DTO
        AgendaResponseDTO dto = new AgendaResponseDTO(agenda.get().getId(), agenda.get().getNome(), agenda.get().getStatusAgenda(),
            agenda.get().getEmpresa().getNome(), agenda.get().getProfissional().getId(), agenda.get().getProfissional().getUsuario().getNome(),
            agenda.get().getDias());
        //Retorna DTO
        return new ResponseEntity<>(dto, HttpStatus.OK);

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
            return new ResponseEntity<>("Agenda não encontrada!", HttpStatus.NOT_FOUND);

        for (DiaCadastroDTO diaDTO : agendaDTO.dias()) {

            Dias dia;

            if (diaDTO.id() != 0){
                dia = diasService.findById(diaDTO.id()).get();
            }else {
                dia = new Dias();
            }

            dia.setNome(diaDTO.nome());
            
            dia.setDuracaoSessaoInMinutes(diaDTO.duracaoSessaoInMinutes());
            dia.setIntervaloSessaoInMinutes(diaDTO.intervaloSessaoInMinutes());

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
            long intervaloSessaoBeforeEdit = dia.get().getIntervaloSessaoInMinutes();
            LocalTime inicioBeforeEdit = dia.get().getInicio();
            LocalTime fimBeforeEdit = dia.get().getFim();

            dia.get().setDuracaoSessaoInMinutes(diaDTO.duracaoSessaoInMinutes());
            dia.get().setIntervaloSessaoInMinutes(diaDTO.intervaloSessaoInMinutes());
            Optional<StatusDia> statusDia = statusDiaService.findById(diaDTO.idStatusDia());
            if (statusDia.isPresent()) dia.get().setStatusDia(statusDia.get());
            LocalTime inicio = LocalTime.of(Integer.parseInt(diaDTO.inicio().split(":")[0]), Integer.parseInt(diaDTO.inicio().split(":")[1]));
            LocalTime fim = LocalTime.of(Integer.parseInt(diaDTO.fim().split(":")[0]), Integer.parseInt(diaDTO.fim().split(":")[1]));
            dia.get().setInicio(inicio);
            dia.get().setFim(fim);

            
            if (duracaoSessaoBeforeEdit != dia.get().getDuracaoSessaoInMinutes() ||
                intervaloSessaoBeforeEdit != dia.get().getIntervaloSessaoInMinutes()||
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

    //Deleta horas da agenda
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
    


    //Lista horas por ID do dia
    @GetMapping("/agenda/horas")
    public ResponseEntity<Object> listHorasByDia(@RequestParam long id, HttpServletRequest request){
        Optional<Dias> dia = diasService.findById(id);

        if (!dia.isPresent()){
            return new ResponseEntity<>("Dia não encontrado", HttpStatus.NOT_FOUND);
        }

        List<Horas> horas = dia.get().getHoras();

        return new ResponseEntity<>(horas, HttpStatus.OK);
    }


    //Lista Status da Hora
    @GetMapping("/agenda/horas/status")
    public ResponseEntity<Object> listaStatusHora(HttpServletRequest request){
        Usuario usuarioLogado = usuarioService.findByLogin(tokenService.validateToken(tokenService.recoverToken(request))).get();

        Optional<List<StatusHora>> status = statusHoraService.findByEmpresa(usuarioLogado.getEmpresa());

        if (!status.isPresent()){
            return new ResponseEntity<>("Nenhum status encontrado", HttpStatus.NOT_FOUND);
        }

        //Além de buscar os status cadastrados pelo cliente, trás tambem os status padrão.
        Optional<StatusHora> ativo = statusHoraService.findByNome("Ativo");
        Optional<StatusHora> bloqueado = statusHoraService.findByNome("Bloqueado");

        if (ativo.isPresent() || bloqueado.isPresent()) {
            status.get().add(ativo.get());
            status.get().add(bloqueado.get());
        }

        //Passa os dados do model para o DTO
        List<StatusHoraResponseDTO> statusDTO = status.get().stream().map(item -> {
            StatusHoraResponseDTO dto = new StatusHoraResponseDTO(item.getId(), item.getNome());
            return dto;

        }).collect(Collectors.toList());

        return new ResponseEntity<>(statusDTO, HttpStatus.OK);
    }

    //Cadastra Status Hora
    @PostMapping("/agenda/horas/status")
    public ResponseEntity<Object> cadastraStatusHora(@RequestBody @Valid StatusHoraCadastroDTO statusHoraDTO, HttpServletRequest request){

        if (statusHoraService.existsByNome(statusHoraDTO.nome()))
            return new ResponseEntity<>("Status ja existe", HttpStatus.CONFLICT);

        Usuario usuarioLogado = usuarioService.findByLogin(tokenService.validateToken(tokenService.recoverToken(request))).get();
        Empresa empresa = usuarioLogado.getEmpresa();

        StatusHora status = new StatusHora();
        BeanUtils.copyProperties(statusHoraDTO, status);

        status.setEmpresa(empresa);
        statusHoraService.save(status);

        return new ResponseEntity<>("Cadastro realizado com sucesso!", HttpStatus.CREATED);
    }
   
   
    //Deleta status da hora
    @DeleteMapping("/agenda/horas/status/{id}")
    public ResponseEntity<String> deletaStatusHora(@PathVariable long id, HttpServletRequest request){

        // Nâo permite deletar status com ID 1 Ativo ou 2 Bloqueado.
        if (id == 1 || id == 2){
            return new ResponseEntity<>("Status não pode ser excluído", HttpStatus.FORBIDDEN);
        }

        Usuario usuarioLogado = usuarioService.findByLogin(tokenService.validateToken(tokenService.recoverToken(request))).get();

        Optional<StatusHora> status = statusHoraService.findById(id);

        // Se status não existe retorn erro
        if (!status.isPresent()){
            return new ResponseEntity<>("Status não encontrado", HttpStatus.NOT_FOUND);
        }

        // Se empresa do status for diferente da empresa do usuario logado retorna erro - Evita usuario excluir ID que não pertence a sua empresa
        if (status.get().getEmpresa() != usuarioLogado.getEmpresa())
            return new ResponseEntity<>("Status não encontrado", HttpStatus.NOT_FOUND);

        statusHoraService.delete(status.get());

        return new ResponseEntity<>("Status deletado", HttpStatus.OK);
    }
   
    //Lista Status do dia
    @GetMapping("/agenda/dia/status")
    public ResponseEntity<Object> listaStatusDiaByEmpresa(HttpServletRequest request){
        Usuario usuarioLogado = usuarioService.findByLogin(tokenService.validateToken(tokenService.recoverToken(request))).get();

        Optional<List<StatusDia>> statusDia = statusDiaService.findByEmpresa(usuarioLogado.getEmpresa());

        if (!statusDia.isPresent())
            return new ResponseEntity<>("Status não encontrado", HttpStatus.NOT_FOUND);

        List<StatusDiaResponseDTO> status = statusDia.get().stream().map(item -> {
            StatusDiaResponseDTO dto = new StatusDiaResponseDTO(item.getId(), item.getNome());
            return dto;

        }).collect(Collectors.toList()); //Converte Stream para List

        //Adiciona Status padrao ATIVO e BLOQUEADO na lista
        Optional<StatusDia> statusAtivo = statusDiaService.findById(1);
        if (statusAtivo.isPresent()){

            StatusDiaResponseDTO statusDTO = new StatusDiaResponseDTO(statusAtivo.get().getId(), statusAtivo.get().getNome());
            
            status.add(statusDTO);
        }

        Optional<StatusDia> statusBloqueado = statusDiaService.findById(2);
        if (statusBloqueado.isPresent()){

            StatusDiaResponseDTO statusDTO = new StatusDiaResponseDTO(statusBloqueado.get().getId(), statusBloqueado.get().getNome());
            
            status.add(statusDTO);
        }

        return new ResponseEntity<>(status, HttpStatus.OK);

    }

    //Cadastra Status Dia
    @PostMapping("/agenda/dia/status")
    public ResponseEntity<Object> cadastraStatusDia(@RequestBody StatusDiaCadastroDTO dto, HttpServletRequest request){
        Usuario usuarioLogado = usuarioService.findByLogin(tokenService.validateToken(tokenService.recoverToken(request))).get();

        if (statusDiaService.existsByNome(dto.nome()))
            return new ResponseEntity<>("Status já existe", HttpStatus.CONFLICT);

        StatusDia status = new StatusDia();
        status.setNome(dto.nome());
        status.setEmpresa(usuarioLogado.getEmpresa());

        statusDiaService.save(status);

        return new ResponseEntity<>("Status Cadastrado com sucesso", HttpStatus.CREATED);
    }

    //Edita Status Dia
    @PutMapping("/agenda/dia/status")
    public ResponseEntity<Object> editaStatusDia(@RequestBody StatusDiaEditDTO dto, HttpServletRequest request){
        //Usuario usuarioLogado = usuarioService.findByLogin(tokenService.validateToken(tokenService.recoverToken(request))).get();
        
        //Não permite alterar o status Ativo nem Bloqueado
        if (dto.id() == 1 || dto.id() == 2)
            return new ResponseEntity<>("Não é possível editar este status.", HttpStatus.FORBIDDEN);

        Optional<StatusDia> status = statusDiaService.findById(dto.id());

        if (!status.isPresent())
            return new ResponseEntity<>("Status não encontrado", HttpStatus.NOT_FOUND);

        status.get().setNome(dto.nome());

        statusDiaService.save(status.get());

        return new ResponseEntity<>("Status editado com sucesso", HttpStatus.OK);
    }


    //Deleta Status Dia
    @DeleteMapping("/agenda/dia/status/{id}")
    public ResponseEntity<Object> deletaStatusDia(@PathVariable long id, HttpServletRequest request){
        Usuario  usuarioLogado = usuarioService.findByLogin(tokenService.validateToken(tokenService.recoverToken(request))).get();

        Optional<StatusDia> status = statusDiaService.findById(id);

        //Se status exite
        if (status.isPresent()){

            //Não deleta se status for padrão do sistema Ativo ou Bloqueado.
            if (status.get().getId() == 1 || status.get().getId() == 2)
                return new ResponseEntity<>("Status não pode ser deletado.", HttpStatus.FORBIDDEN);

            //Se empresa do usuario e empresa do status são a mesma
            if (status.get().getEmpresa() == usuarioLogado.getEmpresa()){
                statusDiaService.delete(status.get());
                return new ResponseEntity<>("Status deletado com sucesso", HttpStatus.OK);
            }else {
                return new ResponseEntity<>("Status não encontrado", HttpStatus.NOT_FOUND);
            }
        }else {
            return new ResponseEntity<>("Status não encontrado", HttpStatus.NOT_FOUND);
        }
    }

    //Replica dia para outro.
    //É recebido um Id do dia origem e uma lista de ids do dia alvo.
    @PostMapping("/agenda/dia/replica")
    public ResponseEntity<Object> replicaDia(@RequestBody AgendaReplicaDiaDTO dto, HttpServletRequest request){
        //Usuario usuarioLogado = usuarioService.findByLogin(tokenService.validateToken(tokenService.recoverToken(request))).get();

        Optional<Dias> diaOrigem = diasService.findById(dto.idOrigemDia());

        // Se dia não encontrado retorna not_found
        if (!diaOrigem.isPresent())
            return new ResponseEntity<>("Dia Origem não encontrado.", HttpStatus.NOT_FOUND);

        //Busca a agenda do diaOrigem para setar nos alvos
        Long idAgenda = diasService.getAgenda(diaOrigem.get().getId()).get();

        Optional<Agenda> agenda = agendaService.findById(idAgenda);

        if (!agenda.isPresent()){
            return new ResponseEntity<>("Erro ao buscar agenda do Dia Origem", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        // Map percore o DTO e busca os dias alvo e preeche o array diasAlvo
        List<Dias> diasAlvo = dto.alvoDias().stream().map(nome -> {

            Optional<Dias> dia = diasService.findByNome(nome);

            if (dia.isPresent()){
                return dia.get();
            }

            System.out.print("-------------------");

            Dias newDia  = new Dias();

            newDia.setNome(nome);
            
            return newDia;

        }).collect(Collectors.toList()); // converte Stream para List

        // Para cada dia da lista de alvos
        diasAlvo.forEach(dia -> {

            if (dia.getHoras() != null){ //Evita NullPointerException se getHoras for null em caso de dia não configurado.
                //Remove horas atuais do dia
                for (Horas horas : dia.getHoras()) {
                    horasService.delete(horas);
                }
            }

            //Define os mesmo parametros do dia origem
            dia.setDuracaoSessaoInMinutes(diaOrigem.get().getDuracaoSessaoInMinutes());
            dia.setIntervaloSessaoInMinutes(diaOrigem.get().getIntervaloSessaoInMinutes());
            dia.setInicio(diaOrigem.get().getInicio());
            dia.setFim(diaOrigem.get().getFim());
            dia.setStatusDia(diaOrigem.get().getStatusDia());
            agenda.get().getDias().add(dia);

            List<Horas> horasNovas = new ArrayList<>();

            //Percore o dia Origem e para cada hora, cria uma nova hora para o alvo com o mesmo inicio e fim;
            diaOrigem.get().getHoras().forEach(hora -> {
                Horas horasAlvo = new Horas();
                horasAlvo.setInicio(hora.getInicio());
                horasAlvo.setFim(hora.getFim());
                horasAlvo.setStatusHora(hora.getStatusHora());

                horasNovas.add(hora);
                
            }); // Fim Foreach hora do diaOrigem

            dia.setHoras(horasNovas);
            diasService.save(dia);


        }); //Fim Foreach dias alvos

        return new ResponseEntity<>("Replicação do dia com sucesso.", HttpStatus.OK);
    }
    //=============================================== PROFISSIONAL =======================================================

    //Cadastro de Profissional
    @PostMapping("/profissional/cadastro")
    public ResponseEntity<Object> profissionalCadastro(@RequestBody @Valid ProfissionalCadastroDTO profissionalDTO, HttpServletRequest request){
        Usuario usuarioLogado = usuarioService.findByLogin(tokenService.validateToken(tokenService.recoverToken(request))).get();

        Optional<Usuario> usuario = usuarioService.findByLogin(profissionalDTO.login());

        //Se usuario não existe a empresa do usuario do profissional for diferente da empresa do usuario logado retorn 404
        if (!usuario.isPresent() || usuario.get().getEmpresa().getId() != usuarioLogado.getEmpresa().getId()) 
            return new ResponseEntity<>("Usuário não encontrado!", HttpStatus.NOT_FOUND);


        Profissional profissional = new Profissional();
        BeanUtils.copyProperties(profissionalDTO, profissional);
        profissional.setUsuario(usuario.get());

        profissionalService.save(profissional);

        return new ResponseEntity<>("Profissional Cadastrado com sucesso", HttpStatus.CREATED);
    }

    //Editar Profissional
    @PutMapping("/profissional/edit")
    public ResponseEntity<Object> profissionalEditar(@RequestBody @Valid ProfissionalEditDTO profissionalDTO, HttpServletRequest request) throws Exception{
        Optional<Profissional> profissional = profissionalService.findById(profissionalDTO.id());

        Usuario usuarioLogado = usuarioService.findByLogin(tokenService.validateToken(tokenService.recoverToken(request))).get();

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
    public ResponseEntity<Page<ProfissionalResponseDTO>> profissionalListByEmpresa(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable, HttpServletRequest request){
        Usuario usuario = usuarioService.findByLogin(tokenService.validateToken(tokenService.recoverToken(request))).get();

        Page<Profissional> page = profissionalService.findByEmpresa(usuario.getEmpresa().getId(), pageable);

        Page<ProfissionalResponseDTO> profissionais = page.map(profissional -> {
            ProfissionalResponseDTO dto = new ProfissionalResponseDTO(profissional.getId(),profissional.getTitulo(),profissional.getRegistro(),
            profissional.getUsuario().getNome(), profissional.getUsuario().getSobrenome(), profissional.getUsuario().getLogin(), profissional.getUsuario().getEmpresa().getNome(), null);

            return dto;
        });

        return new ResponseEntity<>(profissionais, HttpStatus.OK);
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


    @GetMapping("/profissional/findbyid")
    public ResponseEntity<Object> profissionalFindById(@RequestParam long id){

        Optional<Profissional> profissional = profissionalService.findById(id);

        if (!profissional.isPresent()) 
            return new ResponseEntity<>("Prosfissional não encontrado!", HttpStatus.NOT_FOUND);

        ProfissionalResponseDTO dto = new ProfissionalResponseDTO(
            profissional.get().getId(), profissional.get().getTitulo(), profissional.get().getRegistro(), profissional.get().getUsuario().getNome(),
            profissional.get().getUsuario().getSobrenome(), profissional.get().getUsuario().getLogin(), profissional.get().getUsuario().getEmpresa().getNome(),
            profissional.get().getAgenda());

        
        return new ResponseEntity<>(dto, HttpStatus.OK);
        
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

 
