package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import application.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;

public class SettingController implements Initializable{
	@FXML
    private JFXButton cancelButton;

    @FXML
    private JFXButton saveButton;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		cancelButton.setOnAction((event) -> {
			Main.changeScreen("acceuil", Main.settingStage);
		});
		saveButton.setOnAction((event) -> {
			/*final URL cssURL = getClass().getResource("@../application/css/Style.css"); 
			final URL cssDarkURL = getClass().getResource("@../application/css/StyleDark.css"); 
			for(int i = 0;i <= 3; i++) {
				Main.scenes.get(i).getStylesheets().add(cssURL.toExternalForm());
				Main.scenes.get(i).getStylesheets().remove(cssDarkURL.toExternalForm());
				/*Main.scenes.get(i).getStylesheets().remove("application/css/StyleDark.css");
				Main.scenes.get(i).getStylesheets().add("application/css/Style.css");
			}*/
		});
			
	}
}
