package br.com.zup.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.zup.POJO.CidadesPojo;
import br.com.zup.conectionfactory.ConnectionFactory;

public class CidadesDao {

	private Connection conn;

	public CidadesDao() {
		this.conn = new ConnectionFactory().getConnection();
	}
	
	public static void contrutorListasDeCidades(List<CidadesPojo> cidades, String sql, PreparedStatement stmt) throws SQLException {
		
		ResultSet rs = stmt.executeQuery();
		
		while (rs.next()) {
			CidadesPojo cidade = new CidadesPojo();

			cidade.setNome(rs.getString("nome"));
			cidade.setCep(rs.getString("CEP"));
			cidade.setNumeroHabitantes(rs.getInt("numero_habitantes"));
			cidade.setCapital(rs.getInt("capital"));
			cidade.setEstado(rs.getString("estado"));
			cidade.setRendaPerCapita(rs.getFloat("renda_per_capta"));
			cidade.setDataDeFundacao(rs.getString("data_de_fundacao"));

			cidades.add(cidade);
		}

	}

	public void insereCidade(CidadesPojo cidade) {
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
			System.out.println(e.getMessage());
		}
	}

	public List<CidadesPojo> listaCidades() {

		List<CidadesPojo> cidades = new ArrayList<CidadesPojo>();

		String sql = "select * from cidade";

		try {
			PreparedStatement stmt = conn.prepareStatement(sql);

			contrutorListasDeCidades (cidades, sql, stmt);

		} catch (SQLException e) {
			System.out.println("\nOcorreu um erro ao executar a consulta. Tente novamente.\n");
			System.out.println(e.getMessage());
		}
		return cidades;
	}

	public List<CidadesPojo> listaCidadePorCEP(String cep) {

		List<CidadesPojo> cidades = new ArrayList<CidadesPojo>();

		String sql = "select * from cidade where cep = ?";

		try {
			PreparedStatement stmt = conn.prepareStatement(sql);

			stmt.setString(1, cep);

			contrutorListasDeCidades (cidades, sql, stmt);
			
		} catch (SQLException e) {
			System.out.println("\nErro ao efetuar a consulta!\n");
			System.out.println(e.getMessage());
		}
		return cidades;
	}

	public List<CidadesPojo> listaCidadesComMesmoInicio(String letra) {

		List<CidadesPojo> cidades = new ArrayList<CidadesPojo>();

		String sql = "select * from cidade where nome like ?";

		try {
			PreparedStatement stmt = conn.prepareStatement(sql);

			stmt.setString(1, letra + "%");

			contrutorListasDeCidades (cidades, sql, stmt);

		} catch (SQLException e) {
			System.out.println(
					"\nNão existe uma cidade iniciada com a letra informada ou ocorreu um erro ao efetuar a consulta!\n");
			System.out.println(e.getMessage());
		}
		return cidades;
	}

	public List<CidadesPojo> listaCidadesPelaSiglaEstado(String sigla) {

		List<CidadesPojo> cidades = new ArrayList<CidadesPojo>();

		String sql = "select * from cidade where estado = ?";

		try {
			PreparedStatement stmt = conn.prepareStatement(sql);

			stmt.setString(1, sigla);

			contrutorListasDeCidades (cidades, sql, stmt);

		} catch (SQLException e) {
			System.out.println("\nNão existem cidades no CEP informado ou ocorreu um erro ao efetuar a consulta!\n");
			System.out.println(e.getMessage());
		}

		return cidades;
	}

	public int contaQuantidadeDeCidadesDoEstado(String estado) {

		String sql = "select count(*) from cidade where estado = ?;";

		int contadorCidades = 0;

		try {
			PreparedStatement stmt = conn.prepareStatement(sql);

			stmt.setString(1, estado);

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				contadorCidades = rs.getInt(1);
			}

		} catch (SQLException e) {
			System.out.println("\nNão existem cidades no estado informado ou ocorreu um erro ao efetuar a consulta!\n");
			System.out.println(e.getMessage());
		}

		return contadorCidades;
	}

	public List<CidadesPojo> listaCidadesCapitaisOuNao(int capital) {

		List<CidadesPojo> cidades = new ArrayList<CidadesPojo>();

		String sql = "select * from cidade where capital = ?";

		try {
			PreparedStatement stmt = conn.prepareStatement(sql);

			stmt.setInt(1, capital);

			contrutorListasDeCidades (cidades, sql, stmt);

		} catch (SQLException e) {
			System.out.println("\nNão existem cidades com o parâmetro informado ou ocorreu um erro ao efetuar a consulta!\n");
			System.out.println(e.getMessage());
		}
		return cidades;
	}

	public void deletaCidade(String cep) {

		String sql = "delete from cidade where cep = ?";

		try {

			PreparedStatement stmt = this.conn.prepareStatement(sql);

			stmt.setString(1, cep);

			stmt.execute();
			stmt.close();

			System.out.println("\nCidade excluída com sucesso!\n");

		} catch (SQLException e) {
			System.out.println("\nNão foi possível deletar a cidade. Verifique se o CEP informado está correto.");
			System.out.println(e.getMessage());
		}
	}
}
