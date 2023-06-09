package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import connection.ConnectionFactory;
import model.Categoria;

public class CategoriaDAO {
	
	public HashMap<Integer, Categoria> getAll() throws SQLException {
        Connection conn = ConnectionFactory.getConnection();
        Statement statement = null;
        ResultSet rs = null;
        HashMap<Integer, Categoria> listaCategorias = null;
       
        try {
            String query = "SELECT * FROM categoria ORDER BY nome";
            
            statement = conn.createStatement();
           
            rs = statement.executeQuery(query);
           
            listaCategorias = new HashMap<Integer, Categoria>(); 
            while(rs.next()){
            	Categoria categoria_banco = new Categoria();
            	categoria_banco.setId_categoria(Integer.parseInt(rs.getString("id")));
            	categoria_banco.setNome_categoria(rs.getString("nome"));
            	
                listaCategorias.put(categoria_banco.getId_categoria(), categoria_banco);
            }
            
        } catch (SQLException e){
            System.out.println("ERRO AO LISTAR AS CATEGORIAS. ERRO: " + e.getMessage());
            
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    System.out.println("ERRO AO FECHAR O RESULTSET. ERRO: " + e.getMessage());
                }
            }

            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    System.out.println("ERRO AO FECHAR O STATEMENT. ERRO: " + e.getMessage());
                }
            }

            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    System.out.println("ERRO AO FECHAR A CONNECTION. ERRO: " + e.getMessage());
                }
            }
	    }
        
        return listaCategorias;
	}
	
	public int getIdMax() throws SQLException {
	    Connection conn = ConnectionFactory.getConnection();
	    Statement statement = null;
	    ResultSet rs = null;
	    int idMax = -1;

	    try {
	        String query = "SELECT MAX(id) AS max_id FROM categoria";

	        statement = conn.createStatement();
	        rs = statement.executeQuery(query);

	        if (rs.next()) {
	            idMax = rs.getInt("max_id");
	        }
	    } catch (SQLException e){
            System.out.println("ERRO AO ENCONTRAR O ID MÁXIMO DAS CATEGORIAS. ERRO: " + e.getMessage());
            
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    System.out.println("ERRO AO FECHAR O RESULTSET. ERRO: " + e.getMessage());
                }
            }

            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    System.out.println("ERRO AO FECHAR O STATEMENT. ERRO: " + e.getMessage());
                }
            }

            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    System.out.println("ERRO AO FECHAR A CONNECTION. ERRO: " + e.getMessage());
                }
            }
	    }
	    
	    return idMax;
	}
	
	public boolean insert(Categoria nova_categoria) throws SQLException {
        Connection conn = ConnectionFactory.getConnection();
        Statement statement = null;
       
        try {
            String query = String.format("INSERT INTO categoria (id, nome) VALUES (%s, '%s')", nova_categoria.getId_categoria(), nova_categoria.getNome_categoria());
            statement = conn.createStatement();          
            statement.executeUpdate(query);
            return true;
            
        } catch (SQLException e){
            System.out.println("ERRO AO INSERIR A CATEGORIA. ERRO: " + e.getMessage());
            return false;
            
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    System.out.println("ERRO AO FECHAR O STATEMENT. ERRO: " + e.getMessage());
                }
            }

            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    System.out.println("ERRO AO FECHAR A CONNECTION. ERRO:  " + e.getMessage());
                }
            }
	    }
	}
	
	public boolean update(Categoria categoria_atualizar) throws SQLException {
        Connection conn = ConnectionFactory.getConnection();
        Statement statement = null;
       
        try {
            String query = String.format("UPDATE categoria SET nome = '%s' WHERE id = %s", categoria_atualizar.getNome_categoria(), categoria_atualizar.getId_categoria());
           
            statement = conn.createStatement();          
            statement.executeUpdate(query);
            return true;
            
        } catch (SQLException e){
            System.out.println("ERRO AO ATUALIZAR A CATEGORIA. ERRO: " + e.getMessage());
            return false;
            
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    System.out.println("ERRO AO FECHAR O STATEMENT. ERRO:  " + e.getMessage());
                }
            }

            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    System.out.println("ERRO AO FECHAR A CONNECTION. ERRO:  " + e.getMessage());
                }
            }
	    }
	}
	
	public boolean delete(int id_categoria) throws SQLException {
        Connection conn = ConnectionFactory.getConnection();
        Statement statement = null;
       
        try {
            String query = String.format("DELETE FROM categoria WHERE id = %s", id_categoria);
           
            statement = conn.createStatement();          
            statement.executeUpdate(query);
            return true;
            
        } catch (SQLException e){
            System.out.println("ERRO AO DELETAR A CATEGORIAS. ERRO: " + e.getMessage());
            return false;
            
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    System.out.println("ERRO AO FECHAR O STATEMENT. ERRO:  " + e.getMessage());
                }
            }

            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    System.out.println("ERRO AO FECHAR A CONNECTION. ERRO:  " + e.getMessage());
                }
            }
	    }
	}
}