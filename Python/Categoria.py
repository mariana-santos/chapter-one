import oracledb

from pydantic import BaseModel
from typing import Optional

from Utils import Utils

class Categoria(BaseModel):
    id: Optional[int]
    nome: str

    def buscar_categorias_banco(dsn):
        try:
            conn = Utils.connect(dsn)
            cursor = conn.cursor()

            cursor.execute("""
                SELECT c.id, c.nome
                FROM categoria c
                ORDER BY c.nome
            """)

            lista_categorias = []
            for row in cursor:
                categoria_banco = Categoria(
                    id=row[0],
                    nome=row[1],
                )
                lista_categorias.append(categoria_banco)

            return lista_categorias

        except oracledb.DatabaseError as e:
            print(f"OCORREU UM ERRO DE BANCO DE DADOS AO BUSCAR AS CATEGORIAS: {str(e)}")
            return None

        except Exception as e:
            print(f"OCORREU UM ERRO INESPERADO AO BUSCAR AS CATEGORIAS NO BANCO DE DADOS: {str(e)}")
            return None

        finally:
            Utils.disconnect(conn, cursor)
