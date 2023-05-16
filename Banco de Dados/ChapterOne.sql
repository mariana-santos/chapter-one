-- CRIANDO AS TABELAS

CREATE TABLE categoria (
    id NUMBER(9) CONSTRAINT id_categoria_pk PRIMARY KEY,
    nome VARCHAR(50) CONSTRAINT nome_categoria_nn NOT NULL CONSTRAINT nome_categoria_uk UNIQUE
);

CREATE TABLE editora(
    id NUMBER(9) CONSTRAINT id_editora_pk PRIMARY KEY,
    nome VARCHAR(255) CONSTRAINT nome_editora_nn NOT NULL,
    endereco VARCHAR(255) CONSTRAINT end_editora_nn NOT NULL,
    telefone VARCHAR(20) CONSTRAINT tel_editora_nn NOT NULL
);

CREATE TABLE autor(
    id NUMBER(9) CONSTRAINT id_autor_pk PRIMARY KEY,
    nome VARCHAR(255) CONSTRAINT nome_autor_nn NOT NULL,
    email VARCHAR(255) CONSTRAINT email_autor_nn NOT NULL,
    telefone VARCHAR(20) CONSTRAINT tel_autor_nn NOT NULL,
    bio VARCHAR(4000) CONSTRAINT bio_autor_nn NOT NULL,
    imagem VARCHAR(4000) CONSTRAINT imagem_autor_nn NOT NULL
);

CREATE TABLE livro(
    id NUMBER(9) CONSTRAINT id_livro_pk PRIMARY KEY,
    titulo VARCHAR(255) CONSTRAINT titulo_livro_nn NOT NULL,
    resumo VARCHAR(4000),
    ano NUMBER(4) CONSTRAINT ano_livro_nn NOT NULL,
    paginas NUMBER(6) CONSTRAINT pg_livro_nn NOT NULL,
    isbn VARCHAR(20) CONSTRAINT isbn_livro_nn NOT NULL CONSTRAINT isbn_livro_uk UNIQUE,
    id_categoria NUMBER(9) CONSTRAINT id_cat_fk REFERENCES categoria CONSTRAINT id_cat_nn NOT NULL,
    id_editora NUMBER(9) CONSTRAINT id_editora_fk REFERENCES editora CONSTRAINT id_editora_nn NOT NULL,
    imagem VARCHAR2(4000) CONSTRAINT img_livro_nn NOT NULL,
    preco NUMBER(9,2) CONSTRAINT preco_livro_nn NOT NULL,
    desconto NUMBER (9,2)
);

CREATE TABLE autor_livro(
    id_autor NUMBER(9) CONSTRAINT id_autor_fk REFERENCES autor CONSTRAINT id_autor_nn NOT NULL,
    id_livro NUMBER(9) CONSTRAINT id_livro_fk REFERENCES livro CONSTRAINT id_livro_nn NOT NULL
);

-- FAZENDO INSERTS

INSERT INTO categoria VALUES (1, 'Ação e Aventura');
INSERT INTO categoria VALUES (2, 'Distopia');
INSERT INTO categoria VALUES (3, 'Fantasia');
INSERT INTO categoria VALUES (4, 'Ficção Científica');
INSERT INTO categoria VALUES (5, 'Ficção Policial');

INSERT INTO editora VALUES (1, 'Editora 01', 'Rua 01 - 100', '(11) 1000-0000');
INSERT INTO editora VALUES (2, 'Editora 02', 'Rua 02 - 200', '(11) 2000-0000');
INSERT INTO editora VALUES (3, 'Editora 03', 'Rua 03 - 300', '(11) 3000-0000');
INSERT INTO editora VALUES (4, 'Editora 04', 'Rua 04 - 400', '(11) 4000-0000');
INSERT INTO editora VALUES (5, 'Editora 05', 'Rua 05 - 500', '(11) 5000-0000');

INSERT INTO autor VALUES (1, 'Autor 01', 'autor01@gmail.com', '(11) 1100-0000', 'Bio 01', 'URL IMAGEM AUTOR 01');
INSERT INTO autor VALUES (2, 'Autor 02', 'autor02@gmail.com', '(11) 2200-0000', 'Bio 02', 'URL IMAGEM AUTOR 02');
INSERT INTO autor VALUES (3, 'Autor 03', 'autor03@gmail.com', '(11) 3300-0000', 'Bio 03', 'URL IMAGEM AUTOR 03');
INSERT INTO autor VALUES (4, 'Autor 04', 'autor04@gmail.com', '(11) 4400-0000', 'Bio 04', 'URL IMAGEM AUTOR 04');
INSERT INTO autor VALUES (5, 'Autor 05', 'autor05@gmail.com', '(11) 5500-0000', 'Bio 05', 'URL IMAGEM AUTOR 05');

INSERT INTO livro VALUES (1, 'Título 01', 'Resumo 01', '2001', 100, 'ISBN 01', 1, 1, 'URL 01', 10.50, 0.0);
INSERT INTO livro VALUES (2, 'Título 02', 'Resumo 02', '2002', 200, 'ISBN 02', 2, 2, 'URL 02', 25.00, 5.5);
INSERT INTO livro VALUES (3, 'Título 03', 'Resumo 03', '2003', 300, 'ISBN 03', 3, 3, 'URL 03', 50.00, 19.90);
INSERT INTO livro VALUES (4, 'Título 04', 'Resumo 04', '2004', 400, 'ISBN 04', 4, 4, 'URL 04', 9.90, 0.0);
INSERT INTO livro VALUES (5, 'Título 05', 'Resumo 05', '2005', 500, 'ISBN 05', 5, 5, 'URL 05', 19.90, 1.90);

INSERT INTO autor_livro VALUES (1, 1);
INSERT INTO autor_livro VALUES (2, 2);
INSERT INTO autor_livro VALUES (3, 3);
INSERT INTO autor_livro VALUES (4, 4);
INSERT INTO autor_livro VALUES (5, 5);

COMMIT;