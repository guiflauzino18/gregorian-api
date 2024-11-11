package com.gregoryan.api.Controllers;

import java.util.List;
import java.util.Locale;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Calendar;
import java.util.TimeZone;
import com.gregoryan.api.DTO.EmpresaFaturamentoDTO;
import com.gregoryan.api.DTO.EmpresaSaveDTO;
import com.gregoryan.api.DTO.EmpresaSetPlanoDTO;
import com.gregoryan.api.DTO.EmpresaSetStatusDTO;
import com.gregoryan.api.Models.Empresa;
import com.gregoryan.api.Models.FaturamentoEmpresa;
import com.gregoryan.api.Models.PlanoEmpresa;
import com.gregoryan.api.Models.StatusEmpresa;
import com.gregoryan.api.Repositorys.FaturamentoEmpresaRepository;
import com.gregoryan.api.Repositorys.PlanoEmpresaRepository;
import com.gregoryan.api.Repositorys.StatusEmpresaRepository;
import com.gregoryan.api.Services.Crud.EmpresaService;

import jakarta.validation.Valid;

@RestController 
@RequestMapping("/api/gestor")
public class GestorController {

    @Autowired
    private EmpresaService empresaService;
    @Autowired
    private PlanoEmpresaRepository planoEmpresaRepository;
    @Autowired
    private StatusEmpresaRepository statusEmpresaRepository;
    @Autowired
    private FaturamentoEmpresaRepository faturamentoEmpresaRepository;


    /* ======================================= Empresa ============================================= */
    @PostMapping("/empresa/cadastro")
    public ResponseEntity<Object> cadastraEmpresa(@RequestBody @Valid EmpresaSaveDTO empresaDTO){

        if(empresaService.existsByCnpj(empresaDTO.cnpj())) return new ResponseEntity<Object>("Conflito: Cnpj já existe!", HttpStatus.CONFLICT);

        // Define Status da empresa como plano pendente
        var empresa = new Empresa();
        BeanUtils.copyProperties(empresaDTO, empresa);
        empresa.setStatusEmpresa(statusEmpresaRepository.findById(Long.valueOf(3)).get());

        Calendar now = Calendar.getInstance(TimeZone.getTimeZone("GMT"), new Locale("pt-BR"));
        empresa.setDataRegistro(now);

        return new ResponseEntity<Object>(empresaService.save(empresa), HttpStatus.CREATED);

    }

    @GetMapping("/empresa/list")
    public ResponseEntity<Page<Empresa>> allEmpresas(@PageableDefault(page = 0, size = 10, sort = "id", direction =  Sort.Direction.DESC) Pageable pageable){
        return new ResponseEntity<Page<Empresa>>(empresaService.findAll(pageable), HttpStatus.OK);
    }


    @PostMapping("/empresa/setplano")
    public ResponseEntity<String> setPlanoEmpresa(@RequestBody EmpresaSetPlanoDTO planoDTO){
        Empresa empresa = empresaService.findById(planoDTO.empresa()).get();
        PlanoEmpresa planoEmpresa = planoEmpresaRepository.findById(planoDTO.plano()).get();

        empresa.setPlanoEmpresa(planoEmpresa);
        empresaService.save(empresa);

        return new ResponseEntity<String>("Plano Cadastrado para a Empresa", HttpStatus.OK);
    }

    @PostMapping("/empresa/setStatus")
    public ResponseEntity<String> setStatusEmpresa(@RequestBody EmpresaSetStatusDTO statusDTO){
        Empresa empresa = empresaService.findById(statusDTO.empresa()).get();
        StatusEmpresa statusEmpresa = statusEmpresaRepository.findById(statusDTO.status()).get();

        empresa.setStatusEmpresa(statusEmpresa);
        empresaService.save(empresa);

        return new ResponseEntity<String>("Status da Empresa "+ empresa.getNome()+" Atualizado!", HttpStatus.OK);
    }

    @PostMapping("/empresa/faturamento")
    public ResponseEntity<String> faturamentoEmpresa(@RequestBody EmpresaFaturamentoDTO faturamentoDTO){
        Empresa empresa = empresaService.findById(faturamentoDTO.empresa()).get();
        PlanoEmpresa planoEmpresa = planoEmpresaRepository.findById(faturamentoDTO.plano()).get();

        Calendar hoje = Calendar.getInstance(TimeZone.getTimeZone("GMT"), new Locale("pt-BR"));

        FaturamentoEmpresa faturamentoEmpresa = new FaturamentoEmpresa();
        faturamentoEmpresa.setEmpresa(empresa);
        faturamentoEmpresa.setPlanoEmpresa(planoEmpresa);
        faturamentoEmpresa.setDesconto(faturamentoDTO.desconto());
        faturamentoEmpresa.setData(hoje);

        faturamentoEmpresaRepository.save(faturamentoEmpresa);

        return new ResponseEntity<String>("Faturamento Concluído!", HttpStatus.OK);
    }

    /*============================ Plano Empresa ============================================ */

    @PostMapping("/planoempresa/cadastro")
    public ResponseEntity<String> cadastraPlanoEmpresa(@RequestBody PlanoEmpresa planoEmpresa){

        planoEmpresaRepository.save(planoEmpresa);

        return new ResponseEntity<String>("Plano Cadastrado com Sucesso!", HttpStatus.OK);
    }

    @GetMapping("/planoempresa/list")
    public ResponseEntity<List<PlanoEmpresa>> allPlanoEmpresa(){

        List<PlanoEmpresa> planoEmpresas = planoEmpresaRepository.findAll();

        return new ResponseEntity<List<PlanoEmpresa>>(planoEmpresas, HttpStatus.OK);

    }

    /*=============================== Status Empresa ========================================= */

    @PostMapping("/statusempresa/cadastro")
    public ResponseEntity<String> statusEmpresa(@RequestBody StatusEmpresa statusEmpresa){

        statusEmpresaRepository.save(statusEmpresa);

        return new ResponseEntity<String>("Status Cadastrado com Sucesso!", HttpStatus.OK);
    }

    @GetMapping("/statusempresa/list")
    public ResponseEntity<List<StatusEmpresa>> allStatusEmpresa(){

        return new ResponseEntity<List<StatusEmpresa>>(statusEmpresaRepository.findAll(), HttpStatus.OK);
    }
    
}
