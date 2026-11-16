package com.gustavo.biblioteca.Controller;

import com.gustavo.biblioteca.Entity.Livro;
import com.gustavo.biblioteca.Repository.LivroRepository;
import com.gustavo.biblioteca.Service.LivroService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/livro")
@RequiredArgsConstructor
public class LivroController {

    private final LivroService livroService;

    @PostMapping
    public ResponseEntity<Livro> cadastrarLivro(@RequestBody Livro livro){
        return ResponseEntity.accepted().body(livroService.salvar(livro));
    }

    @GetMapping
    public ResponseEntity<List<Livro>> listarLivros(){
        return ResponseEntity.ok().body(livroService.buscarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Livro> buscarLivro(@PathVariable Long id){
        return  ResponseEntity.ok().body(livroService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public  ResponseEntity<Livro> atualizarLivro(@PathVariable Long id, @RequestBody Livro livro){
        return ResponseEntity.accepted().body(livroService.atualizar(id, livro));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Livro> deletarLivro(@PathVariable Long id){
        return ResponseEntity.noContent().build();
    }
}