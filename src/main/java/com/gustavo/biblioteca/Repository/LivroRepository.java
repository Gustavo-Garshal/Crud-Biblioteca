package com.gustavo.biblioteca.Repository;

import com.gustavo.biblioteca.Entity.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LivroRepository extends JpaRepository<Livro, Long> {

    Livro findByCodigoIsbn(String codigoIsbn);

}
