package com.MRK.alanparsons2;

import com.MRK.alanparsons2.screens.GameScreen;
import com.MRK.alanparsons2.screens.MainScreen;
import com.MRK.alanparsons2.screens.Screen;
import com.badlogic.gdx.Game;

public class Alanparsons2 extends Game {

	private boolean isInitialized = false;
	
	private Screen screen;
	
	@Override
	public void render() {
		screen.update();

		screen.render();

		// when the screen is done we change to the
		// next screen
		if (screen.result().length() != 0) {
			System.out.println("result=" + screen.result());
			// dispose the current screen
			screen.dispose();

			if (screen instanceof MainScreen) {
				if (screen.result().equalsIgnoreCase("play"))
					screen = new GameScreen();
			}
			if (screen instanceof GameScreen) {
				if (screen.result().equalsIgnoreCase("lose"))
					screen = new MainScreen();
			}
		}
	}

	@Override
	public void resize(int width, int height) {
		screen.resize(width, height);
	}

	@Override
	public void create() {
		if (!isInitialized) {
			screen = new MainScreen();
//			Music music = Gdx.audio.newMusic(Gdx.files.getFileHandle(
//					"data/8.12.mp3", FileType.Internal));
//			music.setLooping(true);
//			music.play();
			isInitialized = true;
		}
	}
	
}
