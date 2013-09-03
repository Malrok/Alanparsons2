package com.MRK.alanparsons2.templates;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface Screen {

	public void update();
	
	public void render(SpriteBatch batch);
	
	public String result();
	
	public void dispose();
	
	public void resize(int width, int height);
	
	public void backKeyStroke();
	
}
