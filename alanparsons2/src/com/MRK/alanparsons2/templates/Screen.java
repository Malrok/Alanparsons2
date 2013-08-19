package com.MRK.alanparsons2.templates;

public interface Screen {

	public void update();
	
	public void render();
	
	public String result();
	
	public void dispose();
	
	public void resize(int width, int height);
	
	public void backKeyStroke();
	
}
