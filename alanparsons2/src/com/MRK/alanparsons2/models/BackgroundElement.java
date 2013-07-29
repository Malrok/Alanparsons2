package com.MRK.alanparsons2.models;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class BackgroundElement implements Comparable<BackgroundElement> {

	private boolean moveable;
	private int zrank;
	private float width, height;
	private float repeatWidth, repeatHeight;
	private float x, y;
	private float shiftx, shifty;
	private TextureRegion texture;
	
	public boolean isMoveable() {
		return moveable;
	}
	
	public void setMoveable(boolean moveable) {
		this.moveable = moveable;
	}
	
	public int getZrank() {
		return zrank;
	}
	
	public void setZrank(int zrank) {
		this.zrank = zrank;
	}
	
	public float getWidth() {
		return width;
	}
	
	public void setWidth(float width) {
		this.width = width;
	}
	
	public float getHeight() {
		return height;
	}
	
	public void setHeight(float height) {
		this.height = height;
	}
	
	public float getRepeatWidth() {
		return repeatWidth;
	}
	
	public void setRepeatWidth(float repeatWidth) {
		this.repeatWidth = repeatWidth;
	}
	
	public float getRepeatHeight() {
		return repeatHeight;
	}
	
	public void setRepeatHeight(float repeatHeight) {
		this.repeatHeight = repeatHeight;
	}
	
	public float getX() {
		return x;
	}
	
	public void setX(float x) {
		this.x = x;
	}
	
	public float getY() {
		return y;
	}
	
	public void setY(float y) {
		this.y = y;
	}
	
	public float getShiftx() {
		return shiftx;
	}
	
	public void setShiftx(float shiftx) {
		this.shiftx = shiftx;
	}
	
	public float getShifty() {
		return shifty;
	}
	
	public void setShifty(float shifty) {
		this.shifty = shifty;
	}
	
	public TextureRegion getTexture() {
		return texture;
	}
	
	public void setTexture(TextureRegion texture) {
		this.texture = texture;
	}

	@Override
	public int compareTo(BackgroundElement other) {
		if (zrank > other.getZrank())
			return -1;
		if (zrank < other.getZrank())
			return 1;
		return 0;
	}

	public void draw(SpriteBatch batch, float deltax, float deltay) {
		batch.draw(texture, getX() - deltax * shiftx, getY() - deltay * shifty, getWidth(), getHeight());
	}
}
