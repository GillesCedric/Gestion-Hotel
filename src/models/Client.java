package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.GregorianCalendar;
import java.util.Random;

import application.DBConnection;
import application.Parameter;

public class Client {
	static DBConnection connection = new DBConnection(Parameter.HOST_DB, Parameter.USERNAME_DB, Parameter.PASSWORD_DB,
			Parameter.IPHOST, Parameter.PORT);

	private String codeClient;
	private String nom;
    private String prenom;
    private String tel;
    private String email;
    private String numCni;
	
	public Client(String codeClient, String nom, String prenom, String tel, String email, String numCni) {
		this.codeClient = codeClient;
		this.nom = nom;
		this.prenom = prenom;
		this.tel = tel;
		this.email = email;
		this.numCni = numCni;
	}

	public Client() {
		
	}

	public ResultSet getAllClient() {
		try {
			Connection connect = connection.connexionDatabase();
			String sql = "SELECT * FROM client";
			PreparedStatement ps = connect.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			return rs;
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
		return null;
	}
	public static String generateCodeClient() {
		GregorianCalendar d = new GregorianCalendar();
		int hour = d.getTime().getHours();
		int minute = d.getTime().getMinutes();
		int second = d.getTime().getSeconds();
		String random = "";
		int min = 0;
		int max = 9;
		for(int i=0;i<6;i++) {
			int n = min + (int) (Math.random() * ((max - min) + 1));
			random += String.valueOf(n);
		}
		return random+""+String.valueOf(hour)+String.valueOf(minute)+String.valueOf(second);
	}
	public void insertAll() {
		Connection connect = connection.connexionDatabase();
		String sql = "INSERT INTO client(codeclient,nom,prenom,tel,email,numcni) VALUES(?,?,?,?,?,?)";
		PreparedStatement ps;
		try {
			ps = connect.prepareStatement(sql);
			ps.setString(1, this.codeClient);
			ps.setString(2, this.nom);
			ps.setString(3, this.prenom);
			ps.setString(4, this.tel);
			ps.setString(5, this.email);
			ps.setString(6, this.numCni);
			ps.executeUpdate();
			ps.close();
			connect.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void deleteClient(String code) {
		Connection connect = connection.connexionDatabase();
		String sql = "DELETE FROM client WHERE codeclient = ?";
		PreparedStatement ps;
		try {
			ps = connect.prepareStatement(sql);
			ps.setString(1, code);
			ps.executeUpdate();
			ps.close();
			connect.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
