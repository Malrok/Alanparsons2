package com.MRK.alanparsons2;

import com.MRK.alanparsons2.screens.GameScreen;
import com.MRK.alanparsons2.screens.MainScreen;
import com.MRK.alanparsons2.screens.Screen;
import com.badlogic.gdx.Game;

public class Alanparsons2 extends Game {

	private boolean isInitialized = false;
	private int width, height;
	
	private Screen screen;
	
	@Override
	public void render() {
		if (screen == null)
			screen = new MainScreen(width, height);
		
		screen.update();

		screen.render();

		// when the screen is done we change to the
		// next screen
		if (screen.result().length() != 0) {
			// dispose the current screen
			screen.dispose();

			if (screen instanceof MainScreen) {
				if (screen.result().equalsIgnoreCase("play"))
					screen = new GameScreen(width, height);
			}
			if (screen instanceof GameScreen) {
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
