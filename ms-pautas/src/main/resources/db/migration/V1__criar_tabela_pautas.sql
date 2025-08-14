CREATE TABLE PAUTAS(
    id BIGINT(20) NOT NULL auto_increment,
    nome VARCHAR(255) NOT NULL,
    descricao VARCHAR(255) NULL,
    categoria VARCHAR(255) NULL,
    associado_id BIGINT(20) NOT NULL,
    sessao_id BIGINT(20) NULL,
    PRIMARY KEY (id)
);