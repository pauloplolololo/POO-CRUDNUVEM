package Crude;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Produto {

	public static void main(String[] args) {
		
		// conexao no céu (Railway)
		String url = "jdbc:mysql://autorack.proxy.rlwy.net:43505/railway";
		String usuariobanco = "root";
		String bancosenha = "weuFoAIlugDdRhZuiLvLmyiextJqNiUy";
		
		// conexao local
		//String url="jdbc:mysql://localhost:3306/dbproduto";
		//String usuariobanco="root";
		//String bancosenha="1234567";
		
		try (Scanner sc = new Scanner(System.in)) {
			try {
				Connection conexao = DriverManager.getConnection(url, usuariobanco, bancosenha);
				
				int opcao = -1;
				while(opcao != 0) {
					System.out.println("1 - Inserir | 2 - Listar | 3 - Atualizar | 4 - Deletar | 0 - Sair");
					opcao = sc.nextInt();
					
					
					switch(opcao){
					// botao para adicionar um produto
					case 1:
						System.out.print("Código do produto: ");
						int codigo = sc.nextInt();
						
						System.out.print("Nome do produto: ");
						String nome = sc.next();
						
						System.out.print("Preco do produto: ");
						float preco = sc.nextFloat();
						
						String sql = "INSERT INTO PRODUTO(CODIGO,NOME,PRECO) VALUES(?,?,?)";
						
						PreparedStatement stmt=conexao.prepareStatement(sql);
						stmt.setInt(1, codigo);
						stmt.setString(2, nome);
						stmt.setFloat(3, preco);
						stmt.executeUpdate();
						
						System.out.println("Registro inserido com sucesso!!");
						break;
						
						// botao para ver os produtos disponiveis
					case 2:
						System.out.println("Digite o código do produto");
						int codigo2 = sc.nextInt();
						
						String sql2 = "SELECT * FROM PRODUTO where codigo = ?";
						PreparedStatement stm2 = conexao.prepareStatement(sql2);
						stm2.setInt(1, codigo2);
						ResultSet rs=stm2.executeQuery();
						while(rs.next()) {
							System.out.println("Nome produto: "+ rs.getString("nome")+ " Preço: "+rs.getFloat("preco"));
						}
						break;
						// Botao para alterar o valor de algum produto
					case 3:
						System.out.println("Digite o codigo do produto para atualizar: ");
						int codPesquisa = sc.nextInt();
						System.out.println("Digite o novo preço do produto: ");
						float novopreco = sc.nextFloat();
						
						String SqlUp = "UPDATE PRODUTO SET PRECO = ? where codigo = ?";
						
						PreparedStatement stmtUp = conexao.prepareStatement(SqlUp);
						stmtUp.setFloat(1, novopreco);
						stmtUp.setInt(2, codPesquisa);
						
						int linhaAfetada = stmtUp.executeUpdate();
						
						if(linhaAfetada>0) {
							System.out.println("Preço alterado com sucesso!!");
						}else {
							System.out.println("Produto não localizado.");
						}
						break;
						// Botao para deletar algum produto que estaja na lista
					case 4:
						
						System.out.println("Digite o codigo do produto que deseja deletar: ");
						int codDel = sc.nextInt();
						
						String SqlDel = "DELETE FROM PRODUTO where codigo = ?";
						
						PreparedStatement stmtDel = conexao.prepareStatement(SqlDel);
						stmtDel.setInt(1, codDel);
						
						int linhaDel = stmtDel.executeUpdate();
						
						if(linhaDel>0) {
							System.out.println("Item deletado!");
							
						}else {
							System.out.println("Item não deletado.");
						}
						break;
						// Botão para fechar
					case 0:
						System.out.println("Até mais!");
						System.exit(0);
						break;
						
					default:
						System.out.println("Opção inválida!!!");
					}
					
				}
				
			}catch(Exception e){
				System.out.println("Erro de cadastro."+e.getMessage());
			}
		}
		
	}

}
