package br.com.SellControl.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.SellControl.db.DB;
import br.com.SellControl.model.entities.Provider;
import br.com.SellControl.model.exception.ControlException;
import br.com.SellControl.model.exception.DbException;
import javafx.scene.control.Alert.AlertType;

public class ProviderDAO {

	private Connection conn = null;

	public ProviderDAO(Connection conn) {
		this.conn = conn;
	}

	public void insert(Provider provider) {

		PreparedStatement ps = null;

		try {
			StringBuilder query = new StringBuilder();
			query.append(
					"insert into tb_provider (name,cnpj,email,phone,cellphone,cep,address,number,complement,neighborhood,city,state)");
			query.append("values (?,?,?,?,?,?,?,?,?,?,?,?)");

			ps = conn.prepareStatement(query.toString());

			ps.setString(1, provider.getName());
			ps.setString(2, provider.getCpf());
			ps.setString(3, provider.getEmail());
			ps.setString(4, provider.getPhone());
			ps.setString(5, provider.getCellphone());
			ps.setString(6, provider.getCnpj());
			ps.setString(7, provider.getAddress());
			ps.setInt(8, provider.getNumber());
			ps.setString(9, provider.getComplement());
			ps.setString(10, provider.getNeighborhood());
			ps.setString(11, provider.getCity());
			ps.setString(12, provider.getState());

			int rows = ps.executeUpdate();

			if (rows <= 0)
				throw new DbException("Error, no rows affected!");

		} catch (SQLException e) {
			throw new DbException(e.getMessage());

		} finally {
			DB.closePreparedStatement(ps);
		}

	}

	public List<Provider> findAll() {

		PreparedStatement ps = null;
		ResultSet rs = null;

		List<Provider> list = new ArrayList<>();

		try {
			String sql = "select * from tb_provider";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next()) {
				Provider c = new Provider();
				list.add(makeProvider(rs, c));
			}

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closePreparedStatement(ps);
			DB.closeResultSet(rs);

		}
		return list;

	}

	public void delete(Provider provider) {

		PreparedStatement ps = null;

		try {

			String query = "delete from tb_provider where id = ?";

			ps = conn.prepareStatement(query);

			ps.setInt(1, provider.getId());

			int rows = ps.executeUpdate();

			if (rows <= 0)
				throw new DbException("Error, no rows affected!");

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
			// If have code field empty, them throw this exception
		} catch (DbException e) {
			throw new ControlException(e.getMessage(), "message", null, "Code it has to be the same", AlertType.ERROR,true);

		} finally {
			DB.closePreparedStatement(ps);
		}

	}

	public void update(Provider provider) {

		PreparedStatement ps = null;

		try {

			StringBuilder query = new StringBuilder();
			query.append(
					"update tb_provider set name=?,cnpj=?,email=?,phone=?,cellphone=?,cep=?,address=?,number=?,complement=?,neighborhood=?,city=?,state=?");
			query.append("where id=?");
			ps = conn.prepareStatement(query.toString());

			ps.setString(1, provider.getName());
			ps.setString(2, provider.getCpf());
			ps.setString(3, provider.getEmail());
			ps.setString(4, provider.getPhone());
			ps.setString(5, provider.getCellphone());
			ps.setString(6, provider.getCnpj());
			ps.setString(7, provider.getAddress());
			ps.setInt(8, provider.getNumber());
			ps.setString(9, provider.getComplement());
			ps.setString(10, provider.getNeighborhood());
			ps.setString(11, provider.getCity());
			ps.setString(12, provider.getState());
			ps.setInt(13, provider.getId());

			int rows = ps.executeUpdate();

			if (rows <= 0)
				throw new DbException("Error, no rows affected!");

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
			// If have code field empty, them throw this exception
		} catch (DbException e) {
			throw new ControlException(e.getMessage(), "message", null, "Code it has to be the same", AlertType.ERROR,true);

		} finally {
			DB.closePreparedStatement(ps);
		}

	}

	public List<Provider> findbyName(String name) {

		PreparedStatement ps = null;
		ResultSet rs = null;

		List<Provider> list = new ArrayList<>();

		try {
			String sql = "select * from tb_provider where name like ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, "%" + name + "%");
			rs = ps.executeQuery();

			while (rs.next()) {
				Provider c = new Provider();
				list.add(makeProvider(rs, c));
			}

			return list;

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}

		finally {
			DB.closePreparedStatement(ps);
			DB.closeResultSet(rs);

		}

	}

	// This method will be used in first tab, and his search is ' name = ? '
	// otherwise the user have to fill the whole name.
	public Provider findProviderByName(String name) {

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			Provider c = new Provider();
			String sql = "select * from tb_provider where name = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, name);
			rs = ps.executeQuery();

			while (rs.next()) {
				c = makeProvider(rs, c);
			}

			return c;

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}

		finally {
			DB.closePreparedStatement(ps);
			DB.closeResultSet(rs);

		}

	}

	public Provider makeProvider(ResultSet rs, Provider c) throws SQLException {

		c.setId(rs.getInt("id"));
		c.setName(rs.getString("name"));
		c.setCpf(rs.getString("cpf"));
		c.setEmail(rs.getString("email"));
		c.setPhone(rs.getString("phone"));
		c.setCellphone(rs.getString("cellphone"));
		c.setCnpj(rs.getString("cnpj"));
		c.setAddress(rs.getString("address"));
		c.setNumber(rs.getInt("number"));
		c.setComplement(rs.getString("complement"));
		c.setNeighborhood(rs.getString("neighborhood"));
		c.setCity(rs.getString("city"));
		c.setState(rs.getString("state"));

		return c;

	}

}
