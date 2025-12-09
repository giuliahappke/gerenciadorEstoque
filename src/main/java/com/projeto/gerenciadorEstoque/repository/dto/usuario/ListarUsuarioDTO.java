package com.projeto.gerenciadorEstoque.repository.dto.usuario;

public class ListarUsuarioDTO {
    private int id;
    private String nome;
    private String email;

    public ListarUsuarioDTO(int id, String nome, String email) {
        this.id = id;
        this.nome = nome;
        this.email = email;
    }

    public int getId() {
        return id;
    }
    public String getNome() {
        return nome;
    }
    public String getEmail() {
        return email;
    }
}
