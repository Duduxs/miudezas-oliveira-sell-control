package br.com.SellControl.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.SellControl.db.ControlException;
import br.com.SellControl.db.DB;
import br.com.SellControl.db.DbException;
import br.com.SellControl.model.entities.Client;
import javafx.scene.control.Alert.AlertType;

public class ClientDAO {

	private Connection conn = null;

	public ClientDAO(Connection conn) {
		this.conn = conn;
	}

	public void insert(Client client) {

		PreparedStatement ps = null;

		try {
			StringBuilder query = new StringBuilder();
			query.append(
					"insert into tb_client (name,cpf,email,phone,cellphone,cep,address,number,complement,neighborhood,city,state)");
			query.append("values (?,?,?,?,?,?,?,?,?,?,?,?)");

			ps = conn.prepareStatement(query.toString());

			ps.setString(1, client.getName());
			ps.setString(2, client.getCpf());
			ps.setString(3, client.getEmail());
			ps.setString(4, client.getPhone());
			ps.setString(5, client.getCellphone());
			ps.setString(6, client.getCep());
			ps.setString(7, client.getAddress());
			ps.setInt(8, client.getNumber());
			ps.setString(9, client.getComplement());
			ps.setString(10, client.getNeighborhood());
			ps.setString(11, client.getCity());
			ps.setString(12, client.getState());

			int rows = ps.executeUpdate();

			if (rows <= 0)
				throw new DbException("Error, no rows affected!");

		} catch (SQLException e) {
			throw new DbException(e.getMessage());

		} finally {
			DB.closePreparedStatement(ps);
		}

	}

	public List<Client> findAll() {

		PreparedStatement ps = null;
		ResultSet rs = null;

		List<Client> list = new ArrayList<>();

		try {
			String sql = "select * from tb_client";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next()) {
				Client c = new Client();
				list.add(makeClient(rs, c));
			}

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closePreparedStatement(ps);
			DB.closeResultSet(rs);

		}
		return list;

	}

	public void delete(Client client) {

		PreparedStatement ps = null;

		try {

			String query = "delete from tb_client where id = ?";

			ps = conn.prepareStatement(query);

			ps.setInt(1, client.getId());

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

	public void update(Client client) {

		PreparedStatement ps = null;

		try {

			StringBuilder query = new StringBuilder();
			query.append(
					"update tb_client set name=?,cpf=?,email=?,phone=?,cellphone=?,cep=?,address=?,number=?,complement=?,neighborhood=?,city=?,state=?");
			query.append("where id=?");
			ps = conn.prepareStatement(query.toString());

			ps.setString(1, client.getName());
			ps.setString(2, client.getCpf());
			ps.setString(3, client.getEmail());
			ps.setString(4, client.getPhone());
			ps.setString(5, client.getCellphone());
			ps.setString(6, client.getCep());
			ps.setString(7, client.getAddress());
			ps.setInt(8, client.getNumber());
			ps.setString(9, client.getComplement());
			ps.setString(10, client.getNeighborhood());
			ps.setString(11, client.getCity());
			ps.setString(12, client.getState());
			ps.setInt(13, client.getId());

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

	public List<Client> findbyName(String name) {

		PreparedStatement ps = null;
		ResultSet rs = null;

		List<Client> list = new ArrayList<>();

		try {
			String sql = "select * from tb_client where name like ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, "%" + name + "%");
			rs = ps.executeQuery();

			while (rs.next()) {
				Client c = new Client();
				list.add(makeClient(rs, c));
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
	public Client findClientByName(String name) {

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			Client c = new Client();
			String sql = "select * from tb_client where name = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, name);
			rs = ps.executeQuery();

			while (rs.next()) {
				c = makeClient(rs, c);
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

	public Client makeClient(ResultSet rs, Client c) throws SQLException {

		c.setId(rs.getInt("id"));
		c.setName(rs.getString("name"));
		c.setCpf(rs.getString("cpf"));
		c.setEmail(rs.getString("email"));
		c.setPhone(rs.getString("phone"));
		c.setCellphone(rs.getString("cellphone"));
		c.setCep(rs.getString("cep"));
		c.setAddress(rs.getString("address"));
		c.setNumber(rs.getInt("number"));
		c.setComplement(rs.getString("complement"));
		c.setNeighborhood(rs.getString("neighborhood"));
		c.setCity(rs.getString("city"));
		c.setState(rs.getString("state"));

		return c;

	}

}
