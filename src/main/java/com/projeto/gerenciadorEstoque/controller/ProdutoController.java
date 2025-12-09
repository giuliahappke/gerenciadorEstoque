package com.projeto.gerenciadorEstoque.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/listar")
    public ResponseEntity<?> listarProdutos() {
        try {
            List<ListarProdutoDTO> produtos = produtoService.getProdutos();
            return ResponseEntity.ok(produtos);
        } catch (Exception ex) {
            return new ResponseEntity<>("Erro ao listar produtos", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/listar/{id}")
    public ResponseEntity<?> listarProdutoPorId(@PathVariable Integer id) {
        try {
            return produtoService.getProdutoPorCodigo(id)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception ex) {
            return new ResponseEntity<>("Erro ao buscar produto", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/buscar")
    public ResponseEntity<?> buscarPorNome(@RequestParam String nome) {
        try {
            List<ListarProdutoDTO> produtos = produtoService.getProdutosPorNome(nome);
            return ResponseEntity.ok(produtos);
        } catch (Exception ex) {
            return new ResponseEntity<>("Erro ao buscar produtos por nome", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/criar")
    public ResponseEntity<?> criar(@RequestBody CriarProdutoDTO dto) {
        try {
            produtoService.criarProduto(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body("Produto criado com sucesso!");
        } catch (Exception ex) {
            return new ResponseEntity<>("Erro ao criar produto", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/atualizar/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Integer id, @RequestBody AtualizarProdutoDTO dto) {
        try {
            produtoService.atualizarProduto(id, dto);
            return ResponseEntity.ok("Produto atualizado com sucesso!");
        } catch (Exception ex) {
            return new ResponseEntity<>("Erro ao atualizar produto", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<?> excluir(@PathVariable Integer id) {
        try {
            produtoService.excluirProduto(id);
            return ResponseEntity.ok("Produto excluído com sucesso!");
        } catch (Exception ex) {
            return new ResponseEntity<>("Erro ao excluir produto", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
