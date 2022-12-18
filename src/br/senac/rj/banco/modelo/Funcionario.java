package br.senac.rj.banco.modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Funcionario {
	private String nome;
	private String sobrenome;
	private int id;
	
	public Funcionario() {
		this.nome = "";
	}

	Funcionario(int id) {
		this();
		this.id = id;
	}
	
	
	public String getNome() {
		return nome;
	}
	
	public String getSobrenome() {
		return sobrenome;
	}
	
	public int getId() {
		return id;
	}
	
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	
	public boolean cadastrarFuncionario(String nome, String sobrenome, int id) {

		Connection conexao = null;
		try {
			conexao = Conexao.conectaBanco();
	
			String sql = "INSERT INTO funcionario SET nome=?, sobrenome=?, id=?";

			PreparedStatement ps = conexao.prepareStatement(sql);

			ps.setString(1, nome);
			ps.setString(2, sobrenome);
			ps.setInt(3, id);
			
			int totalRegistrosAfetados = ps.executeUpdate();
			if (totalRegistrosAfetados == 0) {
				System.out.println("Não foi feito o cadastro de funcionário corretamente!");
				return false;
			}
			System.out.println("Cadastro de funcionário realizado!");
			return true;
		} catch (SQLException erro) {
			System.out.println("Erro ao cadastrar o funcionário: " + erro.toString());
			return false;
		} finally {
			Conexao.fechaConexao(conexao);
		}
	}
	
	public boolean consultarFuncionario(int id) {
		Connection conexao = null;
		try {
			conexao = Conexao.conectaBanco();

			String sql = "SELECT * FROM funcionario WHERE id=?";

			PreparedStatement ps = conexao.prepareStatement(sql);

			ps.setInt(1, id);

			ResultSet rs = ps.executeQuery();
			if (!rs.isBeforeFirst()) {
				System.out.println("Funcionário não cadastrado!");
				return false;
			} else {

				while (rs.next()) {
					this.nome = rs.getString("nome");
					this.sobrenome = rs.getString("sobrenome");
					this.id = rs.getInt("id");
				}
				return true;
			}
		} catch (SQLException erro) {
			System.out.println("Erro ao consultar o funcionário: " + erro.toString());
			return false;
		} finally {
			Conexao.fechaConexao(conexao);
		}
	}
	
	
	public boolean excluirFuncionario(int id) {
		Connection conexao = null;
		PreparedStatement st  = null;
		try {
			conexao = Conexao.conectaBanco();
			st = conexao.prepareStatement(
					"DELETE FROM funcionario WHERE id = ?");
			
			st.setInt(1, id);
			
			int totalRegistrosAfetados = st.executeUpdate();
			if (totalRegistrosAfetados == 0)
				System.out.println("Não foi possivel deletar!");
			else
				System.out.println("Deletado com sucesso!");
			return true;
			
		} catch (SQLException erro) {
			System.out.println("Erro ao consultar o funcionário: " + erro.toString());
			return false;
		} finally {
			Conexao.fechaConexao(conexao);
		}
		
	}
	
}