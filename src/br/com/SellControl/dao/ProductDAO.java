package br.com.SellControl.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.SellControl.db.DB;
import br.com.SellControl.model.entities.Product;
import br.com.SellControl.model.entities.Provider;
import br.com.SellControl.model.exception.ControlException;
import br.com.SellControl.model.exception.DbException;
import javafx.scene.control.Alert.AlertType;

public class ProductDAO {

	private Connection conn = null;

	public ProductDAO(Connection conn) {
		this.conn = conn;
	}

	public void insert(Product product) {

		PreparedStatement ps = null;

		try {
			String query = "insert into tb_product (description,price,qtd_stock,for_id) values (?,?,?,?)";

			ps = conn.prepareStatement(query);

			ps.setString(1, product.getDescription());
			ps.setDouble(2, product.getPrice());
			ps.setInt(3, product.getQtdStock());
			ps.setInt(4, product.getProvider().getId());

			int rows = ps.executeUpdate();

			if (rows <= 0)
				throw new DbException("Error, no rows affected!");

		} catch (SQLException e) {
			throw new DbException(e.getMessage());

		} finally {
			DB.closePreparedStatement(ps);
		}

	}

	public List<Product> findAll() {

		PreparedStatement ps = null;
		ResultSet rs = null;

		List<Product> list = new ArrayList<>();
		StringBuilder query = new StringBuilder();
		try {
			query.append(
					"select p.id, p.description, p.price, p.qtd_stock, pr.name from tb_product as p inner join tb_provider as pr");
			query.append(" on p.for_id=pr.id");
			ps = conn.prepareStatement(query.toString());
			rs = ps.executeQuery();

			while (rs.next()) {
				Product c = new Product();
				Provider pr = new Provider();
				list.add(makeProduct(rs, c, pr));
			}

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closePreparedStatement(ps);
			DB.closeResultSet(rs);

		}
		return list;

	}

	public void delete(Product product) {

		PreparedStatement ps = null;

		try {

			String query = "delete from tb_product where id = ?";

			ps = conn.prepareStatement(query);

			ps.setInt(1, product.getId());

			int rows = ps.executeUpdate();

			if (rows <= 0)
				throw new DbException("Error, no rows affected!");

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
			// If have code field empty, them throw this exception
		} catch (DbException e) {
			throw new ControlException(e.getMessage(), "message", null, "Code it has to be the same", AlertType.ERROR,
					true);

		} finally {
			DB.closePreparedStatement(ps);
		}

	}

	public void update(Product product) {

		PreparedStatement ps = null;

		try {

			StringBuilder query = new StringBuilder();
			query.append("update tb_product set description=?,price=?,qtd_stock=?,for_id=? where id=?");
			ps = conn.prepareStatement(query.toString());

			ps.setString(1, product.getDescription());
			ps.setDouble(2, product.getPrice());
			ps.setInt(3, product.getQtdStock());
			ps.setInt(4, product.getProvider().getId());
			ps.setInt(5, product.getId());

			int rows = ps.executeUpdate();

			if (rows <= 0)
				throw new DbException("Error, no rows affected!");

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
			// If have code field empty, them throw this exception
		} catch (DbException e) {
			throw new ControlException(e.getMessage(), "message", null, "Code it has to be the same", AlertType.ERROR,
					true);

		} finally {
			DB.closePreparedStatement(ps);
		}

	}

	public List<Product> findbyName(String name) {

		PreparedStatement ps = null;
		ResultSet rs = null;

		List<Product> list = new ArrayList<>();
		StringBuilder query = new StringBuilder();
		try {

			query.append(
					"select p.id, p.description, p.price, p.qtd_stock, pr.name from tb_product as p inner join tb_provider as pr");
			query.append(" on p.for_id=pr.id where p.description like ?");
			ps = conn.prepareStatement(query.toString());
			ps.setString(1, "%" + name + "%");
			rs = ps.executeQuery();

			while (rs.next()) {
				Product p = new Product();
				Provider pr = new Provider();
				list.add(makeProduct(rs, p, pr));
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
	public Product findProductByName(String name) {

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			StringBuilder query = new StringBuilder();
			Product p = new Product();
			Provider pr = new Provider();
			query.append(
					"select p.id, p.description, p.price, p.qtd_stock, pr.name from tb_product as p inner join tb_provider as pr");
			query.append(" on p.for_id=pr.id where p.description = ?");
			ps = conn.prepareStatement(query.toString());
			ps.setString(1, name);
			rs = ps.executeQuery();

			while (rs.next()) {
				p = makeProduct(rs, p, pr);
			}

			return p;

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}

		finally {
			DB.closePreparedStatement(ps);
			DB.closeResultSet(rs);

		}

	}

	public Product makeProduct(ResultSet rs, Product p, Provider pr) throws SQLException {

		p.setId(rs.getInt("p.id"));
		p.setDescription(rs.getString("p.description"));
		p.setPrice(rs.getDouble("p.price"));
		p.setQtdStock(rs.getInt("p.qtd_stock"));
		pr.setName(rs.getString("pr.name"));
		p.setProvider(pr);

		return p;

	}

}
