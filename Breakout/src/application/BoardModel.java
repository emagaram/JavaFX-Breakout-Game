package application;
import java.util.LinkedList;
import java.util.Optional;

public class BoardModel {
	private final int RAN_ANGLE = 5;
	private int gameScore = 0;
	private final int WIDTH;
	private final int HEIGHT;
	private final int BAT_WIDTH = 40;
	private final int BAT_HEIGHT = 10;
	private final int BAT_SPEED = 10;
	private int gapAboveBricks;
	private int brickRowHeight; // The height of a row of bricks, gap is not included
	private int brickHeight; // Similar to brickRowHeight but gap is included
	private int brickGapH; // The gap that will separate the bricks horizontally
	private int brickGapV; // The gap that will separate the bricks vertically
	private int brickColumns; // The number of columns of bricks
	private int brickRows; // The number of rows of bricks
	private int columnWidth;
	private int brickWidth;
	private Rectangle windowRectangle;
	private Rectangle bat;
	private Ball ball;
	private LinkedList<Rectangle> bricks = new LinkedList<Rectangle>();
	private LinkedList<Rectangle> destroyedBricks = new LinkedList<Rectangle>();
	private ICollisionDetector detector;

	public BoardModel(int width, int height, int brickRowHeight, int brickGapH, int brickGapV,
			int gapAboveBricks, int brickColumns,
		int brickRows, ICollisionDetector detector) {
		this.WIDTH = width;
		this.HEIGHT = height;
		this.detector = detector;
		this.windowRectangle = new Rectangle(new Coordinate(0,0),WIDTH,HEIGHT);
		this.brickColumns = brickColumns;
		this.brickRows = brickRows;
		this.brickRowHeight = brickRowHeight;
		this.brickGapH = brickGapH;
		this.brickGapV = brickGapV;
		this.gapAboveBricks = gapAboveBricks;
		this.columnWidth = WIDTH / brickColumns;
		this.brickWidth = columnWidth - brickGapH;
		this.brickHeight = brickRowHeight - brickGapV;
		//Brick creation algorithm
		for (int column = 0; column < brickColumns; column++) {
			for (int row = 0; row < brickRows; row++) {
				Rectangle r = new Rectangle(
				new Coordinate((column * columnWidth)+(brickGapH/2), (row * brickRowHeight) + gapAboveBricks), brickWidth,
				brickHeight);
				bricks.add(r);
			}
		}
		//Brick creation algorithm end
		Coordinate batUL = new Coordinate((WIDTH - BAT_WIDTH) / 2, (HEIGHT - BAT_HEIGHT));
		bat = new Rectangle(batUL, BAT_WIDTH, BAT_HEIGHT);
		ball = new Ball(new Coordinate(WIDTH / 2, HEIGHT / 2), 7, 7, 1, Math.toRadians(75));
	}

	public void movePaddleLeft() {
		if ((bat.getTopLeftCoordinate().getX() - BAT_SPEED) >= 0) {
			bat = bat.createMove(-BAT_SPEED, 0);
		}
	}

	public void movePaddleRight() {
		if (bat.getBottomRightCoordinate().getX() + BAT_SPEED <= WIDTH) {
			bat = bat.createMove(BAT_SPEED, 0);
		}
	}

	public void updateBricks() {
		for (Rectangle b : destroyedBricks) {
			b.setOpacity(0);
			b.setAlive(false);
		}
	}

	public void updateBall() {
		Ball nextBall = ball.getMove();
		LineSegment ballPath = new LineSegment(ball.getCenterCoordinate(), ball.getMove().getCenterCoordinate());
		boolean shouldFlipX = detector.intersects(ballPath, windowRectangle.getLeftLineSegment()) || detector.intersects(ballPath, windowRectangle.getRightLineSegment());
		boolean shouldFlipY = detector.intersects(ballPath, windowRectangle.getTopLineSegment()) || detector.intersects(ballPath, windowRectangle.getBottomLineSegment());
		Optional<Double> r = bricks.stream().map(i -> i.getBottomRightCoordinate().getY()).max(Double::compare);
		// Variables that will let the ball know what new object to be

		// Bat collision detection
		if (shouldFlipX==false&&shouldFlipY==false&&detector.intersects(ballPath, bat.getTopLineSegment())) {
			shouldFlipY=true;
		}
		// Bat collision detection end

		//Brick collision detection
		else if (shouldFlipX==false&&shouldFlipY==false&&r.get() >= ball.getTopLeftCoordinate().getY()) {
			for (Rectangle brick : bricks) {
				if (brick.isAlive()) {
						if(detector.intersects(ballPath,brick.getBottomLineSegment())||
						detector.intersects(ballPath,brick.getTopLineSegment())){
						 shouldFlipY=true;
						}
						if(detector.intersects(ballPath,brick.getLeftLineSegment())||detector.intersects(ballPath, brick.getRightLineSegment())){
							shouldFlipX=true;
						}
						if(shouldFlipY==true||shouldFlipX==true){
							destroyedBricks.add(brick);
							break;
						}
					}
				}
		}
		//Brick collision detection end

		//Calculating ball's new position
		if(shouldFlipX==false && shouldFlipY==false){
			ball = nextBall;
		}
		else if (shouldFlipX == true && shouldFlipY == true) {
			ball = ball.flipXDirection().flipYDirection().changeAngle(RAN_ANGLE);
		} else if (shouldFlipX == true && shouldFlipY == false) {
			ball = ball.flipXDirection().changeAngle(RAN_ANGLE);
		} else if (shouldFlipX == false && shouldFlipY == true) {
			ball = ball.flipYDirection().changeAngle(RAN_ANGLE);
		}

		//Gameover check
		if(brickColumns*brickRows==destroyedBricks.size()){
			gameScore+=1;
		}
	}

	public void updateAll() {
		updateBall();
		updateBricks();
	}

	public Rectangle getBat() {
		return bat;
	}

	public Rectangle getBall() {
		return ball;
	}

	public int getWidth() {
		return WIDTH;
	}

	public int getHeight() {
		return HEIGHT;
	}
	public int getGameScore() {
		return gameScore;
	}

	public LinkedList<Rectangle> getBricks() {
		return new LinkedList<Rectangle>(this.bricks);
	}
}
