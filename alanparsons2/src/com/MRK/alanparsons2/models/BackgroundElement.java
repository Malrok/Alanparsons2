package com.MRK.alanparsons2.models;

import com.MRK.alanparsons2.helpers.PathInterpreter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
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
	private float time = 0;
	private PathInterpreter interpreter;
	private boolean isLight = false;
	
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
		setRepeated(true);
		this.repeatX = repeatX;
	}

	public float getRepeatY() {
		return repeatY;
	}

	public void setRepeatY(float repeatY) {
		setRepeated(true);
		this.repeatY = repeatY;
	}

	public float getX() {
		if (!moving)
			return x;
		else {
			interpreter.evaluate(new String[]{"t"}, new float[]{(time += Gdx.graphics.getDeltaTime())});
			return interpreter.getInt();
		}
	}
	
	public void setX(float x) {
		this.x = x;
	}
	
	public float getY() {
		if (!moving)
			return y;
		else {
			interpreter.evaluate(new String[]{"t"}, new float[]{(time += Gdx.graphics.getDeltaTime())});
			return interpreter.getInt();
		}
	}
	
	public void setY(float y) {
		this.y = y;
	}
	
	public String getxFormula() {
		return xFormula;
	}

	public void setxFormula(String xFormula) {
		this.xFormula = xFormula;
		if (interpreter == null) interpreter = new PathInterpreter();
		interpreter.parse(xFormula, "t");
		moving = true;
	}

	public String getyFormula() {
		return yFormula;
	}

	public void setyFormula(String yFormula) {
		this.yFormula = yFormula;
		if (interpreter == null) interpreter = new PathInterpreter();
		interpreter.parse(yFormula, "t");
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
		texture.getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		this.texture = texture;
	}

	public boolean isLight() {
		return isLight;
	}

	public void setLight(boolean isLight) {
		this.isLight = isLight;
	}

	@Override
	public int compareTo(BackgroundElement other) {
		if (zrank > other.getZrank())
			return -1;
		if (zrank < other.getZrank())
			return 1;
		return 0;
	}
}
