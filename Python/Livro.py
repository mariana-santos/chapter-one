import oracledb

from pydantic import BaseModel
from typing import Optional, List, Dict

from Utils import Utils

class Livro(BaseModel):
    id: Optional[int]
    titulo: str
    resumo: str
    ano: int
    paginas: int
    isbn: str
    id_categoria: int
    id_editora: int
    imagem: str
    preco: float
    desconto: float
    autores: Optional[List[Dict[str, int]]]

    def buscar_livros_banco(dsn):
        try:
            conn = Utils.connect(dsn)
            cursor_livro = conn.cursor()

            cursor_livro.execute("""
                SELECT l.id, l.titulo, l.resumo, l.ano, l.paginas, l.isbn, l.id_categoria, l.id_editora, l.imagem, l.preco, l.desconto
                FROM livro l
                ORDER BY l.titulo
            """)

            lista_livros = []
            for row in cursor_livro:
                livro_banco = Livro(
                    id=row[0],
                    titulo=row[1],
                    resumo=row[2],
                    ano=row[3],
                    paginas=row[4],
                    isbn=row[5],
                    id_categoria=row[6],
                    id_editora=row[7],
                    imagem=row[8],
                    preco=row[9],
                    desconto=row[10],
                    autores=[]
                )

                cursor_autor = conn.cursor()
                cursor_autor.execute("""
                    SELECT id_autor FROM autor_livro WHERE id_livro = :id_livro
                """, {"id_livro": livro_banco.id})

                for autor_row in cursor_autor:
                    autor = {"id": autor_row[0]}
                    livro_banco.autores.append(autor)

                cursor_autor.close()
                lista_livros.append(livro_banco)

            return lista_livros

        except oracledb.DatabaseError as e:
            print(f"OCORREU UM ERRO DE BANCO DE DADOS AO BUSCAR OS LIVROS: {str(e)}")
            return None

        except Exception as e:
            print(f"OCORREU UM ERRO INESPERADO AO BUSCAR OS LIVROS NO BANCO DE DADOS: {str(e)}")
            return None

        finally:
            Utils.disconnect(conn, cursor_livro)
