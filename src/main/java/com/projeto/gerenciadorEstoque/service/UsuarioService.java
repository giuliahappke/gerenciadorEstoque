package com.projeto.gerenciadorEstoque.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.projeto.gerenciadorEstoque.config.JwtUtil;
import com.projeto.gerenciadorEstoque.entity.Usuario;
import com.projeto.gerenciadorEstoque.repository.UsuarioRepository;
import com.projeto.gerenciadorEstoque.repository.dto.usuario.ListarUsuarioDTO;
import com.projeto.gerenciadorEstoque.repository.dto.usuario.LoginDTO;
import com.projeto.gerenciadorEstoque.repository.dto.usuario.AtualizarUsuarioDTO;
import com.projeto.gerenciadorEstoque.repository.dto.usuario.CriarUsuarioDTO;

@Service
public class UsuarioService {

    private final JwtUtil jwtUtil;
    private final UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, JwtUtil jwtUtil) {
        this.usuarioRepository = usuarioRepository;
        this.jwtUtil = jwtUtil;
    }

    public List<ListarUsuarioDTO> getUsuarios() {
        List<Usuario> usuarios = usuarioRepository.findAll();

        return usuarios.stream()
                .map(usuario -> new ListarUsuarioDTO(
                        usuario.getId(),
                        usuario.getNome(),
                        usuario.getEmail()))
                .toList();
    }

    public Optional<ListarUsuarioDTO> getUsuarioPorCodigo(Integer id) {
        return usuarioRepository.findById(id)
                .map(usuario -> new ListarUsuarioDTO(
                        usuario.getId(),
                        usuario.getNome(),
                        usuario.getEmail()));
    }

    public List<ListarUsuarioDTO> getUsuariosPorNome(String nome) {
        return usuarioRepository.findByNomeContainingIgnoreCase(nome)
                .stream()
                .map(usuario -> new ListarUsuarioDTO(
                        usuario.getId(),
                        usuario.getNome(),
                        usuario.getEmail()))
                .toList();
    }

    public void criarUsuario(CriarUsuarioDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(passwordEncoder.encode(dto.getSenha()));

        usuarioRepository.save(usuario);
    }

    public Usuario atualizarUsuario(Integer id, AtualizarUsuarioDTO dto) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        return usuarioRepository.save(usuario);
    }

    public void excluirUsuario(Integer id) {
        usuarioRepository.deleteById(id);
    }

    public String login(LoginDTO dto) {
        Usuario usuario = usuarioRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (!passwordEncoder.matches(dto.getSenha(), usuario.getSenha())) {
            throw new RuntimeException("Senha inválida");
        }

        return jwtUtil.generateToken(usuario.getEmail());
    }
}
