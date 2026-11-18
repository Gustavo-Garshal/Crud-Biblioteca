package com.gustavo.biblioteca.Service;

import com.gustavo.biblioteca.Entity.Livro;
import com.gustavo.biblioteca.Repository.LivroRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.gustavo.biblioteca.ConstTeste.livroValido;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LivroServiceTest {

    @InjectMocks
    private LivroService livroService;

    @Mock
    private LivroRepository livroRepository;

    @Test
    void salvaComSucesso(){
        Livro livro = livroValido();
        livro.setId(null);

        Livro livroEsperado = livroValido();

        when(livroRepository.findByCodigoIsbn(livro.getCodigoIsbn())).thenReturn(null);
        when (livroRepository.save(livro)).thenReturn(livroEsperado);

        Livro livroSalvo = livroService.salvar(livro);

        assertAll("Verificação dos dados",
                () -> assertNotNull(livroSalvo),
                () -> assertEquals(1L, livroSalvo.getId()),
                () -> assertEquals(livro.getTitulo(), livroSalvo.getTitulo()),
                () -> assertEquals(livro.getAutor(), livroSalvo.getAutor()),
                () -> assertEquals(livro.getCodigoIsbn(), livroSalvo.getCodigoIsbn()),
                () -> assertEquals(livro.getPreco(), livroSalvo.getPreco()),
                () -> assertEquals(livro.getAnoPublicacao(), livroSalvo.getAnoPublicacao())
        );

        verify(livroRepository, times(1)).findByCodigoIsbn(livro.getCodigoIsbn());
        verify(livroRepository, times(1)).save(livro);
    }
    }
