package com.MRK.alanparsons2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.MRK.alanparsons2.factories.LevelBuilder;
import com.MRK.alanparsons2.interfaces.AndroidCallback;
import com.MRK.alanparsons2.screens.LevelScreen;
import com.MRK.alanparsons2.screens.LevelSelect;
import com.MRK.alanparsons2.screens.LoadingScreen;
import com.MRK.alanparsons2.screens.MainScreen;
import com.MRK.alanparsons2.templates.Screen;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class Alanparsons2 extends Game {

	public static final boolean DEBUG = false; 
	
	private boolean isInitialized = false;
	private int width, height;
	
	private LevelBuilder levelBuilder = new LevelBuilder();
	private Screen screen;
	private AndroidCallback callback;
	
	private List<FileHandle> levels = new ArrayList<FileHandle>();
	private String currentLevel;
	
	public Alanparsons2() {
		FileHandle dirHandle;
		if (Gdx.app.getType() == ApplicationType.Android) {
		  dirHandle = Gdx.files.internal("levels");
		} else {
		  dirHandle = Gdx.files.internal("./bin/levels");
		}
		levels = Arrays.asList(dirHandle.list());
	}
	
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
		
		System.out.println("result = " + screen.result());
		
		if (screen.result().length() != 0) {
			screen.dispose();

			if (screen instanceof MainScreen) {
				if (screen.result().equalsIgnoreCase("next"))
					screen = new LevelSelect(levels, width, height);
			}
			if (screen instanceof LevelSelect)
				if (screen.result().startsWith("play")) {
					currentLevel = screen.result().split(" ")[1];
					System.out.println("currentLevel = " + currentLevel);
					screen = new LoadingScreen((screen.result().split(" ")[2]).equalsIgnoreCase("internal"), currentLevel, levelBuilder, width, height);
				}
			if (screen instanceof LoadingScreen)
				if (screen.result().startsWith("loaded"))
					screen = new LevelScreen(levelBuilder.getLevel(), width, height);
			if (screen instanceof LevelScreen) {
				if (screen.result().equalsIgnoreCase(LevelScreen.SELECT))
					screen = new LevelSelect(levels, width, height);
				else if (screen.result().equalsIgnoreCase(LevelScreen.NEXT))
					screen = new LevelSelect(levels, width, height);
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
