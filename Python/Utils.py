import oracledb

class Utils:

    def connect(dsn):
            try:
                conn = oracledb.connect(user='RM96466', password='220693', dsn=dsn)
                return conn
            
            except oracledb.Error as e:
                print(f"ERRO AO CONECTAR COM O BANCO DE DADOS: {e}")

    def disconnect(conn, cursor):
        try:
            if not conn.close:
                conn.close()
            if not cursor.close:
                cursor.close()

        except oracledb.Error as e:
            print(f"ERRO AO DESCONECTAR DO BANCO DE DADOS: {e}")