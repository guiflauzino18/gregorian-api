package com.gregoryan.api.Controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gregoryan.api.DTO.EmpresaCadastroDTO;
import com.gregoryan.api.Models.Empresa;
import com.gregoryan.api.Models.Gestor;
import com.gregoryan.api.Models.PlanoEmpresa;
import com.gregoryan.api.Models.Usuario;
import com.gregoryan.api.Repositorys.EmpresaRepository;
import com.gregoryan.api.Repositorys.GestorRepository;
import com.gregoryan.api.Repositorys.PlanoEmpresaRepository;

@RestController 
@RequestMapping("/api/gestor")
public class GestorController {

    @Autowired
    private GestorRepository gestorRepository;
    @Autowired
    private EmpresaRepository empresaRepository;
    @Autowired
    private PlanoEmpresaRepository planoEmpresaRepository;

    @GetMapping("/list")
    public ResponseEntity<List<Gestor>> listGestor(){

        List<Gestor> gestores = gestorRepository.findAll();

        return new ResponseEntity<>(gestores, HttpStatus.OK);
    }

    @PostMapping("/empresa/cadastro")
    public ResponseEntity<String> cadastraEmpresa(@RequestBody Empresa empresa){

        empresaRepository.save(empresa);

        return new ResponseEntity<String>("Cadastro com Sucesso", HttpStatus.OK);

    }

    @GetMapping("/empresa/list")
    public ResponseEntity<List<Empresa>> allEmpresas(){
        List<Empresa> empresas = new ArrayList<Empresa>();
        empresas = empresaRepository.findAll();

        return new ResponseEntity<List<Empresa>>(empresas, HttpStatus.OK);
    }

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
    
}
