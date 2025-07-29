package com.gregoryan.api.Controllers;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.*;
import java.text.ParseException;
import java.util.stream.Collectors;

import com.gregoryan.api.DTO.*;
import com.gregoryan.api.Interfaces.*;
import com.gregoryan.api.Services.*;
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
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import com.gregoryan.api.Exception.AcessoNegadoException;
import com.gregoryan.api.Exception.EntityDontExistException;
import com.gregoryan.api.Exception.ConflictException;
import com.gregoryan.api.Models.Agenda;
import com.gregoryan.api.Models.DiaBloqueado;
import com.gregoryan.api.Models.Feriado;
import com.gregoryan.api.Models.PlanoPaciente;
import com.gregoryan.api.Models.Profissional;
import com.gregoryan.api.Models.StatusAgenda;
import com.gregoryan.api.Models.StatusDia;
import com.gregoryan.api.Models.StatusHora;
import com.gregoryan.api.Models.Usuario;
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
    @Autowired
    private UsuarioBlockService usuarioBlock;

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
    @Autowired
    private HoraEditService horaEdit;
    @Autowired
    private HoraListInterface horaList;
    @Autowired
    private HoraConverterInterface horaConverter;

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
            System.out.print(usuarioDTO.toString());
            usuarioCreate.create(usuarioDTO, usuarioLogado);

            return new ResponseEntity<>(new HttpResponseDTO("Sucesso :", "Usuário cadastrado com sucesso"),
                                            HttpStatus.CREATED);

        }catch (ConflictException e){

            return new ResponseEntity<>(new HttpResponseDTO("Erro:", e.getMessage()),
                                            HttpStatus.CONFLICT);

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

        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    //OK
    @GetMapping("/user/list")
    @Operation(summary = "Lista usuário da usuario", description = "Lista usuários da usuario da qual o usuário faz parte.")
    @ApiResponse(responseCode = "200", description = "Usuário listado com sucesso")
    @ApiResponse(responseCode = "403", description = "Usuário sem permissão para esta operação")
    public ResponseEntity<?> userByEmpresa(
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam String input, HttpServletRequest request
    ){

        try {
            var usuarioLogado = tokenService.getUserLogado(request, usuarioService);

            //If input is brank show all users
            if (input.isBlank()){

                var listDTO = usuarioList.list(usuarioLogado.getEmpresa(), pageable)
                        .map(usuario -> usuarioConverter.toUsuarioResponseDTO(usuario));


                return new ResponseEntity<>(listDTO, HttpStatus.OK);

            }else {

                var listDTO = usuarioList.list(pageable, usuarioLogado, input);
                Page<UsuarioResponseDTO> dto = listDTO.map(usuarioConverter::toUsuarioResponseDTO);

                return new ResponseEntity<>(dto, HttpStatus.OK);
            }


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
            return new ResponseEntity<>(new HttpResponseDTO("Sucesso", "Usuário atualizado"), HttpStatus.OK);

        }catch (EntityDontExistException e){
            return new ResponseEntity<>(new HttpResponseDTO("Erro", e.getMessage()), HttpStatus.NOT_FOUND);

        }catch (AcessoNegadoException e){
            return new ResponseEntity<>(new HttpResponseDTO("Erro", e.getMessage()), HttpStatus.FORBIDDEN);

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


    //Bloqueia usuario
    @PatchMapping("/user/block/{id}")
    public ResponseEntity<?> userBlock(@PathVariable long id, HttpServletRequest request){
         try {
             var usuarioLogado = tokenService.getUserLogado(request, usuarioService);
             usuarioBlock.block(id, usuarioLogado);
             return new ResponseEntity<>(new HttpResponseDTO("Sucesso", "Usuário bloqueado"), HttpStatus.OK);

         }catch(AcessoNegadoException e){
             return new ResponseEntity<>(new HttpResponseDTO("Erro", e.getMessage()), HttpStatus.FORBIDDEN);

         }catch (EntityDontExistException e){
             return new ResponseEntity<>(new HttpResponseDTO("Erro", e.getMessage()), HttpStatus.NOT_FOUND);

         }
    }

    //Busca de usuários
    @GetMapping("/user/search")
    public ResponseEntity<?> userSearch(@RequestParam String input, HttpServletRequest request,
                                        @PageableDefault(page = 0, size = 10, direction = Sort.Direction.DESC) Pageable pageable
    ){
        try{
            var usuariologado = tokenService.getUserLogado(request, usuarioService);
            var listDTO = usuarioList.list(pageable, usuariologado, input);
            Page<UsuarioResponseDTO> dto = listDTO.map(usuarioConverter::toUsuarioResponseDTO);

            return new ResponseEntity<>(dto, HttpStatus.OK);

        }catch (Exception e){
            return new ResponseEntity<>(new HttpResponseDTO("Erro", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }
// ================================= Fim Usuários dos Sistema =================================

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

            Page<AgendaResponseDTO> listDTO = agendaList.list(usuarioLogado.getEmpresa(), pageable)
                    .map(agenda -> agendaConverter.toResponseDTO(agenda));

            return new ResponseEntity<>(listDTO, HttpStatus.OK);

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

    }

    // ===============================================FIM AGENDA ======================================

    // =============================================== DIA ======================================

    //Edita Dias da Agenda
    @PutMapping("/dia/edit")
    public ResponseEntity<Object> diaEdit(@RequestBody @Valid DiaEditDTO diaDTO, HttpServletRequest request){

        try{
            var usuarioLogado = tokenService.getUserLogado(request, usuarioService);
            diaEditService.editar(usuarioLogado, diaDTO);
            return new ResponseEntity<>("Dia atualizado com sucesso", HttpStatus.OK);

        }catch(EntityDontExistException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);

        }catch(AcessoNegadoException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }


// =============================================== FIM DIA ======================================

    //============================================= HORA ==============================================
    //Edit Hora do dia da Agenda
    @PutMapping("/hora/edit")
    @Operation(summary = "Edita hora de um dia", description = "Atualiza status de uma hora do dia")
    @ApiResponse(responseCode = "200", description = "Hora atualizada com sucess")
    @ApiResponse(responseCode = "403", description = "Usuário sem permissão para esta operação")
    @ApiResponse(responseCode = "404", description = "Hora ou status não encontrado")
    public ResponseEntity<Object> horaEdit(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "ID da Hora e ID do status",
                    content = @Content(schema = @Schema(implementation = HoraEditDTO.class)))
            @RequestBody @Valid HoraEditDTO horaDTO, HttpServletRequest request){

        try{
            var usuarioLogado = tokenService.getUserLogado(request, usuarioService);
            horaEdit.edit(horaDTO, usuarioLogado);
            return new ResponseEntity<>("Hora atualizada", HttpStatus.OK);

        }catch (AcessoNegadoException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);

        }catch (EntityDontExistException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);

        }
    }

    //Deleta horas da agenda
    @DeleteMapping("/hora/delete/{id}")
    @Operation(summary = "Deleta hora", description = "Deleta uma hora do Banco de Dados")
    @ApiResponse(responseCode = "200", description = "Hora deletada com sucesso")
    @ApiResponse(responseCode = "404", description = "Hora não encontrada para ser deletada")
    public ResponseEntity<Object> horaDelete(
            @Parameter(description = "ID da hora a ser excluída", required = true)
            @PathVariable long id){

        try{
            horaDelete.delete(id);
            return new ResponseEntity<>("Hora excluída", HttpStatus.OK);

        }catch(EntityDontExistException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);

        }
        // Optional<Hora> hora = horasService.findById(id);
        // if(!hora.isPresent()) return new ResponseEntity<>("Hora não encontrada!", HttpStatus.NOT_FOUND);

        // horasService.delete(hora.get());
        // return new ResponseEntity<>("Hora deletada da Agenda", HttpStatus.OK);
    }

    @GetMapping("/hora/bydia")
    @Operation(summary = "Lista hora do dia", description = "Retorna lista de horas do dia")
    @ApiResponse(responseCode = "200", description = "Retorna horas do dia")
    @ApiResponse(responseCode = "404", description = "Dia não encontrado")
    @ApiResponse(responseCode = "403", description = "Usuário sem permissão para esta operação")
    public ResponseEntity<Object> horaByDia(
            @Parameter(description = "ID do dia", required = true) @RequestParam long id,
            HttpServletRequest request){

        try{
            var usuarioLogado = tokenService.getUserLogado(request, usuarioService);
            var dia = diaList.list(id, usuarioLogado);
            var horas = dia.getHoras();
            List<HoraResposeDTO> listDTO = horas.stream().map(item -> {
                return horaConverter.toResponseDTO(item);
            }).toList();

            return new ResponseEntity<>(listDTO, HttpStatus.OK);

        }catch(AcessoNegadoException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);

        }catch(EntityDontExistException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/hora/bystatus")
    @Operation(summary = "Lista horas por status", description = "Retorna lista de horas com status selecionado")
    @ApiResponse(responseCode = "200", description = "Retorna horas do dia")
    @ApiResponse(responseCode = "404", description = "Dia não encontrado")
    @ApiResponse(responseCode = "403", description = "Usuário sem permissão para esta operação")
    public ResponseEntity<?> horaByStatus(
            @Parameter(description = "ID do status", required = true)
            @RequestParam long id, HttpServletRequest request,
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable
    ){
        try{
            var usuarioLogado = tokenService.getUserLogado(request, usuarioService);
            var status = statusHoraList.list(id, usuarioLogado);
            var listHoras = horaList.list(status, pageable).getContent();
            List<HoraResposeDTO> listDTO = listHoras.stream().map(item -> {
                return horaConverter.toResponseDTO(item);
            }).collect(Collectors.toList());
            return new ResponseEntity<>(new PageImpl<>(listDTO), HttpStatus.OK);

        }catch(AcessoNegadoException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);

        }catch(EntityDontExistException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


//============================================= FIM HORA ==============================================

    //================================================ STATUS AGENDA ========================================
    //Cadastro de Status Agenda
    @PostMapping("/statusagenda/create")
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
    @DeleteMapping("/statusagenda/delete/{id}")
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

    //Lista Status da Agenda por Empresa;
    @GetMapping("/statusagenda/list")
    @Operation(summary = "Lista Status da Agenda", description = "Lista Status das Agendas por Empresa")
    @ApiResponse(responseCode = "200", description = "Lista status com sucesso")
    @ApiResponse(responseCode = "403", description = "Usuário sem permissão para esta operação")
    public ResponseEntity<Page<StatusAgendaResponseDTO>> statusAgendaByEmpresa(HttpServletRequest request, @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable){

        var usuarioLogado = tokenService.getUserLogado(request, usuarioService);

        Page<StatusAgendaResponseDTO> listDTO = statusAgendaList.list(usuarioLogado.getEmpresa(), pageable)
                .map(statusAgenda -> statusAgendaConverter.toResponseDTO(statusAgenda));

        return new ResponseEntity<>(listDTO, HttpStatus.OK);
    }


    //Lista Status da Agenda por ID
    @GetMapping("/statusagenda/byid")
        @Operation(summary = "Lista Status da Agenda", description = "Lista Status das Agendas por ID da Agenda")
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
    // =========================================================== FIM STATUS AGENDA ==========================================



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
//        List<StatusHora> statusHoras = statusHoraList.list(usuarioLogado.getEmpresa(), pageable).getContent();
//
//        List<StatusHoraResponseDTO> listDTO = statusHoras.stream().map(status -> {
//            StatusHoraResponseDTO dto = statusHoraConverter.toResponseDTO(status);
//            return dto;
//        }).collect(Collectors.toList());

        Page<StatusHoraResponseDTO> listDTO = statusHoraList.list(usuarioLogado.getEmpresa(), pageable)
                .map(statusHora -> statusHoraConverter.toResponseDTO(statusHora));

        return new ResponseEntity<>(listDTO, HttpStatus.OK);

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

    //=========================== FIM STATUS HORA ============================================

    //================================ STATUS DIA ===========================================================

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

        Page<StatusDiaResponseDTO> listDTO = statusDiaList.list(usuarioLogado.getEmpresa(), pageable)
                .map(statusDia -> statusDiaConverter.toResponseDTO(statusDia));

        return new ResponseEntity<>(listDTO, HttpStatus.OK);

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

    //================================ FIM STATUS DIA ===========================================================

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
//                for (Hora horas : dia.getHoras()) {
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
//            List<Hora> horasNovas = new ArrayList<>();
//
//            //Percore o dia Origem e para cada hora, cria uma nova hora para o alvo com o mesmo inicio e fim;
//            diaOrigem.get().getHoras().forEach(hora -> {
//                Hora horasAlvo = new Hora();
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

        Page<ProfissionalResponseDTO> listDTO = profissionalList.list(usuarioLogado.getEmpresa(), pageable)
                .map(profissional -> profissionalConverter.toResponseDTO(profissional));

        return new ResponseEntity<>(listDTO, HttpStatus.OK);

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

    //=============================================== FIM PROFISSIONAL =======================================================

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

//                List<Feriado> feriados = feriadoList.list(usuarioLogado.getEmpresa(), pageable).getContent();
//                List<FeriadoResponseDTO> listDTO = feriados.stream().map(feriado -> {
//                    FeriadoResponseDTO dto = feriadoConverter.toResponseDTO(feriado);
//                    return dto;
//
//                }).collect(Collectors.toList());

        Page<FeriadoResponseDTO> listDTO = feriadoList.list(usuarioLogado.getEmpresa(), pageable)
                .map(feriado -> feriadoConverter.toResponseDTO(feriado));

                return new ResponseEntity<>(listDTO, HttpStatus.OK);
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

    // ========================================== FIM FERIADOS =============================================================


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

//            List<DiaBloqueado> diaBloqueados = diaBloqueadoList.list(usuarioLogado.getEmpresa(), pageable).getContent();
//
//            List<DiaBloqueadoResponseDTO> listDTO = diaBloqueados.stream().map(item -> {
//                DiaBloqueadoResponseDTO dto = diaBloqueadoConverter.toResponseDTO(item);
//                return dto;
//
//            }).collect(Collectors.toList());

        Page<DiaBloqueadoResponseDTO> listDTO = diaBloqueadoList.list(usuarioLogado.getEmpresa(), pageable)
                .map(diaBloqueado -> diaBloqueadoConverter.toResponseDTO(diaBloqueado));

            return new ResponseEntity<>(listDTO, HttpStatus.OK);
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

    // ================================= FIM DIAS BLOQUEADOS ==============================================

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

    // ================================= FIM PLANO PACIENTE ==================================

}
