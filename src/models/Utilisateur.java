package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import application.DBConnection;
import application.Parameter;

public class Utilisateur{
	private String login;
	private String password;
	private String compte;
	private String profil = null;
	static DBConnection connection = new DBConnection(Parameter.HOST_DB, Parameter.USERNAME_DB, Parameter.PASSWORD_DB, Parameter.IPHOST, Parameter.PORT);
	
	public Utilisateur(String login, String password, String compte) {
		this.login = login;
		this.password = password;
		this.compte = compte;
	}
	public Utilisateur(String login, String password) {
		this.login = login;
		this.password = password;
	}
	
	public Utilisateur() {
		// TODO Auto-generated constructor stub
	}
	public ResultSet getUser() throws SQLException {
		//DBConnection connection = new DBConnection(Parameter.HOST_DB, Parameter.USERNAME_DB, Parameter.PASSWORD_DB, Parameter.IPHOST, Parameter.PORT);
		try {
		Connection connect = connection.connexionDatabase();
		String sql = "SELECT * FROM utilisateur WHERE login=? AND password=?";
		PreparedStatement ps = connect.prepareStatement(sql);
		ps.setString(1, this.login);
		ps.setString(2, this.password);
		return ps.executeQuery();
		}catch(Exception e) {
			System.out.print(e.getMessage());
		}
		return null;
	}
	
	public ResultSet getAllUser() throws SQLException {
		Connection connect = connection.connexionDatabase();
		String sql = "SELECT * FROM utilisateur";
		PreparedStatement ps = connect.prepareStatement(sql);
		return  ps.executeQuery();
	}
	
	public void addUser() throws SQLException, ClassNotFoundException {
		Connection connect = connection.connexionDatabase();
		String sql = "INSERT INTO utilisateur (login,password,compte,profil) VALUES (?,?,?,?)";
		PreparedStatement ps = connect.prepareStatement(sql);
		ps.setString(1, this.login);
		ps.setString(2, this.password);
		ps.setString(3, this.compte);
		ps.setString(4, "default.jpg");
		ps.executeUpdate();
	}
	
	public boolean verifUser() throws SQLException {
		Connection connect = connection.connexionDatabase();
		String sql = "SELECT * FROM utilisateur WHERE login = ?";
		PreparedStatement ps = connect.prepareStatement(sql);
		ps.setString(1, this.login);
		ResultSet rs = ps.executeQuery();
		if(!rs.next()) {
			return true;
		}
		return false;
	}
	
	public static void deleteUser(String login, String password) {
		Connection connect = connection.connexionDatabase();
		String sql = "DELETE FROM utilisateur WHERE login = ? AND password = ?";
		PreparedStatement ps;
		try {
			ps = connect.prepareStatement(sql);
			ps.setString(1, login);
			ps.setString(2, password);
			ps.executeUpdate();
			ps.close();
			connect.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
