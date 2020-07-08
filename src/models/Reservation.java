package models;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import application.DBConnection;
import application.Parameter;

public class Reservation{
	static DBConnection connection = new DBConnection(Parameter.HOST_DB, Parameter.USERNAME_DB, Parameter.PASSWORD_DB,
			Parameter.IPHOST, Parameter.PORT);

	private String codeClient;
    private int numChambre;
    private LocalDate dateDebut;
    private LocalDate dateFin;

	public Reservation(String codeClient, int numChambre, LocalDate dateDebut, LocalDate dateFin) {
		this.codeClient = codeClient;
		this.numChambre = numChambre;
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
	}
	
	public Reservation() {
		
	}

	public ResultSet getAllReservation() {
		try {
			Connection connect = connection.connexionDatabase();
			String sql = "SELECT reservation.codeclient,nom,prenom,client.tel,chambre.numchambre,prix,description,datedebut,datefin FROM reservation,client,chambre,categorie WHERE client.codeclient=reservation.codeclient  AND chambre.numchambre=reservation.numchambre AND categorie.id=chambre.idcategorie";
			PreparedStatement ps = connect.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			return rs;
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
		return null;
	}
	
	public void insertAll() {
		Connection connect = connection.connexionDatabase();
		String sql = "INSERT INTO reservation(datedebut,datefin,codeclient,numchambre) VALUES(?,?,?,?)";
		PreparedStatement ps;
		try {
			ps = connect.prepareStatement(sql);
			ps.setString(3, this.codeClient);
			ps.setInt(4, this.numChambre);
			ps.setString(1, this.dateDebut.toString());
			ps.setString(2, this.dateFin.toString());
			ps.executeUpdate();
			ps.close();
			connect.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void deleteReservation(String code, String num) {
		Connection connect = connection.connexionDatabase();
		String sql = "DELETE FROM reservation WHERE codeclient = ? AND numchambre = ?";
		PreparedStatement ps;
		try {
			ps = connect.prepareStatement(sql);
			ps.setString(1, code);
			ps.setString(2, num);
			ps.executeUpdate();
			ps.close();
			connect.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getCodeClient() {
		return codeClient;
	}

	public int getNumChambre() {
		return numChambre;
	}

	public LocalDate getDateDebut() {
		return dateDebut;
	}

	public LocalDate getDateFin() {
		return dateFin;
	}

	public void setCodeClient(String codeClient) {
		this.codeClient = codeClient;
	}

	public void setNumChambre(int numChambre) {
		this.numChambre = numChambre;
	}

	public void setDateDebut(LocalDate dateDebut) {
		this.dateDebut = dateDebut;
	}

	public void setDateFin(LocalDate dateFin) {
		this.dateFin = dateFin;
	}
	
}
