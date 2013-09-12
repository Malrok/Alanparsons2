package com.MRK.alanparsons2.screens;

import com.MRK.alanparsons2.Alanparsons2;
import com.MRK.alanparsons2.constants.ScreenAction;
import com.MRK.alanparsons2.models.GameLevel;
import com.MRK.alanparsons2.renderers.LevelHUD;
import com.MRK.alanparsons2.renderers.LevelRenderer;
import com.MRK.alanparsons2.resources.GamePreferences;
import com.MRK.alanparsons2.templates.Screen;
import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class LevelScreen implements Screen {

	private LevelHUD hud;
	private Skin skin;
	private LevelRenderer renderer;
	private Music music;
	
	private String result = "";
	private boolean win = false;	
	private boolean paused = false;
	
	public LevelScreen(GameLevel level, int width, int height) {		
		skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
	    
		hud = new LevelHUD(skin, width, height) { 
			public void paused() {
				paused = true;
				
				new Dialog("", skin) {
					protected void result (Object object) {
						paused = false;
						if (object.equals(false))
							result = ScreenAction.SELECT;
					}
				}.text("Would you like to continue").button("continue", true).button("Exit", false).show(hud);
			}
		};
		
        Gdx.input.setInputProcessor(hud);
        
        level.getTouchTemplate().setUpperTouchLimit(LevelHUD.PAUSE_BUTTON_HEIGHT);
        level.getTouchTemplate().setLowerTouchLimit(Gdx.graphics.getHeight());
        
		renderer = new LevelRenderer(level) {
			public void updateScore(int newScore, int startx, int starty) {
				hud.updateScore(newScore, startx, starty);
			}
		};		
		renderer.resize(width, height);
		renderer.init();
		
		GamePreferences preferences = new GamePreferences();
		
		music = Gdx.audio.newMusic(Gdx.files.getFileHandle("sounds/music.mp3", FileType.Internal));
		music.setLooping(true);
		music.setVolume(preferences.getMusicVolume() == 0 ? 0 : (float) preferences.getMusicVolume() / 7);
		music.play();
	}

	public void pause() {
		paused = true;
	}
	
	public void resume() {
		paused = false;
		renderer.reload();
	}
	
	@Override
	public void update() {
		if (!win) {
			if (!paused) {
				if (Alanparsons2.DEBUG) hud.updateFPS();
				renderer.update();
				
				if (renderer.win()) {
					win = true;
					
					new Dialog("YOU WIN", skin) {
						protected void result (Object object) {
							paused = false;
							result = (String)object;
						}
					}.text("What do you want to do").button("Select level", ScreenAction.SELECT).button("Next level", ScreenAction.NEXT).show(hud);
				}
			}
		}
		
		hud.act();
	}

	@Override
	public void render(SpriteBatch batch) {
		renderer.render();
		hud.draw();
	}

	@Override
	public String result() {
		return result;
	}

	@Override
	public void dispose() {
		music.stop();
		music.dispose();
		hud.dispose();
		renderer.dispose();
	}

	@Override
	public void resize(int width, int height) {
        hud.setViewport(width, height, true);
		renderer.resize(width, height);
	}

	@Override
	public void backKeyStroke() {
		result = ScreenAction.BACK;
	}
}
