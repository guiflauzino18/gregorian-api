package com.gregoryan.api.Controllers;

import java.util.Calendar;
import java.util.Locale;
import java.util.Optional;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gregoryan.api.DTO.AgendamentoCreateDTO;
import com.gregoryan.api.DTO.AgendamentoEditDTO;
import com.gregoryan.api.Exception.AcessoNegadoException;
import com.gregoryan.api.Exception.EntityDontExistException;
import com.gregoryan.api.Models.Agendamento;
import com.gregoryan.api.Models.Empresa;
import com.gregoryan.api.Models.Paciente;
import com.gregoryan.api.Models.Profissional;
import com.gregoryan.api.Models.StatusAgendamento;
import com.gregoryan.api.Models.Usuario;
import com.gregoryan.api.Services.AgendamentoCreateService;
import com.gregoryan.api.Services.AgendamentoDeleteService;
import com.gregoryan.api.Services.AgendamentoEditService;
import com.gregoryan.api.Services.Crud.AgendamentoService;
import com.gregoryan.api.Services.Crud.PacienteService;
import com.gregoryan.api.Services.Crud.ProfissionalService;
import com.gregoryan.api.Services.Crud.StatusAgendamentoService;
import com.gregoryan.api.Services.Crud.UsuarioService;
import com.gregoryan.api.Services.Security.TokenService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/agendamento")
public class AgendamentoController {
    
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private AgendamentoCreateService agendamentoCreate;
    @Autowired
    private AgendamentoDeleteService agendamentoDelete;
    @Autowired
    private AgendamentoEditService agendamentoEdit;

    @PostMapping("/create")
    public ResponseEntity<Object> agendamentoCreate(@RequestBody @Valid AgendamentoCreateDTO agendamentoDTO, HttpServletRequest request){
        
        try{
            var empresa = tokenService.getEmpresaFromToken(request, usuarioService);
            agendamentoCreate.create(agendamentoDTO, empresa);
            return new ResponseEntity<>("Agendamento cadastrado", HttpStatus.CREATED);

        }catch(AcessoNegadoException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);

        }catch(EntityDontExistException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        
        // Agendamento agendamento = new Agendamento();

        // Optional<Profissional> profissional = profissionalService.findById(agendamentoDTO.profissional());
        // if (profissional.isPresent()) agendamento.setProfissional(profissional.get());
        //     else return new ResponseEntity<>("Profissional não encontrado!", HttpStatus.NOT_FOUND);

        // Optional<Paciente> paciente = pacienteService.findById(agendamentoDTO.paciente());
        // if (paciente.isPresent()) agendamento.setPaciente(paciente.get());
        //     else return new ResponseEntity<>("Paciente não encontrado!", HttpStatus.NOT_FOUND);
        
        // Optional<StatusAgendamento> statusAgendamento = statusAgendamentoService.findById(agendamentoDTO.statusAgendamento());
        // if (statusAgendamento.isPresent()) agendamento.setStatusAgendamento(statusAgendamento.get());
        //     else return new ResponseEntity<>("Status de Agendamento não encontrado", HttpStatus.NOT_FOUND);

        // Usuario usuario = usuarioService.findByLogin(tokenService.validateToken(tokenService.recoverToken(request))).get();
        // agendamento.setEmpresa(usuario.getEmpresa());

        // Calendar dataRegistro = Calendar.getInstance(TimeZone.getTimeZone("GMT-3:00"), new Locale("pt-BR"));
        // agendamento.setDataRegistro(dataRegistro);

        // Calendar data = Calendar.getInstance(TimeZone.getTimeZone("GMT-3:00"), new Locale("pt-BR"));
        // int ano = Integer.parseInt(agendamentoDTO.data().split("-")[0]);
        // int mes = Integer.parseInt(agendamentoDTO.data().split("-")[1]);
        // int dia = Integer.parseInt(agendamentoDTO.data().split("-")[2]);

        // int hora = Integer.parseInt(agendamentoDTO.hora().split(":")[0]);
        // int min = Integer.parseInt(agendamentoDTO.hora().split(":")[1]);

        // data.set(ano, mes, dia, hora, min);
        // agendamento.setData(data);

        // return new ResponseEntity<>(agendamentoService.save(agendamento), HttpStatus.CREATED);
    }

    @PutMapping("/agendamento/edit")
    public ResponseEntity<Object> agendamentoEdit(@RequestBody @Valid AgendamentoEditDTO agendamentoDTO, HttpServletRequest request){
        
        try{
            var empresa = tokenService.getEmpresaFromToken(request, usuarioService);
            agendamentoEdit.edit(agendamentoDTO, empresa);
            return new ResponseEntity<>("Agendamento atualizado", HttpStatus.OK);

        }catch(EntityDontExistException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);

        }catch(AcessoNegadoException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);

        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);

        }
        // Optional<Agendamento> agendamento = agendamentoService.findById(agendamentoDTO.id());
        // if (agendamento.isPresent()){
        //     Optional<Profissional> profissional = profissionalService.findById(agendamentoDTO.idProfissional());
        //     if (profissional.isPresent()) agendamento.get().setProfissional(profissional.get());
        //         else return new ResponseEntity<>("Profissional não encontrado!", HttpStatus.NOT_FOUND);

        //     Optional<Paciente> paciente = pacienteService.findById(agendamentoDTO.idPaciente());
        //     if (paciente.isPresent()) agendamento.get().setPaciente(paciente.get());
        //         else return new ResponseEntity<>("Paciente não encontrado!", HttpStatus.NOT_FOUND);

        //     Optional<StatusAgendamento> statusAgendamento = statusAgendamentoService.findById(agendamentoDTO.idStatus());
        //     if (statusAgendamento.isPresent()) agendamento.get().setStatusAgendamento(statusAgendamento.get());
        //         else return new ResponseEntity<>("Status de Agendamento não encontrado!", HttpStatus.NOT_FOUND);

        //     Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT-3:00"), new Locale("pt-BR"));
        //     int ano = Integer.parseInt(agendamentoDTO.data().split("-")[0]);
        //     int mes = Integer.parseInt(agendamentoDTO.data().split("-")[1]);
        //     int dia = Integer.parseInt(agendamentoDTO.data().split("-")[2]);
        //     int hora = Integer.parseInt(agendamentoDTO.hora().split(":")[0]);
        //     int minuto = Integer.parseInt(agendamentoDTO.hora().split(":")[1]);

        //     calendar.set(ano, mes, dia, hora, minuto);
        //     agendamento.get().setData(calendar);

        //     return new ResponseEntity<>(agendamentoService.save(agendamento.get()), HttpStatus.OK);

        // } else return new ResponseEntity<>("Agendamento não encontrado!", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> agendamentoDelete(@PathVariable(name = "id") long id, HttpServletRequest request){
        try{
            var empresa = tokenService.getEmpresaFromToken(request, usuarioService);
            agendamentoDelete.delete(id, empresa);
            return new ResponseEntity<>("Agendamento Excluído", HttpStatus.OK);

        }catch(EntityDontExistException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);

        }catch(AcessoNegadoException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);

        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);

        }

        // Optional<Agendamento> agendamento = agendamentoService.findById(id);
        // if (agendamento.isPresent()) {
        //     agendamentoService.delete(agendamento.get());   
        //     return new ResponseEntity<>("Agendamento deletado do Sistema!", HttpStatus.OK);

        // } else return new ResponseEntity<>("Agendamento não encontrado!", HttpStatus.NOT_FOUND);
        
    }

}
