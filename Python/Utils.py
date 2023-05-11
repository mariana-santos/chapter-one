import cx_Oracle

class Utils:

    def connect(dsn):
            try:
                conn = cx_Oracle.connect(user='RM96466', password='220693', dsn=dsn)
                return conn
            
            except cx_Oracle.Error as e:
                print(f"ERRO AO CONECTAR COM O BANCO DE DADOS: {e}")

    def disconnect(conn, cursor):
        try:
            if not conn.close:
                conn.close()
            if not cursor.close:
                cursor.close()

        except cx_Oracle.Error as e:
            print(f"ERRO AO DESCONECTAR DO BANCO DE DADOS: {e}")