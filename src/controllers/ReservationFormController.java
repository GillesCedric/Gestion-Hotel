package controllers;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import models.Chambre;
import models.Client;
import models.Reservation;

public class ReservationFormController implements Initializable {
	@FXML // fx:id="nomClient"
	private JFXTextField nomClient; // Value injected by FXMLLoader

	@FXML // fx:id="prenomClient"
	private JFXTextField prenomClient; // Value injected by FXMLLoader

	@FXML // fx:id="numTelClient"
	private JFXTextField numTelClient; // Value injected by FXMLLoader

	@FXML // fx:id="numCNIClient"
	private JFXTextField numCNIClient; // Value injected by FXMLLoader

	@FXML // fx:id="adresseMailClient"
	private JFXTextField adresseMailClient; // Value injected by FXMLLoader

	@FXML // fx:id="heureArriveClient"
	private JFXTimePicker heureArriveClient; // Value injected by FXMLLoader

	@FXML // fx:id="dateArriveClient"
	private JFXDatePicker dateArriveClient; // Value injected by FXMLLoader

	@FXML // fx:id="numChambreClient"
	private JFXComboBox<Integer> numChambreClient; // Value injected by FXMLLoader

	@FXML // fx:id="dateDepartClient"
	private JFXDatePicker dateDepartClient; // Value injected by FXMLLoader

	@FXML // fx:id="cancelButton"
	private JFXButton cancelButton; // Value injected by FXMLLoader

	@FXML // fx:id="saveButton"
	private JFXButton saveButton; // Value injected by FXMLLoader
	
	private ObservableList<Integer> list = FXCollections.observableArrayList();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Chambre chambre = new Chambre();
		ResultSet rs = chambre.getChambreLibre();
		try {
			while(rs.next()) {
				list.add(rs.getInt("numchambre"));
				numChambreClient.setItems(list);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cancelButton.setOnAction((event) -> {
			Main.changeScreen("acceuil", Main.newReservationStage);
		});
		saveButton.setOnAction((event) -> {
			if (verifIfEmpty()) {
				String codeclient = Client.generateCodeClient();
				Client client = new Client(codeclient, nomClient.getText(), prenomClient.getText(), numTelClient.getText(),
						adresseMailClient.getText(),numCNIClient.getText());
				Reservation reservation = new Reservation(codeclient,numChambreClient.getValue().intValue(),dateArriveClient.getValue(),dateDepartClient.getValue());
				client.insertAll();
				reservation.insertAll();
				this.setEmpty();
				Alert dialog = new Alert(AlertType.ERROR);
				dialog.setTitle("Message");
				dialog.setHeaderText("Succès Base de Données");
				dialog.setContentText("Les informations ont étés enregistrés");
				dialog.showAndWait();
			} else {
				Alert dialog = new Alert(AlertType.ERROR);
				dialog.setTitle("Erreur");
				dialog.setHeaderText("Erreur Base de Données");
				dialog.setContentText("Veuillez remplir tous les champs");
				dialog.showAndWait();
			}
		});

	}

	public boolean verifIfEmpty() {
		if (!nomClient.getText().trim().isEmpty() && !prenomClient.getText().trim().isEmpty()
				&& !numTelClient.getText().trim().isEmpty() && !numCNIClient.getText().trim().isEmpty()
				&& !adresseMailClient.getText().trim().isEmpty()
				&& !heureArriveClient.getValue().toString().trim().isEmpty()
				&& !dateArriveClient.getValue().toString().trim().isEmpty()
				&& !dateDepartClient.getValue().toString().trim().isEmpty()) {
			return true;
		}
		return false;
	}
	
	private void setEmpty() {
		nomClient.setText(null);
		prenomClient.setText(null);
		numTelClient.setText(null);
		numCNIClient.setText(null);
		adresseMailClient.setText(null);
		heureArriveClient.setValue(null);
		dateArriveClient.setValue(null);
		dateDepartClient.setValue(null);
	}
}
