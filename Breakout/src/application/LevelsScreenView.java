package application;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import java.util.LinkedList;
import java.util.Optional;

import javafx.application.Application;

public class LevelsScreenView extends Application {
	private Stage window;
	private Scene scene;
	private Pane layout;
	private LevelsScreenModel lsm;
	private LinkedList<Button> guiButtons;
	private LinkedList<gameComponents.Button> dataButtons;
	private Label highScore;

	public LevelsScreenView(LevelsScreenModel lsm) {
		this.lsm = lsm;
		dataButtons = new LinkedList<gameComponents.Button>();
		guiButtons = new LinkedList<Button>();
		layout = new Pane();
	}


	@Override
	public void start(Stage primaryStage) throws Exception {
		window = primaryStage;
		window.setResizable(false);
		scene = new Scene(layout, TheController.getBoardWidth(), TheController.getBoardHeight());
		window.setScene(scene);
		window.show();
		highScore = new Label();
		highScore.setText(Integer.toString(lsm.getHighScore()));
		highScore.setTranslateY(500);
		highScore.setTranslateX(TheController.getBoardWidth()/2);
		layout.getChildren().add(highScore);
		//drawArrayOfButtons();
		//Optional<Double> r = dataButtons.stream().map(i -> i.getBottomRightCoordinate().getY()).max(Double::compare);
	}

	public void drawArrayOfButtons() {
		for (int column = 0; column < lsm.getButtons().length; column++) {

			for (int row = 0; row < lsm.getButtons()[column].length; row++) {

				gameComponents.Button b = lsm.getButtons()[column][row];
				Button graphicalRect = new Button();
				layout.getChildren().add(graphicalRect);
				dataButtons.add(b);
				graphicalRect.setId(Integer.toString(b.getId()));
				graphicalRect.setPrefWidth(b.getWidth());
				graphicalRect.setPrefHeight(b.getHeight());
				graphicalRect.setTranslateX(b.getTopLeftCoordinate().getX());
				graphicalRect.setTranslateY(b.getTopLeftCoordinate().getY());
				guiButtons.add(graphicalRect);
			}
		}
	}

	public LinkedList<gameComponents.Button> getDataButtons() {
		return dataButtons;
	}
	public LevelsScreenModel getLsm() {
		return lsm;
	}


	public LinkedList<Button> getGUIButtons() {
		return guiButtons;
	}
}
