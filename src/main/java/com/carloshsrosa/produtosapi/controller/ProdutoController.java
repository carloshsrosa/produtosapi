package com.carloshsrosa.produtosapi.controller;

import com.carloshsrosa.produtosapi.model.Produto;
import com.carloshsrosa.produtosapi.repository.ProdutoRepository;
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
    public ResponseEntity<List<Produto>> findAllProduto(){
        List<Produto> produtos = repository.findAll();
        return ResponseEntity.ok().body(produtos);
    }

    @PostMapping
    public ResponseEntity<Void> saveProduto(@RequestBody Produto produto){
        repository.save(produto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(produto.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }
}
