package com.projeto.gerenciadorEstoque.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.projeto.gerenciadorEstoque.entity.Usuario;
import com.projeto.gerenciadorEstoque.repository.UsuarioRepository;
import com.projeto.gerenciadorEstoque.repository.dto.usuario.ListarUsuarioDTO;
import com.projeto.gerenciadorEstoque.repository.dto.usuario.AtualizarSenhaDTO;
import com.projeto.gerenciadorEstoque.repository.dto.usuario.AtualizarUsuarioDTO;
import com.projeto.gerenciadorEstoque.repository.dto.usuario.CriarUsuarioDTO;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
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

    public Usuario atualizarSenhaUsuario(Integer id, AtualizarSenhaDTO dto) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        usuario.setSenha(passwordEncoder.encode(dto.getSenha()));
        return usuarioRepository.save(usuario);
    }

    public void excluirUsuario(Integer id) {
        usuarioRepository.deleteById(id);
    }
}
