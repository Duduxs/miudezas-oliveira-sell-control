package br.com.SellControl.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.SellControl.db.DB;
import br.com.SellControl.gui.MainScreenControl;
import br.com.SellControl.model.entities.Employee;
import br.com.SellControl.model.exception.ControlException;
import br.com.SellControl.model.exception.DbException;
import br.com.SellControl.util.Alerts;
import javafx.scene.control.Alert.AlertType;

public class EmployeeDAO {

	private Connection conn = null;
	private Boolean loginVerification = false;
	public Boolean getLoginVerification() {
		return loginVerification;
	}

	public EmployeeDAO(Connection conn) {
		this.conn = conn;
	}

	public void insert(Employee employee) {

		PreparedStatement ps = null;

		try {
			StringBuilder query = new StringBuilder();
			query.append(
					"insert into tb_employee (name,cpf,email,password,office,acess_level,phone,cellphone,cep,address,number,complement,neighborhood,city,state)");
			query.append("values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

			ps = conn.prepareStatement(query.toString());

			ps.setString(1, employee.getName());
			ps.setString(2, employee.getCpf());
			ps.setString(3, employee.getEmail());
			ps.setString(4, employee.getPassword());
			ps.setString(5, employee.getOffice());
			ps.setString(6, employee.getAcess_level());
			ps.setString(7, employee.getPhone());
			ps.setString(8, employee.getCellphone());
			ps.setString(9, employee.getCep());
			ps.setString(10, employee.getAddress());
			ps.setInt(11, employee.getNumber());
			ps.setString(12, employee.getComplement());
			ps.setString(13, employee.getNeighborhood());
			ps.setString(14, employee.getCity());
			ps.setString(15, employee.getState());

			int rows = ps.executeUpdate();

			if (rows <= 0)
				throw new DbException("Error, no rows affected!");

		} catch (SQLException e) {
			throw new DbException(e.getMessage());

		} finally {
			DB.closePreparedStatement(ps);
		}

	}

	public List<Employee> findAll() {

		PreparedStatement ps = null;
		ResultSet rs = null;

		List<Employee> list = new ArrayList<>();

		try {
			String sql = "select * from tb_employee";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next()) {
				Employee c = new Employee();
				list.add(makeEmployee(rs, c));
			}

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closePreparedStatement(ps);
			DB.closeResultSet(rs);

		}
		return list;

	}

	public void delete(Employee employee) {

		PreparedStatement ps = null;

		try {

			String query = "delete from tb_employee where id = ?";

			ps = conn.prepareStatement(query);

			ps.setInt(1, employee.getId());

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

	public void update(Employee employee) {

		PreparedStatement ps = null;

		try {

			StringBuilder query = new StringBuilder();
			query.append(
					"update tb_employee set name=?,cpf=?,email=?,password=?,office=?,acess_level=?,phone=?,cellphone=?,cep=?,address=?,number=?,complement=?,neighborhood=?,city=?,state=?");
			query.append("where id=?");
			ps = conn.prepareStatement(query.toString());

			ps.setString(1, employee.getName());
			ps.setString(2, employee.getCpf());
			ps.setString(3, employee.getEmail());
			ps.setString(4, employee.getPassword());
			ps.setString(5, employee.getOffice());
			ps.setString(6, employee.getAcess_level());
			ps.setString(7, employee.getPhone());
			ps.setString(8, employee.getCellphone());
			ps.setString(9, employee.getCep());
			ps.setString(10, employee.getAddress());
			ps.setInt(11, employee.getNumber());
			ps.setString(12, employee.getComplement());
			ps.setString(13, employee.getNeighborhood());
			ps.setString(14, employee.getCity());
			ps.setString(15, employee.getState());
			ps.setInt(16, employee.getId());

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

	public List<Employee> findbyName(String name) {

		PreparedStatement ps = null;
		ResultSet rs = null;

		List<Employee> list = new ArrayList<>();

		try {
			String sql = "select * from tb_employee where name like ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, "%" + name + "%");
			rs = ps.executeQuery();

			while (rs.next()) {
				Employee c = new Employee();
				list.add(makeEmployee(rs, c));
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
	public Employee findEmployeeByName(String name) {

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			Employee c = new Employee();
			String sql = "select * from tb_employee where name = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, name);
			rs = ps.executeQuery();

			while (rs.next()) {
				c = makeEmployee(rs, c);
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

	// Method for Employee login
	public void EmployeeLogin(String email, String password) {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			String sql = "select * from tb_employee where email = ? and password = ?";
			ps = conn.prepareStatement(sql);

			ps.setString(1, email);
			ps.setString(2, password);
		
			rs = ps.executeQuery();
			
		
			if (rs.next()) {
				loginVerification = true;
				//Catch the username logged and put in MainScreenControl
				MainScreenControl.userLogged = rs.getString("name");
			}
			 else 
				Alerts.showAlert("message", null, "Incorrect data", AlertType.ERROR);
			
	
			

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closePreparedStatement(ps);
			DB.closeResultSet(rs);
		}
	}

	public Employee makeEmployee(ResultSet rs, Employee e) throws SQLException {

		e.setId(rs.getInt("id"));
		e.setName(rs.getString("name"));
		e.setCpf(rs.getString("cpf"));
		e.setEmail(rs.getString("email"));
		e.setPassword(rs.getString("password"));
		e.setOffice(rs.getString("office"));
		e.setAcess_level(rs.getString("acess_level"));
		e.setPhone(rs.getString("phone"));
		e.setCellphone(rs.getString("cellphone"));
		e.setCep(rs.getString("cep"));
		e.setAddress(rs.getString("address"));
		e.setNumber(rs.getInt("number"));
		e.setComplement(rs.getString("complement"));
		e.setNeighborhood(rs.getString("neighborhood"));
		e.setCity(rs.getString("city"));
		e.setState(rs.getString("state"));

		return e;

	}

}
