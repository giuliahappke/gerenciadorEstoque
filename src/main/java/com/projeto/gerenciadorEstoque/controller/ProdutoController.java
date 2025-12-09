package com.projeto.gerenciadorEstoque.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.projeto.gerenciadorEstoque.repository.dto.produto.AtualizarProdutoDTO;
import com.projeto.gerenciadorEstoque.repository.dto.produto.CriarProdutoDTO;
import com.projeto.gerenciadorEstoque.repository.dto.produto.ListarProdutoDTO;
import com.projeto.gerenciadorEstoque.service.ProdutoService;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @GetMapping
    public ResponseEntity<List<ListarProdutoDTO>> listarUsuarios() {
        return ResponseEntity.ok(produtoService.getProdutos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ListarProdutoDTO> listarProdutoPorId(@PathVariable Integer id) {
        return produtoService.getProdutoPorCodigo(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<ListarProdutoDTO>> buscarPorNome(@RequestParam String nome) {
        return ResponseEntity.ok(produtoService.getProdutosPorNome(nome));
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody CriarProdutoDTO dto) {
        produtoService.criarProduto(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Produto criado com sucesso!");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> atualizar(@PathVariable Integer id, @RequestBody AtualizarProdutoDTO dto) {
        produtoService.atualizarProduto(id, dto);
        return ResponseEntity.ok("Produto atualizado com sucesso!");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePedido(@PathVariable Integer id) {
        produtoService.excluirProduto(id);
        return new ResponseEntity<>("Produto excluído com sucesso!", HttpStatus.OK);
    }
}
