package br.com.zup.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import br.com.zup.POJO.CidadesPojo;
import br.com.zup.conectionfactory.ConnectionFactory;

public class CidadesDao {

	private Connection conn;

	public CidadesDao() {
		this.conn = new ConnectionFactory().getConnection();
	}

	public void insereCidade(CidadesPojo cidade) throws SQLException {
		try {
			String sql = "insert into cidade"
					+ "(nome, CEP, numero_habitantes, capital, estado, renda_per_capta, data_de_fundacao)"
					+ "values (?, ?, ?, ?, ?, ?, ?)";

			PreparedStatement stmt = this.conn.prepareStatement(sql);

			stmt.setString(1, cidade.getNome());
			stmt.setString(2, cidade.getCep());
			stmt.setInt(3, cidade.getNumeroHabitantes());
			stmt.setInt(4, cidade.getCapital());
			stmt.setString(5, cidade.getEstado());
			stmt.setFloat(6, cidade.getRendaPerCapita());
			stmt.setString(7, cidade.getDataDeFundacao());

			stmt.execute();
			stmt.close();

			System.out.println("\nCidade cadastrada com sucesso!\n");

		} catch (SQLException e) {
			System.out.println(
					"\nNão foi possível cadastrar a cidade.\nVerifique se as informações foram inseridas corretamente.\n");
		}
	}

	public void deletaCidade(String cep) throws SQLException {

		CidadesPojo cidade = new CidadesPojo();
		try {
			
			String sql = "delete from cidade where cep = ?";

			PreparedStatement stmt = this.conn.prepareStatement(sql);
			
			stmt.setString(1, cep);

			stmt.execute();
			stmt.close();

			System.out.println("\nCidade excluída com sucesso!\n");

		} catch (SQLException e) {
			throw new SQLException("\nNão foi possível deletar a cidade. Verifique se o CEP informado está correto." + e.getMessage());
		}
	}
}
