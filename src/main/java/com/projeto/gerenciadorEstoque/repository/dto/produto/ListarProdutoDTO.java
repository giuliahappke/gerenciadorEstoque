package com.projeto.gerenciadorEstoque.repository.dto.produto;

public class ListarProdutoDTO {

    private int id;
    private String nome;
    private int quantidade;
    private int categoria;
    private String modelo;
    private String sistemaOperacional;
    private String processador;
    private String memoriaRam;
    private String armazenamento;
    private double preco;

    private int usuarioId;

    public ListarProdutoDTO(
        int id,
        String nome,
        int quantidade,
        int categoria,
        String modelo,
        String sistemaOperacional,
        String processador,
        String memoriaRam,
        String armazenamento,
        double preco,
        int usuarioId
    ){
        this.id = id;
        this.nome = nome;
        this.quantidade = quantidade;
        this.categoria = categoria;
        this.modelo = modelo;
        this.sistemaOperacional = sistemaOperacional;
        this.processador = processador;
        this.memoriaRam = memoriaRam;
        this.armazenamento = armazenamento;
        this.preco = preco;
        this.usuarioId = usuarioId;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public int getCategoria() {
        return categoria;
    }

    public String getModelo() {
        return modelo;
    }

    public String getSistemaOperacional() {
        return sistemaOperacional;
    }

    public String getProcessador() {
        return processador;
    }

    public String getMemoriaRam() {
        return memoriaRam;
    }

    public String getArmazenamento() {
        return armazenamento;
    }

    public double getPreco() {
        return preco;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

}
