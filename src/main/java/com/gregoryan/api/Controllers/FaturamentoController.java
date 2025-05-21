package com.gregoryan.api.Controllers;

import java.util.Optional;
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
import org.springframework.web.bind.annotation.RestController;
import com.gregoryan.api.DTO.FaturamentoCadastroDTO;
import com.gregoryan.api.DTO.FaturamentoEditDTO;
import com.gregoryan.api.DTO.FormaPagamentoCadastroDTO;
import com.gregoryan.api.DTO.FormaPagamentoEditDTO;
import com.gregoryan.api.Models.Faturamento;
import com.gregoryan.api.Models.FormaPagamento;
import com.gregoryan.api.Models.Paciente;
import com.gregoryan.api.Models.Usuario;
import com.gregoryan.api.Services.Crud.FaturamentoService;
import com.gregoryan.api.Services.Crud.FormaPagamentoService;
import com.gregoryan.api.Services.Crud.PacienteService;
import com.gregoryan.api.Services.Crud.UsuarioService;
import com.gregoryan.api.Services.Security.TokenService;
import java.lang.Object;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/faturamento")
public class FaturamentoController {

    @Autowired
    private FaturamentoService faturamentoService;
    @Autowired
    private PacienteService pacienteService;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private FormaPagamentoService formaPagamentoService;
    @Autowired
    private TokenService tokenService;

    // =========================================== FATURAMENTO =======================================

    @PostMapping("/cadastro")
    public ResponseEntity<Object> faturamentoCadastro(@RequestBody @Valid FaturamentoCadastroDTO faturamentoDTO, HttpServletRequest request){
        Faturamento faturamento = new Faturamento();
        BeanUtils.copyProperties(faturamentoDTO, faturamento);

        //set paciente
        Optional<Paciente> paciente = pacienteService.findById(faturamentoDTO.idPaciente());
        if (paciente.isPresent()) faturamento.setPaciente(paciente.get());
            else return new ResponseEntity<>("Paciente não encontrado!", HttpStatus.NOT_FOUND);

        Optional<FormaPagamento> formaPagamento = formaPagamentoService.findById(faturamentoDTO.idFormaPagamento());
        if (formaPagamento.isPresent()) faturamento.setFormaPagamento(formaPagamento.get());
            else return new ResponseEntity<>("Forma de Pagamento não encontrado!", HttpStatus.NOT_FOUND);

        Calendar data = Calendar.getInstance(TimeZone.getTimeZone("GMT-3:00"), new Locale("pt-BR"));
        faturamento.setData(data);

        Usuario usuario = usuarioService.findByLogin(tokenService.validateToken(tokenService.recoverToken(request))).get();
        faturamento.setEmpresa(usuario.getEmpresa());

        faturamento.setStatus(Faturamento.StatusFaturamento.GERADO);

        return new ResponseEntity<>(faturamentoService.save(faturamento), HttpStatus.CREATED);
    }

    @PutMapping("/edit")
    public ResponseEntity<Object> faturamentoEdit(@RequestBody @Valid FaturamentoEditDTO faturamentoDTO){
        Optional<Faturamento> faturamento = faturamentoService.findById(faturamentoDTO.id());

        if (faturamento.isPresent()){
            BeanUtils.copyProperties(faturamentoDTO, faturamento);

            Optional<Paciente> paciente = pacienteService.findById(faturamentoDTO.idPaciente());
            if (paciente.isPresent()) faturamento.get().setPaciente(paciente.get());
                else return new ResponseEntity<>("Paciente não encontrado!", HttpStatus.OK);

            Optional<FormaPagamento> formaPagamento = formaPagamentoService.findById(faturamentoDTO.idFormaPagamento());
            if (formaPagamento.isPresent()) faturamento.get().setFormaPagamento(formaPagamento.get());
                else return new ResponseEntity<>("Forma de Pagamento não encontrado!", HttpStatus.OK);

            return new ResponseEntity<>(formaPagamentoService.save(formaPagamento.get()), HttpStatus.OK);
        } else return new ResponseEntity<>("Faturamento não encontrado!", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> faturamentoDelete(@PathVariable(name = "id") long id){
        Optional<Faturamento> faturamento = faturamentoService.findById(id);

        if (faturamento.isPresent()){
            faturamentoService.delete(faturamento.get());
            return new ResponseEntity<>("Faturamento deletado do sistema!", HttpStatus.OK);
        } else return new ResponseEntity<>("Faturamento não encontrado!", HttpStatus.NOT_FOUND);
    }

    

    // =========================================== FORMA DE PAGAMENTO ================================
    @PostMapping("/formapagamento/cadastro")
    public ResponseEntity<Object> formaPagamentoCadastro(@RequestBody @Valid FormaPagamentoCadastroDTO formaPagamentoDTO, HttpServletRequest request){
        if (formaPagamentoService.existsByNome(formaPagamentoDTO.nome())) {
            return new ResponseEntity<>("Conflito: Forma de Pagamento já existe!", HttpStatus.CONFLICT);
        }

        FormaPagamento formaPagamento = new FormaPagamento();
        BeanUtils.copyProperties(formaPagamentoDTO, formaPagamento);

        Usuario usuario = usuarioService.findByLogin(tokenService.validateToken(tokenService.recoverToken(request))).get();
        formaPagamento.setEmpresa(usuario.getEmpresa());

        return new ResponseEntity<>(formaPagamentoService.save(formaPagamento), HttpStatus.CREATED);
    }


    @PutMapping("/formapagamento/edit")
    public ResponseEntity<Object> formaPagamentoEdit(@RequestBody @Valid FormaPagamentoEditDTO formaPagamentoDTO){
        Optional<FormaPagamento> formaPagamento = formaPagamentoService.findById(formaPagamentoDTO.id());

        if (formaPagamento.isPresent()){
            BeanUtils.copyProperties(formaPagamentoDTO, formaPagamento);

            return new ResponseEntity<>(formaPagamentoService.save(formaPagamento.get()), HttpStatus.OK);
        } else return new ResponseEntity<>("Forma de Pagamento não encontrado!", HttpStatus.NOT_FOUND);

    }

    @DeleteMapping("/formapagamento/delete/{id}")
    public ResponseEntity<String> formaPagamentoDelete(@PathVariable(name = "id") long id){
        Optional<FormaPagamento> formaPagamento = formaPagamentoService.findById(id);

        if (formaPagamento.isPresent()){
            formaPagamentoService.delete(formaPagamento.get());
            return new ResponseEntity<>("Forma de Pagamento deletado do Sistema!", HttpStatus.OK);

        } else return new ResponseEntity<>("Forma de Pagamento não encontrado!", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/formapagamento/list")
    public ResponseEntity<Page<FormaPagamento>> formaPagamentoListByEmpresa(
        @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
        HttpServletRequest request){
            Usuario usuario = usuarioService.findByLogin(tokenService.validateToken(tokenService.recoverToken(request))).get();
            return new ResponseEntity<>(formaPagamentoService.findByEmpresa(usuario.getEmpresa(), pageable), HttpStatus.OK);
        }

}
