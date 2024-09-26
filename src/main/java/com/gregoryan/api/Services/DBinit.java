package com.gregoryan.api.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.gregoryan.api.Models.Empresa;
import com.gregoryan.api.Models.Profissional;
import com.gregoryan.api.Models.StatusAgenda;
import com.gregoryan.api.Models.StatusDia;
import com.gregoryan.api.Models.StatusHora;
import com.gregoryan.api.Models.UserRole;
import com.gregoryan.api.Models.Usuario;
import com.gregoryan.api.Services.Crud.EmpresaService;
import com.gregoryan.api.Services.Crud.ProfissionalService;
import com.gregoryan.api.Services.Crud.StatusAgendaService;
import com.gregoryan.api.Services.Crud.StatusDiaService;
import com.gregoryan.api.Services.Crud.StatusHoraService;
import com.gregoryan.api.Services.Crud.UsuarioService;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import jakarta.annotation.PostConstruct;

@Component
public class DBinit {
    
    @Autowired
    private StatusAgendaService statusAgendaService;
    @Autowired
    private StatusDiaService statusDiaService;
    @Autowired
    private StatusHoraService statusHoraService;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private EmpresaService empresaService;
    @Autowired
    private ProfissionalService profissionalService;

    @Value("${api.security.sysadmin.password}")
    String sysadminpass;

    @PostConstruct
    public void criaStatusSeNaoExistir(){

        //Cria Status da Agenda Ativo
        statusAgendaAtivo();

        //Cria Status do Dia para Ativo
        statusDiaAtivo();

        //Cria Status da Hora para Ativo
        statusHoraAtivo();

        //Cria Usuario Sysadmin se não existir
        usuarioSysAdmin();

        //Cria empresa Gregorian
        empresaGregorian();;

        //Cria Profissional Greg
        profissionalGreg();

    }

    private void statusAgendaAtivo(){
        //Cria Status Ativo para Agenda na Inicialização;
        if (!statusAgendaService.existsByNome("Ativo")){
            StatusAgenda statusAgenda = new StatusAgenda();
            statusAgenda.setNome("Ativo");
            statusAgendaService.save(statusAgenda);
        }
    }

    private void statusDiaAtivo(){
        //Cria Status Ativo para Dia na Inicialização;
        if (!statusDiaService.existsByNome("Ativo")){
            StatusDia statusDia = new StatusDia();
            statusDia.setNome("Ativo");
            statusDiaService.save(statusDia);
        }
    }

    private void statusHoraAtivo(){
        //cria status Ativo para Hora na Incialização
        if (!statusHoraService.existsByNome("Ativo")){
            StatusHora statusHora = new StatusHora();
            statusHora.setNome("Ativo");
            statusHoraService.save(statusHora);
        }
    }

    private void empresaGregorian(){
        if(!empresaService.existsByCnpj(123456789)){
            Empresa empresa = new Empresa();
            empresa.setCnpj(123456789);
            empresa.setEndereco("Rua 0");
            empresa.setNome("Gregorian");
            empresa.setResponsavel("sysadmin");
            empresa.setTelefone("00 000000000");
            Calendar now = Calendar.getInstance(TimeZone.getTimeZone("GMT"), new Locale("pt-BR"));
            empresa.setDataRegistro(now);
            empresaService.save(empresa);
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
            usuario.setStatus(Usuario.STATUS_ATIVO);
            usuario.setLogin("sysadmin");
            String encryptedPassword = new BCryptPasswordEncoder().encode(sysadminpass);
            usuario.setSenha(encryptedPassword);
            Calendar now = Calendar.getInstance(TimeZone.getTimeZone("GMT"), new Locale("pt-BR"));
            usuario.setDataRegistro(now);
            usuario.setNascimento(now);
            usuario.setEmpresa(empresaService.findByCnpj(12346789).get());

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
