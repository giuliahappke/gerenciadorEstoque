package com.projeto.gerenciadorEstoque.controller;

import org.springframework.web.bind.annotation.RestController;

import com.projeto.gerenciadorEstoque.repository.dto.usuario.AtualizarUsuarioDTO;
import com.projeto.gerenciadorEstoque.repository.dto.usuario.CriarUsuarioDTO;
import com.projeto.gerenciadorEstoque.repository.dto.usuario.ListarUsuarioDTO;
import com.projeto.gerenciadorEstoque.service.UsuarioService;

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

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public ResponseEntity<List<ListarUsuarioDTO>> listarUsuarios() {
        return ResponseEntity.ok(usuarioService.getUsuarios());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ListarUsuarioDTO> listarUsuarioPorId(@PathVariable Integer id) {
        return usuarioService.getUsuarioPorCodigo(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<ListarUsuarioDTO>> buscarPorNome(@RequestParam String nome) {
        return ResponseEntity.ok(usuarioService.getUsuariosPorNome(nome));
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody CriarUsuarioDTO dto) {
        usuarioService.criarUsuario(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Usuário criado com sucesso!");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> atualizar(@PathVariable Integer id, @RequestBody AtualizarUsuarioDTO dto) {
        usuarioService.atualizarUsuario(id, dto);
        return ResponseEntity.ok("Usuário atualizado com sucesso!");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePedido(@PathVariable Integer id) {
        usuarioService.excluirUsuario(id);
        return new ResponseEntity<>("Usuário excluído com sucesso!", HttpStatus.OK);
    }
}
