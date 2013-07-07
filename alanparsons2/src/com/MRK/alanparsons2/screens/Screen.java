package com.MRK.alanparsons2.screens;

public interface Screen {

	public void update();
	
	public void render();
	
	public String result();
	
	public void dispose();
	
	public void resize(int width, int height);
	
}
