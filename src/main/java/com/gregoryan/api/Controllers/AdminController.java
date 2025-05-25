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
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
import com.gregoryan.api.DTO.DiaEditDTO;
import com.gregoryan.api.DTO.FeriadoCadastroDTO;
import com.gregoryan.api.DTO.FeriadoEditDTO;
import com.gregoryan.api.DTO.FeriadoResponseDTO;
import com.gregoryan.api.DTO.HorasEditDTO;
import com.gregoryan.api.DTO.ProfissionalCadastroDTO;
import com.gregoryan.api.DTO.ProfissionalEditDTO;
import com.gregoryan.api.DTO.ProfissionalResponseDTO;
import com.gregoryan.api.DTO.StatusAgendaCadastroDTO;
import com.gregoryan.api.DTO.StatusAgendaResponseDTO;
import com.gregoryan.api.DTO.StatusDiaCadastroDTO;
import com.gregoryan.api.DTO.StatusDiaEditDTO;
import com.gregoryan.api.DTO.StatusDiaResponseDTO;
import com.gregoryan.api.DTO.StatusHoraCadastroDTO;
import com.gregoryan.api.DTO.StatusHoraResponseDTO;
import com.gregoryan.api.DTO.UsuarioCadastroDTO;
import com.gregoryan.api.DTO.UsuarioEditDTO;
import com.gregoryan.api.DTO.planoPacienteCadastroDTO;
import com.gregoryan.api.DTO.UsuarioResetSenhaDTO;
import com.gregoryan.api.DTO.UsuarioResponseDTO;
import com.gregoryan.api.Exception.AcessoNegadoException;
import com.gregoryan.api.Exception.EntityDontExistException;
import com.gregoryan.api.Exception.ConflictException;
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
import com.gregoryan.api.Services.AgendaCreateService;
import com.gregoryan.api.Services.AgendaDeletingService;
import com.gregoryan.api.Services.FeriadoCreateService;
import com.gregoryan.api.Services.FeriadoDeletingService;
import com.gregoryan.api.Services.FeriadoEditingService;
import com.gregoryan.api.Services.ProfissionalCreateService;
import com.gregoryan.api.Services.ProfissionalDeletingService;
import com.gregoryan.api.Services.ProfissionalEditingService;
import com.gregoryan.api.Services.StatusAgendaCreateService;
import com.gregoryan.api.Services.StatusAgendaDeletingService;
import com.gregoryan.api.Services.StatusDiaCreateService;
import com.gregoryan.api.Services.StatusDiaDeletingService;
import com.gregoryan.api.Services.StatusDiaEditingService;
import com.gregoryan.api.Services.StatusHoraCreateService;
import com.gregoryan.api.Services.StatusHoraDeleteService;
import com.gregoryan.api.Services.UsuarioCreateService;
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
import com.gregoryan.api.Services.Interfaces.AgendaConverterInterface;
import com.gregoryan.api.Services.Interfaces.AgendaListInterface;
import com.gregoryan.api.Services.Interfaces.FeriadoConverterInterface;
import com.gregoryan.api.Services.Interfaces.FeriadoListInterface;
import com.gregoryan.api.Services.Interfaces.ProfissionalConverterInterface;
import com.gregoryan.api.Services.Interfaces.ProfissionalListInterface;
import com.gregoryan.api.Services.Interfaces.StatusAgendaConverterInterface;
import com.gregoryan.api.Services.Interfaces.StatusAgendaListInterface;
import com.gregoryan.api.Services.Interfaces.StatusDiaConverterInterface;
import com.gregoryan.api.Services.Interfaces.StatusDiaListInterface;
import com.gregoryan.api.Services.Interfaces.StatusHoraConverterInterface;
import com.gregoryan.api.Services.Interfaces.StatusHoraListInterface;
import com.gregoryan.api.Services.Interfaces.UsuarioConverterInterface;
import com.gregoryan.api.Services.Interfaces.UsuarioListInterface;
import com.gregoryan.api.Services.Interfaces.UsuarioValidateInterface;
import com.gregoryan.api.Services.Security.TokenService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/api/admin")
@Tag(name = "Administradores", description = "Operações relacionadas a Administradores do sistema")
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
    private UsuarioCreateService usuarioCreate;
    @Autowired
    private UsuarioDeletingService usuarioDeleting;
    @Autowired
    private UsuarioListInterface usuarioListing;
    @Autowired
    private UsuarioEditingService usuarioEditing;
    @Autowired
    private UsuarioResetSenha resetSenha;
    @Autowired
    private UsuarioConverterInterface usuarioConverter;
    @Autowired
    private UsuarioValidateInterface usuarioValidate;

    //Injetores relacionados ao Profissional
    @Autowired
    private ProfissionalCreateService profissionalCreate;
    @Autowired
    private ProfissionalEditingService profissionalEditing;
    @Autowired
    private ProfissionalDeletingService profissionalDeleting;
    @Autowired
    private ProfissionalListInterface profissionalList;
    @Autowired
    private ProfissionalConverterInterface profissionalConverter;

    //Injetor relacionado a Agenda
    @Autowired
    private AgendaCreateService agendaCreate;
    @Autowired
    private AgendaListInterface agendaList;
    @Autowired
    private AgendaDeletingService agendaDeleting;
    @Autowired
    private AgendaConverterInterface agendaConverter;
    @Autowired
    private StatusAgendaCreateService statusAgendaCreate;
    @Autowired
    private StatusAgendaListInterface statusAgendaList;
    @Autowired
    private StatusAgendaDeletingService statusAgendaDeleting;
    @Autowired
    private StatusAgendaConverterInterface statusAgendaConverter;
    @Autowired
    private StatusHoraCreateService statusHoraCreate;
    @Autowired
    private StatusHoraListInterface statusHoraList;
    @Autowired
    private StatusHoraConverterInterface statusHoraConverter;
    @Autowired
    private StatusHoraDeleteService statusHoraDelete;
    @Autowired
    private StatusDiaListInterface statusDiaList;
    @Autowired
    private StatusDiaConverterInterface statusDiaConverter;
    @Autowired
    private StatusDiaCreateService statusDiaCreate;
    @Autowired
    private StatusDiaEditingService statusDiaEditing;
    @Autowired
    private StatusDiaDeletingService statusDiaDeleting;

    //Injetores realacionados a feriado
    @Autowired
    private FeriadoCreateService feriadoCreate;
    @Autowired
    FeriadoEditingService feriadoEditing;
    @Autowired
    private FeriadoDeletingService feriadoDeleting;
    @Autowired
    private FeriadoListInterface feriadoList;
    @Autowired
    private FeriadoConverterInterface feriadoConverter;

    // ================================= PLANO PACIENTE ==================================

    @PostMapping("/planopaciente/cadastro")
    @Operation(summary = "Cadastro de plano paciente", description = "Salvar um plano para paciente no Banco")
    @ApiResponse(responseCode = "200", description = "Cadastro realizado com sucesso.")
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
    @Operation(summary = "Cadastro de usuários", description = "Salva um usuário no Banco de Dados")
    @ApiResponse(responseCode = "201", description = "Usuário salvo com sucesso")
    @ApiResponse(responseCode = "409", description = "Conflito de login")
    @ApiResponse(responseCode = "403", description = "Usuário sem permissão para esta operação")
    public ResponseEntity<Object> saveUsuario(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Dados do usuário a ser cadastrado",
            required = true,
            content = @Content(schema = @Schema(implementation = UsuarioCadastroDTO.class))
        )
        @RequestBody @Valid UsuarioCadastroDTO usuarioDTO, HttpServletRequest request) throws ParseException {
        
        try {
            Empresa empresa = tokenService.getEmpresaFromToken(request, usuarioService);
            usuarioCreate.cadastrar(usuarioDTO, empresa);
            
            return new ResponseEntity<>("Usuário cadastrado com sucesso", HttpStatus.CREATED);

        }catch (ConflictException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);

        }
        
    }

    @DeleteMapping("/usuario/exclui/{id}")
    @Operation(summary = "Deleta Usuário", description = "Deleta um usuário do Banco de Dados")
    @ApiResponse(responseCode = "200", description = "Usuário deletado com sucesso")
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado para deletar")
    @ApiResponse(responseCode = "403", description = "Usuário sem permissão para esta operação")
    public ResponseEntity<Object> deleteUsuario(
        @Parameter(
            description = "ID do usuário a ser excluído",
            required = true,
            example = "123"
        )
        @PathVariable long id, HttpServletRequest request){

        try{

            Empresa empresa = tokenService.getEmpresaFromToken(request, usuarioService);
            usuarioDeleting.delete(id, empresa);
            return new ResponseEntity<>("Usuário excluído do sistema", HttpStatus.OK);

        } catch(EntityDontExistException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);

        } catch (AcessoNegadoException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/usuario/list")
    @Operation(summary = "Lista usuário da empresa", description = "Lista usuários da empresa da qual o usuário faz parte.")
    @ApiResponse(responseCode = "200", description = "Usuário listado com sucesso")
    @ApiResponse(responseCode = "403", description = "Usuário sem permissão para esta operação")
    public ResponseEntity<Object> usuarioListByEmpresa(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable, HttpServletRequest request){
        
        try {

            Empresa empresa = tokenService.getEmpresaFromToken(request, usuarioService);
            
            List<UsuarioResponseDTO> listDTO = usuarioListing.list(empresa, pageable).getContent().stream().map(usuario -> {
                UsuarioResponseDTO dto = usuarioConverter.toUsuarioResponseDTO(usuario);
                return dto;
            }).collect(Collectors.toList());
            
            return new ResponseEntity<>(new PageImpl<UsuarioResponseDTO>(listDTO), HttpStatus.OK);

        }catch(BeansException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/usuario")
    @Operation(summary = "Busca Usuário por ID", description = "Busca usuário no Banco pelo ID")
    @ApiResponse(responseCode = "200", description = "Usuário localizado é retornado")
    @ApiResponse(responseCode = "404", description = "Usuário não localizado")
    @ApiResponse(responseCode = "403", description = "Usuário sem permissão para esta operação")
    public ResponseEntity<Object> usuarioListById(
        @Parameter(
            description = "ID do uusário",
            required = true,
            example = "123"
        )
        @RequestParam long id, HttpServletRequest request){

        try {
            Empresa empresa = tokenService.getEmpresaFromToken(request, usuarioService);
            Usuario usuario = usuarioListing.list(id, empresa);
            UsuarioResponseDTO dto = usuarioConverter.toUsuarioResponseDTO(usuario);
            return new ResponseEntity<>(dto, HttpStatus.OK);

        }catch(EntityDontExistException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);

        }catch(AcessoNegadoException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
            
    }

    @GetMapping("/usuario/findlogin")
    @Operation(summary = "Busca usuário por Login", description = "Busca usuário no banco pelo login")
    @ApiResponse(responseCode = "200", description = "Usuário localizado é retornado")
    @ApiResponse(responseCode = "404", description = "Usuário não localizado")
    @ApiResponse(responseCode = "403", description = "Usuário sem permissão para esta operação")
    public ResponseEntity<Object> usuarioListByLogin(
        @Parameter(
            description = "Login do usuário",
            required = true,
            example = "maria.jose"
        )
        @RequestParam String login, HttpServletRequest request){

        try {
            Empresa empresa = tokenService.getEmpresaFromToken(request, usuarioService);
            Usuario usuario = usuarioListing.list(login, empresa);

            UsuarioResponseDTO dto = usuarioConverter.toUsuarioResponseDTO(usuario);
            return new ResponseEntity<>(dto, HttpStatus.OK);
            //return new ResponseEntity<>(usuarioListing.list(login), HttpStatus.OK);

        }catch(EntityDontExistException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);

        }catch(AcessoNegadoException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
            
    }

    @PutMapping("/usuario/edit")
    @Operation(summary = "Edita dados de usuário", description = "Salva dados editados do usuário no Banco de Dados")
    @ApiResponse(responseCode = "200", description = "Usuário salvo com sucesso no Banco de Dados")
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    @ApiResponse(responseCode = "403", description = "Usuário sem permissão para esta operação")
    public ResponseEntity<Object> editUsuario(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Dados do usuário editado a ser salvo",
            required = true,
            content = @Content(schema = @Schema(implementation = UsuarioEditDTO.class))
        )
        @RequestBody @Valid UsuarioEditDTO usuarioDTO, HttpServletRequest request) throws ParseException{

        try {
            Empresa empresa = tokenService.getEmpresaFromToken(request, usuarioService);
            usuarioEditing.edit(usuarioDTO, empresa);
            return new ResponseEntity<>("Usuario Editado", HttpStatus.OK);

        }catch (EntityDontExistException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);

        }catch (AcessoNegadoException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
        
    }

    @PutMapping("/usuario/resetsenha")
    @Operation(summary = "Reseta senha de usuário", description = "Define uma nova senha para o usuário passado como parâmetro")
    @ApiResponse(responseCode = "200", description = "Usuário salvo com sucesso no Banco de Dados")
    @ApiResponse(responseCode = "404", description = "Usuário não localizado")
    @ApiResponse(responseCode = "403", description = "Usuário sem premissão para esta operação")
    public ResponseEntity<Object> usuarioResetPass(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Dados do usuário como ID e nova senha",
            required = true,
            content = @Content(schema =  @Schema(implementation = UsuarioResetSenhaDTO.class))
        )
        @RequestBody @Valid UsuarioResetSenhaDTO usuarioDTO, HttpServletRequest request){

        
        try {

            Empresa empresa = tokenService.getEmpresaFromToken(request, usuarioService);
            resetSenha.reset(usuarioDTO, empresa);
            return new ResponseEntity<>("Senha Resetada", HttpStatus.OK);

        }catch(EntityDontExistException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);

        }catch(AcessoNegadoException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }


    // =============================================== AGENDA ======================================

    @PostMapping("/agenda/cadastro")
    @Operation(summary = "Cadastrado de Agenda", description = "Salva uma nova agenda no Banco de Dados")
    @ApiResponse(responseCode = "201", description = "Agenda cadastrada com sucesso")
    @ApiResponse(responseCode = "404", description = "Profissional ou Status para a agenda não foram encontrados")
    @ApiResponse(responseCode = "403", description = "Usuário sem permissão para esta operação")
    @ApiResponse(responseCode = "400", description = "Profissional informado já possui agenda")
    public ResponseEntity<Object> agendaCadastro(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Dados da agenda a ser cadastrada",
            required = true,
            content = @Content(schema = @Schema(implementation = AgendaCadastroDTO.class))
        )
        @RequestBody @Valid AgendaCadastroDTO agendaDTO, HttpServletRequest request){

        try {
            Empresa empresa = tokenService.getEmpresaFromToken(request, usuarioService);

            agendaCreate.cadastrar(agendaDTO, empresa);

            return new ResponseEntity<>("Agenda cadastrada", HttpStatus.CREATED);

        }catch (EntityDontExistException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);

        }catch(DataIntegrityViolationException e){
            return new ResponseEntity<>("Este profissional já possui agenda", HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/agenda/list")
    @Operation(summary = "Lista agendas da Empresa", description = "Lista agendas da empresa pegando a empresa do usuário logado")
    @ApiResponse(responseCode = "200", description = "Retorna agendas da empresa com sucesso")
    @ApiResponse(responseCode = "403", description = "Usuário sem permissão para esta operação")
    @ApiResponse(responseCode = "500", description = "Se houver algum erro na operação")
    public ResponseEntity<Page<AgendaResponseDTO>> agendaList(
        @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
        HttpServletRequest request){

        try{
            Empresa empresa = tokenService.getEmpresaFromToken(request, usuarioService);
            List<Agenda> agendas = agendaList.list(empresa, pageable).getContent();

            List<AgendaResponseDTO> listDTO = agendas.stream().map(agenda -> {
                AgendaResponseDTO dto = agendaConverter.toResponseDTO(agenda);
                return dto;
            }).collect(Collectors.toList());

            return new ResponseEntity<>(new PageImpl<AgendaResponseDTO>(listDTO), HttpStatus.OK);
            

        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    //Busca Agenda por ID
    @GetMapping("/agenda/{id}")
    @Operation(summary = "Lista agenda pelo ID", description = "Busca agenda pelo ID no Banco de Dados")
    @ApiResponse(responseCode = "200", description = "Retorna agenda encontrada com sucesso")
    @ApiResponse(responseCode = "404", description = "Agenda não encontrada")
    @ApiResponse(responseCode = "403", description = "Usuário sem permissão para esta operação")
    public ResponseEntity<Object> agendaById(
        @Parameter(description = "ID da Agenda a ser buscada",required = true,example = "123")
        @PathVariable long id , HttpServletRequest request){
 
        try{
            Empresa empresa = tokenService.getEmpresaFromToken(request, usuarioService);
            Agenda agenda = agendaList.list(id, empresa);
            AgendaResponseDTO dto = agendaConverter.toResponseDTO(agenda);
            return new ResponseEntity<>(dto, HttpStatus.OK);

        }catch(EntityDontExistException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);

        }catch(AcessoNegadoException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    //Exclui Agenda
    @DeleteMapping("/agenda/delete/{id}")
    @Operation(summary = "Deleta Agenda", description = "Deleta uma agenda do Banco de Dados")
    @ApiResponse(responseCode = "200", description = "Agenda deletada com sucesso")
    @ApiResponse(responseCode = "404", description = "Agenda não encontrada")
    @ApiResponse(responseCode = "403", description = "Usuário sem permissão para esta operação")
    @ApiResponse(responseCode = "500", description = "Erro ao excluir agenda. Agenda possivelmente vinculada a um profissional ou agendamento. ")
    public ResponseEntity<String> agendaDelete(@PathVariable long id, HttpServletRequest request){

        try{
            Empresa empresa = tokenService.getEmpresaFromToken(request, usuarioService);
            agendaDeleting.deletar(empresa, id);
            return new ResponseEntity<>("Agenda deletada", HttpStatus.OK);

        }catch(EntityDontExistException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);

        }catch(AcessoNegadoException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);

        }catch(DataIntegrityViolationException e){
            return new ResponseEntity<>("Erro ao excluir agenda: "+e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    //Configura Agenda
    @PutMapping("/agenda/config")
    public ResponseEntity<Object> agendaConfig(@RequestBody @Valid AgendaConfigDTO agendaDTO, HttpServletRequest request){

        Optional<StatusHora> statusHora = statusHoraService.findByNome("Ativo");
        Optional<StatusDia> statusDia = statusDiaService.findByNome("Ativo");
        Optional<Agenda> agenda = agendaService.findById(agendaDTO.idAgenda());

        // Usuario usuarioLogado = usuarioService.findByLogin(tokenService.validateToken(tokenService.recoverToken(request))).get();

        // //Se agenda não existe ou empresa da agenda é diferente da empresa do usuário logado retorno 404
        // if (!agenda.isPresent() || agenda.get().getEmpresa().getId() != usuarioLogado.getEmpresa().getId()) 
        //     return new ResponseEntity<>("Agenda não encontrada!", HttpStatus.NOT_FOUND);

        // for (DiaCadastroDTO diaDTO : agendaDTO.dias()) {

        //     Dias dia;

        //     if (diaDTO.id() != 0){
        //         dia = diasService.findById(diaDTO.id()).get();
        //     }else {
        //         dia = new Dias();
        //     }

        //     dia.setNome(diaDTO.nome());
            
        //     dia.setDuracaoSessaoInMinutes(diaDTO.duracaoSessaoInMinutes());
        //     dia.setIntervaloSessaoInMinutes(diaDTO.intervaloSessaoInMinutes());

        //     LocalTime inicio = LocalTime.of(Integer.parseInt(diaDTO.inicio().split(":")[0]), Integer.parseInt(diaDTO.inicio().split(":")[1]));
        //     LocalTime fim = LocalTime.of(Integer.parseInt(diaDTO.fim().split(":")[0]), Integer.parseInt(diaDTO.fim().split(":")[1]));
        //     dia.setInicio(inicio);
        //     dia.setFim(fim);

        //     if (statusDia.isPresent()) dia.setStatusDia(statusDia.get());     
            
        //     if (statusHora.isPresent()) dia.createHoras(statusHora.get(), horasService);
            
        //     agenda.get().getDias().add(dia);
        // }

        //agendaService.save(agenda.get());

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
    @Operation(summary = "Cadastra um StatusAgenda", description = "Cadastra um novo status para a agenda")
    @ApiResponse(responseCode = "201", description = "Status cadastrado com sucesso")
    @ApiResponse(responseCode = "403", description = "Usuário sem permissão para esta operação")
    public ResponseEntity<Object> statusAgendaCadstro(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Dados do status a ser cadastrado",
            required = true,
            content = @Content(schema = @Schema(implementation = StatusAgendaCadastroDTO.class))
        )
        @RequestBody @Valid StatusAgendaCadastroDTO statusAgendaDTO, HttpServletRequest request){
        
        try{
            
            Empresa empresa = tokenService.getEmpresaFromToken(request, usuarioService);
            statusAgendaCreate.create(statusAgendaDTO, empresa);
            return new ResponseEntity<>("Status de Agenda cadastrado com sucesso", HttpStatus.CREATED);
        }catch(EntityDontExistException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
        
    }
    
    //Deleta um status da Agenda
    @DeleteMapping("agenda/status/delete/{id}")
    @Operation(summary = "Exclui um StatusAgenda", description = "Exclui um status da agenda")
    @ApiResponse(responseCode = "200", description = "Status excluído com sucesso")
    @ApiResponse(responseCode = "403", description = "Usuário sem permissão para esta operação")
    @ApiResponse(responseCode = "404", description = "Status não encontrado para exclus]ao")
    public ResponseEntity<Object> statusAgendaDelete(
        @Parameter(description = "ID do status a excluir", required = true, example = "123")
        @PathVariable long id , HttpServletRequest request){

        try{
            Empresa empresa = tokenService.getEmpresaFromToken(request, usuarioService);
            statusAgendaDeleting.delete(id, empresa);
            return new ResponseEntity<>("Status Excluída", HttpStatus.OK);

        }catch(EntityDontExistException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);

        }catch(AcessoNegadoException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    //Lista Status da Agenda por Empresa;
    @GetMapping("/agenda/status/empresa")
    @Operation(summary = "Lista Status da Agenda", description = "Lista Status das Agendas por Empresa")
    @ApiResponse(responseCode = "200", description = "Lista status com sucesso")
    @ApiResponse(responseCode = "403", description = "Usuário sem permissão para esta operação")
    public ResponseEntity<Page<StatusAgendaResponseDTO>> statusAgendaByEmpresa(HttpServletRequest request, @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable){
        
        Empresa empresa = tokenService.getEmpresaFromToken(request, usuarioService);
        List<StatusAgendaResponseDTO> listDTO = statusAgendaList.list(empresa, pageable).getContent().stream().map(status -> {
            StatusAgendaResponseDTO dto = statusAgendaConverter.toResponseDTO(status);
            return dto;
        }).collect(Collectors.toList());

        return new ResponseEntity<>(new PageImpl<StatusAgendaResponseDTO>(listDTO), HttpStatus.OK);
    }


    //Lista Status da Agenda por ID
    @GetMapping("/agenda/status")
        @Operation(summary = "Lista Status da Agenda", description = "Lista Status das Agendas por ID")
    @ApiResponse(responseCode = "200", description = "Lista status com sucesso")
    @ApiResponse(responseCode = "403", description = "Usuário sem permissão para esta operação")
    @ApiResponse(responseCode = "404", description = "Status não encontrado")
    public ResponseEntity<Object> statusAgendaById(@RequestParam long id, HttpServletRequest request){
        try{
            Empresa empresa = tokenService.getEmpresaFromToken(request, usuarioService);
            StatusAgenda statusAgenda = statusAgendaList.list(id, empresa);
            StatusAgendaResponseDTO dto = statusAgendaConverter.toResponseDTO(statusAgenda);
            return new ResponseEntity<>(dto,HttpStatus.OK);

        }catch( EntityDontExistException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);

        }catch(AcessoNegadoException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
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


    //Lista Status da Hora por empresa
    @GetMapping("/agenda/horas/status")
    @Operation(summary = "Lista Status da Hora", description = "Lista status por empresa")
    @ApiResponse(responseCode = "200", description = "Lista status encontrados ou lista vazia;")
    public ResponseEntity<Object> listaStatusHora(
        HttpServletRequest request,
        @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable){
        
        Empresa empresa = tokenService.getEmpresaFromToken(request, usuarioService);
        List<StatusHora> statusHoras = statusHoraList.list(empresa, pageable).getContent();

        List<StatusHoraResponseDTO> listDTO = statusHoras.stream().map(status -> {
            StatusHoraResponseDTO dto = statusHoraConverter.toResponseDTO(status);
            return dto;
        }).collect(Collectors.toList());

        return new ResponseEntity<>(listDTO, HttpStatus.OK);

    }

    //Cadastra Status Hora
    @PostMapping("/agenda/horas/status")
    @Operation(summary = "Deleta StatusHora", description = "Deleta status para as horas")
    @ApiResponse(responseCode = "201", description = "Status cadastrado com sucesso")
    @ApiResponse(responseCode = "409", description = "Já existe status com esse nome")
    public ResponseEntity<Object> cadastraStatusHora(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dados a serem cadastrados", required = true,
        content = @Content(schema = @Schema(implementation = StatusHoraCadastroDTO.class)))
        @RequestBody @Valid StatusHoraCadastroDTO statusHoraDTO, HttpServletRequest request){

        try{

            Empresa empresa = tokenService.getEmpresaFromToken(request, usuarioService);
            statusHoraCreate.create(statusHoraDTO, empresa);
            return new ResponseEntity<>("Status cadastrado com sucesso", HttpStatus.CREATED);
            
        }catch(ConflictException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);

        }

    }
   
   
    //Deleta status da hora
    @DeleteMapping("/agenda/horas/status/{id}")
    @Operation(summary = "Exclui Status Hora", description = "Exclui um status hora do Banco de Dados")
    @ApiResponse(responseCode = "200", description = "Status excluído com sucesso")
    @ApiResponse(responseCode = "404", description = "Status não encontrado")
    @ApiResponse(responseCode = "403", description = "Usuário sem permissão para esta operação;")
    public ResponseEntity<String> deletaStatusHora(@PathVariable long id, HttpServletRequest request){

        try{
            Empresa empresa = tokenService.getEmpresaFromToken(request, usuarioService);
            statusHoraDelete.delete(id, empresa);
            return new ResponseEntity<>("Status excluído", HttpStatus.OK);

        }catch(EntityDontExistException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);

        }catch(AcessoNegadoException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }
   
    //Lista Status do dia
    @GetMapping("/agenda/dia/status")
    @Operation(summary = "Lista Status Dia", description = "Lista Status Dia por empresa")
    @ApiResponse(responseCode = "200", description = "Retorna lista com status dia por empresa com sucesso")
    public ResponseEntity<Page<StatusDiaResponseDTO>> listaStatusDiaByEmpresa(
        HttpServletRequest request,
        @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable
    ){
        
        Empresa empresa = tokenService.getEmpresaFromToken(request, usuarioService);
        List<StatusDia> statusDias = statusDiaList.list(empresa, pageable).getContent();

        List<StatusDiaResponseDTO> listDTO = statusDias.stream().map(item -> {
            StatusDiaResponseDTO dto = statusDiaConverter.toResponseDTO(item);
            return dto;
        }).collect(Collectors.toList());

        return new ResponseEntity<>(new PageImpl<StatusDiaResponseDTO>(listDTO), HttpStatus.OK);

    }

    //Cadastra Status Dia
    @PostMapping("/agenda/dia/status")
    @Operation(summary = "Cadastra um Status Dia", description = "Insere um novo status dia no Banco de Dados")
    @ApiResponse(responseCode = "201", description = "Status cadastrado com sucesso")
    @ApiResponse(responseCode = "403", description = "Já existe um status com este nome")
    public ResponseEntity<Object> cadastraStatusDia(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Dados a serem cadastrados",
            required = true,
            content = @Content(schema = @Schema(implementation = StatusDiaCadastroDTO.class))
        )
        @RequestBody StatusDiaCadastroDTO dto, HttpServletRequest request){
        
        try{
            Empresa empresa = tokenService.getEmpresaFromToken(request, usuarioService);
            statusDiaCreate.create(dto, empresa);
            return new ResponseEntity<>("Status cadastrado com sucesso", HttpStatus.CREATED);
        
        }catch(ConflictException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    //Edita Status Dia
    @PutMapping("/agenda/dia/status")
    @Operation(summary = "Edita Status Dia", description = "Edita nome de um status dia")
    @ApiResponse(responseCode = "200", description = "Edição do status realizado com sucesso")
    @ApiResponse(responseCode = "409", description = "Já esite um status com esse nome")
    @ApiResponse(responseCode = "404", description = "Status não encontrado para edição")
    public ResponseEntity<Object> editaStatusDia(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Dados a serem editados",
            required = true,
            content = @Content(schema = @Schema(implementation = StatusDiaEditDTO.class))
        )
        @RequestBody StatusDiaEditDTO dto, HttpServletRequest request){
        
        try{
            Empresa empresa = tokenService.getEmpresaFromToken(request, usuarioService);
            statusDiaEditing.edit(dto, empresa);
            return new ResponseEntity<>("Status editado com sucesso", HttpStatus.OK);

        }catch(ConflictException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);

        }catch(EntityDontExistException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);

        }catch(AcessoNegadoException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }


    //Deleta Status Dia
    @DeleteMapping("/agenda/dia/status/{id}")
    @Operation(summary = "Deleta Status Dia", description = "Deleta um status dia do Banco de Dados")
    @ApiResponse(responseCode = "200", description = "Exclusão realizada com sucesso")
    @ApiResponse(responseCode = "404", description = "Status não encontrado para exclusão")
    @ApiResponse(responseCode = "403", description = "Usuário sem permissão para esta operação")
    public ResponseEntity<Object> deletaStatusDia(@PathVariable long id, HttpServletRequest request){
        
        try{
            Empresa empresa = tokenService.getEmpresaFromToken(request, usuarioService);
            statusDiaDeleting.delete(id, empresa);
            return new ResponseEntity<>("Status excluído com sucesso", HttpStatus.OK);

        }catch(EntityDontExistException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);

        }catch(AcessoNegadoException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
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
    @Operation(summary = "Cadastro de Profissional", description = "Insere um novo Profissional no Banco de Dados")
    @ApiResponse(responseCode = "201", description = "Cadastro do usuário realizado com sucesso")
    @ApiResponse(responseCode = "404", description = "Usuário do profissional não encontrado")
    @ApiResponse(responseCode = "403", description = "Usuário sem permissão para esta operação")
    @ApiResponse(responseCode = "409", description = "Usuário do profissional já vinculado com outro Profissional")
    public ResponseEntity<Object> profissionalCadastro(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Dados a serem cadastrados",
            required = true,
            content = @Content(schema = @Schema(implementation = ProfissionalCadastroDTO.class))
        )
        @RequestBody @Valid ProfissionalCadastroDTO profissionalDTO, HttpServletRequest request){

        try{
            Empresa empresa = tokenService.getEmpresaFromToken(request, usuarioService);
            profissionalCreate.create(profissionalDTO, empresa);
            return new ResponseEntity<>("Profissional cadastrado com sucesso", HttpStatus.CREATED);


        }catch(EntityDontExistException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);

        }catch(AcessoNegadoException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);

        }catch(DataIntegrityViolationException e){
            return new ResponseEntity<>("Erro no cadastro. Prossivelmente este usuário já possui um Profissional vinculado", HttpStatus.CONFLICT);
        }
    }

    //Editar Profissional
    @PutMapping("/profissional/edit")
    @Operation(summary = "Edição de Profissional", description = "Altera atributos do Profissional")
    @ApiResponse(responseCode = "200", description = "Edição do profissional ocoreu com sucesso")
    @ApiResponse(responseCode = "400", description = "Profissional não encontrado para edição")
    @ApiResponse(responseCode = "403", description = "Usuário sem permissão para esta operação")
    public ResponseEntity<Object> profissionalEditar(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Dados editados",
            required = true,
            content = @Content(schema = @Schema(implementation = ProfissionalEditDTO.class))
        )
        @RequestBody @Valid ProfissionalEditDTO profissionalDTO, HttpServletRequest request) throws Exception{
        
        try{
            Empresa empresa = tokenService.getEmpresaFromToken(request, usuarioService);
            profissionalEditing.edit(profissionalDTO, empresa);
            return new ResponseEntity<>("Profissional editado com sucesso", HttpStatus.OK);

        }catch(EntityDontExistException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);

        }catch(AcessoNegadoException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    //Excluir Profissional
    @DeleteMapping("/profissional/delete/{id}")
    @Operation(summary = "Delta Profissional", description = "Exclui um profissional do Banco de Dados")
    @ApiResponse(responseCode = "200", description = "Profissional excluído com sucesso")
    @ApiResponse(responseCode = "404", description = "Profissional não encontrado para exclusão")
    @ApiResponse(responseCode = "403", description = "Usuário sem permissão para esta operação")
    public ResponseEntity<Object> profissionalDelete(
        @Parameter(
            description = "ID do profissional a ser excluído",
            required = true,
            example = "123"
        )
        @PathVariable long id, HttpServletRequest request){
        try{
            Empresa empresa = tokenService.getEmpresaFromToken(request, usuarioService);
            profissionalDeleting.delete(id, empresa);
            return new ResponseEntity<>("Profissional deletado", HttpStatus.OK);

        }catch(EntityDontExistException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);

        }catch(AcessoNegadoException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);

        }catch(DataIntegrityViolationException e){
            return new ResponseEntity<>("Profissional não pode ser excludído. Possivelmente está vinculado a uma agenda.", HttpStatus.NOT_FOUND);

        }
    }

    //Lista Profissoinal por empresa
    @GetMapping("/profissional/list")
    @Operation(summary = "Lista profissionais da Empresa")
    @ApiResponse(responseCode = "200", description = "Lista profissionais com sucesso")
    @ApiResponse(responseCode = "403", description = "Usuario sem permissão para esta operação")
    public ResponseEntity<Page<ProfissionalResponseDTO>> profissionalListByEmpresa(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable, HttpServletRequest request){

        Empresa empresa = tokenService.getEmpresaFromToken(request, usuarioService);
        List<ProfissionalResponseDTO> listDTO = profissionalList.list(empresa, pageable).getContent().stream().map(profissional -> {
            ProfissionalResponseDTO dto = profissionalConverter.toResponseDTO(profissional);
            return dto;
        }).collect(Collectors.toList());

        return new ResponseEntity<>(new PageImpl<ProfissionalResponseDTO>(listDTO), HttpStatus.OK);
    
    }

    @GetMapping("/profissional/findbyid")
    @Operation(summary = "Lista profissional pelo ID", description = "Busca profissional pelo ID no Banco de Dados")
    @ApiResponse(responseCode = "200", description = "Profissional encontrado é retornado")
    @ApiResponse(responseCode = "404", description = "Profissional não encontrado")
    @ApiResponse(responseCode = "403", description = "Usuário sem permissão para esta operação")
    public ResponseEntity<Object> profissionalFindById(
        @Parameter(description = "ID do Profissional a ser buscado", required = true, example = "123")
        @RequestParam long id, HttpServletRequest request){


        try{
            Empresa empresa = tokenService.getEmpresaFromToken(request, usuarioService);
            Profissional profissional = profissionalList.list(id, empresa);
            ProfissionalResponseDTO dto = profissionalConverter.toResponseDTO(profissional);

            return new ResponseEntity<>(dto, HttpStatus.OK);
        
        }catch(EntityDontExistException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);

        }catch(AcessoNegadoException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }

        
        
    }


    // ========================================== FERIADOS =============================================================
    @PostMapping("/feriado/cadastro")
    @Operation(summary = "Cadastra Feriado", description = "Insere um novo feriado no Bando de Dados")
    @ApiResponse(responseCode = "201", description = "Feriado cadastrado com sucesso")
    @ApiResponse(responseCode = "409", description = "Feriado já existe")
    public ResponseEntity<Object> feriadoCadastro(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Dados a serem cadastrados",
            required = true,
            content = @Content(schema = @Schema(implementation = FeriadoCadastroDTO.class))
        )
        @RequestBody @Valid FeriadoCadastroDTO feriadoDTO, HttpServletRequest request){
        System.out.println("------------------------");
        try{
            Empresa empresa = tokenService.getEmpresaFromToken(request, usuarioService);
            feriadoCreate.create(feriadoDTO, empresa);
            return new ResponseEntity<>("Feriado cadastrado com sucesso", HttpStatus.CREATED);

        }catch(ConflictException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);

        }


        // Feriado feriado = new Feriado();
        // BeanUtils.copyProperties(feriadoDTO, feriado);

        // Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT-3:00"), new Locale("pt-BR"));
        // String ano = feriadoDTO.dia().split("-")[0];
        // String mes = feriadoDTO.dia().split("-")[1];
        // String dia = feriadoDTO.dia().split("-")[2];
        // calendar.set(Integer.parseInt(ano), Integer.parseInt(mes), Integer.parseInt(dia));
        // feriado.setDia(calendar);

        // Usuario usuario = usuarioService.findByLogin(tokenService.validateToken(tokenService.recoverToken(request))).get();
        // feriado.setEmpresa(usuario.getEmpresa());
        // return new ResponseEntity<>(feriadoService.save(feriado), HttpStatus.CREATED);
    }

    @PutMapping("/feriado/edit")
    @Operation(summary = "Edita um feriado", description = "Edita um feriado já cadastrado")
    @ApiResponse(responseCode = "200", description = "Feriado editado com sucesso")
    @ApiResponse(responseCode = "404", description = "Feriado não encontrado para edição")
    @ApiResponse(responseCode = "403", description = "Usuário sem permissão para esta operação")
    @ApiResponse(responseCode = "409", description = "Jà existe um feriado com esse nome")
    public ResponseEntity<Object> feriadoEdit(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Dados a serem editados",
            required = true,
            content = @Content(schema = @Schema(implementation = FeriadoEditDTO.class))
        )
        @RequestBody @Valid FeriadoEditDTO feriadoDTO, HttpServletRequest request){

        try{
            Empresa empresa = tokenService.getEmpresaFromToken(request, usuarioService);
            feriadoEditing.edit(feriadoDTO, empresa);
            return new ResponseEntity<>("Feriado editado com sucesso", HttpStatus.OK);

        }catch(EntityDontExistException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);

        }catch(AcessoNegadoException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);

        }catch(ConflictException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }

        // Usuario usuarioLogado = usuarioService.findByLogin(tokenService.validateToken(tokenService.recoverToken(null))).get();

        // Optional<Feriado> feriado = feriadoService.findById(feriadoDTO.id());

        // //SOmente edita feriado se Empresa do feriado for a memsa do usuario logado.
        // if (feriado.isPresent() && feriado.get().getEmpresa().getId() == usuarioLogado.getEmpresa().getId()){
        //     Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT-3:00"), new Locale("pt-BR"));
        //     int ano = Integer.parseInt(feriadoDTO.data().split("-")[0]);
        //     int mes = Integer.parseInt(feriadoDTO.data().split("-")[1]);
        //     int dia = Integer.parseInt(feriadoDTO.data().split("-")[2]); 
        //     calendar.set(ano, mes, dia);

        //     feriado.get().setNome(feriadoDTO.nome());
        //     feriado.get().setDia(calendar);

        //     return new ResponseEntity<>(feriadoService.save(feriado.get()), HttpStatus.OK);
        
        // } else return new ResponseEntity<>("Feriado não encontrado!", HttpStatus.NOT_FOUND);
        
    }

    @DeleteMapping("/feriado/delete/{id}")
    @Operation(summary = "Deleta Feriado", description = "Deleta um feriado do Banco de Dados")
    @ApiResponse(responseCode = "200", description = "Feriado excluído com sucesso")
    @ApiResponse(responseCode = "403", description = "Usuário sem permissão para esta operação")
    @ApiResponse(responseCode = "404", description = "Feriado não encontrado para exclusão")
    public ResponseEntity<Object> feriadoDelete(
        @Parameter(description = "Id do feriado", required = true, example = "123")
        @PathVariable (name = "id") long id, HttpServletRequest request){
        
        try{
            Empresa empresa = tokenService.getEmpresaFromToken(request, usuarioService);
            feriadoDeleting.delete(id, empresa);
            return new ResponseEntity<>("Feriado excluído com sucesso", HttpStatus.OK);

        }catch(AcessoNegadoException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);

        }catch(EntityDontExistException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);

        }
        
        
    }

    
    @GetMapping("/feriado/list")
    @Operation(summary = "Lista feriados", description = "Lista feriados da Empresa")
    @ApiResponse(responseCode = "200", description = "Retorna feriados com sucesso")
    @ApiResponse(responseCode = "403", description = "Usuário sem permissão para esta operação")
    public ResponseEntity<Page<FeriadoResponseDTO>> feriadoListByEmpresa(
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) 
            Pageable pageable, HttpServletRequest request){
               
                Empresa empresa = tokenService.getEmpresaFromToken(request, usuarioService);
                List<Feriado> feriados = feriadoList.list(empresa, pageable).getContent();
                List<FeriadoResponseDTO> listDTO = feriados.stream().map(feriado -> {
                    FeriadoResponseDTO dto = feriadoConverter.toResponseDTO(feriado);
                    return dto;

                }).collect(Collectors.toList());

                return new ResponseEntity<>(new PageImpl<FeriadoResponseDTO>(listDTO), HttpStatus.OK);
            }

    @GetMapping("/feriado")
    @Operation(summary = "Busca feriado", description = "Busca feriado da empresa pelo ID")
    @ApiResponse(responseCode = "200", description = "Feriado encontrado é retornado")
    @ApiResponse(responseCode = "404", description = "Feriado não encontrado")
    @ApiResponse(responseCode = "403", description = "Usuário sem permissão para esta operação")
    public ResponseEntity<Object> feriadoListById(@RequestParam long id, HttpServletRequest request){
        try{
            Empresa empresa = tokenService.getEmpresaFromToken(request, usuarioService);
            Feriado feriado = feriadoList.list(id, empresa);
            FeriadoResponseDTO dto = feriadoConverter.toResponseDTO(feriado);
            return new ResponseEntity<>(dto, HttpStatus.OK);

        }catch(EntityDontExistException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);

        }catch(AcessoNegadoException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
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

 
