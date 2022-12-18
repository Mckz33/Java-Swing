package br.senac.rj.banco.modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class Produto {
	
	private int id;
	private String nome;
	int validade;
	protected double quantidade;

	
	Produto() {
		this.nome = "";
	}

	Produto(int id) {
		this();
		this.id = id;
	}
	


	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public String getNome() {
		return nome;
	}



	public void setNome(String nome) {
		this.nome = nome;
	}



	public double getQuantidade() {
		return quantidade;
	}



	public void setQuantidade(double quantidade) {
		this.quantidade = quantidade;
	}



	public abstract boolean saca(double valor);

	public boolean cadastrarProduto(int id, double quantidade, String nome) {

		Connection conexao = null;
		try {
			conexao = Conexao.conectaBanco();

			String sql = "insert into produto set id=?, quantidade=?, nome=?";

			PreparedStatement ps = conexao.prepareStatement(sql);

			ps.setInt(1, id);
			ps.setDouble(2, quantidade);
			ps.setString(3, nome);
			
			int totalRegistrosAfetados = ps.executeUpdate();
			if (totalRegistrosAfetados == 0) {
				System.out.println("Não foi feito o cadastro!!");
				return false;
			}
			System.out.println("Cadastro realizado!");
			return true;
		} catch (SQLException erro) {
			System.out.println("Erro ao cadastrar o produto: " + erro.toString());
			return false;
		} finally {
			Conexao.fechaConexao(conexao);
		}
	}

	public boolean consultarProduto(int id) {
		// Define a conex�o
		Connection conexao = null;
		try {
			conexao = Conexao.conectaBanco();
			// Define a consulta
			String sql = "select * from produto where id=?";
			// Prepara a consulta
			PreparedStatement ps = conexao.prepareStatement(sql);
			// Define os par�metros da consulta
			ps.setInt(1, id); // Substitui o primeiro par�metro da consulta pela ag�ncia informada
			// Executa a consulta, resultando em um objeto da classe ResultSet
			ResultSet rs = ps.executeQuery();
			if (!rs.isBeforeFirst()) { // Verifica se n�o est� antes do primeiro registro
				System.out.println("Conta não cadastrada!");
				return false; // Conta n�o cadastrada
			} else {
				// Efetua a leitura do registro da tabela
				while (rs.next()) {
					this.id = rs.getInt("id");
					this.nome = rs.getString("nome");
					this.quantidade = rs.getDouble("quantidade");
				}
				return true;
			}
		} catch (SQLException erro) {
			System.out.println("Erro ao consultar a produto: " + erro.toString());
			return false;
		} finally {
			Conexao.fechaConexao(conexao);
		}
	}

	public boolean atualizarProduto(int id, String nome, double quantidade) {
		if (!consultarProduto(id))
			return false;
		else {
			// Define a conex�o
			Connection conexao = null;
			try {
				// Define a conex�o
				conexao = Conexao.conectaBanco();
				// Define a consulta
				String sql = "update produto set nome=?, quantidade=? where id=?";
				// Prepara a consulta
				PreparedStatement ps = conexao.prepareStatement(sql);
				// Define os par�metros da atualiza��o
				ps.setString(1, nome);
				ps.setDouble(2, quantidade);
				ps.setInt(3, id);
				int totalRegistrosAfetados = ps.executeUpdate();
				if (totalRegistrosAfetados == 0)
					System.out.println("Não foi feita a atualização!");
				else
					System.out.println("Atualização realizada!");
				return true;
			} catch (SQLException erro) {
				System.out.println("Erro ao atualizar a conta: " + erro.toString());
				return false;
			} finally {
				Conexao.fechaConexao(conexao);
			}
		}
	}
	
	
	public boolean excluirProduto(int id) {
		Connection conexao = null;
		PreparedStatement st  = null;
		try {
			conexao = Conexao.conectaBanco();
			st = conexao.prepareStatement(
					"DELETE FROM produto WHERE id = ?");
			
			st.setInt(1, id);
			int totalRegistrosAfetados = st.executeUpdate();
			if (totalRegistrosAfetados == 0)
				System.out.println("Não foi possivel deletar!");
			else
				System.out.println("Deletado com sucesso!");
			return true;
			
		} catch (SQLException erro) {
			System.out.println("Erro ao consultar a produto: " + erro.toString());
			return false;
		} finally {
			Conexao.fechaConexao(conexao);
		}
		
	}
	
}
