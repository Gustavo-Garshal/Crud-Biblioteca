package com.gustavo.biblioteca.Service;

import com.gustavo.biblioteca.Entity.Livro;
import com.gustavo.biblioteca.Repository.LivroRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LivroServiceTest {

    @InjectMocks
    private LivroService livroService;

    @Mock
    private LivroRepository livroRepository;

    private Livro livro;

    @BeforeEach
    void setUp() {
        livro = new Livro(
                1L,
                "Livro bao",
                "Autor bao",
                "1234567890123",
                BigDecimal.valueOf(19.99),
                LocalDate.of(2010, 10, 1)
        );
    }

    @Test
    void salvaComSucesso(){

        when(livroRepository.findByCodigoIsbn(livro.getCodigoIsbn())).thenReturn(null);
        when (livroRepository.save(livro)).thenReturn(livro);

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

    @Test
    void salvarComErro() {

        when(livroRepository.findByCodigoIsbn(livro.getCodigoIsbn())).thenReturn(livro);

        assertThrows(RuntimeException.class, () -> livroService.salvar(livro));

        verify(livroRepository, only()).findByCodigoIsbn(livro.getCodigoIsbn());
        verify(livroRepository, never()).save(any(Livro.class));
    }
}
