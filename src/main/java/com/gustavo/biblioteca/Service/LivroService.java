package com.gustavo.biblioteca.Service;

import com.gustavo.biblioteca.Entity.Livro;
import com.gustavo.biblioteca.Repository.LivroRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.function.ToIntFunction;

@Service
@RequiredArgsConstructor
public class LivroService {

    private final LivroRepository livroRepository;

    public Livro salvar(Livro livro){
        if (Objects.nonNull(livroRepository.findByCodigoIsbn(livro.getCodigoIsbn()))) {
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

    public Livro atualizar(Long id, Livro livro){
        Livro livroOld = buscarPorId(id);

        if (livro.getTitulo() != null && !livro.getTitulo().isBlank()){
            livroOld.setTitulo(livro.getTitulo());
        }
        if (livro.getAutor() != null && !livro.getAutor().isBlank()){
            livroOld.setAutor(livro.getAutor());
        }
        if (livro.getCodigoIsbn() != null && livro.getCodigoIsbn().length() == 13){
            livroOld.setCodigoIsbn(livro.getCodigoIsbn());
        }
        if (livro.getPreco().compareTo(BigDecimal.ZERO) > 0) {
            livroOld.setPreco(livro.getPreco());
        }
        if (livro.getAnoPublicacao() != null){
            livroOld.setAnoPublicacao(livro.getAnoPublicacao());
        }

        return livroRepository.save(livroOld);
    }

    public void deletar(Long id){
        Livro livro = buscarPorId(id);
        livroRepository.delete(livro);
    }

    private void validaLivro(Livro livro){
        Objects.requireNonNull(livro, "O Livro não pode ser vazio.");
        Objects.requireNonNull(livro.getTitulo(),"O Titulo precisa ser preenchido.");
        if (livro.getTitulo().isBlank()){
            throw new IllegalArgumentException("O Titulo precisa ser preenchido.");
        }

        Objects.requireNonNull(livro.getAutor(), "O Autor precisa ser preenchido");
        if (livro.getAutor().isBlank()){
            throw new IllegalArgumentException("O Titulo precisa ser preenchido.");
        }

        Objects.requireNonNull(livro.getCodigoIsbn(),"O Codigo ISBN precisa ser preenchido");
        if (livro.getCodigoIsbn().length() != 13){
            throw new RuntimeException("Codigo ISBN precisa ter 13 digitos");
        }

        Objects.requireNonNull(livro.getPreco(),"O livro precisa ter um Preço");
        if (livro.getPreco().compareTo(BigDecimal.ZERO) <= 0){
            throw new RuntimeException("Preco tem que ser maior que zero");
        }
        Objects.requireNonNull(livro.getAnoPublicacao(),"O Ano de Publicação precisa ser preenchido");
    }

}
