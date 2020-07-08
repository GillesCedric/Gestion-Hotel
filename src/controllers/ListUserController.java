package controllers;

import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Objects;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import application.Main;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.TreeTableColumn.CellDataFeatures;
import models.Reservation;
import models.Utilisateur;

public class ListUserController implements Initializable{
	@FXML
    private JFXButton cancelButton;

    @FXML
    private TreeTableView<InfosUser> listeUser;

		@Override
		public void initialize(URL location, ResourceBundle resources) {
			// TODO Auto-generated method stub
			Utilisateur user = new Utilisateur();
			ResultSet rs;
			
			cancelButton.setOnAction((event) -> {
				Main.changeScreen("acceuil", Main.settingStage);
			});
			
			final TreeTableColumn login = new TreeTableColumn<InfosUser, String>("Login");
			final TreeTableColumn password = new TreeTableColumn<InfosUser, String>("Mot de passe");
			final TreeTableColumn typecompte = new TreeTableColumn<InfosUser, String>("Type de compte");
			final TreeTableColumn delete = new TreeTableColumn<InfosUser, Void>("Supprimer");
			
			login.setCellValueFactory(param -> new SimpleStringProperty(((CellDataFeatures<InfosUser,String>) param).getValue().getValue().login));
	        password.setCellValueFactory(param -> new SimpleStringProperty(((CellDataFeatures<InfosUser,String>) param).getValue().getValue().password));
	        typecompte.setCellValueFactory(param -> new SimpleStringProperty(((CellDataFeatures<InfosUser,String>) param).getValue().getValue().compte));
	        delete.setCellFactory(column -> new TreeTableCell<InfosUser, Void>() {
	            private Button renderer;
	 
	            @Override
	            protected void updateItem(Void value, boolean empty) {
	                super.updateItem(value, empty);
	                Node graphic = null;
	                if (!empty) {
	                    if (Objects.isNull(renderer)) {
	                        renderer = new Button("Delete");
	                        renderer.getStyleClass().add("delete-button");
	                        renderer.setMaxWidth(Double.MAX_VALUE);
	                        renderer.setOnAction(event -> mayBeDeleteRow());
	                    }
	                    graphic = renderer;
	                }
	                setText(null);
	                setGraphic(graphic);
	            }
	 
	            private void mayBeDeleteRow() {
	                final int rowIndex = getTreeTableRow().getIndex();
	                final TreeItem<InfosUser> treeItem = getTreeTableRow().getTreeItem();
	                final InfosUser item = treeItem.getValue();
	             // @todo Request deletion of item in DB.
	                Reservation.deleteReservation(item.login, item.password);
	                // @todo When done, remove tree item from tree.
	                listeUser.getRoot().getChildren().remove(rowIndex);
	            }
	        });
	        
	        listeUser.setColumnResizePolicy(TreeTableView.CONSTRAINED_RESIZE_POLICY);
	        listeUser.setShowRoot(false);
	        listeUser.getColumns().setAll(login, password, typecompte,delete);
	        
	        ObservableList<InfosUser> InfosUsers = FXCollections.observableArrayList();
	        try {
	        	rs = user.getAllUser();
				while(rs.next()) {
					InfosUsers.add(new InfosUser(rs.getString("login"),rs.getString("password"), rs.getString("compte")));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
	        final TreeItem treeRoot = new RecursiveTreeItem<InfosUser>(InfosUsers,RecursiveTreeObject::getChildren);
	        listeUser.setRoot(treeRoot);
	        listeUser.setShowRoot(false);
		}
		
}

class InfosUser extends RecursiveTreeObject<InfosUser> {

    String login;
    String password;
    String compte;

    public InfosUser(String login, String password, String compte) {
        this.login = login;
        this.password = password;
        this.compte = compte;
    }

}
