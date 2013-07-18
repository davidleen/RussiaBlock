package com.example.russiablock;

import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import android.util.Log;

import com.example.russiablock.framework.Game;
import com.example.russiablock.framework.Graphics;
import com.example.russiablock.framework.Input.TouchEvent;
import com.example.russiablock.framework.gl.Camera2D;
import com.example.russiablock.framework.gl.FPSCounter;
import com.example.russiablock.framework.gl.SpriteBatcher;
import com.example.russiablock.framework.math.OverlapTester;
import com.example.russiablock.framework.math.Rectangle;
import com.example.russiablock.framework.math.Vector2;
import com.example.russiablock.model.World;
import com.example.russiablock.model.World.WorldListener;
import com.example.russiablock.view.WorldRenderer;

public class GameScreen extends AbstractScreen {
	// static final int GAME_READY = 0;
	// static final int GAME_RUNNING = 1;
	// static final int GAME_PAUSED = 2;
	// static final int GAME_LEVEL_END = 3;
	// static final int GAME_OVER = 4;

	int state;
	Camera2D guiCam;
	Vector2 touchPoint;
	SpriteBatcher batcher;
	World world;
	WorldListener worldListener;
	WorldRenderer renderer;
	Rectangle leftBounds;
	Rectangle rightBounds;
	Rectangle speedUpBounds;
	Rectangle rotateBounds;
	private Rectangle lastRectangle;
	private Rectangle thisRectangle;
	// int lastScore;
	// String scoreString;
	FPSCounter fpsCounter;
	private Graphics graphics;
	private String TAG = "TAG";

	public GameScreen(Game game) {
		super(game);

		// state = GAME_READY;

		graphics = game.getGraphics();
		Log.d(TAG,
				"width:" + graphics.getWidth() + ",height:"
						+ graphics.getHeight());
		guiCam = new Camera2D(graphics, 320, 480);
		touchPoint = new Vector2();
		batcher = new SpriteBatcher(graphics, 1000);
		worldListener = new WorldListener() {

			@Override
			public void collide() {
				// TODO Auto-generated method stub
				Log.e(TAG, "colide");
				Assets.playSound(Assets.collideSound);
			}

			@Override
			public void collapse() {
				// TODO Auto-generated method stub
				Log.e(TAG, "collapsed");
				Assets.playSound(Assets.collapsedSound);
			}

		};
		world = new World(worldListener);
		renderer = new WorldRenderer(graphics, batcher, world);
		leftBounds = new Rectangle(320 - 64, 32, 32, 32);
		rightBounds = new Rectangle(320 - 64, 32 * 3, 32, 32);
		speedUpBounds = new Rectangle(320 - 64, 32 * 5, 32, 32);
		rotateBounds = new Rectangle(320 - 64, 32 * 7, 32, 32);
		// quitBounds = new Rectangle(160 - 96, 240 - 36, 192, 36);
		// lastScore = 0;
		// scoreString = "score: 0";
		fpsCounter = new FPSCounter();
	}

	@Override
	public void update(float deltaTime) {

		List<TouchEvent> events = game.getInput().getTouchEvents();
		int len = events.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = events.get(i);
			//Log.e(TAG, event.toString());
			guiCam.touchToWorld(touchPoint.set(event.x, event.y));
			thisRectangle = null;
			if (OverlapTester.pointInRectangle(leftBounds, touchPoint)) {
				thisRectangle = leftBounds;
			}
			if (OverlapTester.pointInRectangle(rightBounds, touchPoint)) {

				thisRectangle = rightBounds;
			}

			if (OverlapTester.pointInRectangle(speedUpBounds, touchPoint)) {

				thisRectangle = speedUpBounds;
			}

			if (OverlapTester.pointInRectangle(rotateBounds, touchPoint)) {

				thisRectangle = rotateBounds;
			}

			switch (event.type) {
			case TouchEvent.TOUCH_DOWN: {

				if (thisRectangle == leftBounds) {
					world.left();

				}
				if (thisRectangle == rightBounds) {
					world.right();

				}

				if (thisRectangle == speedUpBounds) {
					world.speedDown();

				}

				if (thisRectangle == rotateBounds) {
					world.rotate();

				}

				break;
			}

			case TouchEvent.TOUCH_UP: {

				if (thisRectangle == speedUpBounds
						&& lastRectangle == thisRectangle) {
					world.speedNormal();
				}
				thisRectangle = null;

				break;
			}
			}

			lastRectangle = thisRectangle;

		}
		world.update(deltaTime);

	}

	@Override
	public void present(float deltaTime) {

		GL10 gl = graphics.getGL();
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		gl.glEnable(GL10.GL_TEXTURE_2D);
		gl.glEnable(GL10.GL_BLEND);
		
		 gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		renderer.render(world, deltaTime);

		guiCam.setViewportAndMatrices();
		batcher.beginBatch(Assets.items);

		batcher.drawSprite(leftBounds.lowerLeft.x + leftBounds.width / 2,
				leftBounds.lowerLeft.y + leftBounds.height / 2,
				leftBounds.width, leftBounds.height, Assets.leftRegion);
		batcher.drawSprite(rightBounds.lowerLeft.x + rightBounds.width / 2,
				rightBounds.lowerLeft.y + rightBounds.height / 2,
				rightBounds.width, rightBounds.height, Assets.rightRegion);
		batcher.drawSprite(speedUpBounds.lowerLeft.x + speedUpBounds.width / 2,
				speedUpBounds.lowerLeft.y + speedUpBounds.height / 2,
				speedUpBounds.width, -speedUpBounds.height,
				Assets.speedUpRegion);

		batcher.drawSprite(rotateBounds.lowerLeft.x + rotateBounds.width / 2,
				rotateBounds.lowerLeft.y + rotateBounds.height / 2,
				rotateBounds.width, rotateBounds.height, Assets.rotateRegion);

		batcher.endBatch();
	
		gl.glDisable(GL10.GL_TEXTURE_2D);
		gl.glDisable(GL10.GL_BLEND);
	}

}
