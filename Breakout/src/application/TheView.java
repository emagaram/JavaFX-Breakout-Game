package application;

import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;

import java.util.Optional;

import javafx.application.Application;
import javafx.event.EventHandler;

public class TheView extends Application {
	private Stage window;
	private Scene scene;
	private Runnable onTypePaddleMoveLeft;
	private Runnable onTypePaddleMoveRight;
	private ImagePattern brickPattern;
	private ImagePattern batPattern;
	private ImagePattern ballPattern;
	private Image brickImage;
	private Image batImage;
	private Image ballImage;
	private int width;
	private int height;
	Label score;
	private Pane layout;

	@Override
	public void start(Stage primaryStage) throws Exception {
		window = primaryStage;
		window.setResizable(false);
        brickImage = new Image("Square.png");
        batImage = new Image("Paddle.png");
        ballImage = new Image("Ball.png");

        brickPattern = new ImagePattern(brickImage);
        batPattern = new ImagePattern(batImage);
		ballPattern = new ImagePattern(ballImage);

        layout = new Pane();
		score = new Label();
		score.setTranslateX(250);
		score.setTranslateY(250);
		scene = new Scene(layout, width, height);

		scene.addEventFilter(KeyEvent.ANY, new EventHandler<KeyEvent>() {
			public void handle(KeyEvent k) {
				if (k.getCode() == KeyCode.A) {
					onTypePaddleMoveLeft.run();
				}

				else if (k.getCode() == KeyCode.D) {
					onTypePaddleMoveRight.run();
				}
			}
		});
		window.setScene(scene);
		window.show();
	}

	public void drawRectangle(application.Rectangle r) {
		// find a graphical rectangle in children where r.Id = gr.Id
		// if it isn't there, create one and add it to children
		// then...
		//
		// gr.setwidth, etc. according to r
		Optional<Node> graphicalRectNode = layout
			.getChildren()
			.stream()
			.filter(n->n instanceof Rectangle)
			.filter(n->n.getId().equals(Integer.toString(r.getId())))
			.findFirst();
		Rectangle graphicalRect = null;
		if (graphicalRectNode.isPresent()) {
			graphicalRect = (Rectangle)(graphicalRectNode.get());
		}
		else {
			graphicalRect=new Rectangle();
			if(r.getType()==RectangleType.Brick){
			graphicalRect.setFill(brickPattern);
			}
			else if(r.getType()==RectangleType.Bat){
				graphicalRect.setFill(batPattern);
			}
			else if(r.getType()==RectangleType.Ball){
				graphicalRect.setFill(ballPattern);
			}
			//imageDrawer.drawImage(img, graphicalRect.getLayoutX(), graphicalRect.getLayoutY());
			//Image i = new Image("Square.png");

			graphicalRect.setId(Integer.toString(r.getId()));
			layout.getChildren().add(graphicalRect);
		}
		graphicalRect.setOpacity(r.getOpacity());
		graphicalRect.setWidth(r.getWidth());
		graphicalRect.setHeight(r.getHeight());
		graphicalRect.setTranslateX(r.getTopLeftCoordinate().getX());
		graphicalRect.setTranslateY(r.getTopLeftCoordinate().getY());
	}

	public TheView(Runnable onTypePaddleMoveLeft, Runnable onTypePaddleMoveRight,int width,int height) {
		this.onTypePaddleMoveLeft = onTypePaddleMoveLeft;
		this.onTypePaddleMoveRight = onTypePaddleMoveRight;
		this.width = width;
		this.height = height;
	}
}