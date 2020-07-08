package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.IntStream;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.beans.binding.StringExpression;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;
import models.Chambre;
import models.Client;
import models.Reservation;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.cells.editors.base.JFXTreeTableCell;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.jfoenix.controls.*;

import application.Main;
import controllers.AcceuilFormController.InfosChambre;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.scene.control.TreeTableColumn.CellDataFeatures;
import javafx.scene.image.ImageView;

public class AcceuilFormController implements Initializable{
	 @FXML
	    private ImageView userProfile;

	    @FXML
	    private Label userName;

	    @FXML
	    private JFXButton acceuilButton;

	    @FXML
	    private JFXButton newReservationButton;

	    @FXML
	    private JFXButton listeChambresButton;

	    @FXML
	    private JFXButton parametresButton;

	    @FXML
	    private JFXButton deconnexionButton;
	    
	    @FXML
	    private JFXButton listeClientsButton;

	    @FXML
	    private Label nombreChambresTotal;

	    @FXML
	    private Label nombreClientsTotal;

	    @FXML
	    private Label nombreChambresOccupe;

	    @FXML
	    private Label nombreChambresLibre;

	    @FXML
	    private Pane listeChambresPanel;

	    @FXML
	    private Pane acceuilPanelNew;


	    @FXML
	    private TreeTableView listInformations;
	    
	    @FXML
	    private TreeTableView<InfosChambre> listeChambre;
	    
	    @FXML
	    private Pane listeClientsPanel;

	    @FXML
	    private TreeTableView<InfosClient> listeClient;

	    @FXML
	    private JFXTextField search;
	    
	    @FXML
	    private FontAwesomeIcon lockdownButton;

    
    public String css = "[file:/C:/InfosReservations/CEDRIC/eclipse-workspace/Projet2/bin/application/css/StyleDark.css]";

	@Override
	public void initialize(URL location, ResourceBundle resources) {
			lockdownButton.setOnMouseClicked(event -> {
				System.exit(0);
			});
	        Reservation reservation = new Reservation();
			Client clients = new Client();
			Chambre chambres = new Chambre();
			ResultSet rs;
			try {
				rs = clients.getAllClient();
				rs.last();
				int nb = rs.getRow();
				nombreClientsTotal.setText(String.valueOf(nb));
				
				rs = chambres.getChambre();
				rs.last();
				nb = rs.getRow();
				nombreChambresTotal.setText(String.valueOf(nb));
				
				rs = chambres.getChambreLibre();
				rs.last();
				nb = rs.getRow();
				nombreChambresLibre.setText(String.valueOf(nb));
				
				rs = chambres.getChambreOccupe();
				rs.last();
				nb = rs.getRow();
				nombreChambresOccupe.setText(String.valueOf(nb));
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			final TreeTableColumn codeclient = new TreeTableColumn<InfosReservation, String>("Code client");
			final TreeTableColumn nom = new TreeTableColumn<InfosReservation, String>("Nom");
			final TreeTableColumn prenom = new TreeTableColumn<InfosReservation, String>("Prénom");
			final TreeTableColumn tel = new TreeTableColumn<InfosReservation, String>("Tél");
			final TreeTableColumn numchambre = new TreeTableColumn<InfosReservation, String>("N° de chambre");
			final TreeTableColumn prix = new TreeTableColumn<InfosReservation, String>("Prix");
			final TreeTableColumn categorie = new TreeTableColumn<InfosReservation, String>("Catégorie");
			final TreeTableColumn datedebut = new TreeTableColumn<InfosReservation, String>("Date de début");
			final TreeTableColumn datefin = new TreeTableColumn<InfosReservation, String>("Date de fin");
			final TreeTableColumn delete = new TreeTableColumn<InfosReservation, Void>("Supprimer");
			final TreeTableColumn update = new TreeTableColumn<InfosReservation, String>("Modifier");
			
			codeclient.setCellValueFactory(param -> new SimpleStringProperty(((CellDataFeatures<InfosReservation,String>) param).getValue().getValue().codeclient));
	        nom.setCellValueFactory(param -> new SimpleStringProperty(((CellDataFeatures<InfosReservation,String>) param).getValue().getValue().nom));
	        prenom.setCellValueFactory(param -> new SimpleStringProperty(((CellDataFeatures<InfosReservation,String>) param).getValue().getValue().prenom));
	        tel.setCellValueFactory(param -> new SimpleStringProperty(((CellDataFeatures<InfosReservation,String>) param).getValue().getValue().tel));
	        numchambre.setCellValueFactory(param -> new SimpleStringProperty(((CellDataFeatures<InfosReservation,String>) param).getValue().getValue().numchambre));
	        prix.setCellValueFactory(param -> new SimpleStringProperty(((CellDataFeatures<InfosReservation,String>) param).getValue().getValue().prix));
	        categorie.setCellValueFactory(param -> new SimpleStringProperty(((CellDataFeatures<InfosReservation,String>) param).getValue().getValue().categorie));
	        datedebut.setCellValueFactory(param -> new SimpleStringProperty(((CellDataFeatures<InfosReservation,String>) param).getValue().getValue().datedebut));
	        datefin.setCellValueFactory(param -> new SimpleStringProperty(((CellDataFeatures<InfosReservation,String>) param).getValue().getValue().datefin));
	        
	        delete.setCellFactory(column -> new TreeTableCell<InfosReservation, Void>() {
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
	                final TreeItem<InfosReservation> treeItem = getTreeTableRow().getTreeItem();
	                final InfosReservation item = treeItem.getValue();
	             // @todo Request deletion of item in DB.
	                Reservation.deleteReservation(item.codeclient, item.numchambre);
	                // @todo When done, remove tree item from tree.
	                listeClient.getRoot().getChildren().remove(rowIndex);
	                showDialogMessage();
	            }
	        });
	        
	        update.setCellFactory(column -> new TreeTableCell<InfosReservation, Void>() {
	            private Button renderer;
	 
	            @Override
	            protected void updateItem(Void value, boolean empty) {
	                super.updateItem(value, empty);
	                Node graphic = null;
	                if (!empty) {
	                    if (Objects.isNull(renderer)) {
	                        renderer = new Button("Update");
	                        renderer.getStyleClass().add("update-button");
	                        renderer.setMaxWidth(Double.MAX_VALUE);
	                        renderer.setOnAction(event -> mayUpdateRow());
	                    }
	                    graphic = renderer;
	                }
	                setText(null);
	                setGraphic(graphic);
	            }
	 
	            private void mayUpdateRow() {
	                final int rowIndex = getTreeTableRow().getIndex();
	                final TreeItem<InfosReservation> treeItem = getTreeTableRow().getTreeItem();
	                final String item = treeItem.getValue().numchambre;
	                System.out.printf("Request to update row #%d: %s%n", rowIndex, item);
	                // @todo Request deletion of item in DB.
	                // @todo When done, remove tree item from tree.
	            }
	        });
	        
	        listInformations.setColumnResizePolicy(TreeTableView.CONSTRAINED_RESIZE_POLICY);
	        listInformations.setShowRoot(false);
	        listInformations.getColumns().setAll(codeclient, nom, prenom,tel, numchambre,prix, categorie,datedebut, datefin,delete, update);
	        //final StackPane root = new StackPane(listInformations);
	        //final Scene scene = new Scene(root);
	        /*Optional.ofNullable(getClass().getResource("styles.css"))
	                .stream()
	                .map(URL::toExternalForm)
	                .forEach(scene.getStylesheets()::add);*/
	        
			rs = reservation.getAllReservation();
	        ObservableList<InfosReservation> InfosReservations = FXCollections.observableArrayList();
	        try {
				while(rs.next()) {
					InfosReservations.add(new InfosReservation(rs.getString("codeclient"),rs.getString("nom"), rs.getString("prenom"), rs.getString("tel"), String.valueOf(rs.getInt("numchambre")), rs.getString("description"),String.valueOf(rs.getInt("prix")), rs.getString("datedebut"), rs.getString("datefin")));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
	        final TreeItem treeRoot = new RecursiveTreeItem<InfosReservation>(InfosReservations,RecursiveTreeObject::getChildren);
	        listInformations.setRoot(treeRoot);
	        listInformations.setShowRoot(false);
	        
	        
			final TreeTableColumn numChambreColumn = new TreeTableColumn<InfosChambre, String>("N° de chambre");
			final TreeTableColumn numEtageColumn = new TreeTableColumn<InfosChambre, String>("N° de l'étage");
			final TreeTableColumn telColumn = new TreeTableColumn<InfosChambre, String>("N° de téléphone");
			final TreeTableColumn categorieColumn = new TreeTableColumn<InfosChambre, String>("Catégorie chambre");
			final TreeTableColumn prixColumn = new TreeTableColumn<InfosChambre, String>("Prix chambre");
			final TreeTableColumn deleteColumn = new TreeTableColumn<InfosChambre, Void>("Supprimer");
			final TreeTableColumn updateColumn = new TreeTableColumn<InfosChambre, String>("Modifier");
			
			numChambreColumn.setCellValueFactory(param -> new SimpleStringProperty(((CellDataFeatures<InfosChambre,String>) param).getValue().getValue().numChambre));
			numEtageColumn.setCellValueFactory(param -> new SimpleStringProperty(((CellDataFeatures<InfosChambre,String>) param).getValue().getValue().numEtage));
			telColumn.setCellValueFactory(param -> new SimpleStringProperty(((CellDataFeatures<InfosChambre,String>) param).getValue().getValue().Tel));
			categorieColumn.setCellValueFactory(param -> new SimpleStringProperty(((CellDataFeatures<InfosChambre,String>) param).getValue().getValue().Categorie));
			prixColumn.setCellValueFactory(param -> new SimpleStringProperty(((CellDataFeatures<InfosChambre,String>) param).getValue().getValue().Prix));
	        
	        deleteColumn.setCellFactory(column -> new TreeTableCell<InfosChambre, Void>() {
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
	                final TreeItem<InfosChambre> treeItem = getTreeTableRow().getTreeItem();
	                final String item = treeItem.getValue().numChambre;
	             // @todo Request deletion of item in DB.
	                Chambre.deleteChambre(item);
	                // @todo When done, remove tree item from tree.
	                listeChambre.getRoot().getChildren().remove(rowIndex);
	                showDialogMessage();
	            }
	        });
	        
	        updateColumn.setCellFactory(column -> new TreeTableCell<InfosChambre, Void>() {
	            private Button renderer;
	 
	            @Override
	            protected void updateItem(Void value, boolean empty) {
	                super.updateItem(value, empty);
	                Node graphic = null;
	                if (!empty) {
	                    if (Objects.isNull(renderer)) {
	                        renderer = new Button("Update");
	                        renderer.getStyleClass().add("delete-button");
	                        renderer.setMaxWidth(Double.MAX_VALUE);
	                        renderer.setOnAction(event -> mayUpdateRow());
	                    }
	                    graphic = renderer;
	                }
	                setText(null);
	                setGraphic(graphic);
	            }
	 
	            private void mayUpdateRow() {
	                final int rowIndex = getTreeTableRow().getIndex();
	                final TreeItem<InfosChambre> treeItem = getTreeTableRow().getTreeItem();
	                final String item = treeItem.getValue().numChambre;
	                System.out.printf("Request to update row #%d: %s%n", rowIndex, item);
	                // @todo Request deletion of item in DB.
	                // @todo When done, remove tree item from tree.
	            }
	        });
	        
	        listeChambre.setColumnResizePolicy(TreeTableView.CONSTRAINED_RESIZE_POLICY);
	        listeChambre.setShowRoot(false);
	        listeChambre.getColumns().setAll(numChambreColumn, numEtageColumn,telColumn, categorieColumn,prixColumn,deleteColumn, updateColumn);
			
			rs = chambres.getAllChambre();
	        ObservableList<InfosChambre> InfosChambres = FXCollections.observableArrayList();
	        try {
				while(rs.next()) {
					InfosChambres.add(new InfosChambre(String.valueOf(rs.getInt("numchambre")), String.valueOf(rs.getInt("numetage")), rs.getString("tel"), rs.getString("description"),String.valueOf(rs.getInt("prix"))));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	        final TreeItem treeRoot2 = new RecursiveTreeItem<InfosChambre>(InfosChambres,RecursiveTreeObject::getChildren);
	        listeChambre.setRoot(treeRoot2);
	        listeChambre.setShowRoot(false);
	        
	        final TreeTableColumn codeClientColumn = new TreeTableColumn<InfosClient, String>("Code du client");
	        final TreeTableColumn nomClientColumn = new TreeTableColumn<InfosClient, String>("Nom du client");
			final TreeTableColumn prenomClientColumn = new TreeTableColumn<InfosClient, String>("Prénom du client");
			final TreeTableColumn telClientColumn = new TreeTableColumn<InfosClient, String>("N° de téléphone client");
			final TreeTableColumn emailClientColumn = new TreeTableColumn<InfosClient, String>("Email du client");
			final TreeTableColumn numCniClientColumn = new TreeTableColumn<InfosClient, String>("N° de CNI du client");
			final TreeTableColumn deleteClientColumn = new TreeTableColumn<InfosClient, Void>("Supprimer");
			final TreeTableColumn updateClientColumn = new TreeTableColumn<InfosClient, String>("Modifier");
			
			codeClientColumn.setCellValueFactory(param -> new SimpleStringProperty(((CellDataFeatures<InfosClient,String>) param).getValue().getValue().CodeClient));
			nomClientColumn.setCellValueFactory(param -> new SimpleStringProperty(((CellDataFeatures<InfosClient,String>) param).getValue().getValue().Nom));
			prenomClientColumn.setCellValueFactory(param -> new SimpleStringProperty(((CellDataFeatures<InfosClient,String>) param).getValue().getValue().Prenom));
			telClientColumn.setCellValueFactory(param -> new SimpleStringProperty(((CellDataFeatures<InfosClient,String>) param).getValue().getValue().Tel));
			emailClientColumn.setCellValueFactory(param -> new SimpleStringProperty(((CellDataFeatures<InfosClient,String>) param).getValue().getValue().Email));
			numCniClientColumn.setCellValueFactory(param -> new SimpleStringProperty(((CellDataFeatures<InfosClient,String>) param).getValue().getValue().NumCni));
	        
	        deleteClientColumn.setCellFactory(column -> new TreeTableCell<InfosClient, Void>() {
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
	                final TreeItem<InfosClient> treeItem = getTreeTableRow().getTreeItem();
	                final String item = treeItem.getValue().CodeClient;
	                // @todo Request deletion of item in DB.
	                Client.deleteClient(item);
	                // @todo When done, remove tree item from tree.
	                listeClient.getRoot().getChildren().remove(rowIndex);
	                showDialogMessage();
	            }
	        });
	        
	        updateClientColumn.setCellFactory(column -> new TreeTableCell<InfosClient, Void>() {
	            private Button renderer;
	 
	            @Override
	            protected void updateItem(Void value, boolean empty) {
	                super.updateItem(value, empty);
	                Node graphic = null;
	                if (!empty) {
	                    if (Objects.isNull(renderer)) {
	                        renderer = new Button("Update");
	                        renderer.getStyleClass().add("delete-button");
	                        renderer.setMaxWidth(Double.MAX_VALUE);
	                         		renderer.setOnAction(event -> mayUpdateRow());
	                    }
	                    graphic = renderer;
	                }
	                setText(null);
	                setGraphic(graphic);
	            }
	 
	            private void mayUpdateRow() {
	                final int rowIndex = getTreeTableRow().getIndex();
	                final TreeItem<InfosClient> treeItem = getTreeTableRow().getTreeItem();
	                final String item = treeItem.getValue().CodeClient;
	                System.out.printf("Request to update row #%d: %s%n", rowIndex, item);
	                // @todo Request deletion of item in DB.
	                // @todo When done, remove tree item from tree.
	            }
	        });
	        
	        listeClient.setColumnResizePolicy(TreeTableView.CONSTRAINED_RESIZE_POLICY);
	        listeClient.setShowRoot(false);
	        listeClient.getColumns().setAll(codeClientColumn, nomClientColumn,prenomClientColumn, telClientColumn,emailClientColumn,numCniClientColumn,deleteClientColumn, updateClientColumn);
	        
	        rs = clients.getAllClient();
	        ObservableList<InfosClient> InfosClients = FXCollections.observableArrayList();
	        try {
				while(rs.next()) {
					InfosClients.add(new InfosClient(rs.getString("codeclient"), rs.getString("nom"),rs.getString("prenom"),rs.getString("tel"),rs.getString("email"),rs.getString("numcni")));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
	        final TreeItem treeRoot3 = new RecursiveTreeItem<InfosClient>(InfosClients,RecursiveTreeObject::getChildren);
	        listeClient.setRoot(treeRoot3);
	        listeClient.setShowRoot(false);
	        listeClient.setEditable(true);

	    }
	
	protected void showDialogMessage() {
		// TODO Auto-generated method stub
		Alert dialog = new Alert(AlertType.ERROR);
		dialog.setTitle("Message");
		dialog.setHeaderText("Succès Base de Données");
		dialog.setContentText("Les informations ont étés supprimées de la base de données");
		dialog.showAndWait();
	}

	public void handleClicks(ActionEvent actionEvent) throws IOException {
		/*AnchorPane pane = FXMLLoader.load(getClass().getResource("/views/AcceuilFormView.fxml"));
		pane.getInfosReservationAgentStylesheet();
		System.out.print(pane.getStylesheets());*/
        if (actionEvent.getSource() == acceuilButton) {
            acceuilPanelNew.setStyle("-fx-background-color : #02030A");
            acceuilPanelNew.toFront();
        }
        if (actionEvent.getSource() == listeChambresButton) {
            listeChambresPanel.setStyle("-fx-background-color : #02030A");
        	listeChambresPanel.toFront();
        }
        if (actionEvent.getSource() == listeClientsButton) {
            listeClientsPanel.setStyle("-fx-background-color : #02030A");
            listeClientsPanel.toFront();
        }
        if (actionEvent.getSource() == deconnexionButton) {
            Main.changeScreen("login", Main.acceuilStage);
        }
        if (actionEvent.getSource() == parametresButton) {
            Main.changeScreen("setting", Main.acceuilStage);
        }
        if (actionEvent.getSource() == newReservationButton) {
            Main.changeScreen("newReservation", Main.acceuilStage);
        }
    }

	class InfosReservation extends RecursiveTreeObject<InfosReservation> {

		String codeclient;
        String nom;
        String prenom;
        String tel;
        String numchambre;
        String prix;
        String categorie;
        String datedebut;
        String datefin;

        public InfosReservation(String codeclient, String nom, String prenom, String tel,String numchambre, String prix, String categorie,String datedebut, String datefin) {
            this.codeclient = codeclient;
        	this.nom = nom;
            this.prenom = prenom;
            this.tel = tel;
            this.numchambre = numchambre;
            this.prix = prix;
            this.categorie = categorie;
            this.datedebut = datedebut;
            this.datefin = datefin;
        }

    }
	
	class InfosChambre extends RecursiveTreeObject<InfosChambre> {

        String numChambre;
        String numEtage;
        String Tel;
        String Categorie;
        String Prix;

        public InfosChambre(String numChambre, String numEtage, String Tel,String Categorie, String Prix) {
            this.numChambre = numChambre;
            this.numEtage = numEtage;
            this.Tel = Tel;
            this.Categorie = Categorie;
            this.Prix = Prix;
        }

    }
	
	class InfosClient extends RecursiveTreeObject<InfosClient> {

        String CodeClient;
        String Nom;
        String Prenom;
        String Tel;
        String Email;
        String NumCni;

        public InfosClient(String CodeClient, String Nom, String Prenom,String Tel, String Email, String NumCni) {
            this.CodeClient = CodeClient;
            this.Nom = Nom;
            this.Prenom = Prenom;
            this.Tel = Tel;
            this.Email = Email;
            this.NumCni = NumCni;
        }

    }
}
