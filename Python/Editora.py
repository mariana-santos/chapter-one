import oracledb

from pydantic import BaseModel
from typing import Optional

from Utils import Utils

class Editora(BaseModel):
    id: Optional[int]
    nome: str
    endereco: str
    telefone: str

    def buscar_editoras_banco(dsn):
        try:
            conn = Utils.connect(dsn)
            cursor = conn.cursor()

            cursor.execute("""
                SELECT e.id, e.nome, e.endereco, e.telefone
                FROM editora e
                ORDER BY e.nome
            """)

            lista_editoras = []
            for row in cursor:
                editora_banco = Editora(
                    id=row[0],
                    nome=row[1],
                    endereco=row[2],
                    telefone=row[3]
                )
                lista_editoras.append(editora_banco)

            return lista_editoras

        except oracledb.DatabaseError as e:
            print(f"OCORREU UM ERRO DE BANCO DE DADOS AO BUSCAR AS EDITORAS: {str(e)}")
            return None

        except Exception as e:
            print(f"OCORREU UM ERRO INESPERADO AO BUSCAR AS EDITORAS NO BANCO DE DADOS: {str(e)}")
            return None

        finally:
            Utils.disconnect(conn, cursor)
