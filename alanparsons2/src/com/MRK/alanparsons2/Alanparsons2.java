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
		if (screen.isDone()) {
			// dispose the current screen
			screen.dispose();

//			// if this screen is a main menu screen we switch to
//			// the game loop
//			if (screen instanceof MainMenu)
//				screen = new GameScreen();
//			else
//			// if this screen is a game loop screen we switch to the
//			// game over screen
//			if (screen instanceof GameLoop)
//				screen = new GameOver();
//			else
//			// if this screen is a game over screen we switch to the
//			// main menu screen
//			if (screen instanceof GameOver)
//				screen = new MainMenu();
		}
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void create() {
		if (!isInitialized) {
			screen = new GameScreen();
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
