# IMPORTANDO BIBLIOTECAS
import cx_Oracle
from Autor import Autor
from Categoria import Categoria
from Editora import Editora
from Livro import Livro
from Utils import Utils
from fastapi import FastAPI

# CRIANDO CONEXÃO COM O BANCO
cx_Oracle.init_oracle_client(lib_dir=r"C:\Program Files\instantclient_21_9")
dsn = cx_Oracle.makedsn(host='oracle.fiap.com.br', port=1521, sid='ORCL')

# INSTANCIANDO FASTAPI
app = FastAPI()

listaCategorias = Categoria.buscarCategoriasBanco(dsn, Categoria)
listaEditoras = Editora.buscarEditorasBanco(dsn, Editora)
listaAutores = Autor.buscarAutoresBanco(dsn, Autor)
listaLivros = Livro.buscarLivrosBanco(dsn, Livro)

#-----------------------------------------------------------------
# API'S - AUTOR

# API'S - AUTOR (LISTAR TODAS)
@app.get("/autores", tags=["Autores"])
def listar_autores():
    return listaAutores

# API'S - AUTOR (LISTAR POR ID)
@app.get("/autor/{id}", tags=["Autores"])
def listar_autor_por_id(id: int):
    for autor in listaAutores:
        if autor.id == id:
            return autor

# API'S - AUTOR (ADICIONAR AUTOR)
@app.post("/incluir_autor", tags=["Autores"])
def incluir_autor(novo_autor: Autor):
    try:
        conn = Utils.connect(dsn)
        cursor = conn.cursor()
        query = "INSERT INTO autor (id, nome, email, telefone, bio) VALUES (:id, :nome, :email, :telefone, :bio)"
        cursor.execute(query, id = novo_autor.id, nome = novo_autor.nome, email = novo_autor.email, telefone = novo_autor.telefone, bio = novo_autor.bio)
        conn.commit()
        listaAutores.add(novo_autor)
    except Exception as e:
        print(f"OCORREU UM ERRO: {str(e)}")
        return None
    finally:
        Utils.disconnect(conn, cursor)

# API'S - AUTOR (ATUALIZAR AUTOR)
@app.put("/atualizar_autor/{id}", tags=["Autores"])
def atualizar_autor(autor_atualizar: Autor, id):
    try:
        conn = Utils.connect(dsn)
        cursor = conn.cursor()
        query = "UPDATE autor SET nome = :nome, email = :email, telefone = :telefone, bio = :bio WHERE id = :id"
        cursor.execute(query, nome = autor_atualizar.nome, email = autor_atualizar.email, telefone = autor_atualizar.telefone, bio = autor_atualizar.bio, id = id)
        conn.commit()
        listaAutores.add(autor_atualizar)
    except Exception as e:
        print(f"OCORREU UM ERRO: {str(e)}")
        return None
    finally:
        Utils.disconnect(conn, cursor)

# API'S - AUTOR (DELETAR AUTOR)
@app.delete("/deletar_autor/{id}", tags=["Autores"])
def deletar_autor(id):
    try:
        conn = Utils.connect(dsn)
        cursor = conn.cursor()
        query = "DELETE FROM autor WHERE ID = :id"
        cursor.execute(query, id = id)
        conn.commit()
        for autor_deletar in listaAutores:
            if autor_deletar.id == id:
                listaAutores.remove(autor_deletar)        
    except Exception as e:
        print(f"OCORREU UM ERRO: {str(e)}")
        return None
    finally:
        Utils.disconnect(conn, cursor)

# -----------------------------------------------------------------
# API'S - CATEGORIA - OK

# API'S - CATEGORIA (LISTAR TODAS)
@app.get("/categorias", tags=["Categorias"])
def listar_categorias():
    return listaCategorias

# API'S - CATEGORIA (LISTAR POR ID)
@app.get("/categoria/{id}", tags=["Categorias"])
def listar_categoria_por_id(id: int):
    for categoria in listaCategorias:
        if categoria.id == id:
            return categoria

# API'S - CATEGORIA (ADICIONAR CATEGORIA)
@app.post("/incluir_categoria", tags=["Categorias"])
def incluir_categoria(nova_categoria: Categoria):
    try:
        conn = Utils.connect(dsn)
        cursor = conn.cursor()
        query = "INSERT INTO categoria (id, nome) VALUES (:id, :nome)"
        cursor.execute(query, id = nova_categoria.id, nome = nova_categoria.nome)
        conn.commit()
        listaCategorias.add(nova_categoria)
    except Exception as e:
        print(f"OCORREU UM ERRO: {str(e)}")
        return None
    finally:
        Utils.disconnect(conn, cursor)

# API'S - CATEGORIA (ATUALIZAR CATEGORIA)
@app.put("/atualizar_categoria/{id}", tags=["Categorias"])
def atualizar_categoria(categoria_atualizar: Categoria, id):
    try:
        conn = Utils.connect(dsn)
        cursor = conn.cursor()
        query = "UPDATE categoria SET nome = :nome WHERE id = :id"
        cursor.execute(query, nome = categoria_atualizar.nome, id = id)
        conn.commit()
        listaCategorias.add(categoria_atualizar)
    except Exception as e:
        print(f"OCORREU UM ERRO: {str(e)}")
        return None
    finally:
        Utils.disconnect(conn, cursor)

# API'S - CATEGORIA (DELETAR CATEGORIA)
@app.delete("/deletar_categoria/{id}", tags=["Categorias"])
def deletar_categoria(id):
    try:
        conn = Utils.connect(dsn)
        cursor = conn.cursor()
        query = "DELETE FROM categoria WHERE ID = :id"
        cursor.execute(query, id = id)
        conn.commit()
        for categoria_deletar in listaCategorias:
            if categoria_deletar.id == id:
                listaCategorias.remove(categoria_deletar)
    except Exception as e:
        print(f"OCORREU UM ERRO: {str(e)}")
        return None
    finally:
        Utils.disconnect(conn, cursor)

# -----------------------------------------------------------------
# API'S - EDITORA

# API'S - EDITORA (LISTAR TODAS)
@app.get("/editoras", tags=["Editoras"])
def listar_editoras():
    return listaEditoras

# API'S - EDITORA (LISTAR POR ID)
@app.get("/editora/{id}", tags=["Editoras"])
def listar_editora_por_id(id: int):
    for editora in listaEditoras:
        if editora.id == id:
            return editora

# API'S - EDITORA (ADICIONAR EDITORA)
@app.post("/incluir_editora", tags=["Editoras"])
def incluir_editora(nova_editora: Editora):
    try:
        conn = Utils.connect(dsn)
        cursor = conn.cursor()
        query = "INSERT INTO editora (id, nome, endereco, telefone) VALUES (:id, :nome, :endereco, :telefone)"
        cursor.execute(query, id = nova_editora.id, nome = nova_editora.nome, endereco = nova_editora.endereco, telefone = nova_editora.telefone)
        conn.commit()
        listaEditoras.add(nova_editora)
    except Exception as e:
        print(f"OCORREU UM ERRO: {str(e)}")
        return None
    finally:
        Utils.disconnect(conn, cursor)

# API'S - EDITORA (ATUALIZAR EDITORA)
@app.put("/atualizar_editora/{id}", tags=["Editoras"])
def atualizar_editora(editora_atualizar: Editora, id):
    try:
        conn = Utils.connect(dsn)
        cursor = conn.cursor()
        query = "UPDATE editora SET nome = :nome, endereco = :endereco, telefone = :telefone WHERE id = :id"
        cursor.execute(query, nome = editora_atualizar.nome, endereco = editora_atualizar.endereco, telefone = editora_atualizar.telefone, id = id)
        conn.commit()
        listaEditoras.add(editora_atualizar)
    except Exception as e:
        print(f"OCORREU UM ERRO: {str(e)}")
        return None
    finally:
        Utils.disconnect(conn, cursor)

# API'S - EDITORA (DELETAR EDITORA)
@app.delete("/deletar_editora/{id}", tags=["Editoras"])
def deletar_editora(id):
    try:
        conn = Utils.connect(dsn)
        cursor = conn.cursor()
        query = "DELETE FROM editora WHERE ID = :id"
        cursor.execute(query, id = id)
        conn.commit()
        for editora_deletar in listaEditoras:
            if editora_deletar.id == id:
                listaEditoras.remove(editora_deletar)
    except Exception as e:
        print(f"OCORREU UM ERRO: {str(e)}")
        return None
    finally:
        Utils.disconnect(conn, cursor)

# -----------------------------------------------------------------
# API'S - LIVRO

# API'S - LIVRO (LISTAR TODAS)
@app.get("/livros", tags=["Livros"])
def listar_livros():
    return listaLivros

# API'S - LIVRO (LISTAR POR ID)
@app.get("/livro/{id}", tags=["Livros"])
def listar_livro_por_id(id: int):
    for livro in listaLivros:
        if livro.id == id:
            return livro

# API'S - LIVRO (ADICIONAR LIVRO)
@app.post("/incluir_livro", tags=["Livros"])
def incluir_livro(novo_livro: Livro):
    try:
        conn = Utils.connect(dsn)
        cursor = conn.cursor()
        query = "INSERT INTO livro (id, titulo, resumo, ano, paginas, isbn, id_categoria, id_editora, imagem) VALUES (:id, :titulo, :resumo, :ano, :paginas, :isbn, :id_categoria, :id_editora, :imagem)"
        cursor.execute(query, id = novo_livro.id, titulo = novo_livro.titulo, resumo = novo_livro.resumo, ano = novo_livro.ano, paginas = novo_livro.paginas, isbn = novo_livro.isbn, id_categoria = novo_livro.id_categoria, id_editora = novo_livro.id_editora, imagem = novo_livro.imagem)
        conn.commit()
        listaLivros.add(novo_livro)
    except Exception as e:
        print(f"OCORREU UM ERRO: {str(e)}")
        return None
    finally:
        Utils.disconnect(conn, cursor)

# API'S - LIVRO (ATUALIZAR LIVRO)
@app.put("/atualizar_livro/{id}", tags=["Livros"])
def atualizar_livro(livro_atualizar: Livro, id):
    try:
        conn = Utils.connect(dsn)
        cursor = conn.cursor()
        query = "UPDATE livro SET titulo = :titulo, resumo = :resumo, ano = :ano, paginas = :paginas, isbn = :isbn, id_categoria = :id_categoria, id_editora = :id_editora, imagem = :imagem WHERE id = :id"
        cursor.execute(query, titulo = livro_atualizar.titulo, resumo = livro_atualizar.resumo, ano = livro_atualizar.ano, paginas = livro_atualizar.paginas, isbn = livro_atualizar.isbn, id_categoria = livro_atualizar.id_categoria, id_editora = livro_atualizar.id_editora, imagem = livro_atualizar.imagem, id = id)
        conn.commit()
        listaLivros.add(livro_atualizar)
    except Exception as e:
        print(f"OCORREU UM ERRO: {str(e)}")
        return None
    finally:
        Utils.disconnect(conn, cursor)

# API'S - LIVRO (DELETAR LIVRO)
@app.delete("/deletar_livro/{id}", tags=["Livros"])
def deletar_livro(id):
    try:
        conn = Utils.connect(dsn)
        cursor = conn.cursor()
        query = "DELETE FROM livro WHERE ID = :id"
        cursor.execute(query, id = id)
        conn.commit()
        for livro_deletar in listaLivros:
            if livro_deletar.id == id:
                return livro_deletar
    except Exception as e:
        print(f"OCORREU UM ERRO: {str(e)}")
        return None
    finally:
        Utils.disconnect(conn, cursor)

# -----------------------------------------------------------------