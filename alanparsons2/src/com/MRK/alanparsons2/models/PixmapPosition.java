package com.MRK.alanparsons2.models;

/**
 * Positions sur un {@link Pixmap}
 */
public class PixmapPosition {
	private int x,y;
	
	public PixmapPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
}
