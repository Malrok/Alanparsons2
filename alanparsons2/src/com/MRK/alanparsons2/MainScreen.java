package com.MRK.alanparsons2;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class MainScreen implements Screen {
	
	private Stage stage;
	
	public MainScreen() {
		stage = new Stage(0, 0, true);
		
		TextButton button = new TextButton("Start", new Skin());
		button.addListener(new InputListener() {

//			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
//		 		Gdx.app.log("Example", "touch started at (" + x + ", " + y + ")");
//		 		return false;
//		 	}
		 
		 	public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
//		 		Gdx.app.log("Example", "touch done at (" + x + ", " + y + ")");
		 		
		 	}
			
		});
		stage.addActor(button);
	}
	
	@Override
	public void update() {
		
	}

	@Override
	public void render() {
		
	}

	@Override
	public boolean isDone() {
		return false;
	}

	@Override
	public void dispose() {
		
	}

}
