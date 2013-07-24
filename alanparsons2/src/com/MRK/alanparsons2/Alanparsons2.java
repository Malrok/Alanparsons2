package com.MRK.alanparsons2;

import com.MRK.alanparsons2.interfaces.AndroidCallback;
import com.MRK.alanparsons2.screens.LevelScreen;
import com.MRK.alanparsons2.screens.MainScreen;
import com.MRK.alanparsons2.screens.RabbitvsSausages;
import com.MRK.alanparsons2.screens.Screen;
import com.badlogic.gdx.Game;

public class Alanparsons2 extends Game {

	public static final boolean DEBUG = true; 
	
	private boolean isInitialized = false;
	private int width, height;
	
	private Screen screen;
	
	private AndroidCallback callback;
	
	public void setCallback(AndroidCallback callback) {
		this.callback = callback;
	}
	
	@Override
	public void render() {
		if (screen == null)
			screen = new MainScreen(width, height);
		
		screen.update();
		
		if (screen instanceof LevelScreen && callback != null)
//			System.out.println("begin callback");
			callback.beginRenderLevelCallback();
//		} else
//			System.out.println("no begin callback");
		
		screen.render();

		if (screen instanceof LevelScreen && callback != null)
//			System.out.println("end callback");
			callback.endRenderLevelCallback();
//		} else
//			System.out.println("no end callback");
		
		if (screen.result().length() != 0) {
			screen.dispose();

			if (screen instanceof MainScreen) {
				if (screen.result().equalsIgnoreCase("play"))
					screen = new LevelScreen(width, height);
			}
			if (screen instanceof RabbitvsSausages) {
				if (screen.result().equalsIgnoreCase("lose"))
					screen = new MainScreen(width, height);
			}
		}
	}

	@Override
	public void resize(int width, int height) {
		this.width = width;
		this.height = height;
		
		if (screen != null)
			screen.resize(width, height);
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
	
}
