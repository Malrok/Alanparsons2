package com.MRK.alanparsons2;

import android.os.Bundle;
import android.os.Debug;

import com.MRK.alanparsons2.interfaces.AndroidCallback;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class MainActivity extends AndroidApplication implements AndroidCallback {
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
        cfg.useGL20 = false;
        
        Alanparsons2 game = new Alanparsons2();
        
        game.setCallback(this);
        
        initialize(game, cfg);
    }

	@Override
	public void beginRenderLevelCallback() {
		if (Alanparsons2.DEBUG)
			Debug.startMethodTracing("alanp");
	}

	@Override
	public void endRenderLevelCallback() {
		if (Alanparsons2.DEBUG)
			Debug.stopMethodTracing();
	}
}