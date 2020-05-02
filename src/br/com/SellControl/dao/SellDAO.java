package br.com.SellControl.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import br.com.SellControl.db.DB;
import br.com.SellControl.model.entities.Client;
import br.com.SellControl.model.entities.Sell;
import br.com.SellControl.model.exception.DbException;

public class SellDAO {

	private Connection conn = null;

	public SellDAO(Connection conn) {
		this.conn = conn;
	}

	public void insert(Sell sell) {

		PreparedStatement ps = null;

		try {
			String query = "insert into tb_sell (client_id,sell_date,sell_total,comments) values (?,?,?,?)";

			ps = conn.prepareStatement(query);

			ps.setInt(1, sell.getClient().getId());
			ps.setString(2, sell.getDateSell());
			ps.setDouble(3, sell.getTotalSell());
			ps.setString(4, sell.getObs());

			int rows = ps.executeUpdate();

			if (rows <= 0)
				throw new DbException("Error, no rows affected!");

		} catch (SQLException e) {
			throw new DbException(e.getMessage());

		} finally {
			DB.closePreparedStatement(ps);
		}

	}

	// Return the ID last sell
	public int selectLastSell() {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {

			int idsell = 0;

			String query = "select max(id) id from tb_sell";
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();

			if (rs.next()) {
				Sell s = new Sell();
				s.setId(rs.getInt("id"));
				idsell = s.getId();
			}
			return idsell;

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closePreparedStatement(ps);
		}
	}

	// Return a select from sell done in date X to date Y
	public List<Sell> selectSellByDate(String dateInit, String dateEnd) {
		PreparedStatement ps = null;
		ResultSet rs = null;

		List<Sell> list = new ArrayList<>();
		StringBuilder query = new StringBuilder();

		try {
			query.append(
					"select s.id , date_format(s.sell_date,'%Y/%m/%d') as sell_date, c.name, s.sell_total, s.comments from tb_sell as s");
			query.append(" inner join tb_client as c on (s.client_id = c.id) where s.sell_date");
			query.append(" between ? and ?");

			ps = conn.prepareStatement(query.toString());
			ps.setString(1, dateInit);
			ps.setString(2, dateEnd);

			rs = ps.executeQuery();

			while (rs.next()) {
				Client c = new Client();
				Sell s = new Sell();

				s.setId(rs.getInt("s.id"));
				s.setDateSell(rs.getString("sell_date"));
				c.setName(rs.getString("c.name"));
				s.setTotalSell(rs.getDouble("s.sell_total"));
				s.setObs(rs.getString("s.comments"));

				s.setClient(c);

				list.add(s);
			}

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closePreparedStatement(ps);
			DB.closeResultSet(rs);
		}
		return list;

	}

	// Return a totalSale per date.
	public Double selectTotalSalesByDate(LocalDate date) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			double sellTotal = 0;

			String sql = "select sum(sell_total) as total from tb_sell where sell_date = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, date.toString());

			rs = ps.executeQuery();

			if (rs.next())
				sellTotal = rs.getDouble("total");

			return sellTotal;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closePreparedStatement(ps);
			DB.closeResultSet(rs);
		}
	}

}
