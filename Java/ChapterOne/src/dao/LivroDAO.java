package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import connection.ConnectionFactory;
import model.Livro;

public class LivroDAO {
	
	public HashMap<Integer, Livro> getAll() throws SQLException {
        Connection conn = ConnectionFactory.getConnection();
        Statement statement = null;
        ResultSet rs = null;
        HashMap<Integer, Livro> listaLivros = null;
       
        try {
            String query = "SELECT * FROM livro ORDER BY titulo";
            
            statement = conn.createStatement();
           
            rs = statement.executeQuery(query);
           
            listaLivros = new HashMap<Integer, Livro>(); 
            while(rs.next()){
            	Livro livro_banco = new Livro();
            	livro_banco.setId_livro(Integer.parseInt(rs.getString("id")));
            	livro_banco.setTitulo_livro(rs.getString("titulo"));
            	livro_banco.setResumo_livro(rs.getString("resumo"));
            	livro_banco.setAno_livro(rs.getInt("ano"));
            	livro_banco.setPaginas_livro(rs.getInt("paginas"));
            	livro_banco.setIsbn_livro(rs.getString("isbn"));
            	livro_banco.setId_categoria_livro(rs.getInt("id_categoria"));
            	livro_banco.setId_editora_livro(rs.getInt("id_editora"));
            	livro_banco.setImagem_livro(rs.getString("imagem"));
            	livro_banco.setPreco_livro(rs.getDouble("preco"));
            	livro_banco.setDesconto_livro(rs.getDouble("desconto"));
            	
                listaLivros.put(livro_banco.getId_livro(), livro_banco);
            }
            
        } catch (Exception e){
            System.out.println("ERRO AO LISTAR OS LIVROS! ERRO: " + e);
            
        } finally {
	    	
	        if (rs != null) {
	            rs.close();
	        }
	        
	        if (statement != null) {
	            statement.close();
	        }
	        
	        if (conn != null) {
	            conn.close();
	        }
	    }
        
        return listaLivros;
	}
	
	public int getIdMax() throws SQLException {
	    Connection conn = ConnectionFactory.getConnection();
	    Statement statement = null;
	    ResultSet rs = null;
	    int idMax = -1;

	    try {
	        String query = "SELECT MAX(id) AS max_id FROM livro";

	        statement = conn.createStatement();
	        rs = statement.executeQuery(query);

	        if (rs.next()) {
	            idMax = rs.getInt("max_id");
	        }
	    } finally {
	    	
	        if (rs != null) {
	            rs.close();
	        }
	        
	        if (statement != null) {
	            statement.close();
	        }
	        
	        if (conn != null) {
	            conn.close();
	        }
	    }

	    return idMax;
	}

	public void insert(Livro novo_livro) throws SQLException {
        Connection conn = ConnectionFactory.getConnection();
        Statement statement = null;
       
        try {
            String query = String.format("INSERT INTO livro (id, titulo, resumo, ano, paginas, isbn, id_categoria, id_editora, imagem, preco, desconto) VALUES (%s, '%s', '%s', %s, %s, '%s', %s, %s, '%s', %s, %s)", novo_livro.getId_livro(), novo_livro.getTitulo_livro(), novo_livro.getResumo_livro(), novo_livro.getAno_livro(), novo_livro.getPaginas_livro(), novo_livro.getIsbn_livro(), novo_livro.getId_categoria_livro(), novo_livro.getId_editora_livro(), novo_livro.getImagem_livro(), novo_livro.getPreco_livro(), novo_livro.getDesconto_livro());
            statement = conn.createStatement();          
            statement.executeUpdate(query);
            
        } catch (Exception e){
            System.out.println("ERRO AO INSERIR O LIVRO! ERRO: " + e);
            
        } finally {
        	
	        if (statement != null) {
	            statement.close();
	        }
	        
	        if (conn != null) {
	            conn.close();
	        }
	    }
	}
	
	public void update(Livro livro_atualizar) throws SQLException {
        Connection conn = ConnectionFactory.getConnection();
        Statement statement = null;
       
        try {
            String query = String.format("UPDATE livro SET titulo = '%s', resumo = '%s', ano = %s, paginas = %s, isbn = '%s', id_categoria = %s, id_editora = %s, imagem = '%s', preco = %s, desconto = %s WHERE id = %s", livro_atualizar.getTitulo_livro(), livro_atualizar.getResumo_livro(), livro_atualizar.getAno_livro(), livro_atualizar.getPaginas_livro(), livro_atualizar.getIsbn_livro(), livro_atualizar.getId_categoria_livro(), livro_atualizar.getId_editora_livro(), livro_atualizar.getImagem_livro(), livro_atualizar.getPreco_livro(), livro_atualizar.getDesconto_livro(), livro_atualizar.getId_livro());
           
            statement = conn.createStatement();          
            statement.executeUpdate(query);
            
        } catch (Exception e){
            System.out.println("ERRO AO ATUALIZAR O LIVRO! ERRO: " + e);
            
        } finally {
        	
	        if (statement != null) {
	            statement.close();
	        }
	        
	        if (conn != null) {
	            conn.close();
	        }
	    }
	}
	
	public void delete(int id_livro) throws SQLException {
        Connection conn = ConnectionFactory.getConnection();
        Statement statement = null;
       
        try {
            String query = String.format("DELETE FROM livro WHERE id = %s", id_livro);
           
            statement = conn.createStatement();          
            statement.executeUpdate(query);
            
        } catch (Exception e){
            System.out.println("ERRO AO EXCLUIR O LIVRO! ERRO: " + e);
            
        } finally {
        	
	        if (statement != null) {
	            statement.close();
	        }
	        
	        if (conn != null) {
	            conn.close();
	        }
	    }
	}
	
}

