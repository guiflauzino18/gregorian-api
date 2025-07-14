package com.gregoryan.api.Controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.gregoryan.api.Models.Empresa;
import com.gregoryan.api.DTO.EnderecoEditDTO;
import com.gregoryan.api.DTO.PacienteEditDTO;
import com.gregoryan.api.DTO.PacienteCadastroDTO;
import com.gregoryan.api.Exception.AcessoNegadoException;
import com.gregoryan.api.Exception.ConflictException;
import com.gregoryan.api.Exception.EntityDontExistException;
import com.gregoryan.api.Services.EnderecoDeleteService;
import com.gregoryan.api.Services.EnderecoEditService;
import com.gregoryan.api.Services.PacienteCreateService;
import com.gregoryan.api.Services.PacienteDeleteService;
import com.gregoryan.api.Services.PacienteEditService;
import com.gregoryan.api.Services.Crud.UsuarioService;
import com.gregoryan.api.Interfaces.PacienteListInterface;
import com.gregoryan.api.Services.Security.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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


@RestController
@RequestMapping("/api/atendimento")
public class AtendimentoController {

    @Autowired
    private TokenService tokenService;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private PacienteListInterface pacienteList;
    @Autowired
    private PacienteCreateService pacienteCreate;
    @Autowired
    private PacienteEditService pacienteEdit;
    @Autowired
    private PacienteDeleteService pacienteDelete;
    @Autowired
    private EnderecoEditService enderecoEdit;
    @Autowired
    private EnderecoDeleteService enderecoDelete;
    
    
    //================================== PACIENTE =======================================

    @GetMapping("/paciente/byusuarioLogado")
    public ResponseEntity<?> pacienteByEmpresa(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable, HttpServletRequest request){

        try {
            Empresa usuarioLogado = tokenService.getEmpresaFromToken(request, usuarioService);
            var pacientes = pacienteList.list(usuarioLogado, pageable);

            return new ResponseEntity<>(pacientes, HttpStatus.OK);

        }catch(EntityDontExistException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);

        }catch(AcessoNegadoException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }

    }

    @PostMapping("/paciente/create")
    public ResponseEntity<?> pacienteCreate(@RequestBody @Valid PacienteCadastroDTO pacienteDTO, HttpServletRequest request){

        try {
            var usuarioLogado = tokenService.getUserLogado(request, usuarioService);
            pacienteCreate.create(pacienteDTO, usuarioLogado);
            return new ResponseEntity<>("Paciente cadastrado", HttpStatus.CREATED);

        }catch(ConflictException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);

        }catch(AcessoNegadoException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);

        }catch(EntityDontExistException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

        // if(pacienteService.existsByCpf(pacienteDTO.cpf())) return new ResponseEntity<>("Conflito: CPF já existe!", HttpStatus.CONFLICT);

        // var paciente = new Paciente();
        // BeanUtils.copyProperties(pacienteDTO, paciente);

        // Usuario logado = usuarioService.findByLogin(tokenService.validateToken(tokenService.recoverToken(request))).get();
        // paciente.setEmpresa(logado.getEmpresa());
        
        // Calendar now = Calendar.getInstance(TimeZone.getTimeZone("GMT-3:00"), new Locale("pt-BR"));
        // paciente.setDataRegistro(now);

        // Optional<PlanoPaciente> plano = planoPacienteService.findById(pacienteDTO.idPlanoPaciente());
        // if (plano.isPresent()) paciente.setPlanoPaciente(plano.get());
        //     else return new ResponseEntity<>("Plano de Paciente não Encontrado!", HttpStatus.NOT_FOUND);

        // return new ResponseEntity<>(pacienteService.save(paciente), HttpStatus.CREATED);

    }

    @PutMapping("/paciente/edit")
    public ResponseEntity<Object> pacienteEdit(@RequestBody @Valid PacienteEditDTO pacienteDTO, HttpServletRequest request){

        try {

            var usuarioLogado = tokenService.getUserLogado(request, usuarioService);
            pacienteEdit.edit(pacienteDTO, usuarioLogado);
            return new ResponseEntity<>("Paciente editado com sucesso", HttpStatus.OK);

        }catch(ConflictException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);

        }catch(EntityDontExistException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);

        }catch(AcessoNegadoException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
        // Optional<Paciente> paciente = pacienteService.findById(pacienteDTO.id());
        // if (paciente.isPresent()){
        //     Optional<PlanoPaciente> planoPaciente = planoPacienteService.findById(pacienteDTO.idPlanoPaciente());
        //     if (planoPaciente.isPresent()) paciente.get().setPlanoPaciente(planoPaciente.get());
        //         else return new ResponseEntity<>("Plano do Paciente não encontrado!",HttpStatus.NOT_FOUND);

        //     BeanUtils.copyProperties(pacienteDTO, paciente.get());
        //     return new ResponseEntity<>(pacienteService.save(paciente.get()), HttpStatus.OK);
        // }

        // return new ResponseEntity<>("Paciente não encontrado!", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/paciente/delete/{id}")
    public ResponseEntity<String> pacienteDelete(@PathVariable(name = "id") long id, HttpServletRequest request){

        try{
            var usuarioLogado = tokenService.getUserLogado(request, usuarioService);
            pacienteDelete.delete(id, usuarioLogado);
            return new ResponseEntity<>("Paciente excluído",HttpStatus.OK);

        }catch(AcessoNegadoException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);

        }catch(EntityDontExistException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

        // Optional<Paciente> paciente = pacienteService.findById(id);
        // if (paciente.isPresent()) {
        //     pacienteService.delete(paciente.get());
        //     return new ResponseEntity<>("Paciente deletado do Sistema!", HttpStatus.OK);
        // } 

        // return new ResponseEntity<>("Paciente não encontrado!", HttpStatus.NOT_FOUND);

    }


    // ================================= ENDEREÇO =============================================

    @PutMapping("/endereco/edit")
    public ResponseEntity<Object> enderecoEdit(@RequestBody @Valid EnderecoEditDTO enderecoDTO){
        
        try{
            enderecoEdit.edit(enderecoDTO);
            return new ResponseEntity<>("Endereço editado", HttpStatus.OK);

        }catch(EntityDontExistException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);

        }

        // Optional<Endereco> endereco = enderecoService.findById(enderecoDTO.id());
        // if (endereco.isPresent()){
        //     BeanUtils.copyProperties(enderecoDTO, endereco.get());
        //     return new ResponseEntity<>(enderecoService.save(endereco.get()), HttpStatus.OK);
        // }

        // return new ResponseEntity<>("Endereço não Encontrado!", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/endereco/delete/{id}")
    public ResponseEntity<String> enderecoDelete(@PathVariable(name = "id") long id){

        try{
            enderecoDelete.delete(id);
            return new ResponseEntity<>("Endereço excluído", HttpStatus.OK);

        }catch(EntityDontExistException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);

        }
        // Optional<Endereco> endereco = enderecoService.findById(id);
        // if (endereco.isPresent()){
        //     enderecoService.delete(endereco.get());
        //     return new ResponseEntity<>("Endereço Deletado do Sistema!", HttpStatus.OK);
        // }

        // return new ResponseEntity<>("Endereço não Encontrado!", HttpStatus.NOT_FOUND);
    }

   
    
}
