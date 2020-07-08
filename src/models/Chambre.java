package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import application.DBConnection;
import application.Parameter;

public class Chambre {
	static DBConnection connection = new DBConnection(Parameter.HOST_DB, Parameter.USERNAME_DB, Parameter.PASSWORD_DB,
			Parameter.IPHOST, Parameter.PORT);

	private int numChambre;
	private int numEtage;
    private String tel;
    private int idCategorie;
    private int idClient;
    
	public Chambre(int numChambre, int numEtage, String tel, int idCategorie, int idClient) {
		this.numChambre = numChambre;
		this.numEtage = numEtage;
		this.tel = tel;
		this.idCategorie = idCategorie;
		this.idClient = idClient;
	}
	
	public Chambre() {
		
	}
	
	public ResultSet getChambre() {
		try {
			Connection connect = connection.connexionDatabase();
			String sql = "SELECT * FROM chambre";
			PreparedStatement ps = connect.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			return rs;
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
		return null;
	}
	
	public ResultSet getAllChambre() {
		try {
			Connection connect = connection.connexionDatabase();
			String sql = "SELECT * FROM chambre,categorie WHERE chambre.idcategorie=categorie.id";
			PreparedStatement ps = connect.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			return rs;
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
		return null;
	}
	
	public ResultSet getChambreLibre() {
		try {
			Connection connect = connection.connexionDatabase();
			String sql = "SELECT numchambre FROM chambre where chambre.numchambre NOT IN (SELECT numchambre FROM reservation)";
			PreparedStatement ps = connect.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			return rs;
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
		return null;
	}
	
	public ResultSet getChambreOccupe() {
		try {
			Connection connect = connection.connexionDatabase();
			String sql = "SELECT numchambre FROM chambre where chambre.numchambre IN (SELECT numchambre FROM reservation)";
			PreparedStatement ps = connect.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			return rs;
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
		return null;
	}
	
	public static void deleteChambre(String num) {
		Connection connect = connection.connexionDatabase();
		String sql = "DELETE FROM chambre WHERE numchambre = ?";
		PreparedStatement ps;
		try {
			ps = connect.prepareStatement(sql);
			ps.setString(1, num);
			ps.executeUpdate();
			ps.close();
			connect.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    
}
