package controllers;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.*;

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
import models.Utilisateur;

public class NewUserController implements Initializable{
	@FXML
    private JFXButton cancelButton;

    @FXML
    private JFXTextField loginUser;

    @FXML
    private JFXComboBox<String> typeCompte;

    @FXML
    private JFXTextField passwordUser;

    @FXML
    private JFXButton saveButton;
    
    private ObservableList<String> liste = FXCollections.observableArrayList();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
		liste.add("Administrateur");
		typeCompte.setItems(liste);
		liste.add("Employé");
		typeCompte.setItems(liste);
		
		cancelButton.setOnAction((event) -> {
			Main.changeScreen("acceuil", Main.settingStage);
		});
		saveButton.setOnAction((event) -> {
			if (verifIfEmpty()) {
				Utilisateur user = new Utilisateur(loginUser.getText(), passwordUser.getText(), typeCompte.getValue());
				try {
					if(user.verifUser()) {
						user.addUser();
						this.setEmpty();
						Alert dialog = new Alert(AlertType.ERROR);
						dialog.setTitle("Message");
						dialog.setHeaderText("Succès Base de Données");
						dialog.setContentText("Les informations ont étés enregistrés");
						dialog.showAndWait();
					}else {
						Alert dialog = new Alert(AlertType.ERROR);
						dialog.setTitle("Erreur");
						dialog.setHeaderText("Erreur Base de Données");
						dialog.setContentText("Ce login est déja utilisé");
						dialog.showAndWait();
					}
				} catch (ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
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
		if (!loginUser.getText().trim().isEmpty() && !passwordUser.getText().trim().isEmpty() && !typeCompte.getValue().toString().trim().isEmpty()) {
			return true;
		}
		return false;
	}
	
	private void setEmpty() {
		loginUser.setText(null);
		passwordUser.setText(null);
		typeCompte.setValue(null);
	}
}
