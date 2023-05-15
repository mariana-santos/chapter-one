import cx_Oracle

from pydantic import BaseModel
from typing import Optional, List, Dict

from Utils import Utils

class Autor(BaseModel):
    id: Optional[int]
    nome: str
    email: str
    telefone: str
    bio: str
    livros: Optional[List[Dict[str, int]]]
    imagem: str

    def buscar_autores_banco(dsn):
        try:
            conn = Utils.connect(dsn)
            cursor_autor = conn.cursor()

            cursor_autor.execute("""
                SELECT a.id, a.nome, a.email, a.telefone, a.bio, a.imagem
                FROM autor a
                ORDER BY a.nome
            """)

            lista_autores = []
            for row in cursor_autor:
                autor_banco = Autor(
                    id=row[0],
                    nome=row[1],
                    email=row[2],
                    telefone=row[3],
                    bio=row[4],
                    imagem=row[5],
                    livros=[]
                )

                cursor_livro = conn.cursor()
                cursor_livro.execute("""
                    SELECT id_livro FROM autor_livro WHERE id_autor = :id_autor
                """, {"id_autor": autor_banco.id})

                for livro_row in cursor_livro:
                    livro = {"id": livro_row[0]}
                    autor_banco.livros.append(livro)

                cursor_livro.close()
                lista_autores.append(autor_banco)

            return lista_autores

        except cx_Oracle.DatabaseError as e:
            print(f"OCORREU UM ERRO DE BANCO DE DADOS AO BUSCAR OS AUTORES: {str(e)}")
            return None

        except Exception as e:
            print(f"OCORREU UM ERRO INESPERADO AO BUSCAR OS AUTORES NO BANCO DE DADOS: {str(e)}")
            return None

        finally:
            Utils.disconnect(conn, cursor_autor)
