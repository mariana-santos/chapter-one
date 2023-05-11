from pydantic import BaseModel
from Utils import Utils

class Editora(BaseModel):
    id: int
    nome: str
    endereco: str
    telefone: str

    def buscarEditorasBanco(dsn, Editora):
        try:
            conn = Utils.connect(dsn)
            cursor = conn.cursor()
            listaEditoras = []
            cursor.execute("""
                SELECT e.id, e.nome, e.endereco, e.telefone
                FROM editora e
                ORDER BY e.nome
            """)
            for row in cursor:
                editora_banco = Editora(
                    id = row[0],
                    nome = row[1],
                    endereco = row[2],
                    telefone = row[3]
                )
                listaEditoras.append(editora_banco)
            
            return listaEditoras
        
        except Exception as e:
            print(f"OCORREU UM ERRO: {str(e)}")
            return None
        
        finally:
            Utils.disconnect(conn, cursor)

