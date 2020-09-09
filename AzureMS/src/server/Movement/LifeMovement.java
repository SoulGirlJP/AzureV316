package server.Movement;

import java.awt.Point;

public interface LifeMovement extends LifeMovementFragment
{

	
	Point getPosition();

	int getNewstate();

	int getDuration();

	int getType();
}
