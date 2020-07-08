package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import application.Main;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import models.Utilisateur;

public class LoginFormController implements Initializable{
	//@FXML
	//private AnchoerPane 
	@FXML
	private Button loginButton; // Object injected by FXMLLoader(fx:id="btnHello")
	@FXML
	private Label  forgotPassword;// Object injected by FXMLLoader(fx:id="title")
	@FXML
    private JFXPasswordField passwordUser;

    @FXML
    private JFXTextField loginUser;
    
    @FXML
    private FontAwesomeIcon lockdownButton;

	@FXML
	private void handleloginButtonClicked(MouseEvent event) throws IOException {
		
		String login = loginUser.getText();
		String password = passwordUser.getText();
		if(!login.trim().isEmpty() && !password.trim().isEmpty()) {
			Utilisateur user = new Utilisateur(loginUser.getText(),passwordUser.getText());
			try {
				ResultSet rs = user.getUser();
				if(rs.next()) {
					this.setEmpty();
					Main.changeScreen("acceuil",Main.loginStage);
				}else {
					Alert dialog = new Alert(AlertType.ERROR);
					dialog.setTitle("Erreur");
					dialog.setHeaderText("Erreur Base de Données");
					dialog.setContentText("Le login et / ou le mot de passe sont incorrects");
					dialog.showAndWait();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			Alert dialog = new Alert(AlertType.ERROR);
			dialog.setTitle("Erreur");
			dialog.setHeaderText("Erreur Base de Données");
			dialog.setContentText("Veuillez remplir tous les champs");
			dialog.showAndWait();
		}
		
	}
	@FXML
	private void handleforgotPasswordAction(MouseEvent event) {
		//Main.changeScreen("forgotpassword",Main.forgotPasswordStage);
	}
	private void setEmpty() {
		loginUser.setText(null);
		passwordUser.setText(null);
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		lockdownButton.setOnMouseClicked(event -> {
			System.exit(0);
		});
	}
	
}