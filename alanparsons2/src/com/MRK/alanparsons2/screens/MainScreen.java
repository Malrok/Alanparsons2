package com.MRK.alanparsons2.screens;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;
import aurelienribon.tweenengine.equations.Linear;

import com.MRK.alanparsons2.helpers.SpriteAccessor;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;


public class MainScreen implements Screen {

	private final float PULSE = 1.0f;
	
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Sprite start;

	private TweenManager tweenManager = new TweenManager();
	
	private String result = "";
	
	public MainScreen(int width, int height) {
		camera = new OrthographicCamera();
		batch = new SpriteBatch();
		start = new Sprite(new Texture(Gdx.files.internal("buttons/start.png")));
		start.setOrigin(start.getWidth()/2, start.getHeight()/2);
		
		resize(width, height);
		
		Tween.registerAccessor(Sprite.class, new SpriteAccessor());

		Tween.to(start, SpriteAccessor.ZOOM, PULSE).ease(Linear.INOUT).target(1.1f, 1.1f).repeatYoyo(-1, 0).start(tweenManager);
	}
	
	@Override
	public void update() {
		if(Gdx.input.isTouched()) {
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);
			
			if (start.getBoundingRectangle().contains(new Vector2(touchPos.x, touchPos.y))) {
				tweenManager.killAll();
//				Tween.to(start, SpriteAccessor.ZOOM, PULSE).ease(Linear.INOUT).target(1.4f, 1.4f).call(tweenCallback).start(tweenManager);
				Tween.call(tweenCallback).start(tweenManager);
//				result = "play";
			}
		}
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		start.draw(batch);
		batch.end();
		
		tweenManager.update(Gdx.graphics.getDeltaTime());
	}

	@Override
	public String result() {
		return result;
	}

	@Override
	public void dispose() {
		batch.dispose();
	}

	@Override
	public void resize(int width, int height) {
		camera.setToOrtho(false, width, height);
		start.setPosition(width / 2 - start.getWidth() / 2, height / 2 - start.getHeight() / 2);
	}
	
	private final TweenCallback tweenCallback = new TweenCallback() {

		@Override
		public void onEvent(int type, BaseTween<?> source) {
			if (type == TweenCallback.BEGIN) {
				Tween.to(start, SpriteAccessor.ZOOM, PULSE).ease(Linear.INOUT).target(1.4f, 1.4f).start(tweenManager);
			}
			if (type == TweenCallback.END) {
				result = "play";
			}
		}
		
	};
}
