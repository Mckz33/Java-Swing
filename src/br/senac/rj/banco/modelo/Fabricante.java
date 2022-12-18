package br.senac.rj.banco.modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Fabricante {
	private String nomeDono;
	private String nomeEmpresa;
	private int id;
	
	public Fabricante() {
		this.nomeDono = "";
	}

	Fabricante(int id) {
		this();
		this.id = id;
	}
	
	
	public String getNomeDono() {
		return nomeDono;
	}
	
	public String getNomeEmpresa() {
		return nomeEmpresa;
	}
	
	public int getId() {
		return id;
	}
	
	
	public void setNomeDono(String nomeDono) {
		this.nomeDono = nomeDono;
	}
	
	public void setNomeEmpresa(String nomeEmpresa) {
		this.nomeEmpresa = nomeEmpresa;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	
	public boolean cadastrarFabricante(String nomeDono, String nomeEmpresa, int id) {

		Connection conexao = null;
		try {
			conexao = Conexao.conectaBanco();
	
			String sql = "INSERT INTO fabricante SET nomeDono=?, nomeEmpresa=?, id=?";

			PreparedStatement ps = conexao.prepareStatement(sql);

			ps.setString(1, nomeDono);
			ps.setString(2, nomeEmpresa);
			ps.setInt(3, id);
			
			int totalRegistrosAfetados = ps.executeUpdate();
			if (totalRegistrosAfetados == 0) {
				System.out.println("Não foi feito o cadastro de fabricante corretamente!");
				return false;
			}
			System.out.println("Cadastro de fabricante realizado!");
			return true;
		} catch (SQLException erro) {
			System.out.println("Erro ao cadastrar o fabricante: " + erro.toString());
			return false;
		} finally {
			Conexao.fechaConexao(conexao);
		}
	}
	
	public boolean consultarFabricante(int id) {
		Connection conexao = null;
		try {
			conexao = Conexao.conectaBanco();

			String sql = "SELECT * FROM fabricante WHERE id=?";

			PreparedStatement ps = conexao.prepareStatement(sql);

			ps.setInt(1, id);

			ResultSet rs = ps.executeQuery();
			if (!rs.isBeforeFirst()) {
				System.out.println("Fabricante não cadastrado!");
				return false;
			} else {

				while (rs.next()) {
					this.nomeDono = rs.getString("nomeDono");
					this.nomeEmpresa = rs.getString("nomeEmpresa");
					this.id = rs.getInt("id");
				}
				return true;
			}
		} catch (SQLException erro) {
			System.out.println("Erro ao consultar o fabricante: " + erro.toString());
			return false;
		} finally {
			Conexao.fechaConexao(conexao);
		}
	}
	
	
	public boolean excluirFabricante(int id) {
		Connection conexao = null;
		PreparedStatement st  = null;
		try {
			conexao = Conexao.conectaBanco();
			st = conexao.prepareStatement(
					"DELETE FROM fabricante WHERE id = ?");
			
			st.setInt(1, id);
			
			int totalRegistrosAfetados = st.executeUpdate();
			if (totalRegistrosAfetados == 0)
				System.out.println("Não foi possivel deletar!");
			else
				System.out.println("Deletado com sucesso!");
			return true;
			
		} catch (SQLException erro) {
			System.out.println("Erro ao consultar o fabricante: " + erro.toString());
			return false;
		} finally {
			Conexao.fechaConexao(conexao);
		}
		
	}
	
}