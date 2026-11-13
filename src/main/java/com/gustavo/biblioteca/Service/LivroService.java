package com.gustavo.biblioteca.Service;

import com.gustavo.biblioteca.Entity.Livro;
import com.gustavo.biblioteca.Repository.LivroRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class LivroService {

    private final LivroRepository livroRepository;

    public Livro salvar(Livro livro){
        if (Objects.nonNull(livroRepository.findByIsbn(livro.getCodigoIsbn()))) {
            throw new RuntimeException("Livro já cadastrado com esse código ISBN");
        }

        validaLivro(livro);

        return livroRepository.save(livro);
    }

    public Livro buscarPorId(Long id){
        Livro livro = livroRepository.findById(id).orElse(null);
        if (Objects.isNull(livro)) {
            throw new RuntimeException("Livro não encontrado");
        }
        return livro;
    }

    public List<Livro> buscarTodos(){
        return livroRepository.findAll();
    }

    private void validaLivro(Livro livro){
        Objects.requireNonNull(livro, "O Livro não pode ser vazio.");
        Objects.requireNonNull(livro.getTitulo(),"O Titulo precisa ser preenchido.");
        Objects.requireNonNull(livro.getAutor(), "O Autor precisa ser preenchido");
        Objects.requireNonNull(livro.getCodigoIsbn(),"O Codigo ISBN precisa ser preenchido");
        Objects.requireNonNull(livro.getPreco(),"O livro precisa ter um Preço");
        Objects.requireNonNull(livro.getAnoPublicacao(),"O Ano de Publicação precisa ser preenchido");
    }

}
