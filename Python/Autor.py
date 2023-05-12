from pydantic import BaseModel
from Utils import Utils

class Autor(BaseModel):
    id: int
    nome: str
    email: str
    telefone: str
    bio: str
    livros: list

    def buscarAutoresBanco(dsn, Autor):
        try:
            conn = Utils.connect(dsn)
            cursor_autor = conn.cursor()
            listaAutores = []
            cursor_autor.execute("""
                SELECT a.id, a.nome, a.email, a.telefone, a.bio
                FROM autor a
                ORDER BY a.nome
            """)

            for row in cursor_autor:
                autor_banco = Autor(
                    id = row[0],
                    nome = row[1],
                    email = row[2],
                    telefone = row[3],
                    bio = row[4],
                    livros = []
                )

                cursor_livro = conn.cursor()
                cursor_livro.execute("""
                SELECT * FROM autor_livro WHERE id_autor = :1
                """, (autor_banco.id,))

                for livro_row in cursor_livro:
                    livro = {
                        'id': livro_row[1]
                    }

                    autor_banco.livros.append(livro)
                
                cursor_livro.close()

                listaAutores.append(autor_banco)
            
            return listaAutores
        
        except Exception as e:
            print(f"OCORREU UM ERRO: {str(e)}")
            return None
        
        finally:
            Utils.disconnect(conn, cursor_autor)

