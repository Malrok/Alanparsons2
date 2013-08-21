package com.MRK.alanparsons2.models;

import com.MRK.alanparsons2.helpers.PathInterpreter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class BackgroundElement implements Comparable<BackgroundElement> {

	private boolean moving;
	private boolean isRepeated;
	private int zrank;
	private float width, height;
	private float repeatX, repeatY;
	private float x, y;
	private String xFormula, yFormula;
	private float shiftx, shifty;
	private TextureRegion texture;
	
//	public boolean isMoveable() {
//		return moveable;
//	}
//	
//	public void setMoveable(boolean moveable) {
//		this.moveable = moveable;
//	}
	
	public boolean isRepeated() {
		return isRepeated;
	}

	public void setRepeated(boolean isRepeated) {
		this.isRepeated = isRepeated;
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
	
	public float getRepeatX() {
		return repeatX;
	}

	public void setRepeatX(float repeatX) {
		this.repeatX = repeatX;
	}

	public float getRepeatY() {
		return repeatY;
	}

	public void setRepeatY(float repeatY) {
		this.repeatY = repeatY;
	}

	public float getX(PathInterpreter interpreter) {
		if (!moving)
			return x;
		else {
			interpreter.evaluate(getxFormula());
			return interpreter.getInt("x");
		}
	}
	
	public void setX(float x) {
		this.x = x;
	}
	
	public float getY(PathInterpreter interpreter) {
		if (!moving)
			return y;
		else {
			interpreter.evaluate(getxFormula());
			return interpreter.getInt("y");
		}
	}
	
	public void setY(float y) {
		this.y = y;
	}
	
	public String getxFormula() {
		if (xFormula.toLowerCase().contains("t"))
			return xFormula.replaceAll("t", String.valueOf(Gdx.graphics.getDeltaTime()));
		else
			return xFormula;
	}

	public void setxFormula(String xFormula) {
		this.xFormula = xFormula;
		moving = true;
	}

	public String getyFormula() {
		if (yFormula.toLowerCase().contains("t"))
			return yFormula.replaceAll("t", String.valueOf(Gdx.graphics.getDeltaTime()));
		else
			return yFormula;
	}

	public void setyFormula(String yFormula) {
		this.yFormula = yFormula;
		moving = true;
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

//	public void draw(SpriteBatch batch, float deltax, float deltay) {
//		batch.draw(texture, getX() - deltax * shiftx, getY() - deltay * shifty, getWidth(), getHeight());
//	}
}
