package gameComponents;

public enum RectangleType {
	RegularBrick,
	Twohit2, Twohit1,
	Threehit3,Threehit2,Threehit1,
	ExplosiveBrick,
	BallPacket,
	UnstoppablePacket,
	PhotonPacket,
	PhotonBullet,
	PhotonBlaster,
	//BallBrick, This will actually be a packet that will fall and when you hit it, with the paddle, a second ball will
	// appear in the center of the screen
	Bat, Ball, Window, LevelButton;
}
