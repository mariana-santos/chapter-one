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

public class LivroDAO {

	public HashMap<Integer, Livro> getAll() throws SQLException {
        Connection conn = ConnectionFactory.getConnection();
        Statement statement_livros = null;
        ResultSet rs_livros = null;
        HashMap<Integer, Livro> listaLivros = null;
       
        try {
            String query_livros = "SELECT * FROM livro ORDER BY titulo";
            
            statement_livros = conn.createStatement();
           
            rs_livros = statement_livros.executeQuery(query_livros);
           
            listaLivros = new HashMap<Integer, Livro>(); 
            while(rs_livros.next()){
            	Livro livro_banco = new Livro();
            	HashMap<Integer, Autor> listaAutoresLivros = new HashMap<Integer, Autor>();
            	
            	livro_banco.setId_livro(Integer.parseInt(rs_livros.getString("id")));
            	livro_banco.setTitulo_livro(rs_livros.getString("titulo"));
            	livro_banco.setResumo_livro(rs_livros.getString("resumo"));
            	livro_banco.setAno_livro(rs_livros.getInt("ano"));
            	livro_banco.setPaginas_livro(rs_livros.getInt("paginas"));
            	livro_banco.setIsbn_livro(rs_livros.getString("isbn"));
            	livro_banco.setId_categoria_livro(rs_livros.getInt("id_categoria"));
            	livro_banco.setId_editora_livro(rs_livros.getInt("id_editora"));
            	livro_banco.setImagem_livro(rs_livros.getString("imagem"));
            	livro_banco.setPreco_livro(rs_livros.getDouble("preco"));
            	livro_banco.setDesconto_livro(rs_livros.getDouble("desconto"));
            	
            	Statement statement_autores = null;
	    	    ResultSet rs_autores = null;
	    	    
	    	    try {
	    	    	String query_autores = "SELECT autor_livro.id_autor, autor.nome, autor.email, autor.telefone, autor.bio, autor.imagem, autor_livro.id_livro " +
		                    "FROM autor_livro " +
		                    "INNER JOIN autor ON autor_livro.id_livro = autor.id " +
		                    "WHERE autor_livro.id_livro = " + livro_banco.getId_livro() + " " +
		                    "ORDER BY autor_livro.id_autor";
	    	    	
	    	    	statement_autores = conn.createStatement();
		            rs_autores = statement_autores.executeQuery(query_autores);
		            
		            while (rs_autores.next()) {
		                Autor autor_livro = new Autor();
		                autor_livro.setId_autor(rs_autores.getInt("id_autor"));
		                autor_livro.setNome_autor(rs_autores.getString("nome"));
		                autor_livro.setEmail_autor(rs_autores.getString("email"));
		                autor_livro.setTelefone_autor(rs_autores.getString("telefone"));
		                autor_livro.setBio_autor(rs_autores.getString("bio"));
		                autor_livro.setImagem_autor(rs_autores.getString("imagem"));
		                
		                listaAutoresLivros.put(autor_livro.getId_autor(), autor_livro);

		            }
		            
		            livro_banco.setAutores_livro(listaAutoresLivros);
	        		
		            listaLivros.put(livro_banco.getId_livro(), livro_banco);
		            
	            } catch (SQLException e) {
	    	        System.out.println("ERRO AO LISTAR OS AUTORES DOS LIVROS. ERRO: " + e.getMessage());
	    	    } finally {
	    	        if (rs_autores != null) {
	    	            try {
	    	            	rs_autores.close();
	    	            } catch (SQLException e) {
	    	                System.out.println("ERRO AO FECHAR O RESULTSET DOS AUTORES DOS LIVROS. ERRO: " + e.getMessage());
	    	            }
	    	        }
	    	
	    	        if (statement_autores != null) {
	    	            try {
	    	            	statement_autores.close();
	    	            } catch (SQLException e) {
	    	                System.out.println("ERRO AO FECHAR O STATEMENT DOS AUTORES DOS LIVROS. ERRO: " + e.getMessage());
	    	            }
	    	        }
	    	    }
    	    }

        } catch (SQLException e){
            System.out.println("ERRO AO LISTAR OS LIVROS. ERRO: " + e.getMessage());
            
        } finally {
            if (rs_livros != null) {
                try {
                	rs_livros.close();
                } catch (SQLException e) {
                    System.out.println("ERRO AO FECHAR O RESULTSET. ERRO: " + e.getMessage());
                }
            }

            if (statement_livros != null) {
                try {
                	statement_livros.close();
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
		} catch (SQLException e) {
			System.out.println("ERRO AO ENCONTRAR O ID MÁXIMO DOS LIVROS. ERRO: " + e.getMessage());

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

	public boolean insert(Livro novo_livro) throws SQLException {
		Connection conn = ConnectionFactory.getConnection();
		Statement statement = null;

		try {
			String query = String.format(
					"INSERT INTO livro (id, titulo, resumo, ano, paginas, isbn, id_categoria, id_editora, imagem, preco, desconto) VALUES (%s, '%s', '%s', %s, %s, '%s', %s, %s, '%s', %s, %s)",
					novo_livro.getId_livro(), novo_livro.getTitulo_livro(), novo_livro.getResumo_livro(),
					novo_livro.getAno_livro(), novo_livro.getPaginas_livro(), novo_livro.getIsbn_livro(),
					novo_livro.getId_categoria_livro(), novo_livro.getId_editora_livro(), novo_livro.getImagem_livro(),
					novo_livro.getPreco_livro(), novo_livro.getDesconto_livro());
			statement = conn.createStatement();
			statement.executeUpdate(query);
			return true;

		} catch (SQLException e) {
			System.out.println("ERRO AO INSERIR O LIVRO. ERRO: " + e.getMessage());
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

	public boolean insertAutoresLivro(Livro novo_livro) {
		Connection conn = ConnectionFactory.getConnection();
		Statement statement = null;

		try {

			if (novo_livro.getAutores_livro().size() == 0) {
				if (conn != null) {
					try {
						conn.close();
					} catch (SQLException e) {
						System.out.println("ERRO AO FECHAR A CONNECTION. ERRO:  " + e.getMessage());
					}
				}
				
				return true;
			}

			else {
				for (Map.Entry<Integer, Autor> entry : novo_livro.getAutores_livro().entrySet()) {
					int id_autor = entry.getKey();
					int id_livro = novo_livro.getId_livro();
					String query = String.format("INSERT INTO autor_livro (id_autor, id_livro) VALUES (%s, %s)",
							id_autor, id_livro);
					statement = conn.createStatement();
					statement.executeUpdate(query);
				}
				
				return true;
			}

		} catch (SQLException e) {
			System.out.println("ERRO AO INSERIR OS AUTORES DO LIVRO. ERRO: " + e.getMessage());
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

	public boolean update(Livro livro_atualizar) throws SQLException {
		Connection conn = ConnectionFactory.getConnection();
		Statement statement = null;

		try {
			String query = String.format(
					"UPDATE livro SET titulo = '%s', resumo = '%s', ano = %s, paginas = %s, isbn = '%s', id_categoria = %s, id_editora = %s, imagem = '%s', preco = %s, desconto = %s WHERE id = %s",
					livro_atualizar.getTitulo_livro(), livro_atualizar.getResumo_livro(),
					livro_atualizar.getAno_livro(), livro_atualizar.getPaginas_livro(), livro_atualizar.getIsbn_livro(),
					livro_atualizar.getId_categoria_livro(), livro_atualizar.getId_editora_livro(),
					livro_atualizar.getImagem_livro(), livro_atualizar.getPreco_livro(),
					livro_atualizar.getDesconto_livro(), livro_atualizar.getId_livro());

			statement = conn.createStatement();
			statement.executeUpdate(query);
			return true;

		} catch (Exception e) {
			System.out.println("ERRO AO ATUALIZAR O LIVRO! ERRO: " + e);
			return false;

		} finally {

			if (statement != null) {
				statement.close();
			}

			if (conn != null) {
				conn.close();
			}
		}
	}

	public boolean updateAutoresLivro(Livro livro_atualizar) {
		Connection conn = ConnectionFactory.getConnection();
		Statement statement = null;

		try {

			if (livro_atualizar.getAutores_livro().size() == 0) {
				String query = String.format("DELETE FROM autor_livro WHERE id_livro = %s",
						livro_atualizar.getId_livro());
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
				
				return true;
			}

			else {
				String query = String.format("DELETE FROM autor_livro WHERE id_livro = %s",
						livro_atualizar.getId_livro());
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

				for (Map.Entry<Integer, Autor> entry : livro_atualizar.getAutores_livro().entrySet()) {
					int id_autor = entry.getKey();
					int id_livro = livro_atualizar.getId_livro();
					query = String.format("INSERT INTO autor_livro (id_autor, id_livro) VALUES (%s, %s)", id_autor,
							id_livro);
					statement = conn.createStatement();
					statement.executeUpdate(query);
				}
				
				return true;
			}

		} catch (SQLException e) {
			System.out.println("ERRO AO INSERIR OS AUTORES DO LIVRO. ERRO: " + e.getMessage());
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

	public boolean delete(int id_livro) throws SQLException {
		Connection conn = ConnectionFactory.getConnection();
		Statement statement = null;

		try {
			String query = String.format("DELETE FROM autor_livro WHERE id_livro = %s", id_livro);

			statement = conn.createStatement();
			statement.executeUpdate(query);

		} catch (SQLException e) {
			System.out.println("ERRO AO DELETAR OS AUTORES DO LIVRO. ERRO: " + e.getMessage());
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

		conn = ConnectionFactory.getConnection();
		statement = null;

		try {
			String query = String.format("DELETE FROM livro WHERE id = %s", id_livro);

			statement = conn.createStatement();
			statement.executeUpdate(query);
			return true;

		} catch (SQLException e) {
			System.out.println("ERRO AO DELETAR O LIVRO. ERRO: " + e.getMessage());
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
