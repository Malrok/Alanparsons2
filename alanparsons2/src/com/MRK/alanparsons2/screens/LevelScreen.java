package com.MRK.alanparsons2.screens;

import com.MRK.alanparsons2.models.Level;
import com.MRK.alanparsons2.renderers.LevelRenderer;
import com.badlogic.gdx.Gdx;

public class LevelScreen implements Screen {

//	private Level level;
	private LevelRenderer renderer; 
	
	private String result = "";
	
	public LevelScreen(Level level, int width, int height) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		
//		level = new Level();
		renderer = new LevelRenderer(level);		
		renderer.resize(width, height);
	}
	
	@Override
	public void update() {
		renderer.update();
	}

	@Override
	public void render() {
		renderer.render();
	}

	@Override
	public String result() {
		return result;
	}

	@Override
	public void dispose() {
//		level.dispose();
		renderer.dispose();
	}

	@Override
	public void resize(int width, int height) {
		renderer.resize(width, height);
	}
}
