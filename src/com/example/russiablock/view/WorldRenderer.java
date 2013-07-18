package com.example.russiablock.view;

import com.example.russiablock.Assets;
import com.example.russiablock.framework.Graphics;
import com.example.russiablock.framework.gl.Animation;
import com.example.russiablock.framework.gl.Camera2D;
import com.example.russiablock.framework.gl.SpriteBatcher;
import com.example.russiablock.framework.gl.TextureRegion;
import com.example.russiablock.framework.math.Vector2;
import com.example.russiablock.model.WallItem;
import com.example.russiablock.model.World;
import com.example.russiablock.model.World.State;

public class WorldRenderer {

	private World world;
	private Camera2D cam;
	private SpriteBatcher batcher;
	private Vector2 gameCenter;
	private Vector2 consoleCenter;
	private Graphics graphics;

	public WorldRenderer(Graphics graphics, SpriteBatcher batcher, World world) {
		this.graphics = graphics;
		this.world = world;
		this.cam = new Camera2D(graphics, world.GAME_WIDTH
				+ world.CONSOLE_WIDTH, world.FRUSTUM_HEIGHT);
		cam.position.sub(0.5f, 0.5f);
		this.batcher = batcher;
		gameCenter = new Vector2(world.GAME_WIDTH / 2, world.FRUSTUM_HEIGHT / 2);
		gameCenter.sub(0.5f, 0.5f);
		consoleCenter = new Vector2(world.CONSOLE_WIDTH / 2 + world.GAME_WIDTH,
				world.FRUSTUM_HEIGHT / 2);
		consoleCenter.sub(0.5f, 0.5f);

	}

	public void render(World world, float deltaTime) {

		cam.setViewportAndMatrices();

		renderBackground(world, deltaTime);
		renderGamePart(world, deltaTime);
		renderConsolePart(world, deltaTime);
	}

	public void renderBackground(World world, float deltaTime) {

	}

	public void renderGamePart(World world, float deltaTime) {

		batcher.beginBatch(Assets.texture_1);

		batcher.drawSprite(gameCenter.x, gameCenter.y, world.GAME_WIDTH,
				world.FRUSTUM_HEIGHT, Assets.courtbg);

		batcher.drawSprite(consoleCenter.x, consoleCenter.y,
				world.CONSOLE_WIDTH, world.FRUSTUM_HEIGHT, Assets.menubg);
		// render block

		for (int i = 0, len = world.block.area.length; i < len; i++) {
			batcher.drawSprite(world.block.area[i].x, world.block.area[i].y, 1,
					1, Assets.blocks[world.block.color]);

		}

		for (int i = 0, len = world.nextBlock.area.length; i < len; i++) {
			batcher.drawSprite(world.nextBlock.area[i].x,
					world.nextBlock.area[i].y, 1, 1, //60,
					Assets.blocks[world.nextBlock.color]);

			// batcher.drawSprite(0 ,0 , 1,1, Assets.blocks[world.block.color]);
		}
		for (WallItem item : world.walls) {
			batcher.drawSprite(item.position.x, item.position.y, 1, 1,
					Assets.blocks[item.color]);
		}

		TextureRegion frame;
		if (world.currentState == State.collapsing) {
			frame = Assets.collpasedAnimation.getKeyFrame(world.timeConsumed,
					Animation.ANIMATION_NONLOOPING);

			int x = (int) (world.timeConsumed / World.COLLAPSEDTICKTIME);
			for (int y : world.collapsedLine) {
				batcher.drawSprite(x / 2, y, x + 1, 1, frame);

			}

		}
		if (world.currentState == State.gameOver) {
			// draw GameOver
			batcher.drawSprite(gameCenter.x, gameCenter.y, 12, 3,
					Assets.gameOver);

		}
		batcher.endBatch();

	}

	/** 
	 * 
	 */
	public void renderConsolePart(World world, float deltaTime) {

	}

}
