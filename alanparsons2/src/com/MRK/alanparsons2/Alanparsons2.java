package com.MRK.alanparsons2;

import com.MRK.alanparsons2.controllers.KeystrokeController;
import com.MRK.alanparsons2.controllers.LevelController;
import com.MRK.alanparsons2.controllers.ScreensController;
import com.MRK.alanparsons2.factories.LevelBuilder;
import com.MRK.alanparsons2.interfaces.AndroidCallback;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

public class Alanparsons2 extends Game {

	public static final boolean DEBUG = false;
	
	private boolean isInitialized = false;
	private LevelController levelController;
	private LevelBuilder levelBuilder = new LevelBuilder();
	private ScreensController screenController;
	private KeystrokeController keyController = new KeystrokeController();
	
	public void setCallback(AndroidCallback callback) {
		screenController.setCallback(callback);
	}
	
	@Override
	public void pause() {
		super.pause();
		screenController.pause();
	}
	
	@Override
	public void resume() {
		super.resume();
		screenController.resume();
	}
	
	@Override
	public void render() {
		keyController.update();
		
		if (keyController.keyReleased(Keys.BACK)) screenController.backKeyStroke();
		
		screenController.render();
	}

	@Override
	public void resize(int width, int height) {
		screenController.resize(width, height);
	}

	@Override
	public void create() {
		if (!isInitialized) {
			Gdx.input.setCatchBackKey(true);
			
//			Music music = Gdx.audio.newMusic(Gdx.files.getFileHandle(
//					"data/8.12.mp3", FileType.Internal));
//			music.setLooping(true);
//			music.play();
			
			levelController = new LevelController();
			levelController.init();
			
			screenController = new ScreensController(levelController, levelBuilder);
			
			isInitialized = true;
		}
	}
	
	@Override
	public void dispose() {
		levelBuilder.dispose();
		screenController.dispose();
	}
}
