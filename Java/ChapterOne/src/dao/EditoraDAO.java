package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import connection.ConnectionFactory;
import model.Editora;

public class EditoraDAO {
	
	public HashMap<Integer, Editora> getAll() throws SQLException {
        Connection conn = ConnectionFactory.getConnection();
        Statement statement = null;
        ResultSet rs = null;
        HashMap<Integer, Editora> listaEditoras = null;
       
        try {
            String query = "SELECT * FROM editora ORDER BY nome";
            
            statement = conn.createStatement();
           
            rs = statement.executeQuery(query);
           
            listaEditoras = new HashMap<Integer, Editora>(); 
            while(rs.next()){
            	Editora editora_banco = new Editora();
            	editora_banco.setId_editora(Integer.parseInt(rs.getString("id")));
            	editora_banco.setNome_editora(rs.getString("nome"));
            	editora_banco.setEndereco_editora(rs.getString("endereco"));
            	editora_banco.setTelefone_editora(rs.getString("telefone"));
            	
                listaEditoras.put(editora_banco.getId_editora(), editora_banco);
            }
            
        } catch (SQLException e){
            System.out.println("ERRO AO LISTAR AS EDITORAS ERRO: " + e.getMessage());
            
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
        
        return listaEditoras;
	}
	
	public int getIdMax() throws SQLException {
	    Connection conn = ConnectionFactory.getConnection();
	    Statement statement = null;
	    ResultSet rs = null;
	    int idMax = -1;

	    try {
	        String query = "SELECT MAX(id) AS max_id FROM editora";

	        statement = conn.createStatement();
	        rs = statement.executeQuery(query);

	        if (rs.next()) {
	            idMax = rs.getInt("max_id");
	        }
	    } catch (SQLException e){
            System.out.println("ERRO AO ENCONTRAR O ID MÁXIMO DAS EDITORAS. ERRO: " + e.getMessage());
            
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

	    return idMax;
	}
	
	public void insert(Editora nova_editora) throws SQLException {
        Connection conn = ConnectionFactory.getConnection();
        Statement statement = null;
       
        try {
            String query = String.format("INSERT INTO editora (id, nome, endereco, telefone) VALUES (%s, '%s', '%s', '%s')", nova_editora.getId_editora(), nova_editora.getNome_editora(), nova_editora.getEndereco_editora(), nova_editora.getTelefone_editora());
            statement = conn.createStatement();          
            statement.executeUpdate(query);
            
        } catch (SQLException e){
            System.out.println("ERRO AO INSERIR A EDITORA. ERRO: " + e.getMessage());
            
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
	
	public void update(Editora editora_atualizar) throws SQLException {
        Connection conn = ConnectionFactory.getConnection();
        Statement statement = null;
       
        try {
            String query = String.format("UPDATE editora SET nome = '%s', endereco = '%s', telefone = '%s' WHERE id = %s", editora_atualizar.getNome_editora(), editora_atualizar.getEndereco_editora(), editora_atualizar.getTelefone_editora(), editora_atualizar.getId_editora());
           
            statement = conn.createStatement();          
            statement.executeUpdate(query);
            
        } catch (SQLException e){
            System.out.println("ERRO AO ATUALIZAR A EDITORA. ERRO: " + e.getMessage());
            
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
	
	public void delete(int id_editora) throws SQLException {
        Connection conn = ConnectionFactory.getConnection();
        Statement statement = null;
       
        try {
            String query = String.format("DELETE FROM editora WHERE id = %s", id_editora);
           
            statement = conn.createStatement();          
            statement.executeUpdate(query);
            
        } catch (SQLException e){
            System.out.println("ERRO AO DELETAR A EDITORA. ERRO: " + e.getMessage());
            
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