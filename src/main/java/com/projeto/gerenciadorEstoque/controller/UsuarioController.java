package com.projeto.gerenciadorEstoque.controller;

import org.springframework.web.bind.annotation.RestController;

import com.projeto.gerenciadorEstoque.repository.dto.usuario.AtualizarSenhaDTO;
import com.projeto.gerenciadorEstoque.repository.dto.usuario.AtualizarUsuarioDTO;
import com.projeto.gerenciadorEstoque.repository.dto.usuario.CriarUsuarioDTO;
import com.projeto.gerenciadorEstoque.repository.dto.usuario.ListarUsuarioDTO;
import com.projeto.gerenciadorEstoque.service.UsuarioService;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/listar")
    public ResponseEntity<?> listarUsuarios() {
        try {
            List<ListarUsuarioDTO> usuarios = usuarioService.getUsuarios();
            return ResponseEntity.ok(usuarios);
        } catch (Exception ex) {
            return new ResponseEntity<>("Erro ao listar usuários", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/listar/{id}")
    public ResponseEntity<?> listarUsuarioPorId(@PathVariable Integer id) {
        try {
            return usuarioService.getUsuarioPorCodigo(id)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception ex) {
            return new ResponseEntity<>("Erro ao buscar usuário", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/buscar")
    public ResponseEntity<?> buscarPorNome(@RequestParam String nome) {
        try {
            List<ListarUsuarioDTO> usuarios = usuarioService.getUsuariosPorNome(nome);
            return ResponseEntity.ok(usuarios);
        } catch (Exception ex) {
            return new ResponseEntity<>("Erro ao buscar usuários por nome", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/criar")
    public ResponseEntity<?> criar(@RequestBody CriarUsuarioDTO dto) {
        try {
            usuarioService.criarUsuario(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body("Usuário criado com sucesso!");
        } catch (Exception ex) {
            return new ResponseEntity<>("Erro ao criar usuário", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/atualizar/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Integer id, @RequestBody AtualizarUsuarioDTO dto) {
        try {
            usuarioService.atualizarUsuario(id, dto);
            return ResponseEntity.ok("Usuário atualizado com sucesso!");
        } catch (Exception ex) {
            return new ResponseEntity<>("Erro ao atualizar usuário", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/atualizar/senha/{id}")
    public ResponseEntity<?> atualizarSenha(@PathVariable Integer id, @RequestBody AtualizarSenhaDTO dto) {
        try {
            usuarioService.atualizarSenhaUsuario(id, dto);
            return ResponseEntity.ok("Senha alterada com sucesso!");
        } catch (Exception ex) {
            return new ResponseEntity<>("Erro ao alterar senha do usuário", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<?> excluir(@PathVariable Integer id) {
        try {
            usuarioService.excluirUsuario(id);
            return ResponseEntity.ok("Usuário excluído com sucesso!");
        } catch (Exception ex) {
            return new ResponseEntity<>("Erro ao excluir usuário", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
