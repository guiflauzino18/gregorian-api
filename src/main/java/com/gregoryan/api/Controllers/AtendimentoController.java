package com.gregoryan.api.Controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gregoryan.api.Models.Endereco;
import com.gregoryan.api.Models.Paciente;
import com.gregoryan.api.Models.PlanoPaciente;
import com.gregoryan.api.DTO.EnderecoEditDTO;
import com.gregoryan.api.DTO.PacienteEditDTO;
import com.gregoryan.api.DTO.PacienteSaveDTO;
import com.gregoryan.api.Models.Usuario;
import com.gregoryan.api.Services.Crud.EnderecoService;
import com.gregoryan.api.Services.Crud.PacienteService;
import com.gregoryan.api.Services.Crud.PlanoPacienteService;
import com.gregoryan.api.Services.Crud.UsuarioService;
import com.gregoryan.api.Services.Security.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
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
    private PacienteService pacienteService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private PlanoPacienteService planoPacienteService;
    @Autowired
    private EnderecoService enderecoService;
    
    
    //================================== PACIENTE =======================================

    @GetMapping("/paciente/listall")
    public ResponseEntity<Page<Paciente>> findAll(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable){
        return new ResponseEntity<>(pacienteService.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping("/paciente/list")
    public ResponseEntity<Page<Paciente>> pacienteListByEmpresa(
        @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
        HttpServletRequest request){

            Usuario usuario = usuarioService.findByLogin(tokenService.validateToken(tokenService.recoverToken(request))).get();
            return new ResponseEntity<>(pacienteService.findByEmpresa(usuario.getEmpresa(), pageable), HttpStatus.OK);
    }

    @PostMapping("/paciente/cadastro")
    public ResponseEntity<Object> pacienteCadastro(@RequestBody @Valid PacienteSaveDTO pacienteDTO, HttpServletRequest request){
        if(pacienteService.existsByCpf(pacienteDTO.cpf())) return new ResponseEntity<>("Conflito: CPF já existe!", HttpStatus.CONFLICT);

        var paciente = new Paciente();
        BeanUtils.copyProperties(pacienteDTO, paciente);

        Usuario logado = usuarioService.findByLogin(tokenService.validateToken(tokenService.recoverToken(request))).get();
        paciente.setEmpresa(logado.getEmpresa());
        
        Calendar now = Calendar.getInstance(TimeZone.getTimeZone("GMT-3:00"), new Locale("pt-BR"));
        paciente.setDataRegistro(now);

        Optional<PlanoPaciente> plano = planoPacienteService.findById(pacienteDTO.idPlanoPaciente());
        if (plano.isPresent()) paciente.setPlanoPaciente(plano.get());
            else return new ResponseEntity<>("Plano de Paciente não Encontrado!", HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(pacienteService.save(paciente), HttpStatus.CREATED);

    }

    @PutMapping("/paciente/edit")
    public ResponseEntity<Object> pacienteEdit(@RequestBody @Valid PacienteEditDTO pacienteDTO){
        Optional<Paciente> paciente = pacienteService.findById(pacienteDTO.id());
        if (paciente.isPresent()){
            Optional<PlanoPaciente> planoPaciente = planoPacienteService.findById(pacienteDTO.idPlanoPaciente());
            if (planoPaciente.isPresent()) paciente.get().setPlanoPaciente(planoPaciente.get());
                else return new ResponseEntity<>("Plano do Paciente não encontrado!",HttpStatus.NOT_FOUND);

            BeanUtils.copyProperties(pacienteDTO, paciente.get());
            return new ResponseEntity<>(pacienteService.save(paciente.get()), HttpStatus.OK);
        }

        return new ResponseEntity<>("Paciente não encontrado!", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/paciente/delete/{id}")
    public ResponseEntity<String> pacienteDelete(@PathVariable(name = "id") long id){
        Optional<Paciente> paciente = pacienteService.findById(id);
        if (paciente.isPresent()) {
            pacienteService.delete(paciente.get());
            return new ResponseEntity<>("Paciente deletado do Sistema!", HttpStatus.OK);
        } 

        return new ResponseEntity<>("Paciente não encontrado!", HttpStatus.NOT_FOUND);

    }


    // ================================= ENDEREÇO =============================================

    @PutMapping("/endereco/edit")
    public ResponseEntity<Object> enderecoEdit(@RequestBody @Valid EnderecoEditDTO enderecoDTO){
        Optional<Endereco> endereco = enderecoService.findById(enderecoDTO.id());
        if (endereco.isPresent()){
            BeanUtils.copyProperties(enderecoDTO, endereco.get());
            return new ResponseEntity<>(enderecoService.save(endereco.get()), HttpStatus.OK);
        }

        return new ResponseEntity<>("Endereço não Encontrado!", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/endereco/delete/{id}")
    public ResponseEntity<String> enderecoDelete(@PathVariable(name = "id") long id){
        Optional<Endereco> endereco = enderecoService.findById(id);
        if (endereco.isPresent()){
            enderecoService.delete(endereco.get());
            return new ResponseEntity<>("Endereço Deletado do Sistema!", HttpStatus.OK);
        }

        return new ResponseEntity<>("Endereço não Encontrado!", HttpStatus.NOT_FOUND);
    }


    
    
}
