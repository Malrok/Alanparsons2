package com.MRK.alanparsons2.renderers;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.MRK.alanparsons2.controllers.ScreensController;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class LevelHUD extends Stage {

	public static int PAUSE_BUTTON_HEIGHT;
	private static final int PADDING = 10;
	
	private Skin skin;
	private Label timeLabel;
	private Label scoreLabel;
	private Label fps;
	private Button pause;
	private List<Actor> scores = new ArrayList<Actor>();
	private Texture command;
	
	private float time = 0;
	private int score = 0;
	
	private DecimalFormat formatter;
	
	public LevelHUD(Skin skin, int width, int height) {
		super(width, height, false);
		
		this.skin = skin;
		
		PAUSE_BUTTON_HEIGHT = width / 10;
        
        pause = new Button(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("images/pause.png")))));
        pause.setSize(PAUSE_BUTTON_HEIGHT, PAUSE_BUTTON_HEIGHT);
        pause.setPosition(PADDING, height - PADDING - pause.getHeight());
        pause.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				paused();
			}
		});
        addActor(pause);
        
        formatter = new DecimalFormat("#0");
        formatter.setMinimumIntegerDigits(10);
        
        score = new Integer(formatter.format(score));
        
        scoreLabel = new Label("0000000000", skin);
        scoreLabel.setPosition(width - scoreLabel.getWidth() - PADDING, height - scoreLabel.getHeight() * 2 - PADDING);
        addActor(scoreLabel);
        
        Label score = new Label("Score", new Label.LabelStyle(skin.getFont("outline-font"), Color.WHITE));
        score.setPosition(width - scoreLabel.getWidth() - score.getWidth() - PADDING * 2, height - scoreLabel.getHeight() * 2 - PADDING);
        addActor(score);

        timeLabel = new Label("         0", skin);
        timeLabel.setPosition(width - scoreLabel.getWidth() - PADDING, height - scoreLabel.getHeight() - PADDING);
        addActor(timeLabel);
        
        Label time = new Label("Time", new Label.LabelStyle(skin.getFont("outline-font"), Color.WHITE));
        time.setPosition(width - scoreLabel.getWidth() - score.getWidth() - PADDING * 2, height - score.getHeight() - PADDING);
        addActor(time);
        
        fps = new Label("", skin);
        fps.setPosition(width - scoreLabel.getWidth() - score.getWidth() - PADDING * 2, height - score.getHeight() * 3 - PADDING);
        addActor(fps);
        
        command = new Texture(Gdx.files.internal("images/control.png"));
	}
	
	@Override
	public void draw() {
		super.draw();
		
		getSpriteBatch().begin();
		getSpriteBatch().draw(command, PADDING, PADDING, pause.getWidth(), getHeight() - pause.getHeight() - PADDING * 2, 0, 0, command.getWidth(), command.getHeight(), false, false);
		getSpriteBatch().draw(command, getWidth() - pause.getWidth() - PADDING, PADDING, pause.getWidth(), getHeight() - pause.getHeight() - PADDING * 2, 0, 0, command.getWidth(), command.getHeight(), true, false);
		getSpriteBatch().end();
	}
	
	@Override
	public void act() {
		super.act();
		
		time += Gdx.graphics.getDeltaTime();
		timeLabel.setText(String.valueOf(formatter.format(time)));
		
		List<Actor> toBeRemoved = new ArrayList<Actor>();
		
		for (Actor actor : scores) {
			if (actor.getActions().size == 0) {
				toBeRemoved.add(actor);
				this.getRoot().removeActor(actor);
			}
		}
		
		scores.removeAll(toBeRemoved);
	}
	
	public void updateScore(int newScore, int startx, int starty) {
		Label scoreAdder = new Label(String.valueOf(newScore), skin);
		scoreAdder.setPosition(screenXToStage(startx), screenYToStage(starty));
		
		scoreAdder.addAction(new SequenceAction(Actions.parallel(Actions.moveTo(scoreLabel.getX(), scoreLabel.getY(), 1), Actions.scaleBy(0.1f, 0.1f, 1))));//, completeAction));
		
		addActor(scoreAdder);
		scores.add(scoreAdder);
		
		score += newScore;
		scoreLabel.setText(String.valueOf(formatter.format(score)));
	}
	
	public void updateFPS() {
		fps.setText("FPS = " + Gdx.graphics.getFramesPerSecond());
	}
	
	protected void paused() {}
	
	private int screenXToStage(int x) {
		return x * ScreensController.VIRTUAL_WIDTH / Gdx.graphics.getWidth();
	}
	
	private int screenYToStage(int y) {
		return y * ScreensController.VIRTUAL_HEIGHT / Gdx.graphics.getHeight();
	}
}
