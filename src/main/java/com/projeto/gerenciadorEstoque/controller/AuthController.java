package com.projeto.gerenciadorEstoque.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projeto.gerenciadorEstoque.repository.dto.usuario.LoginDTO;
import com.projeto.gerenciadorEstoque.service.UsuarioService;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UsuarioService usuarioService;

    public AuthController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<?> login(@RequestBody LoginDTO dto) {
        String token = usuarioService.login(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Usuário logado" + token);
    }
}
