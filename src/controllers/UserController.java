package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import application.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class UserController implements Initializable{

    @FXML
    private JFXButton cancelButton;
    
    @Override
	public void initialize(URL location, ResourceBundle resources) {
		cancelButton.setOnAction((event) -> {
			Main.changeScreen("acceuil", Main.settingStage);
		});
			
	}

}
