package com.gregoryan.api.unit;

import com.gregoryan.api.Models.Usuario;
import com.gregoryan.api.Repositorys.UsuarioRepository;
import com.gregoryan.api.Services.Crud.UsuarioService;
import com.gregoryan.api.mocks.MockUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    MockUser mockUser;

    @InjectMocks //Nos testes se usa InjectMocks no lugar de Autowired
    private UsuarioService usuarioService;

    @Mock
    UsuarioRepository usuarioRepository;

    @BeforeEach
    void setUp() {
        mockUser = new MockUser();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void save() {
        Usuario usuario = mockUser.mockEntity();
        usuario.setId(1L);
        when(usuarioRepository.save(usuario)).thenReturn(usuario);

        var result = usuarioService.save(usuario);
        assertNotNull(result.getNome());
        assertNotNull(result.getSobrenome());
        assertNotNull(result.getEmail());
        assertNotNull(result.getEndereco());
        assertNotNull(result.getLogin());
        assertNotNull(result.getPassword());
        assertNotNull(result.getNascimento());
        assertNotNull(result.getTelefone());
        assertNotNull(result.getDataRegistro());
        assertNotNull(result.getRole());
        assertNotNull(result.getStatus());
    }

    @Test
    void findAll() {

        List<Usuario> list = mockUser.mockEntityList();
        when(usuarioRepository.findAll(Pageable.unpaged())).thenReturn(new PageImpl<>(list));

        Page<Usuario> users = usuarioService.findAll(Pageable.unpaged());
        assertNotNull(users);

    }

    @Test
    void delete() {
    }

    @Test
    void existByLogin() {
    }

    @Test
    void findById() {
        Usuario usuario = mockUser.mockEntity();
        usuario.setId(1L);
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        var result = usuarioService.findById(1L).get();
        assertNotNull(result.getNome());
        assertNotNull(result.getSobrenome());
        assertNotNull(result.getEmail());
        assertNotNull(result.getEndereco());
        assertNotNull(result.getLogin());
        assertNotNull(result.getPassword());
        assertNotNull(result.getNascimento());
        assertNotNull(result.getTelefone());
        assertNotNull(result.getDataRegistro());
        assertNotNull(result.getRole());
        assertNotNull(result.getStatus());
    }

    @Test
    void findByLogin() {

    }

    @Test
    void findByEmpresa() {
    }
}