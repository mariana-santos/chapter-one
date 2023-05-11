from pydantic import BaseModel
from Utils import Utils

class Autor(BaseModel):
    id: int
    nome: str
    email: str
    telefone: str
    bio: str

    def buscarAutoresBanco(dsn, Autor):
        try:
            conn = Utils.connect(dsn)
            cursor = conn.cursor()
            listaAutores = []
            cursor.execute("""
                SELECT a.id, a.nome, a.email, a.telefone, a.bio
                FROM autor a
                ORDER BY a.nome
            """)
            for row in cursor:
                autor_banco = Autor(
                    id = row[0],
                    nome = row[1],
                    email = row[2],
                    telefone = row[3],
                    bio = row[4]
                )
                listaAutores.append(autor_banco)
            
            return listaAutores
        
        except Exception as e:
            print(f"OCORREU UM ERRO: {str(e)}")
            return None
        
        finally:
            Utils.disconnect(conn, cursor)

