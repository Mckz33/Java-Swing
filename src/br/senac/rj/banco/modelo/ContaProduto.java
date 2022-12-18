package br.senac.rj.banco.modelo;

public class ContaProduto extends Produto {
	public boolean saca (double quantidade) {
		double novaQuantidade = this.quantidade - quantidade;
		if (novaQuantidade < 0) 
			return false;
		this.quantidade = novaQuantidade;
		return true;
	}

}