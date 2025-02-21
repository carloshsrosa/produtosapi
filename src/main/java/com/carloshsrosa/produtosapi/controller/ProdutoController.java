package com.carloshsrosa.produtosapi.controller;

import com.carloshsrosa.produtosapi.model.Produto;
import com.carloshsrosa.produtosapi.repository.ProdutoRepository;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("produtos")
public class ProdutoController {

    @Autowired
    ProdutoRepository repository;

    @GetMapping
    public ResponseEntity<List<Produto>> findAll(){
        List<Produto> produtos = repository.findAll();
        return ResponseEntity.ok().body(produtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produto> findById(@PathVariable Long id){
        Produto produto = repository.findById(id).orElse(null);
        if (produto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(produto);
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody Produto produto){
        repository.save(produto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(produto.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        Produto produto = repository.findById(id).orElse(null);
        if (produto == null) {
            return ResponseEntity.notFound().build();
        } else {
            repository.delete(produto);
        }
        return ResponseEntity.noContent().build();
    }
}
