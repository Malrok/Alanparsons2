package com.MRK.alanparsons2.templates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class MenuScreen implements Screen {
    
    protected int width, height;
    protected String result = "";

    private TextureRegion background;
    
    public MenuScreen(int width, int height) {
    	this.width = width;
    	this.height = height;
    	
		Texture texture = new Texture(Gdx.files.internal("images/menu.png"));
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		background = new TextureRegion(texture, 0, 0, texture.getWidth(), texture.getHeight() * height / width);
    }
    
	@Override
	public void update() {
		
	}

	@Override
	public void render(SpriteBatch batch) {		
//		batch.draw(background, 0, 0, ScreensController.VIRTUAL_WIDTH, ScreensController.VIRTUAL_HEIGHT);
		batch.draw(background, 0, 0, width, height);
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
		this.width = width;
    	this.height = height;
	}

	@Override
	public void backKeyStroke() {
		
	}
	
	public void generateStage(Stage stage, Skin skin) {};
}
