package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import connection.ConnectionFactory;
import model.Autor;
import model.Livro;

public class AutorDAO {
	
	public HashMap<Integer, Autor> getAll() throws SQLException {
	    Connection conn = ConnectionFactory.getConnection();
	    Statement statement_autores = null;
	    ResultSet rs_autores = null;
	    HashMap<Integer, Autor> listaAutores = null;
	
	    try {
	        String query_autores = "SELECT * FROM autor ORDER BY nome";
	
	        statement_autores = conn.createStatement();
	
	        rs_autores = statement_autores.executeQuery(query_autores);
	
	        listaAutores = new HashMap<Integer, Autor>();
	        while (rs_autores.next()) {
	            Autor autor_banco = new Autor();
	            HashMap<Integer, Livro> listaLivrosAutor = new HashMap<Integer, Livro>();
	
	            autor_banco.setId_autor(Integer.parseInt(rs_autores.getString("id")));
	            autor_banco.setNome_autor(rs_autores.getString("nome"));
	            autor_banco.setEmail_autor(rs_autores.getString("email"));
	            autor_banco.setTelefone_autor(rs_autores.getString("telefone"));
	            autor_banco.setBio_autor(rs_autores.getString("bio"));
	            autor_banco.setImagem_autor(rs_autores.getString("imagem"));
	            
	            Statement statement_livros = null;
	    	    ResultSet rs_livros = null;
	            
	            try {
	            	String query_livros = "SELECT autor_livro.id_autor, autor_livro.id_livro, livro.titulo, livro.resumo, livro.ano, livro.paginas, livro.isbn, livro.id_categoria, livro.id_editora, livro.imagem, livro.preco, livro.desconto " +
		                    "FROM autor_livro " +
		                    "INNER JOIN livro ON autor_livro.id_livro = livro.id " +
		                    "WHERE autor_livro.id_autor = " + autor_banco.getId_autor() + " " +
		                    "ORDER BY autor_livro.id_livro";
		            
		            statement_livros = conn.createStatement();
		            rs_livros = statement_livros.executeQuery(query_livros);
		
		            while (rs_livros.next()) {
		                Livro livro_autor = new Livro();
		                livro_autor.setId_livro(rs_livros.getInt("id_livro"));
		                livro_autor.setTitulo_livro(rs_livros.getString("titulo"));
		                livro_autor.setResumo_livro(rs_livros.getString("resumo"));
		                livro_autor.setAno_livro(rs_livros.getInt("ano"));
		                livro_autor.setPaginas_livro(rs_livros.getInt("paginas"));
		                livro_autor.setIsbn_livro(rs_livros.getString("isbn"));
		                livro_autor.setId_categoria_livro(rs_livros.getInt("id_categoria"));
		                livro_autor.setId_editora_livro(rs_livros.getInt("id_editora"));
		                livro_autor.setImagem_livro(rs_livros.getString("imagem"));
		                livro_autor.setPreco_livro(rs_livros.getDouble("preco"));
		                livro_autor.setDesconto_livro(rs_livros.getDouble("desconto"));
		                
		                listaLivrosAutor.put(livro_autor.getId_livro(), livro_autor);
		                
		            }
		            
		            autor_banco.setLivros_autor(listaLivrosAutor);
	        		
		            listaAutores.put(autor_banco.getId_autor(), autor_banco);

	            } catch (SQLException e) {
	    	        System.out.println("ERRO AO LISTAR OS LIVROS DOS AUTORES. ERRO: " + e.getMessage());
	    	    } finally {
	    	        if (rs_livros != null) {
	    	            try {
	    	            	rs_livros.close();
	    	            } catch (SQLException e) {
	    	                System.out.println("ERRO AO FECHAR O RESULTSET DOS LIVROS DOS AUTORES. ERRO: " + e.getMessage());
	    	            }
	    	        }
	    	
	    	        if (statement_livros != null) {
	    	            try {
	    	            	statement_livros.close();
	    	            } catch (SQLException e) {
	    	                System.out.println("ERRO AO FECHAR O STATEMENT DOS LIVROS DOS AUTORES. ERRO: " + e.getMessage());
	    	            }
	    	        }
	    	    }
	        }

	    } catch (SQLException e) {
	        System.out.println("ERRO AO LISTAR OS AUTORES. ERRO: " + e.getMessage());
	        
	    } finally {
	        if (rs_autores != null) {
	            try {
	            	rs_autores.close();
	            } catch (SQLException e) {
	                System.out.println("ERRO AO FECHAR O RESULTSET. ERRO: " + e.getMessage());
	            }
	        }
	
	        if (statement_autores != null) {
	            try {
	            	statement_autores.close();
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
	
	public void insertLivrosAutor(Autor novo_autor) {
		Connection conn = ConnectionFactory.getConnection();
        Statement statement = null;
       
        try {
        	
        	if (novo_autor.getLivros_autor().size() == 0) {
        		if (conn != null) {
                    try {
                        conn.close();
                    } catch (SQLException e) {
                        System.out.println("ERRO AO FECHAR A CONNECTION. ERRO:  " + e.getMessage());
                    }
                }
        	}
        	
        	else {
        		for (Map.Entry<Integer, Livro> entry : novo_autor.getLivros_autor().entrySet()) {
            	    int id_autor = novo_autor.getId_autor();
            	    int id_livro = entry.getKey();
    	            String query = String.format("INSERT INTO autor_livro (id_autor, id_livro) VALUES (%s, %s)", id_autor, id_livro);
    	            statement = conn.createStatement();          
    	            statement.executeUpdate(query);
            	}
        	}
                
        } catch (SQLException e){
            System.out.println("ERRO AO INSERIR OS LIVROS DO AUTOR. ERRO: " + e.getMessage());
            
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
	
	public void updateLivrosAutor(Autor autor_atualizar) throws SQLException {
		Connection conn = ConnectionFactory.getConnection();
        Statement statement = null;
       
        try {
        	
        	if (autor_atualizar.getLivros_autor().size() == 0) {
        		String query = String.format("DELETE FROM autor_livro WHERE id_autor = %s", autor_atualizar.getId_autor());
	            statement = conn.createStatement();          
	            statement.executeUpdate(query);
	            
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
        	
            else {
        		String query = String.format("DELETE FROM autor_livro WHERE id_autor = %s", autor_atualizar.getId_autor());
	            statement = conn.createStatement();          
	            statement.executeUpdate(query);
	            
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
        		
        		conn = ConnectionFactory.getConnection();
                statement = null;
        		
        		for (Map.Entry<Integer, Livro> entry : autor_atualizar.getLivros_autor().entrySet()) {
            	    int id_autor = autor_atualizar.getId_autor();
            	    int id_livro = entry.getKey();
    	            query = String.format("INSERT INTO autor_livro (id_autor, id_livro) VALUES (%s, %s)", id_autor, id_livro);
    	            statement = conn.createStatement();          
    	            statement.executeUpdate(query);
            	}
        	}
                
        } catch (SQLException e){
            System.out.println("ERRO AO INSERIR OS AUTORES DO LIVRO. ERRO: " + e.getMessage());
            
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
	
	public void delete(int id_autor) throws SQLException {
        Connection conn = ConnectionFactory.getConnection();
        Statement statement = null;
        
        try {
            String query = String.format("DELETE FROM autor_livro WHERE id_autor = %s", id_autor);
           
            statement = conn.createStatement();          
            statement.executeUpdate(query);
            
        } catch (SQLException e){
            System.out.println("ERRO AO DELETAR OS LIVROS DO AUTOR. ERRO: " + e.getMessage());
            
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
        
        conn = ConnectionFactory.getConnection();
        statement = null;
       
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