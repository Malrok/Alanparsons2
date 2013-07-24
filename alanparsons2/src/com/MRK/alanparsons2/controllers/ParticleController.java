package com.MRK.alanparsons2.controllers;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool.PooledEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

public class ParticleController implements Disposable {

	private ParticleEffectPool effectPool;
	private List<PooledEffect> effects = new ArrayList<PooledEffect>();
	private List<PooledEffect> toBeRemoved = new ArrayList<PooledEffect>();
	
//	public void addEffect(float x, float y) {
//		PooledEffect effect = effectPool.obtain();
//		effect.setPosition(x, y);
//		effects.add(effect);
//	}
	
	public ParticleController() {
		ParticleEffect effect = new ParticleEffect();
		effect.load(Gdx.files.internal("particles/collision_effect.p"), Gdx.files.internal("particles"));
		effectPool = new ParticleEffectPool(effect, 1, 2);
	}
	
	@Override
	public void dispose() {
		
	}

	public void update(List<Vector2> positions) {
		synchronized(effects) {
			for (Vector2 position : positions) {
				PooledEffect effect = effectPool.obtain();
				effect.setPosition(position.x, position.y);
				effects.add(effect);
			}
		}
	}
	
	public void draw(SpriteBatch batch) {
		toBeRemoved.clear();
		
//		synchronized(effects) {
		for (PooledEffect effect : effects) {
	        effect.draw(batch, Gdx.graphics.getDeltaTime());
	        
	        if (effect.isComplete()) {
                effect.free();
//	                effects.remove(effect);
                toBeRemoved.add(effect);
	        }
		}
//		}
		synchronized(effects) {
			effects.removeAll(toBeRemoved);
		}
	}
}
