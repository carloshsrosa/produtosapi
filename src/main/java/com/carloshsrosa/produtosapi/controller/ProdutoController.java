package com.carloshsrosa.produtosapi.controller;

import com.carloshsrosa.produtosapi.model.Produto;
import com.carloshsrosa.produtosapi.repository.ProdutoRepository;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("produtos")
public class ProdutoController {

    @Autowired
    ProdutoRepository repository;
    private Double preco;

    @GetMapping
    public ResponseEntity<List<Produto>> findAll(@RequestParam(defaultValue = "") String nome){
        List<Produto> produtos = new ArrayList<>();
        if (nome.isBlank()){
            produtos = repository.findAll();
        } else {
            produtos = repository.findByNome(nome);
        }
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

    @PutMapping("/{id}")
    public ResponseEntity<Produto> update(@PathVariable Long id, @RequestBody Produto produto){
        Produto produtoToUpdate = repository.findById(id).orElse(null);
        if (produtoToUpdate == null) {
            return ResponseEntity.notFound().build();
        } else {
            produto.setId(produtoToUpdate.getId());
            repository.save(produto);
        }
        return ResponseEntity.ok().body(produto);
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
