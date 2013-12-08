package com.ornilabs.core;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;

import javax.swing.JFrame;

public class GraphicBoard extends JFrame implements IGraphicBoard, KeyListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5316113647353370088L;
	private BoardPanel panel;
	private IRobot userRobot;
	private IBoard board;
	private double stepAngle = 1 / 360.0 * Math.PI * 2;
	private boolean[] keys = { false, false, false, false, false };

	public GraphicBoard() {
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Robots board");
		setBackground(new Color(255, 0, 255));
		addKeyListener(this);
	}

	@Override
	public void setParameters(IBoard board, IRobot userRobot) {
		this.userRobot = userRobot;
		this.board = board;

		// init
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double width = screenSize.getWidth();
		double height = screenSize.getHeight();

		setBounds((int) (width / 2 - board.getXSize() / 2),
				((int) height / 2 - board.getYSize() / 2), board.getXSize(),
				board.getYSize());

		panel = new BoardPanel(board);
		this.add(panel);
		this.setVisible(true);
	}

	@Override
	public void update(List<IRobot> fireringRobots) {
		if (keys[0])
			userRobot.accel(1);
		if (keys[1])
			userRobot.accel(-1);
		if (keys[2])
			userRobot.turn(-stepAngle);
		if (keys[3])
			userRobot.turn(stepAngle);
		if (keys[4]) {

			userRobot.fire();
			board.drawFire(userRobot);
		}
		
		
		panel.setFirering(fireringRobots);
		panel.repaint();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			keys[0] = true;
			break;
		case KeyEvent.VK_DOWN:
			keys[1] = true;
			break;
		case KeyEvent.VK_LEFT:
			keys[2] = true;
			break;
		case KeyEvent.VK_RIGHT:
			keys[3] = true;
			break;
		case KeyEvent.VK_SPACE:
			keys[4] = true;
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			keys[0] = false;
			break;
		case KeyEvent.VK_DOWN:
			keys[1] = false;
			break;
		case KeyEvent.VK_LEFT:
			keys[2] = false;
			break;
		case KeyEvent.VK_RIGHT:
			keys[3] = false;
			break;
		case KeyEvent.VK_SPACE:
			keys[4] = false;
			break;
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

}
