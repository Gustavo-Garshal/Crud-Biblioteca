package com.gustavo.biblioteca;

import com.gustavo.biblioteca.Entity.Livro;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ConstTeste {

    public static Livro livroValido() {
        return new Livro(
                1L,
                "Livro bao",
                "Autor bao",
                "1234567890123",
                BigDecimal.valueOf(19.99),
                LocalDate.of(2010, 10, 1)
        );
    }
}
