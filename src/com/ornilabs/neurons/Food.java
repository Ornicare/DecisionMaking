package com.ornilabs.neurons;

public class Food {
	private double x;
	private double y;
	private boolean consumed;
	
	public Food(double x, double y){
		this.x = x;
		this.y = y;
		consumed = false;
	}
	public synchronized double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	public synchronized double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public boolean isConsumed() {
		return consumed;
	}
	
	public void consume() {
		consumed = true;
	}
}
