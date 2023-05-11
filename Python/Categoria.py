from pydantic import BaseModel
from Utils import Utils

class Categoria(BaseModel):
    id: int
    nome: str

    def buscarCategoriasBanco(dsn, Categoria):
        try:
            conn = Utils.connect(dsn)
            cursor = conn.cursor()
            listaCategorias = []
            cursor.execute("""
                SELECT c.id, c.nome
                FROM categoria c
                ORDER BY c.nome
            """)
            for row in cursor:
                categoria_banco = Categoria(
                    id = row[0],
                    nome = row[1],
                )
                listaCategorias.append(categoria_banco)
            
            return listaCategorias
        
        except Exception as e:
            print(f"OCORREU UM ERRO: {str(e)}")
            return None
        
        finally:
            Utils.disconnect(conn, cursor)

