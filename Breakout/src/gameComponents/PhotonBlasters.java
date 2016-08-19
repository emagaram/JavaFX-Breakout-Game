package gameComponents;

import java.util.LinkedList;

public class PhotonBlasters {
	private Rectangle rightBlaster;
	private Rectangle leftBlaster;
	private int bulletWidth = 3;
	private double width = 12;
	private double height = 17;

	protected PhotonBlasters(double batWidth, Coordinate batTopLeft) {
		double yPosition = batTopLeft.getY() - height;
		Coordinate leftBlasterTopLeft = new Coordinate(batTopLeft.getX(), yPosition);
		Coordinate rightBlasterTopLeft = new Coordinate(batTopLeft.getX() + batWidth - this.getWidth(), yPosition);
		leftBlaster = new Rectangle(leftBlasterTopLeft, width, height, RectangleType.PhotonBlaster);
		rightBlaster = new Rectangle(rightBlasterTopLeft, width, height, RectangleType.PhotonBlaster);
	}

	protected PhotonBlasters(double batWidth, Coordinate batTopLeft, int leftBlasterId, int rightBlasterId) {
		double yPosition = batTopLeft.getY() - height;
		Coordinate leftBlasterTopLeft = new Coordinate(batTopLeft.getX(), yPosition);
		Coordinate rightBlasterTopLeft = new Coordinate(batTopLeft.getX() + batWidth - this.getWidth(), yPosition);
		leftBlaster = new Rectangle(leftBlasterTopLeft, width, height, leftBlasterId, RectangleType.PhotonBlaster);
		rightBlaster = new Rectangle(rightBlasterTopLeft, width, height, rightBlasterId, RectangleType.PhotonBlaster);
	}

	public PhotonBlasters getMove(double batWidth, Coordinate batTopLeft) {
		return new PhotonBlasters(batWidth, batTopLeft, this.getLeftBlaster().getId(), this.getRightBlaster().getId());
	}

	public void fireRightBlaster(LinkedList<PhotonBullet> photonBullets){
		Coordinate middleOfBlaster = new Coordinate(rightBlaster.getTopLeftCoordinate().getX()+(rightBlaster.getWidth()/2)-(bulletWidth/2),
				rightBlaster.getTopLeftCoordinate().getY());
		photonBullets.add(new PhotonBullet(middleOfBlaster,bulletWidth,10,RectangleType.PhotonBullet));
	}

	public void fireLeftBlaster(LinkedList<PhotonBullet> photonBullets) {
		Coordinate middleOfBlaster = new Coordinate(leftBlaster.getTopLeftCoordinate().getX()+(leftBlaster.getWidth()/2)-(bulletWidth/2),
				leftBlaster.getTopLeftCoordinate().getY());
		photonBullets.add(new PhotonBullet(middleOfBlaster, bulletWidth, 10, RectangleType.PhotonBullet));
	}

	public Rectangle getRightBlaster() {
		return rightBlaster;
	}

	public Rectangle getLeftBlaster() {
		return leftBlaster;
	}

	public double getWidth() {
		return width;
	}

	public double getHeight() {
		return height;
	}
}