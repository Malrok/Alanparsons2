package com.MRK.alanparsons2.controllers;

import com.MRK.alanparsons2.constants.ScreenAction;
import com.MRK.alanparsons2.factories.LevelBuilder;
import com.MRK.alanparsons2.interfaces.AndroidCallback;
import com.MRK.alanparsons2.screens.LevelScreen;
import com.MRK.alanparsons2.screens.LevelSelect;
import com.MRK.alanparsons2.screens.LoadingScreen;
import com.MRK.alanparsons2.screens.MainScreen;
import com.MRK.alanparsons2.screens.OptionsScreen;
import com.MRK.alanparsons2.templates.MenuScreen;
import com.MRK.alanparsons2.templates.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Scaling;

public class ScreensController implements Disposable {

	public static final int VIRTUAL_WIDTH = 800;
    public static final int VIRTUAL_HEIGHT = 480;
    public static final float ASPECT_RATIO = (float)VIRTUAL_WIDTH/(float)VIRTUAL_HEIGHT;
	
	private Screen screen;
	private String currentLevel;
	private AndroidCallback callback;
	
	private LevelController levelController;
	private LevelBuilder levelBuilder;
	
	private OrthographicCamera camera;
    private Rectangle viewport = new Rectangle();
    private SpriteBatch batch;
    private Stage stage;
    private Skin skin;
	
	public void initControllers(LevelController levelController, LevelBuilder levelBuilder) {
		this.levelController = levelController;
		this.levelBuilder = levelBuilder;
		
        camera = new OrthographicCamera(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
        batch = new SpriteBatch();
        
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
	}
	
	@Override
	public void dispose() {
		if (screen != null) screen.dispose();
		if (stage != null) stage.dispose();
	}

	public void pause() {
		if (screen instanceof LevelScreen)
			((LevelScreen) screen).pause();
	}

	public void resume() {
		if (screen instanceof LevelScreen)
			((LevelScreen) screen).resume();
	}

	public void update() {
		if (screen != null) screen.update();
		if (stage != null) stage.act();
		
	}

	public void render() {
		init();
		update();
		
		if (!(screen instanceof LevelScreen)) {
	        camera.update();
	        
	        Gdx.gl.glViewport((int) viewport.x, (int) viewport.y, (int) viewport.width, (int) viewport.height);
	        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
	        
	        batch.begin();
		}
		
		if (screen != null) screen.render(batch);
		
		if (!(screen instanceof LevelScreen)) {
			batch.end();
			
			if (stage != null) stage.draw();
		}
		
		compute();
	}

	public void resize(int width, int height) {
		Vector2 resize = new Vector2(0f, 0f);
        resize.set(Scaling.fit.apply(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, width, height));
        
        viewport.width = resize.x;
        viewport.height = resize.y;
        viewport.y = (height - viewport.height) / 2;
        viewport.x = (width - viewport.width) / 2;
	}

	public void backKeyStroke() {
		screen.backKeyStroke();
	}

	public void compute() {
		if (screen.result().length() != 0) computeScreen();
	}
	
	public void init() {
		if (screen == null) {
			screen = new MainScreen(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
			computeStage();
		}
	}
	
	public void setCallback(AndroidCallback callback) {
		this.callback = callback;
	}
	
	public void beginRenderLevelCallback() {
		if (screen instanceof LevelScreen && callback != null) callback.beginRenderLevelCallback();
	}
	
	public void endRenderLevelCallback() {
		if (screen instanceof LevelScreen && callback != null) callback.endRenderLevelCallback();
	}
	
	private void computeStage() {
		stage = new Stage(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, false);
		if (screen instanceof MenuScreen) ((MenuScreen)screen).generateStage(stage, skin);
	}
	
	/**
	 * Défini la réaction des écrans aux différents retour
	 */
	private void computeScreen() {
		if (stage != null) stage.dispose();
		screen.dispose();

		if (screen instanceof MainScreen) {
			if (screen.result().equalsIgnoreCase(ScreenAction.NEXT))
				screen = new LevelSelect(levelController.getLevels(), VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
			else if (screen.result().equalsIgnoreCase(ScreenAction.OPTIONS))
				screen = new OptionsScreen(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
			else if (screen.result().equalsIgnoreCase(ScreenAction.BACK))
				Gdx.app.exit();
		}
		if (screen instanceof OptionsScreen)
			if (screen.result().startsWith(ScreenAction.BACK)) {
				screen = new MainScreen(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
			}
		if (screen instanceof LevelSelect)
			if (screen.result().startsWith(ScreenAction.PLAY)) {
				currentLevel = screen.result().split(" ")[1];
				screen = new LoadingScreen(currentLevel, levelBuilder);
			} else if (screen.result().startsWith(ScreenAction.BACK)) {
				screen = new MainScreen(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
			}
		if (screen instanceof LoadingScreen)
			if (screen.result().startsWith(ScreenAction.LOADED))
				screen = new LevelScreen(levelBuilder.getLevel(), VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
		if (screen instanceof LevelScreen) {
			if (screen.result().equalsIgnoreCase(ScreenAction.SELECT))
				screen = new LevelSelect(levelController.getLevels(), VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
			else if (screen.result().equalsIgnoreCase(ScreenAction.NEXT)) {
				currentLevel = levelController.getNextLevel(currentLevel);
				if (currentLevel != null) {
					screen = new LoadingScreen(currentLevel, levelBuilder);
				} else {
					screen = new LevelSelect(levelController.getLevels(), VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
				}
			}
		}
		
		screen.resize(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
		computeStage();
	}

}
