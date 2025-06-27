package com.gregoryan.api.Controllers;

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
import com.gregoryan.api.DTO.DiaBloqueadoCadastroDTO;
import com.gregoryan.api.DTO.DiaBloqueadoEditDTO;
import com.gregoryan.api.DTO.DiaBloqueadoResponseDTO;
import com.gregoryan.api.DTO.DiaEditDTO;
import com.gregoryan.api.DTO.FeriadoCadastroDTO;
import com.gregoryan.api.DTO.FeriadoEditDTO;
import com.gregoryan.api.DTO.FeriadoResponseDTO;
import com.gregoryan.api.DTO.HorasEditDTO;
import com.gregoryan.api.DTO.ProfissionalCadastroDTO;
import com.gregoryan.api.DTO.ProfissionalEditDTO;
import com.gregoryan.api.DTO.ProfissionalListDTO;
import com.gregoryan.api.DTO.ProfissionalResponseDTO;
import com.gregoryan.api.DTO.StatusAgendaCadastroDTO;
import com.gregoryan.api.DTO.StatusAgendaResponseDTO;
import com.gregoryan.api.DTO.StatusDiaCadastroDTO;
import com.gregoryan.api.DTO.StatusDiaEditDTO;
import com.gregoryan.api.DTO.StatusDiaResponseDTO;
import com.gregoryan.api.DTO.StatusHoraCadastroDTO;
import com.gregoryan.api.DTO.StatusHoraResponseDTO;
import com.gregoryan.api.DTO.UsuarioCreateDTO;
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
import com.gregoryan.api.Models.Feriado;
import com.gregoryan.api.Models.Horas;
import com.gregoryan.api.Models.PlanoPaciente;
import com.gregoryan.api.Models.Profissional;
import com.gregoryan.api.Models.StatusAgenda;
import com.gregoryan.api.Models.StatusDia;
import com.gregoryan.api.Models.StatusHora;
import com.gregoryan.api.Models.Usuario;
import com.gregoryan.api.Services.UsuarioDeleteService;
import com.gregoryan.api.Services.UsuarioEditService;
import com.gregoryan.api.Services.AgendaConfigureService;
import com.gregoryan.api.Services.AgendaCreateService;
import com.gregoryan.api.Services.AgendaDeleteService;
import com.gregoryan.api.Services.AgendaEditService;
import com.gregoryan.api.Services.DiaBloqueadoCreateService;
import com.gregoryan.api.Services.DiaBloqueadoDeleteService;
import com.gregoryan.api.Services.DiaBloqueadoEditingService;
import com.gregoryan.api.Services.DiaEditService;
import com.gregoryan.api.Services.FeriadoCreateService;
import com.gregoryan.api.Services.FeriadoDeleteService;
import com.gregoryan.api.Services.FeriadoEditService;
import com.gregoryan.api.Services.HoraDeleteService;
import com.gregoryan.api.Services.ProfissionalCreateService;
import com.gregoryan.api.Services.ProfissionalDeleteService;
import com.gregoryan.api.Services.ProfissionalEditService;
import com.gregoryan.api.Services.StatusAgendaCreateService;
import com.gregoryan.api.Services.StatusAgendaDeleteService;
import com.gregoryan.api.Services.StatusDiaCreateService;
import com.gregoryan.api.Services.StatusDiaDeleteService;
import com.gregoryan.api.Services.StatusDiaEditService;
import com.gregoryan.api.Services.StatusHoraCreateService;
import com.gregoryan.api.Services.StatusHoraDeleteService;
import com.gregoryan.api.Services.UsuarioCreateService;
import com.gregoryan.api.Services.UsuarioResetSenha;
import com.gregoryan.api.Services.Crud.AgendaService;
import com.gregoryan.api.Services.Crud.DiaBloqueadoService;
import com.gregoryan.api.Services.Crud.DiasService;
import com.gregoryan.api.Services.Crud.HorasService;
import com.gregoryan.api.Services.Crud.PlanoPacienteService;
import com.gregoryan.api.Services.Crud.ProfissionalService;
import com.gregoryan.api.Services.Crud.StatusAgendaService;
import com.gregoryan.api.Services.Crud.StatusDiaService;
import com.gregoryan.api.Services.Crud.StatusHoraService;
import com.gregoryan.api.Services.Crud.UsuarioService;
import com.gregoryan.api.Interfaces.AgendaConverterInterface;
import com.gregoryan.api.Interfaces.AgendaListInterface;
import com.gregoryan.api.Interfaces.DiaBloqueadoConverterInterface;
import com.gregoryan.api.Interfaces.DiaBloqueadoListInterface;
import com.gregoryan.api.Interfaces.DiaListInterface;
import com.gregoryan.api.Interfaces.FeriadoConverterInterface;
import com.gregoryan.api.Interfaces.FeriadoListInterface;
import com.gregoryan.api.Interfaces.ProfissionalConverterInterface;
import com.gregoryan.api.Interfaces.ProfissionalListInterface;
import com.gregoryan.api.Interfaces.StatusAgendaConverterInterface;
import com.gregoryan.api.Interfaces.StatusAgendaListInterface;
import com.gregoryan.api.Interfaces.StatusDiaConverterInterface;
import com.gregoryan.api.Interfaces.StatusDiaListInterface;
import com.gregoryan.api.Interfaces.StatusHoraConverterInterface;
import com.gregoryan.api.Interfaces.StatusHoraListInterface;
import com.gregoryan.api.Interfaces.UsuarioConverterInterface;
import com.gregoryan.api.Interfaces.UsuarioListInterface;
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
    private DiaBloqueadoService diaBloqueadoService;

    // Injetor relacionado a Usuarios
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private UsuarioCreateService usuarioCreate;
    @Autowired
    private UsuarioDeleteService usuarioDeleting;
    @Autowired
    private UsuarioListInterface usuarioList;
    @Autowired
    private UsuarioEditService userEdit;
    @Autowired
    private UsuarioResetSenha resetSenha;
    @Autowired
    private UsuarioConverterInterface usuarioConverter;

    //Injetores relacionados ao Profissional
    @Autowired
    private ProfissionalCreateService profissionalCreate;
    @Autowired
    private ProfissionalEditService profissionalEdit;
    @Autowired
    private ProfissionalDeleteService profissionalDeleting;
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
    private AgendaDeleteService agendaDelete;
    @Autowired
    private AgendaEditService agendaEdit;
    @Autowired
    private AgendaConfigureService agendaConfigure;
    @Autowired
    private AgendaConverterInterface agendaConverter;
    @Autowired
    private StatusAgendaCreateService statusAgendaCreate;
    @Autowired
    private StatusAgendaListInterface statusAgendaList;
    @Autowired
    private StatusAgendaDeleteService statusAgendaDeleting;
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
    private StatusDiaEditService statusDiaEditing;
    @Autowired
    private StatusDiaDeleteService statusDiaDeleting;
    @Autowired
    private DiaBloqueadoListInterface diaBloqueadoList;
    @Autowired
    private DiaBloqueadoCreateService diaBloqueadoCreate;
    @Autowired
    private DiaBloqueadoEditingService diaBloqueadoEditing;
    @Autowired
    private DiaBloqueadoConverterInterface diaBloqueadoConverter;
    @Autowired
    private DiaBloqueadoDeleteService diaBloqueadoDelete;
    @Autowired
    private DiaEditService diaEditService;
    @Autowired
    private DiaListInterface diaList;
    @Autowired
    private HoraDeleteService horaDelete;

    //Injetores realacionados a feriado
    @Autowired
    private FeriadoCreateService feriadoCreate;
    @Autowired
    FeriadoEditService feriadoEditing;
    @Autowired
    private FeriadoDeleteService feriadoDeleting;
    @Autowired
    private FeriadoListInterface feriadoList;
    @Autowired
    private FeriadoConverterInterface feriadoConverter;



    // ================================= Usuários dos Sistema =================================

    //OK
    @PostMapping("/user/create")
    @Operation(summary = "Cadastro de usuários", description = "Salva um usuário no Banco de Dados")
    @ApiResponse(responseCode = "201", description = "Usuário salvo com sucesso")
    @ApiResponse(responseCode = "409", description = "Conflito de login")
    @ApiResponse(responseCode = "403", description = "Usuário sem permissão para esta operação")
    public ResponseEntity<?> userCreate(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Dados do usuário a ser cadastrado",
            required = true,
            content = @Content(schema = @Schema(implementation = UsuarioCreateDTO.class))
        )
        @RequestBody @Valid UsuarioCreateDTO usuarioDTO, HttpServletRequest request) throws ParseException {

        try {
            var usuarioLogado = tokenService.getUserLogado(request, usuarioService);
            usuarioCreate.create(usuarioDTO, usuarioLogado);

            return new ResponseEntity<>("Usuário cadastrado com sucesso", HttpStatus.CREATED);

        }catch (ConflictException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);

        }

    }

    //OK
    @DeleteMapping("/user/delete/{id}")
    @Operation(summary = "Deleta Usuário", description = "Deleta um usuário do Banco de Dados")
    @ApiResponse(responseCode = "200", description = "Usuário deletado com sucesso")
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado para deletar")
    @ApiResponse(responseCode = "403", description = "Usuário sem permissão para esta operação")
    public ResponseEntity<?> userDelete(
        @Parameter(description = "ID do usuário a ser excluído",required = true,example = "123")
        @PathVariable long id, HttpServletRequest request){

        try{
            var usuarioLogado = tokenService.getUserLogado(request, usuarioService);
            usuarioDeleting.delete(id, usuarioLogado);
            return new ResponseEntity<>("Usuário excluído do sistema", HttpStatus.OK);

        } catch(EntityDontExistException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);

        } catch (AcessoNegadoException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    //OK
    @GetMapping("/user/list")
    @Operation(summary = "Lista usuário da usuario", description = "Lista usuários da usuario da qual o usuário faz parte.")
    @ApiResponse(responseCode = "200", description = "Usuário listado com sucesso")
    @ApiResponse(responseCode = "403", description = "Usuário sem permissão para esta operação")
    public ResponseEntity<?> userByEmpresa(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable, HttpServletRequest request){

        try {

            var usuarioLogado = tokenService.getUserLogado(request, usuarioService);

            List<UsuarioResponseDTO> listDTO = usuarioList.list(usuarioLogado.getEmpresa(), pageable).getContent().stream().map(usuario -> {
                UsuarioResponseDTO dto = usuarioConverter.toUsuarioResponseDTO(usuario);
                return dto;
            }).collect(Collectors.toList());

            return new ResponseEntity<>(new PageImpl<UsuarioResponseDTO>(listDTO), HttpStatus.OK);

        }catch(BeansException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    //OK
    @GetMapping("/user/byid")
    @Operation(summary = "Busca Usuário por ID", description = "Busca usuário no Banco pelo ID")
    @ApiResponse(responseCode = "200", description = "Usuário localizado é retornado")
    @ApiResponse(responseCode = "404", description = "Usuário não localizado")
    @ApiResponse(responseCode = "403", description = "Usuário sem permissão para esta operação")
    public ResponseEntity<?> userById(
        @Parameter(description = "ID do uusário",required = true,example = "123")
        @RequestParam long id, HttpServletRequest request){

        try {
            var usuarioLogado = tokenService.getUserLogado(request, usuarioService);
            Usuario usuario = usuarioList.list(id, usuarioLogado);
            UsuarioResponseDTO dto = usuarioConverter.toUsuarioResponseDTO(usuario);
            return new ResponseEntity<>(dto, HttpStatus.OK);

        }catch(EntityDontExistException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);

        }catch(AcessoNegadoException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }

    }

    //OK
    @GetMapping("/user/bylogin")
    @Operation(summary = "Busca usuário por Login", description = "Busca usuário no banco pelo login")
    @ApiResponse(responseCode = "200", description = "Usuário localizado é retornado")
    @ApiResponse(responseCode = "404", description = "Usuário não localizado")
    @ApiResponse(responseCode = "403", description = "Usuário sem permissão para esta operação")
    public ResponseEntity<Object> userByLogin(
        @Parameter(description = "Login do usuário",required = true,example = "maria.jose")
        @RequestParam String login, HttpServletRequest request){

        try {
            var usuarioLogado = tokenService.getUserLogado(request, usuarioService);
            Usuario usuario = usuarioList.list(login, usuarioLogado);

            UsuarioResponseDTO dto = usuarioConverter.toUsuarioResponseDTO(usuario);
            return new ResponseEntity<>(dto, HttpStatus.OK);

        }catch(EntityDontExistException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);

        }catch(AcessoNegadoException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }

    }

    //OK
    @PutMapping("/user/edit")
    @Operation(summary = "Edita dados de usuário", description = "Salva dados editados do usuário no Banco de Dados")
    @ApiResponse(responseCode = "200", description = "Usuário salvo com sucesso no Banco de Dados")
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    @ApiResponse(responseCode = "403", description = "Usuário sem permissão para esta operação")
    public ResponseEntity<Object> userEdit(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Dados do usuário editado a ser salvo",
            required = true,
            content = @Content(schema = @Schema(implementation = UsuarioEditDTO.class))
        )
        @RequestBody @Valid UsuarioEditDTO usuarioDTO, HttpServletRequest request) throws ParseException{

        try {
            var usuarioLogado = tokenService.getUserLogado(request, usuarioService);
            userEdit.edit(usuarioDTO, usuarioLogado);
            return new ResponseEntity<>("Usuario Editado", HttpStatus.OK);

        }catch (EntityDontExistException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);

        }catch (AcessoNegadoException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);

        }

    }

    //OK
    @PutMapping("/user/resetsenha")
    @Operation(summary = "Reseta senha de usuário", description = "Define uma nova senha para o usuário passado como parâmetro")
    @ApiResponse(responseCode = "200", description = "Usuário salvo com sucesso no Banco de Dados")
    @ApiResponse(responseCode = "404", description = "Usuário não localizado")
    @ApiResponse(responseCode = "403", description = "Usuário sem premissão para esta operação")
    public ResponseEntity<Object> userResetPassword(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Dados do usuário como ID e nova senha",
            required = true,
            content = @Content(schema =  @Schema(implementation = UsuarioResetSenhaDTO.class))
        )
        @RequestBody @Valid UsuarioResetSenhaDTO usuarioDTO, HttpServletRequest request){

        try {

            var usuarioLogado = tokenService.getUserLogado(request, usuarioService);
            resetSenha.reset(usuarioDTO, usuarioLogado);
            return new ResponseEntity<>("Senha Resetada", HttpStatus.OK);

        }catch(EntityDontExistException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);

        }catch(AcessoNegadoException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }


    // =============================================== AGENDA ======================================

    @PostMapping("/agenda/create")
    @Operation(summary = "Cadastrado de Agenda", description = "Salva uma nova agenda no Banco de Dados")
    @ApiResponse(responseCode = "201", description = "Agenda cadastrada com sucesso")
    @ApiResponse(responseCode = "404", description = "Profissional ou Status para a agenda não foram encontrados")
    @ApiResponse(responseCode = "403", description = "Usuário sem permissão para esta operação")
    @ApiResponse(responseCode = "400", description = "Profissional informado já possui agenda")
    public ResponseEntity<?> agendaCreate(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Dados da agenda a ser cadastrada",
            required = true,
            content = @Content(schema = @Schema(implementation = AgendaCadastroDTO.class))
        )
        @RequestBody @Valid AgendaCadastroDTO agendaDTO, HttpServletRequest request){

        try {
            var usuarioLogado = tokenService.getUserLogado(request, usuarioService);
            agendaCreate.create(agendaDTO, usuarioLogado);

            return new ResponseEntity<>("Agenda cadastrada", HttpStatus.CREATED);

        }catch (EntityDontExistException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);

        }catch(ConflictException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);

        }catch(DataIntegrityViolationException e){
            return new ResponseEntity<>("Este profissional já possui agenda", HttpStatus.BAD_REQUEST);
        }

    }

    //OK
    @GetMapping("/agenda/list")
    @Operation(summary = "Lista agendas da Empresa", description = "Lista agendas da empresa pegando a empresa do usuário logado")
    @ApiResponse(responseCode = "200", description = "Retorna agendas da empresa com sucesso")
    @ApiResponse(responseCode = "403", description = "Usuário sem permissão para esta operação")
    @ApiResponse(responseCode = "500", description = "Se houver algum erro na operação")
    public ResponseEntity<?> agendaByUser(
        @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
        HttpServletRequest request){

        try{
            var usuarioLogado = tokenService.getUserLogado(request, usuarioService);
            List<Agenda> agendas = agendaList.list(usuarioLogado.getEmpresa(), pageable).getContent();

            List<AgendaResponseDTO> listDTO = agendas.stream().map(agenda -> {
                return agendaConverter.toResponseDTO(agenda);
            }).collect(Collectors.toList());

            return new ResponseEntity<>(new PageImpl<>(listDTO), HttpStatus.OK);

        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //OK
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
            var usuarioLogado = tokenService.getUserLogado(request, usuarioService);
            Agenda agenda = agendaList.list(id, usuarioLogado);
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
            var usuarioLogado = tokenService.getUserLogado(request, usuarioService);
            agendaDelete.deletar(usuarioLogado, id);
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
    @Operation(summary = "Configura agenda", description = "Configura horários e dias para a agenda")
    @ApiResponse(responseCode = "201", description = "Agenda configurada com sucesso")
    @ApiResponse(responseCode = "404", description = "Algum parãmetro da configuração não foi encontrado")
    @ApiResponse(responseCode = "403", description = "Usuário sem permissão para esta operação.")
    public ResponseEntity<Object> agendaConfig(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Dados da agenda a ser configurada",
            required = true,
            content = @Content(schema = @Schema(implementation = AgendaConfigDTO.class))
        )
        @RequestBody @Valid AgendaConfigDTO agendaDTO, HttpServletRequest request){

        try{
            var usuarioLogado = tokenService.getUserLogado(request, usuarioService);
            agendaConfigure.configure(agendaDTO, usuarioLogado);
            return new ResponseEntity<>("Agenda configurada com sucesso", HttpStatus.CREATED);

        }catch(EntityDontExistException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);

        }catch(AcessoNegadoException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    //Edita Agenda
    @PutMapping("/agenda/edit")
    @Operation(summary = "Edita dados da agenda", description = "Altera dados da agenda e salva no banco de dados")
    @ApiResponse(responseCode = "200", description = "Dados foram alterados com sucesso")
    @ApiResponse(responseCode = "200", description = "Dados foram alterados com sucesso")
    @ApiResponse(responseCode = "403", description = "Usuário sem permissão para esta operação")
    public ResponseEntity<Object> agendaEdit(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Dados a serem alterados",
            required = true,
            content = @Content(schema = @Schema(implementation = AgendaEditDTO.class))
        )
        @RequestBody @Valid AgendaEditDTO agendaDTO, HttpServletRequest request){


        try{
            var usuarioLogado = tokenService.getUserLogado(request, usuarioService);
            agendaEdit.edit(agendaDTO, usuarioLogado);
            return new ResponseEntity<>("Agenda atualizada", HttpStatus.OK);

        }catch(EntityDontExistException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);

        }catch(AcessoNegadoException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }

        // Usuario usuarioLogado = usuarioService.findByLogin(tokenService.validateToken(tokenService.recoverToken(request))).get();

        // //Se Agenda Não existe e usuario da Agenda é diferente da usuario do usuário logado retorna 404
        // if(!agendaService.findById(agendaDTO.idAgenda()).isPresent() || agendaService.findById(agendaDTO.idAgenda()).get().getusuario().getId() != usuarioLogado.getusuario().getId())
        //     return new ResponseEntity<>("Agenda não encontrada!", HttpStatus.NOT_FOUND);

        // Agenda agenda = agendaService.findById(agendaDTO.idAgenda()).get();
        // agenda.setNome(agendaDTO.nome());
        // if (statusAgendaService.findById(agendaDTO.idStatusAgenda()).isPresent())
        //     agenda.setStatusAgenda(statusAgendaService.findById(agendaDTO.idStatusAgenda()).get());

        // Optional<Profissional> profissional = profissionalService.findById(agendaDTO.idProfissional());
        // if (profissional.isPresent())
        //     agenda.setProfissional(profissional.get());

        // return new ResponseEntity<>(agendaService.save(agenda),HttpStatus.OK);
    }

    //Edita Dias da Agenda
    @PutMapping("/agenda/edit/dia")
    public ResponseEntity<Object> agendaEditDia(@RequestBody @Valid DiaEditDTO diaDTO, HttpServletRequest request){

        try{
            var usuarioLogado = tokenService.getUserLogado(request, usuarioService);
            diaEditService.editar(usuarioLogado, diaDTO);
            return new ResponseEntity<>("Dia editado com sucesso", HttpStatus.OK);

        }catch(EntityDontExistException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);

        }catch(AcessoNegadoException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }

        // Optional<Dias> dia = diasService.findById(diaDTO.idDia());

        // if (dia.isPresent()) {
        //     long duracaoSessaoBeforeEdit = dia.get().getDuracaoSessaoInMinutes();
        //     long intervaloSessaoBeforeEdit = dia.get().getIntervaloSessaoInMinutes();
        //     LocalTime inicioBeforeEdit = dia.get().getInicio();
        //     LocalTime fimBeforeEdit = dia.get().getFim();

        //     dia.get().setDuracaoSessaoInMinutes(diaDTO.duracaoSessaoInMinutes());
        //     dia.get().setIntervaloSessaoInMinutes(diaDTO.intervaloSessaoInMinutes());
        //     Optional<StatusDia> statusDia = statusDiaService.findById(diaDTO.idStatusDia());
        //     if (statusDia.isPresent()) dia.get().setStatusDia(statusDia.get());
        //     LocalTime inicio = LocalTime.of(Integer.parseInt(diaDTO.inicio().split(":")[0]), Integer.parseInt(diaDTO.inicio().split(":")[1]));
        //     LocalTime fim = LocalTime.of(Integer.parseInt(diaDTO.fim().split(":")[0]), Integer.parseInt(diaDTO.fim().split(":")[1]));
        //     dia.get().setInicio(inicio);
        //     dia.get().setFim(fim);


        //     if (duracaoSessaoBeforeEdit != dia.get().getDuracaoSessaoInMinutes() ||
        //         intervaloSessaoBeforeEdit != dia.get().getIntervaloSessaoInMinutes()||
        //         !inicio.equals(inicioBeforeEdit) || !fim.equals(fimBeforeEdit)) {

        //         StatusHora statusHora = statusHoraService.findByNome("Ativo").get();

        //         dia.get().createHoras(statusHora, horasService);
        //     }

        //     return new ResponseEntity<>(diasService.save(dia.get()), HttpStatus.OK);

        // } else return new ResponseEntity<>("Dia não encontrado!", HttpStatus.NOT_FOUND);

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
    @Operation(summary = "Deleta hora", description = "Deleta uma hora do Banco de Dados")
    @ApiResponse(responseCode = "200", description = "Hora deletada com sucesso")
    @ApiResponse(responseCode = "404", description = "Hora não encontrada para ser deletada")
    public ResponseEntity<Object> agendaHorasDelete(@Parameter(description = "ID da hora a ser excluída", required = true) @PathVariable long id){

        try{
            horaDelete.delete(id);
            return new ResponseEntity<>("Hora excluída", HttpStatus.OK);

        }catch(EntityDontExistException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);

        }
        // Optional<Horas> hora = horasService.findById(id);
        // if(!hora.isPresent()) return new ResponseEntity<>("Hora não encontrada!", HttpStatus.NOT_FOUND);

        // horasService.delete(hora.get());
        // return new ResponseEntity<>("Hora deletada da Agenda", HttpStatus.OK);
    }

    //Cadastro de Status Agenda
    @PostMapping("/agenda/status/create")
    @Operation(summary = "Cadastra um StatusAgenda", description = "Cadastra um novo status para a agenda")
    @ApiResponse(responseCode = "201", description = "Status cadastrado com sucesso")
    @ApiResponse(responseCode = "403", description = "Usuário sem permissão para esta operação")
    public ResponseEntity<Object> statusAgendaCreate(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Dados do status a ser cadastrado",
            required = true,
            content = @Content(schema = @Schema(implementation = StatusAgendaCadastroDTO.class))
        )
        @RequestBody @Valid StatusAgendaCadastroDTO statusAgendaDTO, HttpServletRequest request){

        try{

            var usuarioLogado = tokenService.getUserLogado(request, usuarioService);
            statusAgendaCreate.create(statusAgendaDTO, usuarioLogado.getEmpresa());
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
            var usuarioLogado = tokenService.getUserLogado(request, usuarioService);
            statusAgendaDeleting.delete(id, usuarioLogado);
            return new ResponseEntity<>("Status Excluída", HttpStatus.OK);

        }catch(EntityDontExistException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);

        }catch(AcessoNegadoException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    //Lista Status da Agenda por usuario;
    @GetMapping("/agenda/status/usuario")
    @Operation(summary = "Lista Status da Agenda", description = "Lista Status das Agendas por usuario")
    @ApiResponse(responseCode = "200", description = "Lista status com sucesso")
    @ApiResponse(responseCode = "403", description = "Usuário sem permissão para esta operação")
    public ResponseEntity<Page<StatusAgendaResponseDTO>> statusAgendaByusuario(HttpServletRequest request, @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable){

        var usuarioLogado = tokenService.getUserLogado(request, usuarioService);
        List<StatusAgendaResponseDTO> listDTO = statusAgendaList.list(usuarioLogado.getEmpresa(), pageable).getContent().stream().map(status -> {
            StatusAgendaResponseDTO dto = statusAgendaConverter.toResponseDTO(status);
            return dto;
        }).collect(Collectors.toList());

        return new ResponseEntity<>(new PageImpl<StatusAgendaResponseDTO>(listDTO), HttpStatus.OK);
    }


    //Lista Status da Agenda por ID
    @GetMapping("/agenda/status/byid")
        @Operation(summary = "Lista Status da Agenda", description = "Lista Status das Agendas por ID")
    @ApiResponse(responseCode = "200", description = "Lista status com sucesso")
    @ApiResponse(responseCode = "403", description = "Usuário sem permissão para esta operação")
    @ApiResponse(responseCode = "404", description = "Status não encontrado")
    public ResponseEntity<Object> statusAgendaById(@RequestParam long id, HttpServletRequest request){
        try{
            var usuarioLogado = tokenService.getUserLogado(request, usuarioService);
            StatusAgenda statusAgenda = statusAgendaList.list(id, usuarioLogado);
            StatusAgendaResponseDTO dto = statusAgendaConverter.toResponseDTO(statusAgenda);
            return new ResponseEntity<>(dto,HttpStatus.OK);

        }catch( EntityDontExistException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);

        }catch(AcessoNegadoException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }




    @GetMapping("/agenda/horas/byid")
    @Operation(summary = "Lista hora do dia", description = "Retorna lista de horas do dia")
    @ApiResponse(responseCode = "200", description = "Retorna horas do dia")
    @ApiResponse(responseCode = "404", description = "Dia não encontrado")
    @ApiResponse(responseCode = "403", description = "Usuário sem permissão para esta operação")
    public ResponseEntity<Object> horasByDia(@Parameter(description = "ID do dia", required = true) @RequestParam long id, HttpServletRequest request){
        //Lista horas por ID do dia


        try{
            var usuarioLogado = tokenService.getUserLogado(request, usuarioService);
            var dia = diaList.list(id, usuarioLogado);

            var horas = dia.getHoras();

            return new ResponseEntity<>(horas, HttpStatus.OK);

        }catch(AcessoNegadoException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);

        }catch(EntityDontExistException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

        // Optional<Dias> dia = diasService.findById(id);

        // if (!dia.isPresent()){
        //     return new ResponseEntity<>("Dia não encontrado", HttpStatus.NOT_FOUND);
        // }

        // List<Horas> horas = dia.get().getHoras();

        // return new ResponseEntity<>(horas, HttpStatus.OK);

    }


    //=========================== STATUS HORA ============================================

    //Cadastra Status Hora
    @PostMapping("/statushora/create")
    @Operation(summary = "Cadastra StatusHora", description = "Cadastra status para as horas da Agenda")
    @ApiResponse(responseCode = "201", description = "Status cadastrado com sucesso")
    @ApiResponse(responseCode = "409", description = "Já existe status com esse nome")
    public ResponseEntity<Object> statusHoraCreate(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dados a serem cadastrados", required = true,
                    content = @Content(schema = @Schema(implementation = StatusHoraCadastroDTO.class)))
            @RequestBody @Valid StatusHoraCadastroDTO statusHoraDTO, HttpServletRequest request){

        try{

            var usuarioLogado = tokenService.getUserLogado(request, usuarioService);
            statusHoraCreate.create(statusHoraDTO, usuarioLogado);
            return new ResponseEntity<>("Status cadastrado com sucesso", HttpStatus.CREATED);

        }catch(ConflictException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);

        }

    }

    //Lista Status da Hora por Empresa
    @GetMapping("/statushora/list")
    @Operation(summary = "Lista Status da Hora", description = "Lista status por usuario")
    @ApiResponse(responseCode = "200", description = "Lista status encontrados ou lista vazia;")
    public ResponseEntity<?> statusHoraByEmpresa(
        HttpServletRequest request,
        @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable){

        var usuarioLogado = tokenService.getUserLogado(request, usuarioService);
        List<StatusHora> statusHoras = statusHoraList.list(usuarioLogado.getEmpresa(), pageable).getContent();

        List<StatusHoraResponseDTO> listDTO = statusHoras.stream().map(status -> {
            StatusHoraResponseDTO dto = statusHoraConverter.toResponseDTO(status);
            return dto;
        }).collect(Collectors.toList());

        return new ResponseEntity<>(new PageImpl<>(listDTO), HttpStatus.OK);

    }

    //Lista Status da Hora por Empresa
    @GetMapping("/statushora/byid")
    @Operation(summary = "Lista Status", description = "Lista status da Hora pelo ID")
    @ApiResponse(responseCode = "200", description = "Lista status encontrados ou lista vazia;")
    @ApiResponse(responseCode = "404", description = "Status da hora não encontrado")
    @ApiResponse(responseCode = "403", description = "Usuario sem permissão para esta operação")
    public ResponseEntity<?> statusHoraByID(@RequestParam long id, HttpServletRequest request){

        try {
            var usuarioLogado = tokenService.getUserLogado(request, usuarioService);
            var status = statusHoraList.list(id, usuarioLogado);
            var dto = statusHoraConverter.toResponseDTO(status);
            return new ResponseEntity<>(dto, HttpStatus.OK);

        }catch (AcessoNegadoException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);

        }catch (EntityDontExistException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);

        }
    }

    //Deleta status da hora
    @DeleteMapping("/statushora/delete/{id}")
    @Operation(summary = "Exclui Status Hora", description = "Exclui um status hora do Banco de Dados")
    @ApiResponse(responseCode = "200", description = "Status excluído com sucesso")
    @ApiResponse(responseCode = "404", description = "Status não encontrado")
    @ApiResponse(responseCode = "403", description = "Usuário sem permissão para esta operação;")
    public ResponseEntity<String> statusHoraDelete(@PathVariable long id, HttpServletRequest request){

        try{
            var usuarioLogado = tokenService.getUserLogado(request, usuarioService);
            statusHoraDelete.delete(id, usuarioLogado);
            return new ResponseEntity<>("Status excluído", HttpStatus.OK);

        }catch(EntityDontExistException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);

        }catch(AcessoNegadoException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }


//    ================================ STATUS DIA ===========================================================

    //Cadastra Status Dia
    @PostMapping("/statusdia/create")
    @Operation(summary = "Cadastra um Status Dia", description = "Insere um novo status dia no Banco de Dados")
    @ApiResponse(responseCode = "201", description = "Status cadastrado com sucesso")
    @ApiResponse(responseCode = "403", description = "Já existe um status com este nome")
    public ResponseEntity<Object> statusDiaCreate(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Dados a serem cadastrados",
            required = true,
            content = @Content(schema = @Schema(implementation = StatusDiaCadastroDTO.class))
        )
        @RequestBody StatusDiaCadastroDTO dto, HttpServletRequest request){

        try{
            var usuarioLogado = tokenService.getUserLogado(request, usuarioService);
            statusDiaCreate.create(dto, usuarioLogado);
            return new ResponseEntity<>("Status cadastrado com sucesso", HttpStatus.CREATED);

        }catch(ConflictException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);

        }
    }

    //Edita Status Dia
    @PutMapping("/statusdia/edit")
    @Operation(summary = "Edita Status Dia", description = "Edita nome de um status dia")
    @ApiResponse(responseCode = "200", description = "Edição do status realizado com sucesso")
    @ApiResponse(responseCode = "409", description = "Já esite um status com esse nome")
    @ApiResponse(responseCode = "404", description = "Status não encontrado para edição")
    public ResponseEntity<Object> statusDiaEdit(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Dados a serem editados",
            required = true,
            content = @Content(schema = @Schema(implementation = StatusDiaEditDTO.class))
        )
        @RequestBody StatusDiaEditDTO dto, HttpServletRequest request){

        try{
            var usuarioLogado = tokenService.getUserLogado(request, usuarioService);
            statusDiaEditing.edit(dto, usuarioLogado);
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
    @DeleteMapping("/statusdia/delete/{id}")
    @Operation(summary = "Deleta Status Dia", description = "Deleta um status dia do Banco de Dados")
    @ApiResponse(responseCode = "200", description = "Exclusão realizada com sucesso")
    @ApiResponse(responseCode = "404", description = "Status não encontrado para exclusão")
    @ApiResponse(responseCode = "403", description = "Usuário sem permissão para esta operação")
    public ResponseEntity<Object> statusDiaDelete(@PathVariable long id, HttpServletRequest request){

        try{
            var usuarioLogado = tokenService.getUserLogado(request, usuarioService);
            statusDiaDeleting.delete(id, usuarioLogado);
            return new ResponseEntity<>("Status excluído com sucesso", HttpStatus.OK);

        }catch(EntityDontExistException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);

        }catch(AcessoNegadoException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    //Lista Status do dia
    @GetMapping("/statusdia/list")
    @Operation(summary = "Lista Status Dia", description = "Lista Status Dia por usuario")
    @ApiResponse(responseCode = "200", description = "Retorna lista com status dia por usuario com sucesso")
    public ResponseEntity<Page<StatusDiaResponseDTO>> statusDiaByEmpresa(
            HttpServletRequest request,
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable
    ){

        var usuarioLogado = tokenService.getUserLogado(request, usuarioService);
        List<StatusDia> statusDias = statusDiaList.list(usuarioLogado.getEmpresa(), pageable).getContent();

        List<StatusDiaResponseDTO> listDTO = statusDias.stream().map(item -> {
            StatusDiaResponseDTO dto = statusDiaConverter.toResponseDTO(item);
            return dto;
        }).collect(Collectors.toList());

        return new ResponseEntity<>(new PageImpl<StatusDiaResponseDTO>(listDTO), HttpStatus.OK);

    }

    //Lista Status do dia por ID
    @GetMapping("/statusdia/byid")
    @Operation(summary = "Lista Status Dia pelo ID", description = "Lista Status Dia pelo ID do Status")
    @ApiResponse(responseCode = "200", description = "Retorna status com sucesso")
    public ResponseEntity<?> statusDiaByID(
            @RequestParam long id, HttpServletRequest request
    ){
        try{
            var usuarioLogado = tokenService.getUserLogado(request, usuarioService);
            var status = statusDiaList.list(id, usuarioLogado);
            var dto = statusDiaConverter.toResponseDTO(status);
            return new ResponseEntity<>(dto, HttpStatus.OK);

        }catch (AcessoNegadoException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);

        }catch (EntityDontExistException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);

        }

    }

    //Replica dia para outro.
    //É recebido um Id do dia origem e uma lista de ids do dia alvo.
//    @PostMapping("/agenda/dia/replica")
//    public ResponseEntity<Object> replicaDia(@RequestBody AgendaReplicaDiaDTO dto, HttpServletRequest request){
//        //Usuario usuarioLogado = usuarioService.findByLogin(tokenService.validateToken(tokenService.recoverToken(request))).get();
//
//        Optional<Dias> diaOrigem = diasService.findById(dto.idOrigemDia());
//
//        // Se dia não encontrado retorna not_found
//        if (!diaOrigem.isPresent())
//            return new ResponseEntity<>("Dia Origem não encontrado.", HttpStatus.NOT_FOUND);
//
//        //Busca a agenda do diaOrigem para setar nos alvos
//        Long idAgenda = diasService.getAgenda(diaOrigem.get().getId()).get();
//
//        Optional<Agenda> agenda = agendaService.findById(idAgenda);
//
//        if (!agenda.isPresent()){
//            return new ResponseEntity<>("Erro ao buscar agenda do Dia Origem", HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//
//        // Map percore o DTO e busca os dias alvo e preeche o array diasAlvo
//        List<Dias> diasAlvo = dto.alvoDias().stream().map(nome -> {
//
//            Optional<Dias> dia = diasService.findByNome(nome);
//
//            if (dia.isPresent()){
//                return dia.get();
//            }
//
//            System.out.print("-------------------");
//
//            Dias newDia  = new Dias();
//
//            newDia.setNome(nome);
//
//            return newDia;
//
//        }).collect(Collectors.toList()); // converte Stream para List
//
//        // Para cada dia da lista de alvos
//        diasAlvo.forEach(dia -> {
//
//            if (dia.getHoras() != null){ //Evita NullPointerException se getHoras for null em caso de dia não configurado.
//                //Remove horas atuais do dia
//                for (Horas horas : dia.getHoras()) {
//                    horasService.delete(horas);
//                }
//            }
//
//            //Define os mesmo parametros do dia origem
//            dia.setDuracaoSessaoInMinutes(diaOrigem.get().getDuracaoSessaoInMinutes());
//            dia.setIntervaloSessaoInMinutes(diaOrigem.get().getIntervaloSessaoInMinutes());
//            dia.setInicio(diaOrigem.get().getInicio());
//            dia.setFim(diaOrigem.get().getFim());
//            dia.setStatusDia(diaOrigem.get().getStatusDia());
//            agenda.get().getDias().add(dia);
//
//            List<Horas> horasNovas = new ArrayList<>();
//
//            //Percore o dia Origem e para cada hora, cria uma nova hora para o alvo com o mesmo inicio e fim;
//            diaOrigem.get().getHoras().forEach(hora -> {
//                Horas horasAlvo = new Horas();
//                horasAlvo.setInicio(hora.getInicio());
//                horasAlvo.setFim(hora.getFim());
//                horasAlvo.setStatusHora(hora.getStatusHora());
//
//                horasNovas.add(hora);
//
//            }); // Fim Foreach hora do diaOrigem
//
//            dia.setHoras(horasNovas);
//            diasService.save(dia);
//
//
//        }); //Fim Foreach dias alvos
//
//        return new ResponseEntity<>("Replicação do dia com sucesso.", HttpStatus.OK);
//    }
    //=============================================== PROFISSIONAL =======================================================

    //OK
    //Cadastro de Profissional
    @PostMapping("/profissional/create")
    @Operation(summary = "Cadastro de Profissional", description = "Insere um novo Profissional no Banco de Dados")
    @ApiResponse(responseCode = "201", description = "Cadastro do usuário realizado com sucesso")
    @ApiResponse(responseCode = "404", description = "Usuário do profissional não encontrado")
    @ApiResponse(responseCode = "403", description = "Usuário sem permissão para esta operação")
    @ApiResponse(responseCode = "409", description = "Usuário do profissional já vinculado com outro Profissional")
    public ResponseEntity<Object> profissionalCreate(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Dados a serem cadastrados",
            required = true,
            content = @Content(schema = @Schema(implementation = ProfissionalCadastroDTO.class))
        )
        @RequestBody @Valid ProfissionalCadastroDTO profissionalDTO, HttpServletRequest request){

        try{
            var usuarioLogado = tokenService.getUserLogado(request, usuarioService);
            profissionalCreate.create(profissionalDTO, usuarioLogado);
            return new ResponseEntity<>("Profissional cadastrado com sucesso", HttpStatus.CREATED);


        }catch(EntityDontExistException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);

        }catch(AcessoNegadoException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);

        }catch(DataIntegrityViolationException e){
            return new ResponseEntity<>("Erro no cadastro. Possivelmente este usuário já possui um Profissional vinculado", HttpStatus.CONFLICT);
        }
    }

    //OK
    //Editar Profissional
    @PutMapping("/profissional/edit")
    @Operation(summary = "Edição de Profissional", description = "Altera atributos do Profissional")
    @ApiResponse(responseCode = "200", description = "Edição do profissional ocoreu com sucesso")
    @ApiResponse(responseCode = "400", description = "Profissional não encontrado para edição")
    @ApiResponse(responseCode = "403", description = "Usuário sem permissão para esta operação")
    public ResponseEntity<Object> profissionalEdit(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Dados editados",
            required = true,
            content = @Content(schema = @Schema(implementation = ProfissionalEditDTO.class))
        )
        @RequestBody @Valid ProfissionalEditDTO profissionalDTO, HttpServletRequest request) throws Exception{

        try{
            var usuarioLogado = tokenService.getUserLogado(request, usuarioService);
            profissionalEdit.edit(profissionalDTO, usuarioLogado);
            return new ResponseEntity<>("Profissional editado com sucesso", HttpStatus.OK);

        }catch(EntityDontExistException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);

        }catch(AcessoNegadoException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    //OK
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
            var usuarioLogado = tokenService.getUserLogado(request, usuarioService);
            profissionalDeleting.delete(id, usuarioLogado);
            return new ResponseEntity<>("Profissional deletado", HttpStatus.OK);

        }catch(EntityDontExistException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);

        }catch(AcessoNegadoException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);

        }catch(DataIntegrityViolationException e){
            return new ResponseEntity<>("Profissional não pode ser excludído. Possivelmente está vinculado a uma agenda.", HttpStatus.NOT_FOUND);

        }
    }

    //Ok
    //Lista Profissoinal por usuario
    @GetMapping("/profissional/list")
    @Operation(summary = "Lista profissionais da empresa")
    @ApiResponse(responseCode = "200", description = "Lista profissionais com sucesso")
    @ApiResponse(responseCode = "403", description = "Usuario sem permissão para esta operação")
    public ResponseEntity<Page<ProfissionalResponseDTO>> profissionalByEmpresa(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable, HttpServletRequest request){

        var usuarioLogado = tokenService.getUserLogado(request, usuarioService);
        List<ProfissionalResponseDTO> listDTO = profissionalList.list(usuarioLogado.getEmpresa(), pageable).getContent().stream().map(profissional -> {
            return profissionalConverter.toResponseDTO(profissional);

        }).collect(Collectors.toList());

        return new ResponseEntity<>(new PageImpl<ProfissionalResponseDTO>(listDTO), HttpStatus.OK);

    }


    //OK
    //Lista Profissional por Nome e ID
    @GetMapping("/profissionais")
    @Operation(summary = "Lista nome e id de profissionais", description = "Retorna lista com Nome e ID de Profissionais da usuario.")
    @ApiResponse(responseCode = "200", description = "Retorna usuários encontrado com sucesso")
    public ResponseEntity<List<ProfissionalListDTO>> profissionalNameAndId(HttpServletRequest request,
    @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable){

        var usuarioLogado = tokenService.getUserLogado(request, usuarioService);
        List<Profissional> profissionals = profissionalList.list(usuarioLogado.getEmpresa(), pageable).getContent();

        List<ProfissionalListDTO> listDTO = profissionals.stream().map(item -> {
            ProfissionalListDTO dto = profissionalConverter.toListDTO(item);
            return dto;
        }).collect(Collectors.toList());

        return new ResponseEntity<List<ProfissionalListDTO>>(listDTO, HttpStatus.OK);
    }

    //OK
    @GetMapping("/profissional/byid")
    @Operation(summary = "Lista profissional pelo ID", description = "Busca profissional pelo ID no Banco de Dados")
    @ApiResponse(responseCode = "200", description = "Profissional encontrado é retornado")
    @ApiResponse(responseCode = "404", description = "Profissional não encontrado")
    @ApiResponse(responseCode = "403", description = "Usuário sem permissão para esta operação")
    public ResponseEntity<Object> profissionalById(
        @Parameter(description = "ID do Profissional a ser buscado", required = true, example = "123")
        @RequestParam long id, HttpServletRequest request){


        try{
            var usuarioLogado = tokenService.getUserLogado(request, usuarioService);
            Profissional profissional = profissionalList.list(id, usuarioLogado);
            ProfissionalResponseDTO dto = profissionalConverter.toResponseDTO(profissional);

            return new ResponseEntity<>(dto, HttpStatus.OK);

        }catch(EntityDontExistException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);

        }catch(AcessoNegadoException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }



    }


    // ========================================== FERIADOS =============================================================
    //OK
    @PostMapping("/feriado/create")
    @Operation(summary = "Cadastra Feriado", description = "Insere um novo feriado no Bando de Dados")
    @ApiResponse(responseCode = "201", description = "Feriado cadastrado com sucesso")
    @ApiResponse(responseCode = "409", description = "Feriado já existe")
    public ResponseEntity<Object> feriadoCreate(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Dados a serem cadastrados",
            required = true,
            content = @Content(schema = @Schema(implementation = FeriadoCadastroDTO.class))
        )
        @RequestBody @Valid FeriadoCadastroDTO feriadoDTO, HttpServletRequest request){
        System.out.println("------------------------");
        try{
            var usuarioLogado = tokenService.getUserLogado(request, usuarioService);
            feriadoCreate.create(feriadoDTO, usuarioLogado);
            return new ResponseEntity<>("Feriado cadastrado com sucesso", HttpStatus.CREATED);

        }catch(ConflictException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);

        }

    }

    //OK
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
            var usuarioLogado = tokenService.getUserLogado(request, usuarioService);
            feriadoEditing.edit(feriadoDTO, usuarioLogado);
            return new ResponseEntity<>("Feriado editado com sucesso", HttpStatus.OK);

        }catch(EntityDontExistException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);

        }catch(AcessoNegadoException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);

        }catch(ConflictException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    //OK
    @DeleteMapping("/feriado/delete/{id}")
    @Operation(summary = "Deleta Feriado", description = "Deleta um feriado do Banco de Dados")
    @ApiResponse(responseCode = "200", description = "Feriado excluído com sucesso")
    @ApiResponse(responseCode = "403", description = "Usuário sem permissão para esta operação")
    @ApiResponse(responseCode = "404", description = "Feriado não encontrado para exclusão")
    public ResponseEntity<Object> feriadoDelete(
        @Parameter(description = "Id do feriado", required = true, example = "123")
        @PathVariable (name = "id") long id, HttpServletRequest request){

        try{
            var usuarioLogado = tokenService.getUserLogado(request, usuarioService);
            feriadoDeleting.delete(id, usuarioLogado);
            return new ResponseEntity<>("Feriado excluído com sucesso", HttpStatus.OK);

        }catch(AcessoNegadoException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);

        }catch(EntityDontExistException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);

        }


    }

    //OK
    @GetMapping("/feriado/list")
    @Operation(summary = "Lista feriados", description = "Lista feriados da usuario")
    @ApiResponse(responseCode = "200", description = "Retorna feriados com sucesso")
    @ApiResponse(responseCode = "403", description = "Usuário sem permissão para esta operação")
    public ResponseEntity<Page<FeriadoResponseDTO>> feriadoByEmpresa(
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC)
            Pageable pageable, HttpServletRequest request){

                var usuarioLogado = tokenService.getUserLogado(request, usuarioService);
                List<Feriado> feriados = feriadoList.list(usuarioLogado.getEmpresa(), pageable).getContent();
                List<FeriadoResponseDTO> listDTO = feriados.stream().map(feriado -> {
                    FeriadoResponseDTO dto = feriadoConverter.toResponseDTO(feriado);
                    return dto;

                }).collect(Collectors.toList());

                return new ResponseEntity<>(new PageImpl<FeriadoResponseDTO>(listDTO), HttpStatus.OK);
            }

    //OK
    @GetMapping("/feriado/byid")
    @Operation(summary = "Busca feriado", description = "Busca feriado da usuario pelo ID")
    @ApiResponse(responseCode = "200", description = "Feriado encontrado é retornado")
    @ApiResponse(responseCode = "404", description = "Feriado não encontrado")
    @ApiResponse(responseCode = "403", description = "Usuário sem permissão para esta operação")
    public ResponseEntity<Object> feriadoById(@RequestParam long id, HttpServletRequest request){
        try{
            var usuarioLogado = tokenService.getUserLogado(request, usuarioService);
            Feriado feriado = feriadoList.list(id, usuarioLogado);
            FeriadoResponseDTO dto = feriadoConverter.toResponseDTO(feriado);
            return new ResponseEntity<>(dto, HttpStatus.OK);

        }catch(EntityDontExistException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);

        }catch(AcessoNegadoException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }


    // ======================================================= DIAS BLOQUEADOS ==============================================
    //OK
    @PostMapping("diabloqueado/create")
    @Operation(summary = "Cadastra um bloqueio para um dia", description = "Cadastra um bloqueio de dia no Banco de Dados")
    @ApiResponse(responseCode = "200", description = "Bloqueio editado com sucesso")
    public ResponseEntity<?> diaBloqueadoCreate(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Dados a serem editados",
            required = true,
            content = @Content(schema = @Schema(implementation = DiaBloqueadoCadastroDTO.class))
        )
        @RequestBody @Valid DiaBloqueadoCadastroDTO diaBloqueado, HttpServletRequest request){

        var usuarioLogado = tokenService.getUserLogado(request, usuarioService);
        diaBloqueadoCreate.create(diaBloqueado, usuarioLogado);
        return new ResponseEntity<>("Bloqueio do dia cadastrado com sucesso", HttpStatus.CREATED);
    }

    //OK
    @PutMapping("/diabloqueado/edit")
    @Operation(summary = "Edita um bloqueio de dia", description = "Edita dados de um bloqueio de dia")
    @ApiResponse(responseCode = "200", description = "Bloqueio editado com sucesso")
    @ApiResponse(responseCode = "404", description = "Bloqueio não encontrado para edição")
    @ApiResponse(responseCode = "403", description = "Usuário sem permissão para esta operação")
    public ResponseEntity<?> diaBloqueadoEdit(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Dados a serem editados",
            required = true,
            content = @Content(schema = @Schema(implementation = DiaBloqueadoEditDTO.class))
        )
        @RequestBody @Valid DiaBloqueadoEditDTO diaBloqueado, HttpServletRequest request){

        try{
            var usuarioLogado = tokenService.getUserLogado(request, usuarioService);
            diaBloqueadoEditing.edit(diaBloqueado, usuarioLogado);
            return new ResponseEntity<>("Bloqueio do dia editado com sucesso", HttpStatus.OK);

        }catch(EntityDontExistException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);

        }catch(AcessoNegadoException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }

    }

    //OK
    @DeleteMapping("/diabloqueado/delete/{id}")
    @Operation(summary = "Deleta um bloqueio de dia", description = "Deleta um bloqueio de dia do Banco de Dados")
    @ApiResponse(responseCode = "200", description = "Bloqueio do dia deletado com sucesso")
    @ApiResponse(responseCode = "404", description = "Bloqueio do dia não encontrado para exclusão")
    @ApiResponse(responseCode = "403", description = "Usuário sem permissão para esta operação")
    public ResponseEntity<?> diaBloqueadoDelete(
        @Parameter(description = "ID do bloqueio a ser excluído", required = true, example = "123")
        @PathVariable(name = "id") long id, HttpServletRequest request){

        try{
            var usuarioLogado = tokenService.getUserLogado(request, usuarioService);
            diaBloqueadoDelete.delete(id, usuarioLogado);
            return new ResponseEntity<>("Bloqueio do dia excluído com sucesso", HttpStatus.OK);

        }catch(EntityDontExistException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);

        }catch(AcessoNegadoException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    //OK
    @GetMapping("/diabloqueado/list")
    @Operation(summary = "Lista bloqueios de dia", description = "Lista bloqueios para a usuario")
    @ApiResponse(responseCode = "200", description = "Retorna dados com sucesso")
    public ResponseEntity<?> diaBloqueadoByEmpresa(
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
            HttpServletRequest request){

            var usuarioLogado = tokenService.getUserLogado(request, usuarioService);
            List<DiaBloqueado> diaBloqueados = diaBloqueadoList.list(usuarioLogado.getEmpresa(), pageable).getContent();

            List<DiaBloqueadoResponseDTO> listDTO = diaBloqueados.stream().map(item -> {
                DiaBloqueadoResponseDTO dto = diaBloqueadoConverter.toResponseDTO(item);
                return dto;

            }).collect(Collectors.toList());

            return new ResponseEntity<>(new PageImpl<DiaBloqueadoResponseDTO>(listDTO), HttpStatus.OK);
    }

    //OK
    @GetMapping("/diabloqueado/byid")
    @Operation(summary = "Lista bloqueios de dia pelo ID", description = "Lista bloqueio com ID informado")
    @ApiResponse(responseCode = "200", description = "Retorna dados com sucesso")
    public ResponseEntity<?> diaBloqueadoByID(@RequestParam long id, HttpServletRequest request){

        try{
            var usuarioLogado = tokenService.getUserLogado(request, usuarioService);
            var dia = diaBloqueadoList.list(id, usuarioLogado);
            var dto = diaBloqueadoConverter.toResponseDTO(dia);

            return new ResponseEntity<>(dto, HttpStatus.OK);

        }catch(AcessoNegadoException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);

        }catch(EntityDontExistException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);

        }
    }

    // ================================= PLANO PACIENTE ==================================

    @PostMapping("/planopaciente/create")
    @Operation(summary = "Cadastro de plano paciente", description = "Salvar um plano para paciente no Banco")
    @ApiResponse(responseCode = "200", description = "Cadastro realizado com sucesso.")
    public ResponseEntity<?> planoPacienteCreate(@RequestBody planoPacienteCadastroDTO planoPacienteDTO, HttpServletRequest request){
        PlanoPaciente planoPaciente = new PlanoPaciente();

        BeanUtils.copyProperties(planoPacienteDTO, planoPaciente);

        Calendar now = Calendar.getInstance(TimeZone.getTimeZone("GMT"), new Locale("pt-BR"));

        planoPaciente.setDataRegistro(now);

        Usuario usuario = tokenService.getUserLogado(request, usuarioService);
        planoPaciente.setEmpresa(usuario.getEmpresa());

        planoPaciente.setStatus(PlanoPaciente.PLANO_STATUS_ATIVO);

        return new ResponseEntity<PlanoPaciente>(planoPacienteService.save(planoPaciente), HttpStatus.CREATED);
        
    }

}

 
