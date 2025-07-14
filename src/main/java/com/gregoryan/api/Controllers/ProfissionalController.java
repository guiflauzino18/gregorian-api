package com.gregoryan.api.Controllers;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.gregoryan.api.DTO.AtendimentoCadastroDTO;
import com.gregoryan.api.Models.Agendamento;
import com.gregoryan.api.Models.Atendimento;
import com.gregoryan.api.Models.Profissional;
import com.gregoryan.api.Models.StatusAtendimento;
import com.gregoryan.api.Models.Usuario;
import com.gregoryan.api.Services.Crud.AgendamentoService;
import com.gregoryan.api.Services.Crud.AtendimentoService;
import com.gregoryan.api.Services.Crud.ProfissionalService;
import com.gregoryan.api.Services.Crud.UsuarioService;
import com.gregoryan.api.Services.Crud.StatusAtendimentoService;
import com.gregoryan.api.Services.Security.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;
import java.lang.Object;
import java.time.LocalTime;

@RestController
@RequestMapping("/api/profissional")
public class ProfissionalController {
    
    @Autowired
    private TokenService TokenService;
    @Autowired
    private ProfissionalService profissionalService;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private AgendamentoService agendamentoService;
    @Autowired
    private StatusAtendimentoService StatusAtendimentoService;
    @Autowired
    private AtendimentoService atendimentoService;

    //Acessar agenda do Profissional
    @GetMapping("/agendamentos")
    public ResponseEntity<Object> profissionalAgenda(
        @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
        HttpServletRequest request){

        Usuario Usuario = usuarioService.findByLogin(TokenService.validateToken(TokenService.recoverToken(request))).get();
        Optional<Profissional> profissional = profissionalService.findByUsuario(Usuario);
        if (!profissional.isPresent()) return new ResponseEntity<>("Profissional não encontrado!", HttpStatus.NOT_FOUND);

        Page<Agendamento> agendamentos = agendamentoService.findByProfissional(profissional.get(), pageable);
        Calendar hoje = Calendar.getInstance(TimeZone.getTimeZone("GMT-3:00"), new Locale("pt-BR"));
        Page<Agendamento> agendamentosHoje = (Page<Agendamento>) agendamentos.filter(item -> item.getData().equals(hoje));

        return new ResponseEntity<>(agendamentosHoje, HttpStatus.OK);
    }

    @PostMapping("/atendimento")
    public ResponseEntity<Object> profissionalAtendimento(@RequestBody @Valid AtendimentoCadastroDTO atendimentoDTO){

        Atendimento atendimento = new Atendimento();

        Optional<Agendamento> agendamento = agendamentoService.findById(atendimentoDTO.idAgendamento());
        if (agendamento.isPresent()) atendimento.setAgendamento(agendamento.get());
            else return new ResponseEntity<>("Agendamento não Encontrado!", HttpStatus.NOT_FOUND);

        Optional<StatusAtendimento> status = StatusAtendimentoService.findById(atendimentoDTO.idStatusAtendimento());
        if (status.isPresent()) atendimento.setStatusAtendimento(status.get());
            else return new ResponseEntity<>("Status de Atendimento não Encontrado!", HttpStatus.NOT_FOUND);

        atendimento.setHorainicio(LocalTime.now());

        return new ResponseEntity<>(atendimentoService.save(atendimento), HttpStatus.OK);
        
    }

    @PutMapping("/atendimento/horaatual")
    public ResponseEntity<Object> atendimentoHoraAtual(long idAtendimento){
        Optional<Atendimento> atendimento = atendimentoService.findById(idAtendimento);
        if (atendimento.isPresent()) {
            atendimento.get().setHoraatual(LocalTime.now());
            return new ResponseEntity<>(atendimentoService.save(atendimento.get()), HttpStatus.OK);
        }

        return new ResponseEntity<>("Atendimento não Encontrado!", HttpStatus.NOT_FOUND);
    }

    @PutMapping("/atendimento/finalizar")
    public ResponseEntity<Object> atendimentoFinalizar(long idAtendimento, String laudo){
        Optional<Atendimento> atendimento = atendimentoService.findById(idAtendimento);
        if (atendimento.isPresent()) {
            atendimento.get().setHorafim(LocalTime.now());
            atendimento.get().setLaudo(laudo);
            return new ResponseEntity<>(atendimentoService.save(atendimento.get()), HttpStatus.OK);
        }

        return new ResponseEntity<>("Atendimento não Encontrado!", HttpStatus.NOT_FOUND);
    }

    @PutMapping("/atendimento/salvarlaudo")
    public ResponseEntity<Object> atendimentoSalvarLaudo(long idAtendimento, String laudo){
        Optional<Atendimento> atendimento = atendimentoService.findById(idAtendimento);
        if (atendimento.isPresent()) {
            atendimento.get().setHoraatual(LocalTime.now());
            atendimento.get().setLaudo(laudo);
            return new ResponseEntity<>(atendimentoService.save(atendimento.get()), HttpStatus.OK);
        }

        return new ResponseEntity<>("Atendimento não Encontrado!", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/atendimento/temporestante")
    public ResponseEntity<Object> atendimentoTempoRestante(long idAtendimento){
        Optional<Atendimento> atendimento = atendimentoService.findById(idAtendimento);
        if (atendimento.isPresent()) {
            return new ResponseEntity<>(atendimento.get().tempoSessaoRestanteInminutes(), HttpStatus.OK);
        }

        return new ResponseEntity<>("Atendimento não Encontrado!", HttpStatus.NOT_FOUND);
    }
}
