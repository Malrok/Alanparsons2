package com.MRK.alanparsons2.templates;

import com.MRK.alanparsons2.controllers.ScreensController;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class MenuScreen implements Screen {

//	private static final int VIRTUAL_WIDTH = 480;
//    private static final int VIRTUAL_HEIGHT = 320;
//    private static final float ASPECT_RATIO = (float)VIRTUAL_WIDTH/(float)VIRTUAL_HEIGHT;

//    private OrthographicCamera camera;
//    private Rectangle viewport;
//    private SpriteBatch batch;
    
    protected int width, height;
    protected String result = "";

    private TextureRegion background;
    
    public MenuScreen(int width, int height) {
//        camera = new OrthographicCamera(ScreensController.VIRTUAL_WIDTH, ScreensController.VIRTUAL_HEIGHT);
//        batch = new SpriteBatch();
    	this.width = width;
    	this.height = height;
    	
		Texture texture = new Texture(Gdx.files.internal("images/menu.png"));
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		background = new TextureRegion(texture, 0, 0, texture.getWidth(), texture.getHeight() * height / width);
		
//		resize(width, height);
    }
    
	@Override
	public void update() {
		
	}

	@Override
	public void render(SpriteBatch batch) {
//		// update camera
//        camera.update();
//        camera.apply(Gdx.gl10);
//
//        // set viewport
//        Gdx.gl.glViewport((int) viewport.x, (int) viewport.y,
//                          (int) viewport.width, (int) viewport.height);
//
//        // clear previous frame
//        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
//        
//        batch.begin();
		
		batch.draw(background, 0, 0, ScreensController.VIRTUAL_WIDTH, ScreensController.VIRTUAL_HEIGHT);
		
//		batch.end();
	}

	@Override
	public String result() {
		return null;
	}

	@Override
	public void dispose() {
		
	}

	@Override
	public void resize(int width, int height) {
//		// calculate new viewport
//		float aspectRatio = (float) width / (float) height;
//		float scale = 1f;
//		Vector2 crop = new Vector2(0f, 0f);
//
//		if (aspectRatio > ScreensController.ASPECT_RATIO) {
//			scale = (float) height / (float) ScreensController.VIRTUAL_HEIGHT;
//			crop.x = (width - ScreensController.VIRTUAL_WIDTH * scale) / 2f;
//		} else if (aspectRatio < ScreensController.ASPECT_RATIO) {
//			scale = (float) width / (float) ScreensController.VIRTUAL_WIDTH;
//			crop.y = (height - ScreensController.VIRTUAL_HEIGHT * scale) / 2f;
//		} else {
//			scale = (float) width / (float) ScreensController.VIRTUAL_WIDTH;
//		}
//
//		float w = (float) ScreensController.VIRTUAL_WIDTH * scale;
//		float h = (float) ScreensController.VIRTUAL_HEIGHT * scale;
//		viewport = new Rectangle(crop.x, crop.y, w, h);
	}

	@Override
	public void backKeyStroke() {
		
	}
	
	public void generateStage(Stage stage, Skin skin) {};
}
