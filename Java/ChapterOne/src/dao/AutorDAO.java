package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import connection.ConnectionFactory;
import model.Autor;

public class AutorDAO {
	
	public HashMap<Integer, Autor> getAll() throws SQLException {
        Connection conn = ConnectionFactory.getConnection();
        Statement statement = null;
        ResultSet rs = null;
        HashMap<Integer, Autor> listaAutores = null;
       
        try {
            String query = "SELECT * FROM autor ORDER BY nome";
            
            statement = conn.createStatement();
           
            rs = statement.executeQuery(query);
           
            listaAutores = new HashMap<Integer, Autor>(); 
            while(rs.next()){
            	Autor autor_banco = new Autor();
            	autor_banco.setId_autor(Integer.parseInt(rs.getString("id")));
            	autor_banco.setNome_autor(rs.getString("nome"));
            	autor_banco.setEmail_autor(rs.getString("email"));
            	autor_banco.setTelefone_autor(rs.getString("telefone"));
            	autor_banco.setBio_autor(rs.getString("bio"));
            	autor_banco.setImagem_autor(rs.getString("imagem"));
            	
                listaAutores.put(autor_banco.getId_autor(), autor_banco);
            }
            
        } catch (SQLException e){
            System.out.println("ERRO AO LISTAR OS AUTORES. ERRO: " + e.getMessage());
            
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
                    System.out.println("ERRO AO FECHAR O STATEMENT: " + e.getMessage());
                }
            }

            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    System.out.println("ERRO AO FECHAR A CONNECTION: " + e.getMessage());
                }
            }
	    }
        
        return listaAutores;
	}
	
	public int getIdMax() throws SQLException {
	    Connection conn = ConnectionFactory.getConnection();
	    Statement statement = null;
	    ResultSet rs = null;
	    int idMax = -1;

	    try {
	        String query = "SELECT MAX(id) AS max_id FROM autor";

	        statement = conn.createStatement();
	        rs = statement.executeQuery(query);

	        if (rs.next()) {
	            idMax = rs.getInt("max_id");
	        }
	        
	    } catch (SQLException e){
            System.out.println("ERRO AO ENCONTRAR O ID MÁXIMO DOS AUTORES. ERRO: " + e.getMessage());
            
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
                    System.out.println("ERRO AO FECHAR O STATEMENT: " + e.getMessage());
                }
            }

            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    System.out.println("ERRO AO FECHAR A CONNECTION: " + e.getMessage());
                }
            }
	    }

	    return idMax;
	}
	
	public void insert(Autor novo_autor) throws SQLException {
        Connection conn = ConnectionFactory.getConnection();
        Statement statement = null;
       
        try {
            String query = String.format("INSERT INTO autor (id, nome, email, telefone, bio, imagem) VALUES (%s, '%s', '%s', '%s', '%s', '%s')", novo_autor.getId_autor(), novo_autor.getNome_autor(), novo_autor.getEmail_autor(), novo_autor.getTelefone_autor(), novo_autor.getBio_autor(), novo_autor.getImagem_autor());
            statement = conn.createStatement();          
            statement.executeUpdate(query);
            
        } catch (SQLException e){
            System.out.println("ERRO AO INSERIR O AUTOR. ERRO: " + e.getMessage());
            
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    System.out.println("ERRO AO FECHAR O STATEMENT: " + e.getMessage());
                }
            }

            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    System.out.println("ERRO AO FECHAR A CONNECTION: " + e.getMessage());
                }
            }
	    }
	}
	
	public void update(Autor autor_atualizar) throws SQLException {
        Connection conn = ConnectionFactory.getConnection();
        Statement statement = null;
       
        try {
            String query = String.format("UPDATE autor SET nome = '%s', email = '%s', telefone = '%s', bio = '%s', imagem = '%s' WHERE id = %s", autor_atualizar.getNome_autor(), autor_atualizar.getEmail_autor(), autor_atualizar.getTelefone_autor(), autor_atualizar.getBio_autor(), autor_atualizar.getImagem_autor(), autor_atualizar.getId_autor());
           
            statement = conn.createStatement();          
            statement.executeUpdate(query);
            
        } catch (SQLException e){
            System.out.println("ERRO AO ATUALIZAR O AUTOR. ERRO: " + e.getMessage());
            
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    System.out.println("ERRO AO FECHAR O STATEMENT: " + e.getMessage());
                }
            }

            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    System.out.println("ERRO AO FECHAR A CONNECTION: " + e.getMessage());
                }
            }
	    }
	}
	
	public void delete(int id_autor) throws SQLException {
        Connection conn = ConnectionFactory.getConnection();
        Statement statement = null;
       
        try {
            String query = String.format("DELETE FROM autor WHERE id = %s", id_autor);
           
            statement = conn.createStatement();          
            statement.executeUpdate(query);
            
        } catch (SQLException e){
            System.out.println("ERRO AO DELETAR O AUTOR. ERRO: " + e.getMessage());
            
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    System.out.println("ERRO AO FECHAR O STATEMENT: " + e.getMessage());
                }
            }

            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    System.out.println("ERRO AO FECHAR A CONNECTION: " + e.getMessage());
                }
            }
	    }
	}
}