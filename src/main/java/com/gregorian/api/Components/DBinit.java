package com.gregorian.api.Components;

import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.gregorian.api.Exception.EntityDontExistException;
import com.gregorian.api.Models.Empresa;
import com.gregorian.api.Models.Profissional;
import com.gregorian.api.Models.StatusAgenda;
import com.gregorian.api.Models.StatusDia;
import com.gregorian.api.Models.StatusHora;
import com.gregorian.api.Models.UserRole;
import com.gregorian.api.Models.Usuario;
import com.gregorian.api.Services.Crud.EmpresaService;
import com.gregorian.api.Services.Crud.ProfissionalService;
import com.gregorian.api.Services.Crud.StatusAgendaService;
import com.gregorian.api.Services.Crud.StatusDiaService;
import com.gregorian.api.Services.Crud.StatusHoraService;
import com.gregorian.api.Services.Crud.UsuarioService;

import jakarta.annotation.PostConstruct;

@Component
public class DBinit {
    
    @Autowired
    private EmpresaService empresaService;
    @Autowired
    private StatusAgendaService statusAgendaService;
    @Autowired
    private StatusDiaService statusDiaService;
    @Autowired
    private StatusHoraService statusHoraService;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private ProfissionalService profissionalService;

    @Value("${api.security.sysadmin.password}")
    String sysadminpass;

    @PostConstruct
    public void criaStatusSeNaoExistir(){

        //Cria empresa Gregorian
        empresaGregorian();

        //Cria Status da Agenda Ativo
        statusAgendaAtivo();

        //Cria Status do Dia para Ativo
        statusDiaAtivoEBloqueado();

        //Cria Status da Hora para Ativo
        statusHoraAtivoEBloqueado();

        //Cria Usuario Sysadmin se não existir
        usuarioSysAdmin();

        //Cria Profissional Greg
        profissionalGreg();

    }

        private void empresaGregorian(){
        if(!empresaService.existsByCnpj(0000000000)){
            Empresa empresa = new Empresa();
            empresa.setCnpj(0000000000);
            empresa.setEndereco("Rua 0");
            empresa.setNome("Gregorian");
            empresa.setResponsavel("sysadmin");
            empresa.setTelefone("00 000000000");
            Calendar now = Calendar.getInstance(TimeZone.getTimeZone("GMT"), new Locale("pt-BR"));
            empresa.setDataRegistro(now);
            empresaService.save(empresa);
        }
    }

    private void statusAgendaAtivo(){
        //Cria Status Ativo para Agenda na Inicialização;
        if (!statusAgendaService.existsByNome("Ativo")){
            StatusAgenda statusAgenda = new StatusAgenda();
            Empresa empresa = empresaService.findByCnpj(0000000000).orElseThrow(() -> new EntityDontExistException("Empresa não encontrada"));
            statusAgenda.setNome("Ativo");
            statusAgenda.setEmpresa(empresa);
            statusAgendaService.save(statusAgenda);
        }
    }

    private void statusDiaAtivoEBloqueado(){
        //Cria Status Ativo para Dia na Inicialização;
        if (!statusDiaService.existsByNome("Ativo")){
            StatusDia statusDia = new StatusDia();
            Empresa empresa = empresaService.findByCnpj(0000000000).orElseThrow(() -> new EntityDontExistException("Empresa não encontrada"));
            statusDia.setNome("Ativo");
            statusDia.setEmpresa(empresa);
            statusDiaService.save(statusDia);
        }

        //Cria Status Bloqueado para Dia na Inicialização;
        if (!statusDiaService.existsByNome("Bloqueado")){
            StatusDia statusDia = new StatusDia();
            statusDia.setNome("Bloqueado");
            Empresa empresa = empresaService.findByCnpj(0000000000).orElseThrow(() -> new EntityDontExistException("Empresa não encontrada"));
            statusDia.setEmpresa(empresa);
            statusDiaService.save(statusDia);
        }
    }

    private void statusHoraAtivoEBloqueado(){
        //cria status Ativo para Hora na Incialização
        if (!statusHoraService.existsByNome("Ativo")){
            StatusHora statusHora = new StatusHora();
            statusHora.setNome("Ativo");
            Empresa empresa = empresaService.findByCnpj(0000000000).orElseThrow(() -> new EntityDontExistException("Empresa não encontrada"));
            statusHora.setEmpresa(empresa);
            statusHoraService.save(statusHora);
        }

        //cria status Ativo para Hora na Incialização
        if (!statusHoraService.existsByNome("Bloqueado")){
            Empresa empresa = empresaService.findByCnpj(0000000000).orElseThrow(() -> new EntityDontExistException("Empresa não encontrada"));
            StatusHora statusHora = new StatusHora();
            statusHora.setNome("Bloqueado");
            statusHora.setEmpresa(empresa);
            statusHoraService.save(statusHora);
        }
    }

    private void usuarioSysAdmin(){
        //Cria um um usuario sysadmin se não existir
        if (!usuarioService.existByLogin("sysadmin")){
            Usuario usuario = new Usuario();
            usuario.setNome("Administrador");
            usuario.setSobrenome("Sistema");
            usuario.setEmail("admin@email.com");
            usuario.setEndereco("Sem endereço");
            usuario.setRole(UserRole.GESTOR);
            usuario.setStatus(Usuario.StatusUsuario.ATIVO);
            usuario.setLogin("sysadmin");
            String encryptedPassword = new BCryptPasswordEncoder().encode(sysadminpass);
            usuario.setSenha(encryptedPassword);
            Calendar now = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
            usuario.setDataRegistro(now);
            usuario.setNascimento(now);
            
            Empresa empresa = empresaService.findByCnpj(0000000000).orElseThrow(() -> new EntityDontExistException("Empresa não encontrada"));
            usuario.setEmpresa(empresa);
            usuarioService.save(usuario);
        }
    }


    //Cria um Profissional no Banco se não existir
    private void profissionalGreg(){
        if (!profissionalService.existsByRegistro("987654321")){
            Profissional profissional = new Profissional();
            profissional.setTitulo("Administrador");
            profissional.setRegistro("987654321");
            profissional.setUsuario(usuarioService.findByLogin("sysadmin").get());
            profissionalService.save(profissional);
        }
    }

}
