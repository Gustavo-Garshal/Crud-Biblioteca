package com.gustavo.biblioteca.Service;

import com.gustavo.biblioteca.Entity.Livro;
import com.gustavo.biblioteca.Repository.LivroRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
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
        return livroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Livro não encontrado"));
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

        List<String> erros = new ArrayList<>();

        Objects.requireNonNull(livro, "O Livro não pode ser vazio.");

        if (livro.getTitulo().isBlank() || livro.getTitulo() == null){
            erros.add("O Titulo precisa ser preenchido.");
        }

        if (livro.getAutor().isBlank() || livro.getAutor() == null){
            erros.add("O Titulo precisa ser preenchido.");
        }

        if (livro.getCodigoIsbn().length() != 13 || livro.getCodigoIsbn() == null){
            erros.add("Codigo ISBN precisa conter 13 digitos");
        }

        if (livro.getPreco().compareTo(BigDecimal.ZERO) <= 0 || livro.getPreco() == null){
            erros.add("Preco tem que ser maior que zero");
        }

        if (livro.getAnoPublicacao() == null){
            erros.add("A data de publicação tem que ser preenchida");
        }

        if (!erros.isEmpty()){
            throw new RuntimeException(String.join(", ", erros));
        }
    }

}
