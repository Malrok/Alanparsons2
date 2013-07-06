package com.MRK.alanparsons2;

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
//			if (screen instanceof GameOver) {
//				
//			}
		}
	}

	@Override
	public void resize(int width, int height) {

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
	
//	@Override
//	public void create() {
//		setScreen(new GameScreen());
//	}
	
}
