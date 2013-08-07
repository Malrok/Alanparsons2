package com.MRK.alanparsons2;

import com.MRK.alanparsons2.factories.LevelBuilder;
import com.MRK.alanparsons2.interfaces.AndroidCallback;
import com.MRK.alanparsons2.screens.LevelScreen;
import com.MRK.alanparsons2.screens.LoadingScreen;
import com.MRK.alanparsons2.screens.MainScreen;
import com.MRK.alanparsons2.templates.Screen;
import com.badlogic.gdx.Game;

public class Alanparsons2 extends Game {

	public static final boolean DEBUG = false; 
	
	private boolean isInitialized = false;
	private int width, height;
	
	private LevelBuilder levelBuilder = new LevelBuilder();
	private Screen screen;
	private AndroidCallback callback;
	
	public void setCallback(AndroidCallback callback) {
		this.callback = callback;
	}
	
	@Override
	public void pause() {
		super.pause();
		if (screen instanceof LevelScreen)
			((LevelScreen)screen).pause();
	}
	
	@Override
	public void resume() {
		super.resume();
		if (screen instanceof LevelScreen)
			((LevelScreen)screen).resume();
	}
	
	@Override
	public void render() {
		if (screen == null)
			screen = new MainScreen(width, height);
		
		screen.update();
		
		if (screen instanceof LevelScreen && callback != null) callback.beginRenderLevelCallback();
		
		screen.render();

		if (screen instanceof LevelScreen && callback != null) callback.endRenderLevelCallback();
		
		if (screen.result().length() != 0) {
			screen.dispose();

			if (screen instanceof MainScreen) {
				if (screen.result().startsWith("play"))
					screen = new LoadingScreen((screen.result().split(" ")[2]).equalsIgnoreCase("internal"), screen.result().split(" ")[1], levelBuilder, width, height);
			}
			if (screen instanceof LoadingScreen)
				if (screen.result().startsWith("loaded"))
					screen = new LevelScreen(levelBuilder.getLevel(), width, height);
			if (screen instanceof LevelScreen) {
				if (screen.result().equalsIgnoreCase("lose"))
					screen = new MainScreen(width, height);
			}
			
			screen.resize(width, height);
		}
	}

	@Override
	public void resize(int width, int height) {
		this.width = width;
		this.height = height;
	}

	@Override
	public void create() {
		if (!isInitialized) {
//			Music music = Gdx.audio.newMusic(Gdx.files.getFileHandle(
//					"data/8.12.mp3", FileType.Internal));
//			music.setLooping(true);
//			music.play();
			isInitialized = true;
		}
	}
	
	@Override
	public void dispose() {
		levelBuilder.dispose();
	}
}
