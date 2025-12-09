package com.projeto.gerenciadorEstoque.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.projeto.gerenciadorEstoque.entity.Produto;
import com.projeto.gerenciadorEstoque.entity.Usuario;
import com.projeto.gerenciadorEstoque.repository.ProdutoRepository;
import com.projeto.gerenciadorEstoque.repository.dto.produto.AtualizarProdutoDTO;
import com.projeto.gerenciadorEstoque.repository.dto.produto.CriarProdutoDTO;
import com.projeto.gerenciadorEstoque.repository.dto.produto.ListarProdutoDTO;

@Service
public class ProdutoService {
    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public List<ListarProdutoDTO> getProdutos() {
        List<Produto> produtos = produtoRepository.findAll();

        return produtos.stream().map(produto -> new ListarProdutoDTO(
                produto.getId(),
                produto.getNome(),
                produto.getQuantidade(),
                produto.getCategoria(),
                produto.getModelo(),
                produto.getSistemaOperacional(),
                produto.getProcessador(),
                produto.getMemoriaRam(),
                produto.getArmazenamento(),
                produto.getPreco(),
                produto.getUsuario().getId()))
                .toList();
    }

    public Optional<ListarProdutoDTO> getProdutoPorCodigo(Integer id) {
        return produtoRepository.findById(id)
                .map(produto -> new ListarProdutoDTO(
                        produto.getId(),
                        produto.getNome(),
                        produto.getQuantidade(),
                        produto.getCategoria(),
                        produto.getModelo(),
                        produto.getSistemaOperacional(),
                        produto.getProcessador(),
                        produto.getMemoriaRam(),
                        produto.getArmazenamento(),
                        produto.getPreco(),
                        produto.getUsuario().getId()));
    }

    public List<ListarProdutoDTO> getProdutosPorNome(String name) {
        return produtoRepository.findByNomeContainingIgnoreCase(name)
                .stream()
                .map(produto -> new ListarProdutoDTO(
                        produto.getId(),
                        produto.getNome(),
                        produto.getQuantidade(),
                        produto.getCategoria(),
                        produto.getModelo(),
                        produto.getSistemaOperacional(),
                        produto.getProcessador(),
                        produto.getMemoriaRam(),
                        produto.getArmazenamento(),
                        produto.getPreco(),
                        produto.getUsuario().getId()))
                .toList();
    }

    public Produto criarProduto(CriarProdutoDTO dto) {
        Produto produto = new Produto();
        produto.setNome(dto.getNome());
        produto.setQuantidade(dto.getQuantidade());
        produto.setCategoria(dto.getCategoria());
        produto.setModelo(dto.getModelo());
        produto.setSistemaOperacional(dto.getSistemaOperacional());
        produto.setProcessador(dto.getProcessador());
        produto.setMemoriaRam(dto.getMemoriaRam());
        produto.setArmazenamento(dto.getArmazenamento());
        produto.setPreco(dto.getPreco());

        Usuario usuario = new Usuario();
        usuario.setId(dto.getUsuarioId());
        produto.setUsuario(usuario);
        return produtoRepository.save(produto);
    }

    public Produto atualizarProduto(Integer id, AtualizarProdutoDTO dto) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
        produto.setNome(dto.getNome());
        produto.setQuantidade(dto.getQuantidade());
        produto.setCategoria(dto.getCategoria());
        produto.setModelo(dto.getModelo());
        produto.setSistemaOperacional(dto.getSistemaOperacional());
        produto.setProcessador(dto.getProcessador());
        produto.setMemoriaRam(dto.getMemoriaRam());
        produto.setArmazenamento(dto.getArmazenamento());
        produto.setPreco(dto.getPreco());
        return produtoRepository.save(produto);
    }

    public void excluirProduto(Integer id) {
        produtoRepository.deleteById(id);
    }
}
