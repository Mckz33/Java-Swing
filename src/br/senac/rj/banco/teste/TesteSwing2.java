package br.senac.rj.banco.teste;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import br.senac.rj.banco.modelo.ContaProduto;
import br.senac.rj.banco.modelo.Fabricante;
import br.senac.rj.banco.modelo.Funcionario;


public class TesteSwing2 {

	public static void menuPrincipal() {
		JFrame janelaPrincipal = new JFrame("Cadastro de produtos");
		janelaPrincipal.setTitle("Painel de Controle.");
		janelaPrincipal.setResizable(true);
		janelaPrincipal.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		janelaPrincipal.setSize(400, 300);
		
		JMenuBar menuBar = new JMenuBar();
		janelaPrincipal.setJMenuBar(menuBar);
		
		JMenu menuAtualizar = new JMenu("Painel");
		menuBar.add(menuAtualizar);
		
		JMenuItem menuFuncionario = new JMenuItem("Funcionário");
		JMenuItem menuFabricante = new JMenuItem("Fabricante");
		JMenuItem menuConta = new JMenuItem("Produto");

		menuAtualizar.add(menuFuncionario);
		menuAtualizar.add(menuFabricante);
		menuAtualizar.add(menuConta);

		JFrame janelaConta = criarJanelaProduto();
		JFrame janelaFuncionario = criarJanelaFuncionario();
		JFrame janelaFabricante = criarJanelaFabricante();

		menuConta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				janelaConta.setVisible(true);
			}
		});
		
		menuFuncionario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				janelaFuncionario.setVisible(true);
			}
		});
		
		menuFabricante.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				janelaFabricante.setVisible(true);
			}
		});
		
		janelaPrincipal.setVisible(true);
	}
	
	// *** PRODUTO ***
	public static JFrame criarJanelaProduto() {
		JFrame janelaConta = new JFrame("Atualização de produtos");
		janelaConta.setResizable(false);
		janelaConta.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		janelaConta.setSize(400, 300);

		Container caixa = janelaConta.getContentPane();
		caixa.setLayout(null);

		JLabel labelId = new JLabel("ID: ");
		JLabel labelQuantidade = new JLabel("Quantidade: ");
		JLabel labelNome = new JLabel("Nome: ");

		labelId.setBounds(50, 40, 100, 20);
		labelQuantidade.setBounds(50, 80, 150, 20);
		labelNome.setBounds(50, 120, 100, 20);

		JTextField jTextId = new JTextField();
		JTextField jTextQuantidade = new JTextField();
		JTextField jTextNome = new JTextField();

		jTextId.setEnabled(true);
		jTextQuantidade.setEnabled(false); 
		jTextNome.setEnabled(false);

		jTextId.setBounds(180, 40, 50, 20);
		jTextQuantidade.setBounds(180, 80, 50, 20);
		jTextNome.setBounds(180, 120, 150, 20);

		janelaConta.add(labelId);
		janelaConta.add(labelQuantidade);
		janelaConta.add(labelNome);
		janelaConta.add(jTextId);
		janelaConta.add(jTextQuantidade); 
		janelaConta.add(jTextNome);

		JButton botaoConsultar = new JButton("Consultar");
		botaoConsultar.setBounds(230, 80, 100, 20);
		janelaConta.add(botaoConsultar);
		
		JButton botaoDeletar = new JButton("Deletar");
		botaoDeletar.setBounds(160, 200, 80, 20);
		janelaConta.add(botaoDeletar);
		
		JButton botaoGravar = new JButton("Gravar");
		botaoGravar.setBounds(50, 200, 100, 20);
		botaoGravar.setEnabled(false);
		janelaConta.add(botaoGravar);
		
		JButton botaoLimpar = new JButton("Resetar");
		botaoLimpar.setBounds(250, 200, 100, 20);
		janelaConta.add(botaoLimpar);
		
		var produto = new ContaProduto();
		
		botaoConsultar.addActionListener(new ActionListener() {		
			public void actionPerformed(ActionEvent e) {
				try {
					int id = Integer.parseInt(jTextId.getText());
					int quantidade = (int) produto.getQuantidade();
					botaoGravar.setEnabled(true);
					String nome;
					if (!produto.consultarProduto(id)) {
						nome = "";
						produto.setQuantidade(0);
					}
						
					else
						nome = produto.getNome();
						quantidade = (int) produto.getQuantidade();
						
					jTextNome.setText(nome);
					jTextQuantidade.setText(Integer.toString(quantidade));
					jTextId.setEnabled(false);
					jTextQuantidade.setEnabled(true);
					botaoConsultar.setEnabled(false);
					jTextNome.setEnabled(true);
					jTextNome.requestFocus();
				} catch (Exception erro) {
					JOptionPane.showMessageDialog(janelaConta,
							"Preencha o campo corretamente!!");
				}
			}
		});
				
			
			botaoGravar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int resposta = JOptionPane.showConfirmDialog(janelaConta, "Deseja atualizar?", "Confirmação",
							JOptionPane.YES_NO_OPTION);
					if (resposta == JOptionPane.YES_OPTION) {
						int id = Integer.parseInt(jTextId.getText());
						double quantidade = Integer.parseInt(jTextQuantidade.getText());
						String nome = jTextNome.getText().trim();
						if (nome.length() == 0) {
							JOptionPane.showMessageDialog(janelaConta, "Preencha o campo nome");
							jTextNome.requestFocus();
						} else {
							if (!produto.consultarProduto(id)) {
								if (!produto.cadastrarProduto(id, quantidade, nome))
									JOptionPane.showMessageDialog(janelaConta, "Erro na inclusão do nome!");
								else
									JOptionPane.showMessageDialog(janelaConta, "Inclusão realizada!");
							} else {
								if (!produto.atualizarProduto(id, nome, quantidade))
									JOptionPane.showMessageDialog(janelaConta, "Erro na atualização do nome!");
								else
									JOptionPane.showMessageDialog(janelaConta, "Alteração realizada!");
							}

						}

					}
				}
			});
			
			botaoLimpar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					jTextId.setText("");
					jTextQuantidade.setText("");
					jTextNome.setText("");
					jTextId.setEnabled(true);
					jTextQuantidade.setEnabled(false);
					jTextNome.setEnabled(false);
					botaoConsultar.setEnabled(true);
					botaoGravar.setEnabled(false);
					jTextId.requestFocus();				
				}
			});
			
			
			
			botaoDeletar.addActionListener(new ActionListener() {		
			public void actionPerformed(ActionEvent e) {

				try {
					int resposta = JOptionPane.showConfirmDialog(janelaConta, "Deseja Remover?", "Confirmação",
							JOptionPane.YES_NO_OPTION);
					if (resposta == JOptionPane.YES_OPTION) {
						int id = Integer.parseInt(jTextId.getText());
						produto.excluirProduto(id);
						jTextId.setText("");
						jTextQuantidade.setText("");
						jTextNome.setText("");
						jTextId.setEnabled(true);
						jTextQuantidade.setEnabled(false);
						jTextNome.setEnabled(false);
						botaoConsultar.setEnabled(true);
						
					}
				}
				catch(Exception erro) {
					JOptionPane.showMessageDialog(null, "Impossível de remover produto não cadastrado.");
				}

			}});
			
		
		return janelaConta;
	}
	
	// *** FUNCIONÁRIO ***
	public static JFrame criarJanelaFuncionario() {
		JFrame janelaConta = new JFrame("Cadastro de Funcionários");
		janelaConta.setResizable(true);
		janelaConta.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		janelaConta.setSize(400, 300);

		Container caixa = janelaConta.getContentPane();
		caixa.setLayout(null);

		JLabel labelNome = new JLabel("Nome: ");
		JLabel labelSobrenome = new JLabel("Sobrenome: ");
		JLabel labelId = new JLabel("ID: ");

		labelNome.setBounds(50, 40, 100, 20);
		labelSobrenome.setBounds(50, 80, 150, 20);
		labelId.setBounds(50, 120, 100, 20);

		JTextField jTextNome = new JTextField();
		JTextField jTextSobrenome = new JTextField();
		JTextField jTextId = new JTextField();

		jTextNome.setEnabled(false);
		jTextSobrenome.setEnabled(false); 
		jTextId.setEnabled(true);

		jTextNome.setBounds(180, 40, 150, 20);
		jTextSobrenome.setBounds(180, 80, 150, 20);
		jTextId.setBounds(180, 120, 50, 20);

		janelaConta.add(labelNome);
		janelaConta.add(labelSobrenome);
		janelaConta.add(labelId);
		janelaConta.add(jTextNome);
		janelaConta.add(jTextSobrenome); 
		janelaConta.add(jTextId);
		
		JButton botaoDeletar = new JButton("Deletar");
		botaoDeletar.setBounds(160, 200, 80, 20);
		janelaConta.add(botaoDeletar);
		
		JButton botaoGravar = new JButton("Gravar");
		botaoGravar.setBounds(50, 200, 100, 20);
		botaoGravar.setEnabled(true);
		janelaConta.add(botaoGravar);
		
		JButton botaoLimpar = new JButton("Resetar");//====================================
		botaoLimpar.setBounds(250, 200, 100, 20);
		janelaConta.add(botaoLimpar);
		
		JButton botaoConsultar = new JButton("Consultar");
		botaoConsultar.setBounds(230, 120, 100, 20);
		janelaConta.add(botaoConsultar);
		
		
		var funcionario = new Funcionario();	
		botaoConsultar.addActionListener(new ActionListener() {		
			public void actionPerformed(ActionEvent e) {
				try {
					int id = Integer.parseInt(jTextId.getText());
					botaoGravar.setEnabled(true);
					String nome;
					String sobrenome;
					if (!funcionario.consultarFuncionario(id)) {
						nome = "";
						sobrenome = "";
					}
						
					else
						nome = funcionario.getNome();
						sobrenome = funcionario.getSobrenome(); 
						
					jTextNome.setText(nome);
					jTextSobrenome.setText(sobrenome);
					jTextId.setEnabled(false);
					botaoConsultar.setEnabled(false);
					jTextNome.setEnabled(true);
					jTextSobrenome.setEnabled(true);
					jTextNome.requestFocus();
				} catch (Exception erro) {
					JOptionPane.showMessageDialog(janelaConta,
							"Preencha o campo corretamente!!");
				}
			}
		});
		
			botaoGravar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int resposta = JOptionPane.showConfirmDialog(janelaConta, "Deseja atualizar?", "Confirmação",
							JOptionPane.YES_NO_OPTION);
					if (resposta == JOptionPane.YES_OPTION) {
	
						String nome = jTextNome.getText().trim();
						String sobrenome = jTextSobrenome.getText().trim();
						int id = Integer.parseInt(jTextId.getText());

						if (nome.length() == 0) {
							JOptionPane.showMessageDialog(janelaConta, "Preencha o campo nome");
							jTextId.requestFocus();
						} else {
							if (!funcionario.consultarFuncionario(id)) {
								if (!funcionario.cadastrarFuncionario(nome, sobrenome, id))
									JOptionPane.showMessageDialog(janelaConta, "Erro na inclusão do nome!");
								else
									JOptionPane.showMessageDialog(janelaConta, "Inclusão realizada!");
							}
						}

					}
				}
			});
			
			botaoLimpar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					funcionario.setSobrenome(null);
					jTextNome.setText("");
					jTextSobrenome.setText("");
					jTextId.setText("");
					jTextId.setEnabled(true);
					jTextNome.setEnabled(false);
					jTextSobrenome.setEnabled(false);
					botaoConsultar.setEnabled(true);
					jTextNome.requestFocus();				
				}
			});
			
			
			
			botaoDeletar.addActionListener(new ActionListener() {		
			public void actionPerformed(ActionEvent e) {

				try {
					int resposta = JOptionPane.showConfirmDialog(janelaConta, "Deseja Remover?", "Confirmação",
							JOptionPane.YES_NO_OPTION);
					if (resposta == JOptionPane.YES_OPTION) {
						int id = Integer.parseInt(jTextId.getText());
						funcionario.excluirFuncionario(id);
						jTextNome.setText("");
						jTextSobrenome.setText("");
						jTextId.setText("");
						jTextNome.setEnabled(false);
						jTextSobrenome.setEnabled(false);
						jTextId.setEnabled(false);
						JOptionPane.showMessageDialog(janelaConta, "Funcionário removido!");
						
					}
				}
				catch(Exception erro) {
					JOptionPane.showMessageDialog(null, "Impossível de remover funcionário não cadastrado.");
				}

			}});
			
		
		return janelaConta;
	}
	
	// *** FABRICANTE ***
	public static JFrame criarJanelaFabricante() {
		JFrame janelaConta = new JFrame("Cadastro de Fabricantes");
		janelaConta.setResizable(true);
		janelaConta.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		janelaConta.setSize(400, 300);

		Container caixa = janelaConta.getContentPane();
		caixa.setLayout(null);

		JLabel labelNomeDono = new JLabel("Nome_Dono: ");
		JLabel labelNomeEmpresa = new JLabel("Nome_Empresa: ");
		JLabel labelId = new JLabel("ID: ");

		labelNomeDono.setBounds(50, 40, 100, 20);
		labelNomeEmpresa.setBounds(50, 80, 150, 20);
		labelId.setBounds(50, 120, 100, 20);

		JTextField jTextNomeDono = new JTextField();
		JTextField jTextNomeEmpresa = new JTextField();
		JTextField jTextId = new JTextField();

		jTextId.setEnabled(true);
		jTextNomeEmpresa.setEnabled(true); 
		jTextId.setEnabled(true);

		jTextNomeDono.setBounds(180, 40, 150, 20);
		jTextNomeEmpresa.setBounds(180, 80, 150, 20);
		jTextId.setBounds(180, 120, 50, 20);

		janelaConta.add(labelNomeDono);
		janelaConta.add(labelNomeEmpresa);
		janelaConta.add(labelId);
		janelaConta.add(jTextNomeDono);
		janelaConta.add(jTextNomeEmpresa); 
		janelaConta.add(jTextId);
		
		JButton botaoDeletar = new JButton("Deletar");
		botaoDeletar.setBounds(160, 200, 80, 20);
		janelaConta.add(botaoDeletar);
		
		JButton botaoGravar = new JButton("Gravar");
		botaoGravar.setBounds(50, 200, 100, 20);
		botaoGravar.setEnabled(true);
		janelaConta.add(botaoGravar);
		
		JButton botaoLimpar = new JButton("Resetar");
		botaoLimpar.setBounds(250, 200, 100, 20);
		janelaConta.add(botaoLimpar);
		
		JButton botaoConsultar = new JButton("Consultar");
		botaoConsultar.setBounds(230, 120, 100, 20);
		janelaConta.add(botaoConsultar);
	
		
		var fabricante = new Fabricante();	
		botaoConsultar.addActionListener(new ActionListener() {		
			public void actionPerformed(ActionEvent e) {
				try {
					int id = Integer.parseInt(jTextId.getText());
					botaoGravar.setEnabled(true);
					String nomeDono;
					String nomeEmpresa;
					if (!fabricante.consultarFabricante(id)) {
						nomeDono = "";
						nomeEmpresa = "";
						
					}
						
					else
						nomeDono = fabricante.getNomeDono();
						nomeEmpresa = fabricante.getNomeEmpresa(); 
						
						jTextNomeDono.setText(nomeDono);
						jTextNomeEmpresa.setText(nomeEmpresa);
						jTextId.setEnabled(true);
						jTextNomeDono.setEnabled(true);
						jTextNomeEmpresa.setEnabled(true);
						botaoConsultar.setEnabled(false);
						jTextNomeDono.requestFocus();
				} catch (Exception erro) {
					JOptionPane.showMessageDialog(janelaConta,
							"Preencha o campo corretamente!!");
				}
			}
		});
		
			
		botaoGravar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int resposta = JOptionPane.showConfirmDialog(janelaConta, "Deseja atualizar?", "Confirmação",
						JOptionPane.YES_NO_OPTION);
				if (resposta == JOptionPane.YES_OPTION) {

					String nome = jTextNomeDono.getText().trim();
					String empresa = jTextNomeEmpresa.getText().trim();
					int id = Integer.parseInt(jTextId.getText());

					if (nome.length() == 0) {
						JOptionPane.showMessageDialog(janelaConta, "Preencha o campo nome");
						jTextId.requestFocus();
					} else {
						if (!fabricante.consultarFabricante(id)) {
							if (!fabricante.cadastrarFabricante(nome, empresa, id))
								JOptionPane.showMessageDialog(janelaConta, "Erro na inclusão do nome!");
							else
								JOptionPane.showMessageDialog(janelaConta, "Inclusão realizada!");
						}
					}

				}
			}
		});
		
			
		botaoLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fabricante.setNomeEmpresa(null);
				jTextId.setText("");
				jTextNomeDono.setText("");
				jTextNomeEmpresa.setText("");
				jTextId.setEnabled(true);
				jTextNomeDono.setEnabled(false);
				jTextNomeEmpresa.setEnabled(false);
				botaoConsultar.setEnabled(true);
				botaoGravar.setEnabled(true);
				jTextId.requestFocus();
			}
		});

			
			
			botaoDeletar.addActionListener(new ActionListener() {		
			public void actionPerformed(ActionEvent e) {

				try {
					int resposta = JOptionPane.showConfirmDialog(janelaConta, "Deseja Remover?", "Confirmação",
							JOptionPane.YES_NO_OPTION);
					if (resposta == JOptionPane.YES_OPTION) {
						int id = Integer.parseInt(jTextId.getText());
						fabricante.excluirFabricante(id);
						jTextNomeDono.setText("");
						jTextNomeEmpresa.setText("");
						jTextId.setText("");
						jTextNomeDono.setEnabled(true);
						jTextNomeEmpresa.setEnabled(true);
						jTextId.setEnabled(true);
						botaoDeletar.setEnabled(true);
						
					}
				}
				catch(Exception erro) {
					JOptionPane.showMessageDialog(null, "Impossível de remover fabricante não cadastrado.");
				}

			}});
			
		
		return janelaConta;
	}
	
	
	public static void main(String[] args) {
		menuPrincipal();		
	}
	
}
