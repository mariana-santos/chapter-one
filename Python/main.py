# IMPORTANDO BIBLIOTECAS
import cx_Oracle
from Autor import Autor
from Categoria import Categoria
from Editora import Editora
from Livro import Livro
from Utils import Utils
from fastapi import FastAPI, Response
from fastapi.middleware.cors import CORSMiddleware

# CRIANDO CONEXÃO COM O BANCO
cx_Oracle.init_oracle_client(lib_dir=r"C:\Program Files\instantclient_21_9")
dsn = cx_Oracle.makedsn(host='oracle.fiap.com.br', port=1521, sid='ORCL')

# INSTANCIANDO FASTAPI
app = FastAPI()

app.add_middleware(
    CORSMiddleware,
    allow_origins=['*']
)

listaCategorias = Categoria.buscar_categorias_banco(dsn)
listaEditoras = Editora.buscar_editoras_banco(dsn)
listaAutores = Autor.buscar_autores_banco(dsn)
listaLivros = Livro.buscar_livros_banco(dsn)

#-----------------------------------------------------------------
# API'S - AUTOR

# API'S - AUTOR (LISTAR TODOS)
@app.get("/autores", tags=["Autores"])
def listar_autores():
    try: 
        return listaAutores
    
    except Exception as e:
        print(f"OCORREU UM ERRO AO LISTAR TODOS OS AUTORES: {str(e)}")
        return Response(content="Erro ao listar autores.", media_type="text/plain", status_code=500)

# API'S - AUTOR (LISTAR POR ID)
@app.get("/autor/{id}", tags=["Autores"])
def exibir_autor_por_id(id: int):
    try:
        for autor in listaAutores:
            if autor.id == id:
                return autor
        return Response(content=f"Autor com ID {id} não encontrado.", media_type="text/plain", status_code=404)
    
    except Exception as e:
        print(f"OCORREU UM ERRO AO EXIBIR O AUTOR POR ID: {str(e)}")
        return Response(content=f"Erro ao exibir Autor de id = {id}.", media_type="text/plain", status_code=500)

# API'S - AUTOR (ADICIONAR AUTOR)
@app.post("/incluir_autor", tags=["Autores"])
def incluir_autor(novo_autor: Autor):
    try:
        conn = Utils.connect(dsn)
        cursor = conn.cursor()

        query = "INSERT INTO autor (id, nome, email, telefone, bio, imagem) VALUES (SQ_AUTOR.NEXTVAL, :nome, :email, :telefone, :bio, :imagem)"
        cursor.execute(query, nome = novo_autor.nome, email = novo_autor.email, telefone = novo_autor.telefone, bio = novo_autor.bio, imagem = novo_autor.imagem)
        conn.commit()
        
        listaAutores.append(novo_autor)

        return Response(content="Autor cadastrado com sucesso!", media_type="text/plain")

    except cx_Oracle.DatabaseError as e:
        print(f"OCORREU UM ERRO DE BANCO DE DADOS AO CADASTRAR NOVO AUTOR: {str(e)}")
        return Response(content="Erro de banco de dados ao cadastrar novo autor.", media_type="text/plain", status_code=500)
    
    except Exception as e:
        print(f"OCORREU UM ERRO INESPERADO AO CADASTRAR NOVO AUTOR NO BANCO DE DADOS: {str(e)}")
        return Response(content="Erro inesperado ao cadastrar novo autor.", media_type="text/plain", status_code=500)
    
    finally:
        Utils.disconnect(conn, cursor)

# API'S - AUTOR (ATUALIZAR AUTOR)
@app.put("/atualizar_autor/{id}", tags=["Autores"])
def atualizar_autor(autor_atualizar: Autor, id):
    try:
        conn = Utils.connect(dsn)
        cursor = conn.cursor()

        query = "UPDATE autor SET nome = :nome, email = :email, telefone = :telefone, bio = :bio, imagem = :imagem WHERE id = :id"
        cursor.execute(query, nome = autor_atualizar.nome, email = autor_atualizar.email, telefone = autor_atualizar.telefone, bio = autor_atualizar.bio,  imagem = autor_atualizar.imagem, id = id)
        conn.commit()

        listaAutores.append(autor_atualizar)

        return Response(content="Autor atualizado com sucesso!", media_type="text/plain")

    except cx_Oracle.DatabaseError as e:
        print(f"OCORREU UM ERRO DE BANCO DE DADOS AO ATUALIZAR AUTOR: {str(e)}")
        return Response(content="Erro de banco de dados ao atualizar autor.", media_type="text/plain", status_code=500)
    
    except Exception as e:
        print(f"OCORREU UM ERRO INESPERADO AO ATUALIZAR AUTOR NO BANCO DE DADOS: {str(e)}")
        return Response(content="Erro inesperado ao atualizar autor.", media_type="text/plain", status_code=500)
    
    finally:
        Utils.disconnect(conn, cursor)

# API'S - AUTOR (DELETAR AUTOR)
@app.delete("/deletar_autor/{id}", tags=["Autores"])
def deletar_autor(id):
    try:
        conn = Utils.connect(dsn)
        cursor = conn.cursor()

        query = "DELETE FROM autor_livro WHERE id_autor = :id"
        cursor.execute(query, id = id)
        conn.commit()
        
        query = "DELETE FROM autor WHERE id = :id"
        cursor.execute(query, id = id)
        conn.commit()

        for autor_deletar in listaAutores:
            if autor_deletar.id == id:
                listaAutores.remove(autor_deletar)
        
        return Response(content=f"Autor com ID {id} deletado com sucesso.", media_type="text/plain", status_code=200)

    except cx_Oracle.DatabaseError as e:
        print(f"OCORREU UM ERRO DE BANCO DE DADOS AO DELETAR AUTOR: {str(e)}")
        return Response(content="Erro ao deletar autor.", media_type="text/plain", status_code=500)
    
    except Exception as e:
        print(f"OCORREU UM ERRO INESPERADO AO DELETAR AUTOR NO BANCO DE DADOS: {str(e)}")
        return Response(content="Erro ao deletar autor.", media_type="text/plain", status_code=500)
    
    finally:
        Utils.disconnect(conn, cursor)

# -----------------------------------------------------------------
# API'S - CATEGORIA - OK

# API'S - CATEGORIA (LISTAR TODAS)
@app.get("/categorias", tags=["Categorias"])
def listar_categorias():
    try: 
        return listaCategorias
    
    except Exception as e:
        print(f"OCORREU UM ERRO AO LISTAR TODAS AS CATEGORIAS: {str(e)}")
        return Response(content="Erro ao listar categorias.", media_type="text/plain", status_code=500)

# API'S - CATEGORIA (LISTAR POR ID)
@app.get("/categoria/{id}", tags=["Categorias"])
def exibir_categoria_por_id(id: int):
    try:
        for categoria in listaCategorias:
            if categoria.id == id:
                return categoria
        return Response(content=f"Categoria com ID {id} não encontrada.", media_type="text/plain", status_code=404)
    
    except Exception as e:
        print(f"OCORREU UM ERRO AO EXIBIR A CATEGORIA POR ID: {str(e)}")
        return Response(content=f"Erro ao exibir Categoria de id = {id}.", media_type="text/plain", status_code=500)
    
# API'S - CATEGORIA (ADICIONAR CATEGORIA)
@app.post("/incluir_categoria", tags=["Categorias"])
def incluir_categoria(nova_categoria: Categoria):
    try:
        conn = Utils.connect(dsn)
        cursor = conn.cursor()

        query = "INSERT INTO categoria (id, nome) VALUES (SQ_CATEGORIA.NEXTVAL, :nome)"
        cursor.execute(query, nome = nova_categoria.nome)
        conn.commit()

        listaCategorias.append(nova_categoria)

        return Response(content="Categoria cadastrada com sucesso!", media_type="text/plain")

    except cx_Oracle.DatabaseError as e:
        print(f"OCORREU UM ERRO DE BANCO DE DADOS AO CADASTRAR NOVA CATEGORIA: {str(e)}")
        return Response(content="Erro de banco de dados ao cadastrar nova categoria.", media_type="text/plain", status_code=500)
    
    except Exception as e:
        print(f"OCORREU UM ERRO INESPERADO AO CADASTRAR NOVA CATEGORIA NO BANCO DE DADOS: {str(e)}")
        return Response(content="Erro inesperado ao cadastrar nova categoria.", media_type="text/plain", status_code=500)
    
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

        listaCategorias.append(categoria_atualizar)

        return Response(content="Categoria atualizada com sucesso!", media_type="text/plain")
    
    except cx_Oracle.DatabaseError as e:
        print(f"OCORREU UM ERRO DE BANCO DE DADOS AO ATUALIZAR CATEGORIA: {str(e)}")
        return Response(content="Erro de banco de dados ao atualizar categoria.", media_type="text/plain", status_code=500)
    
    except Exception as e:
        print(f"OCORREU UM ERRO INESPERADO AO ATUALIZAR CATEGORIA NO BANCO DE DADOS: {str(e)}")
        return Response(content="Erro inesperado ao atualizar categoria.", media_type="text/plain", status_code=500)
    
    finally:
        Utils.disconnect(conn, cursor)

# API'S - CATEGORIA (DELETAR CATEGORIA)
@app.delete("/deletar_categoria/{id}", tags=["Categorias"])
def deletar_categoria(id):
    try:
        conn = Utils.connect(dsn)
        cursor = conn.cursor()

        query = "DELETE FROM categoria WHERE id = :id"
        cursor.execute(query, id = id)
        conn.commit()

        for categoria_deletar in listaCategorias:
            if categoria_deletar.id == id:
                listaCategorias.remove(categoria_deletar)
        
        return Response(content=f"Categoria com ID {id} deletada com sucesso.", media_type="text/plain", status_code=200)
   
    except cx_Oracle.DatabaseError as e:
        print(f"OCORREU UM ERRO DE BANCO DE DADOS AO DELETAR CATEGORIA: {str(e)}")
        return Response(content="Erro ao deletar categoria.", media_type="text/plain", status_code=500)
    
    except Exception as e:
        print(f"OCORREU UM ERRO INESPERADO AO DELETAR CATEGORIA NO BANCO DE DADOS: {str(e)}")
        return Response(content="Erro ao deletar categoria.", media_type="text/plain", status_code=500)
    
    finally:
        Utils.disconnect(conn, cursor)

# -----------------------------------------------------------------
# API'S - EDITORA

# API'S - EDITORA (LISTAR TODAS)
@app.get("/editoras", tags=["Editoras"])
def listar_editoras():
    try: 
        return listaEditoras
    
    except Exception as e:
        print(f"OCORREU UM ERRO AO LISTAR TODAS AS EDITORAS: {str(e)}")
        return Response(content="Erro ao listar editoras.", media_type="text/plain", status_code=500)

# API'S - EDITORA (LISTAR POR ID)
@app.get("/editora/{id}", tags=["Editoras"])
def exibir_editora_por_id(id: int):
    try:
        for editora in listaEditoras:
            if editora.id == id:
                return editora
        return Response(content=f"Editora com ID {id} não encontrada.", media_type="text/plain", status_code=404)
    
    except Exception as e:
        print(f"OCORREU UM ERRO AO EXIBIR A EDITORA POR ID: {str(e)}")
        return Response(content=f"Erro ao exibir Editora de id = {id}.", media_type="text/plain", status_code=500)

# API'S - EDITORA (ADICIONAR EDITORA)
@app.post("/incluir_editora", tags=["Editoras"])
def incluir_editora(nova_editora: Editora):
    try:
        conn = Utils.connect(dsn)
        cursor = conn.cursor()

        query = "INSERT INTO editora (id, nome, endereco, telefone) VALUES (SQ_EDITORA.NEXTVAL, :nome, :endereco, :telefone)"
        cursor.execute(query, nome = nova_editora.nome, endereco = nova_editora.endereco, telefone = nova_editora.telefone)
        conn.commit()

        listaEditoras.append(nova_editora)

        return Response(content="Editora cadastrada com sucesso!", media_type="text/plain")
    
    except cx_Oracle.DatabaseError as e:
        print(f"OCORREU UM ERRO DE BANCO DE DADOS AO CADASTRAR NOVA EDITORA: {str(e)}")
        return Response(content="Erro de banco de dados ao cadastrar nova editora.", media_type="text/plain", status_code=500)
    
    except Exception as e:
        print(f"OCORREU UM ERRO INESPERADO AO CADASTRAR NOVA EDITORA NO BANCO DE DADOS: {str(e)}")
        return Response(content="Erro inesperado ao cadastrar nova editora.", media_type="text/plain", status_code=500)
    
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

        listaEditoras.append(editora_atualizar)

        return Response(content="Editora atualizado com sucesso!", media_type="text/plain")
   
    except cx_Oracle.DatabaseError as e:
        print(f"OCORREU UM ERRO DE BANCO DE DADOS AO ATUALIZAR EDITORA: {str(e)}")
        return Response(content="Erro de banco de dados ao atualizar editora.", media_type="text/plain", status_code=500)
    
    except Exception as e:
        print(f"OCORREU UM ERRO INESPERADO AO ATUALIZAR EDITORA NO BANCO DE DADOS: {str(e)}")
        return Response(content="Erro inesperado ao atualizar editora.", media_type="text/plain", status_code=500)
    
    finally:
        Utils.disconnect(conn, cursor)

# API'S - EDITORA (DELETAR EDITORA)
@app.delete("/deletar_editora/{id}", tags=["Editoras"])
def deletar_editora(id):
    try:
        conn = Utils.connect(dsn)
        cursor = conn.cursor()

        query = "DELETE FROM editora WHERE id = :id"
        cursor.execute(query, id = id)
        conn.commit()

        for editora_deletar in listaEditoras:
            if editora_deletar.id == id:
                listaEditoras.remove(editora_deletar)
        
        return Response(content=f"Editora com ID {id} deletada com sucesso.", media_type="text/plain", status_code=200)
    
    except cx_Oracle.DatabaseError as e:
        print(f"OCORREU UM ERRO DE BANCO DE DADOS AO DELETAR EDITORA: {str(e)}")
        return Response(content="Erro ao deletar editora.", media_type="text/plain", status_code=500)
    
    except Exception as e:
        print(f"OCORREU UM ERRO INESPERADO AO DELETAR EDITORA NO BANCO DE DADOS: {str(e)}")
        return Response(content="Erro ao deletar editora.", media_type="text/plain", status_code=500)
    
    finally:
        Utils.disconnect(conn, cursor)

# -----------------------------------------------------------------
# API'S - LIVRO

# API'S - LIVRO (LISTAR TODOS)
@app.get("/livros", tags=["Livros"])
def listar_livros():
    try: 
        return listaLivros
    
    except Exception as e:
        print(f"OCORREU UM ERRO AO LISTAR TODOS OS LIVROS: {str(e)}")
        return Response(content="Erro ao listar livros.", media_type="text/plain", status_code=500)

# API'S - LIVRO (LISTAR POR ID DO AUTOR)
@app.get("/livros_por_autor/{id}", tags=["Livros"])
def exibir_livros_por_autor(id: int):
    try:
        livrosByAutor = []
        for livro in listaLivros:
            if livro.autores[0]['id'] == id:
                livrosByAutor.append(livro)
        return livrosByAutor
    
    except Exception as e:
        print(f"OCORREU UM ERRO AO EXIBIR O LIVRO POR ID: {str(e)}")
        return Response(content=f"Erro ao exibir Livro de id = {id}.", media_type="text/plain", status_code=500)

# API'S - LIVRO (LISTAR POR ID)
@app.get("/livro/{id}", tags=["Livros"])
def exibir_livro_por_id(id: int):
    try:
        for livro in listaLivros:
            if livro.id == id:
                return livro
        return Response(content=f"Livro com ID {id} não encontrado.", media_type="text/plain", status_code=404)
    
    except Exception as e:
        print(f"OCORREU UM ERRO AO EXIBIR O LIVRO POR ID: {str(e)}")
        return Response(content=f"Erro ao exibir Livro de id = {id}.", media_type="text/plain", status_code=500)

# API'S - LIVRO (ADICIONAR LIVRO)
@app.post("/incluir_livro", tags=["Livros"])
def incluir_livro(novo_livro: Livro):
    try:
        conn = Utils.connect(dsn)
        cursor = conn.cursor()

        query = "INSERT INTO livro (id, titulo, resumo, ano, paginas, isbn, id_categoria, id_editora, imagem, preco, desconto) VALUES (SQ_LIVRO.NEXTVAL, :titulo, :resumo, :ano, :paginas, :isbn, :id_categoria, :id_editora, :imagem, :preco, :desconto)"
        cursor.execute(query, titulo = novo_livro.titulo, resumo = novo_livro.resumo, ano = novo_livro.ano, paginas = novo_livro.paginas, isbn = novo_livro.isbn, id_categoria = novo_livro.id_categoria, id_editora = novo_livro.id_editora, imagem = novo_livro.imagem, preco = novo_livro.preco, desconto = novo_livro.desconto)
        conn.commit()

        listaLivros.append(novo_livro)

        return Response(content="Livro cadastrado com sucesso!", media_type="text/plain")
    
    except cx_Oracle.DatabaseError as e:
        print(f"OCORREU UM ERRO DE BANCO DE DADOS AO CADASTRAR NOVO LIVRO: {str(e)}")
        return Response(content="Erro de banco de dados ao cadastrar novo livro.", media_type="text/plain", status_code=500)
    
    except Exception as e:
        print(f"OCORREU UM ERRO INESPERADO AO CADASTRAR NOVO LIVRO NO BANCO DE DADOS: {str(e)}")
        return Response(content="Erro inesperado ao cadastrar novo livro.", media_type="text/plain", status_code=500)
    
    finally:
        Utils.disconnect(conn, cursor)

# API'S - LIVRO (ATUALIZAR LIVRO)
@app.put("/atualizar_livro/{id}", tags=["Livros"])
def atualizar_livro(livro_atualizar: Livro, id):
    try:
        conn = Utils.connect(dsn)
        cursor = conn.cursor()

        query = "UPDATE livro SET titulo = :titulo, resumo = :resumo, ano = :ano, paginas = :paginas, isbn = :isbn, id_categoria = :id_categoria, id_editora = :id_editora, imagem = :imagem, preco = :preco, desconto = :desconto WHERE id = :id"
        cursor.execute(query, titulo = livro_atualizar.titulo, resumo = livro_atualizar.resumo, ano = livro_atualizar.ano, paginas = livro_atualizar.paginas, isbn = livro_atualizar.isbn, id_categoria = livro_atualizar.id_categoria, id_editora = livro_atualizar.id_editora, imagem = livro_atualizar.imagem, preco = livro_atualizar.preco, desconto = livro_atualizar.desconto, id = id)
        conn.commit()

        listaLivros.append(livro_atualizar)

        return Response(content="Livro atualizado com sucesso!", media_type="text/plain")
    
    except cx_Oracle.DatabaseError as e:
        print(f"OCORREU UM ERRO DE BANCO DE DADOS AO ATUALIZAR LIVRO: {str(e)}")
        return Response(content="Erro de banco de dados ao atualizar livro.", media_type="text/plain", status_code=500)
    
    except Exception as e:
        print(f"OCORREU UM ERRO INESPERADO AO ATUALIZAR LIVRO NO BANCO DE DADOS: {str(e)}")
        return Response(content="Erro inesperado ao atualizar livro.", media_type="text/plain", status_code=500)
    
    finally:
        Utils.disconnect(conn, cursor)

# API'S - LIVRO (DELETAR LIVRO)
@app.delete("/deletar_livro/{id}", tags=["Livros"])
def deletar_livro(id):
    try:
        conn = Utils.connect(dsn)
        cursor = conn.cursor()

        query = "DELETE FROM autor_livro WHERE id_livro = :id"
        cursor.execute(query, id = id)
        conn.commit()

        query = "DELETE FROM livro WHERE id = :id"
        cursor.execute(query, id = id)
        conn.commit()

        for livro_deletar in listaLivros:
            if livro_deletar.id == id:
                return livro_deletar
            
        return Response(content=f"Livro com ID {id} deletado com sucesso.", media_type="text/plain", status_code=200)
    
    except cx_Oracle.DatabaseError as e:
        print(f"OCORREU UM ERRO DE BANCO DE DADOS AO DELETAR LIVRO: {str(e)}")
        return Response(content="Erro ao deletar livro.", media_type="text/plain", status_code=500)
    
    except Exception as e:
        print(f"OCORREU UM ERRO INESPERADO AO DELETAR LIVRO NO BANCO DE DADOS: {str(e)}")
        return Response(content="Erro ao deletar livro.", media_type="text/plain", status_code=500)
    
    finally:
        Utils.disconnect(conn, cursor)

# -----------------------------------------------------------------
