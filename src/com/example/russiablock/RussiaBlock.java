package com.example.russiablock;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.example.russiablock.framework.Screen;
import com.example.russiablock.framework.impl.GLGame;

 

public class RussiaBlock extends GLGame {

	private boolean firstTimeCreate = true;

	@Override
	public Screen getStartScreen() {
		// TODO Auto-generated method stub
		return new GameScreen(this);
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		super.onSurfaceCreated(gl, config);
		if (firstTimeCreate) {
			// Settings.load(getFileIO());
			Assets.load(this);
			firstTimeCreate = false;
		} else {
			Assets.reload();
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		// if(Settings.soundEnabled)
		// Assets.music.pause();
	}
}
