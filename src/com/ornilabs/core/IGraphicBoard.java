package com.ornilabs.core;

import java.util.List;

public interface IGraphicBoard {
	
	public void setParameters(IBoard board, IRobot userRobot);
	public void update(List<IRobot> fireringRobots);

}
