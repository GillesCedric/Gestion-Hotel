package application;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;


public class Main extends Application {
	public static List<Parent> roots = new ArrayList<Parent>();
	public static List<Stage> stages = new ArrayList<Stage>();
	public static List<Scene> scenes = new ArrayList<Scene>();
	public static Stage loginStage = new Stage();
	public static Stage acceuilStage = new Stage();
	public static Stage settingStage = new Stage();
	public static Stage newReservationStage = new Stage();
	private static double x,y;
	@Override
	public void start(Stage primary) {
		try {
			roots.add(FXMLLoader.load(getClass().getResource("/views/LoginFormView.fxml")));
			roots.add(FXMLLoader.load(getClass().getResource("/views/AcceuilFormView.fxml")));
			roots.add(FXMLLoader.load(getClass().getResource("/views/SettingFormView.fxml")));
			roots.add(FXMLLoader.load(getClass().getResource("/views/ReservationFormView.fxml")));
			
			stages.add(loginStage);
			stages.add(acceuilStage);
			stages.add(settingStage);
			stages.add(newReservationStage);
			
			for(Stage s : stages) {
				s.initStyle(StageStyle.UNDECORATED);
				s.getIcons().add(new Image("/application/img/logoresto.png"));
			}
			
			for(int i = 0; i <= 3; i++) {
				scenes.add(new Scene(roots.get(i)));
			}
			
			autoriseDeplacement(0,loginStage);
			stages.get(0).setScene(scenes.get(0));
			stages.get(0).centerOnScreen();
			stages.get(0).setResizable(false);
			stages.get(0).show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public static void autoriseDeplacement(int i, Stage stage) {
		roots.get(i).setOnMousePressed(event -> {
			x = event.getSceneX();
			y = event.getSceneY();
		});
		roots.get(i).setOnMouseDragged(event -> {
			stage.setX(event.getScreenX() - x);
			stage.setY(event.getScreenY() - y);
		});
		/*roots.get(i).getStylesheets().removeAll();
		roots.get(i).getStylesheets().add(getClass().getResource("application.css").toExternalForm());*/
	}
	public static void changeScreen(String ecran,Stage lock) {
		switch(ecran) {
			case "setting" :
				stages.get(2).setScene(scenes.get(2));
				autoriseDeplacement(2,stages.get(2));
				//makeTransition(2,scenes.get(2));
				stages.get(2).show();
			break;
			case "acceuil" :
				stages.get(1).setScene(scenes.get(1));
				autoriseDeplacement(1,stages.get(1));
				makeTransition(1,scenes.get(1));
				stages.get(1).show();
				lock.close();
			break;
			case "newReservation" :
				stages.get(3).setScene(scenes.get(3));
				autoriseDeplacement(3,stages.get(3));
				//makeTransition(3,scenes.get(3));
				stages.get(3).show();
			break;
			case "login" :
				stages.get(0).setScene(scenes.get(0));
				autoriseDeplacement(0,stages.get(0));
				//makeTransition(0,scenes.get(0));
				stages.get(0).show();
				lock.close();
			break;
		}
	}
	
	public static void makeTransition(int i, Scene s) {
		roots.get(i).setLayoutX(0);
		roots.get(i).setLayoutY(0);
		Timeline timeline = new Timeline();
		KeyValue keyvalue;
		KeyFrame keyframe;
		
		if(generateNumber(1,2) == 1) {
			roots.get(i).translateYProperty().set(s.getHeight());
	 		keyvalue = new KeyValue(roots.get(i).translateYProperty(),0,Interpolator.EASE_IN);
		}else {
			roots.get(i).translateXProperty().set(s.getWidth());
	 		keyvalue = new KeyValue(roots.get(i).translateXProperty(),0,Interpolator.EASE_IN);
		}
		
		//roots.get(i).rotateProperty().set(360);
		keyframe = new KeyFrame(Duration.seconds(2),keyvalue);
		timeline.getKeyFrames().add(keyframe);
		
		timeline.play();
	}
	
	public static void makeTransition(Parent p, Pane pane) {
		p.setLayoutX(0);
		p.setLayoutY(0);
		Timeline timeline = new Timeline();
		KeyValue keyvalue;
		KeyFrame keyframe;
		if(generateNumber(1,2) == 1) {
			p.translateYProperty().set(pane.getHeight());
	 		keyvalue = new KeyValue(p.translateYProperty(),0,Interpolator.EASE_IN);
		}else {
			p.translateXProperty().set(pane.getWidth());
	 		keyvalue = new KeyValue(p.translateXProperty(),0,Interpolator.EASE_IN);
		}
		//p.rotateProperty().set(360);
		keyframe = new KeyFrame(Duration.seconds(2),keyvalue);
		timeline.getKeyFrames().add(keyframe);
		
		timeline.play();
	}
	
	private static int generateNumber(int min,int max) {
		return  min + (int) (Math.random() * ((max - min) + 1));
	}

}
