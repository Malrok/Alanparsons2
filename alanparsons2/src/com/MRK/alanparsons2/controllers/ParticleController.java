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

	public static int COLLISION = 1;
	public static int EXPLOSION = 2;
	
	private ParticleEffectPool collideEffectPool, explosionEffectPool;
	private List<PooledEffect> effects = new ArrayList<PooledEffect>();
	private List<PooledEffect> toBeRemoved = new ArrayList<PooledEffect>();
	
	public ParticleController() {
		ParticleEffect collisionEffect = new ParticleEffect();
		collisionEffect.load(Gdx.files.internal("particles/collision_effect.p"), Gdx.files.internal("particles"));
		collideEffectPool = new ParticleEffectPool(collisionEffect, 1, 2);
		ParticleEffect explosionEffect = new ParticleEffect();
		explosionEffect.load(Gdx.files.internal("particles/explosion_effect.p"), Gdx.files.internal("particles"));
		explosionEffectPool = new ParticleEffectPool(explosionEffect, 1, 2);
	}
	
	@Override
	public void dispose() {
		
	}

	public void update(int type, List<Vector2> positions) {
		synchronized(effects) {
			for (Vector2 position : positions) {
				PooledEffect effect = (type == COLLISION ? collideEffectPool.obtain() : explosionEffectPool.obtain());
				effect.setPosition(position.x, position.y);
				effects.add(effect);
			}
		}
	}
	
	public void draw(SpriteBatch batch) {
		toBeRemoved.clear();
		
		for (PooledEffect effect : effects) {
	        effect.draw(batch, Gdx.graphics.getDeltaTime());
	        
	        if (effect.isComplete()) {
                effect.free();
                toBeRemoved.add(effect);
	        }
		}

		synchronized(effects) {
			effects.removeAll(toBeRemoved);
		}
	}
}
