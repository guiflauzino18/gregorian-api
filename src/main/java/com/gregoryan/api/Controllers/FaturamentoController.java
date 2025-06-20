package com.gregoryan.api.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RestController;
import com.gregoryan.api.DTO.FaturamentoCreateDTO;
import com.gregoryan.api.DTO.FaturamentoEditDTO;
import com.gregoryan.api.DTO.FormaPagamentoCreateDTO;
import com.gregoryan.api.DTO.FormaPagamentoEditDTO;
import com.gregoryan.api.DTO.FormaPagamentoResponseDTO;
import com.gregoryan.api.Exception.AcessoNegadoException;
import com.gregoryan.api.Exception.ConflictException;
import com.gregoryan.api.Exception.EntityDontExistException;
import com.gregoryan.api.Services.FaturamentoCreateService;
import com.gregoryan.api.Services.FaturamentoEditService;
import com.gregoryan.api.Services.FauramentoDeleteService;
import com.gregoryan.api.Services.FormaPagamentoCreateService;
import com.gregoryan.api.Services.FormaPagamentoDeleteService;
import com.gregoryan.api.Services.FormaPagamentoEditService;
import com.gregoryan.api.Services.Crud.UsuarioService;
import com.gregoryan.api.Interfaces.FormaPagamentoConverterInterface;
import com.gregoryan.api.Interfaces.FormaPagamentoListInterface;
import com.gregoryan.api.Services.Security.TokenService;

import java.util.List;
import java.util.stream.Collectors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/faturamento")
public class FaturamentoController {

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private FormaPagamentoCreateService formaPagamentoCreate;
    @Autowired
    private FormaPagamentoEditService formaPagamentoEdit;
    @Autowired
    private FormaPagamentoDeleteService formaPagamentoDelete;
    @Autowired
    private FormaPagamentoListInterface formaPagamentoList;
    @Autowired
    private FormaPagamentoConverterInterface formaPagamentoConverter;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private FaturamentoCreateService faturamentoCreate;
    @Autowired
    private FaturamentoEditService faturamentoEdit;
    @Autowired
    private FauramentoDeleteService fauramentoDelete;

    // =========================================== FATURAMENTO =======================================

    @PostMapping("/create")
    public ResponseEntity<?> faturamentoCreate(@RequestBody @Valid FaturamentoCreateDTO faturamentoDTO, HttpServletRequest request){
        
        try{
            var usuarioLogado = tokenService.getUserLogado(request, usuarioService);
            faturamentoCreate.create(faturamentoDTO, usuarioLogado);
            return new ResponseEntity<>("Faturamento criado", HttpStatus.CREATED);

        }catch(EntityDontExistException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);

        }catch(AcessoNegadoException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);

        }
        // Faturamento faturamento = new Faturamento();
        // BeanUtils.copyProperties(faturamentoDTO, faturamento);

        // //set paciente
        // Optional<Paciente> paciente = pacienteService.findById(faturamentoDTO.idPaciente());
        // if (paciente.isPresent()) faturamento.setPaciente(paciente.get());
        //     else return new ResponseEntity<>("Paciente não encontrado!", HttpStatus.NOT_FOUND);

        // Optional<FormaPagamento> formaPagamento = formaPagamentoService.findById(faturamentoDTO.idFormaPagamento());
        // if (formaPagamento.isPresent()) faturamento.setFormaPagamento(formaPagamento.get());
        //     else return new ResponseEntity<>("Forma de Pagamento não encontrado!", HttpStatus.NOT_FOUND);

        // Calendar data = Calendar.getInstance(TimeZone.getTimeZone("GMT-3:00"), new Locale("pt-BR"));
        // faturamento.setData(data);

        // Usuario usuario = usuarioService.findByLogin(tokenService.validateToken(tokenService.recoverToken(request))).get();
        // faturamento.setEmpresa(usuario.getEmpresa());

        // faturamento.setStatus(Faturamento.StatusFaturamento.GERADO);

        // return new ResponseEntity<>(faturamentoService.save(faturamento), HttpStatus.CREATED);
    }

    @PutMapping("/edit")
    public ResponseEntity<?> faturamentoEdit(@RequestBody @Valid FaturamentoEditDTO faturamentoDTO, HttpServletRequest request){
        try {
            var usuarioLogado = tokenService.getUserLogado(request, usuarioService);
            faturamentoEdit.edit(faturamentoDTO, usuarioLogado);
            return new ResponseEntity<>("Faturamento atualizado", HttpStatus.OK);

        }catch(EntityDontExistException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);

        }catch(AcessoNegadoException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);

        }
        // Optional<Faturamento> faturamento = faturamentoService.findById(faturamentoDTO.id());

        // if (faturamento.isPresent()){
        //     BeanUtils.copyProperties(faturamentoDTO, faturamento);

        //     Optional<Paciente> paciente = pacienteService.findById(faturamentoDTO.idPaciente());
        //     if (paciente.isPresent()) faturamento.get().setPaciente(paciente.get());
        //         else return new ResponseEntity<>("Paciente não encontrado!", HttpStatus.OK);

        //     Optional<FormaPagamento> formaPagamento = formaPagamentoService.findById(faturamentoDTO.idFormaPagamento());
        //     if (formaPagamento.isPresent()) faturamento.get().setFormaPagamento(formaPagamento.get());
        //         else return new ResponseEntity<>("Forma de Pagamento não encontrado!", HttpStatus.OK);

        //     return new ResponseEntity<>(formaPagamentoService.save(formaPagamento.get()), HttpStatus.OK);
        // } else return new ResponseEntity<>("Faturamento não encontrado!", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> faturamentoDelete(@PathVariable(name = "id") long id, HttpServletRequest request){
        try {
            var usuarioLogado = tokenService.getUserLogado(request, usuarioService);
            fauramentoDelete.delete(id, usuarioLogado);
            return new ResponseEntity<>("Fauramento excluído", HttpStatus.OK);

        }catch(EntityDontExistException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);

        }catch(AcessoNegadoException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);

        }
        // Optional<Faturamento> faturamento = faturamentoService.findById(id);

        // if (faturamento.isPresent()){
        //     faturamentoService.delete(faturamento.get());
        //     return new ResponseEntity<>("Faturamento deletado do sistema!", HttpStatus.OK);
        // } else return new ResponseEntity<>("Faturamento não encontrado!", HttpStatus.NOT_FOUND);
    }

    

    // =========================================== FORMA DE PAGAMENTO ================================
    @PostMapping("/formapagamento/create")
    public ResponseEntity<?> formaPagamentoCreate(@RequestBody @Valid FormaPagamentoCreateDTO formaPagamentoDTO, HttpServletRequest request){
        try {
            var usuarioLogado = tokenService.getUserLogado(request, usuarioService);
            formaPagamentoCreate.create(formaPagamentoDTO, usuarioLogado);
            return new ResponseEntity<>("Forma de Pagamento cadastrada", HttpStatus.CREATED);

        }catch(ConflictException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);

        }
        // if (formaPagamentoService.existsByNome(formaPagamentoDTO.nome())) {
        //     return new ResponseEntity<>("Conflito: Forma de Pagamento já existe!", HttpStatus.CONFLICT);
        // }

        // FormaPagamento formaPagamento = new FormaPagamento();
        // BeanUtils.copyProperties(formaPagamentoDTO, formaPagamento);

        // Usuario usuario = usuarioService.findByLogin(tokenService.validateToken(tokenService.recoverToken(request))).get();
        // formaPagamento.setEmpresa(usuario.getEmpresa());

        // return new ResponseEntity<>(formaPagamentoService.save(formaPagamento), HttpStatus.CREATED);
    }


    @PutMapping("/formapagamento/edit")
    public ResponseEntity<?> formaPagamentoEdit(@RequestBody @Valid FormaPagamentoEditDTO formaPagamentoDTO, HttpServletRequest request){
        try {
            var usuarioLogado = tokenService.getUserLogado(request, usuarioService);
            formaPagamentoEdit.edit(formaPagamentoDTO, usuarioLogado);
            return new ResponseEntity<>("Forma de pagamento atualizada", HttpStatus.OK);

        }catch(EntityDontExistException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);

        }catch(AcessoNegadoException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);

        }catch(ConflictException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);

        }
        // Optional<FormaPagamento> formaPagamento = formaPagamentoService.findById(formaPagamentoDTO.id());

        // if (formaPagamento.isPresent()){
        //     BeanUtils.copyProperties(formaPagamentoDTO, formaPagamento);

        //     return new ResponseEntity<>(formaPagamentoService.save(formaPagamento.get()), HttpStatus.OK);
        // } else return new ResponseEntity<>("Forma de Pagamento não encontrado!", HttpStatus.NOT_FOUND);

    }

    @DeleteMapping("/formapagamento/delete/{id}")
    public ResponseEntity<?> formaPagamentoDelete(@PathVariable(name = "id") long id, HttpServletRequest request){
        try{
            var usuarioLogado = tokenService.getUserLogado(request, usuarioService);
            formaPagamentoDelete.delete(id,usuarioLogado);
            return new ResponseEntity<>("Forma de Pagamento excluída", HttpStatus.OK);

        }catch(AcessoNegadoException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);

        }catch(EntityDontExistException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);

        }
        // Optional<FormaPagamento> formaPagamento = formaPagamentoService.findById(id);

        // if (formaPagamento.isPresent()){
        //     formaPagamentoService.delete(formaPagamento.get());
        //     return new ResponseEntity<>("Forma de Pagamento deletado do Sistema!", HttpStatus.OK);

        // } else return new ResponseEntity<>("Forma de Pagamento não encontrado!", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/formapagamento/byusuarioLogado")
    public ResponseEntity<Page<FormaPagamentoResponseDTO>> formaPagamentoByEmpresa(
        @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
        HttpServletRequest request){

            var usuarioLogado = tokenService.getUserLogado(request, usuarioService);
            var formaPagamentos = formaPagamentoList.list(usuarioLogado.getEmpresa(), pageable);
            
            List<FormaPagamentoResponseDTO> listDTO = formaPagamentos.stream().map((item) -> {
                FormaPagamentoResponseDTO dto = formaPagamentoConverter.toResponseDTO(item);
                return dto;
            }).collect(Collectors.toList());

            return new ResponseEntity<>(new PageImpl<FormaPagamentoResponseDTO>(listDTO), HttpStatus.OK);

            // Usuario usuario = usuarioService.findByLogin(tokenService.validateToken(tokenService.recoverToken(request))).get();
            // return new ResponseEntity<>(formaPagamentoService.findByEmpresa(usuario.getEmpresa(), pageable), HttpStatus.OK);
        }

}
