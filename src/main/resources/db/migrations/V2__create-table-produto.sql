CREATE TABLE produto (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    quantidade INTEGER NOT NULL,
    categoria INTEGER NOT NULL,
    modelo VARCHAR(255) NOT NULL,
    sistema_operacional VARCHAR(255) NOT NULL,
    processador VARCHAR(255) NOT NULL,
    memoria_ram VARCHAR(255) NOT NULL,
    armazenamento VARCHAR(255) NOT NULL,
    preco NUMERIC(10,2) NOT NULL,

    id_usuario INTEGER,
    CONSTRAINT fk_produto_usuario
        FOREIGN KEY (id_usuario) REFERENCES usuario(id)
);