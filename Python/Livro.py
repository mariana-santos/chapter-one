from pydantic import BaseModel
from Utils import Utils

class Livro(BaseModel):
    id: int
    titulo: str
    resumo: str
    ano: int
    paginas: int
    isbn: str
    id_categoria: int
    id_editora: int
    imagem: str
    preco: float
    autores: list

    def buscarLivrosBanco(dsn, Livro):
        try:
            conn = Utils.connect(dsn)
            cursor_livro = conn.cursor()
            listaLivros = []
            cursor_livro.execute("""
                SELECT l.id, l.titulo, l.resumo, l.ano, l.paginas, l.isbn, l.id_categoria, l.id_editora, l.imagem, l.preco
                FROM livro l
                ORDER BY l.titulo
            """)
            for row in cursor_livro:
                livro_banco = Livro(
                    id = row[0],
                    titulo = row[1],
                    resumo = row[2],
                    ano = row[3],
                    paginas = row[4],
                    isbn = row[5],
                    id_categoria = row[6],
                    id_editora = row[7],
                    imagem = row[8],
                    preco = row[9],
                    autores = []
                )

                cursor_autor = conn.cursor()
                cursor_autor.execute("""
                SELECT * FROM autor_livro WHERE id_livro = :1
                """, (livro_banco.id,))

                for autor_row in cursor_autor:
                    autor = {
                        'id': autor_row[0]
                    }

                    livro_banco.autores.append(autor)
                
                cursor_autor.close()

                listaLivros.append(livro_banco)
            
            return listaLivros
        
        except Exception as e:
            print(f"OCORREU UM ERRO: {str(e)}")
            return None
        
        finally:
            Utils.disconnect(conn, cursor_livro)