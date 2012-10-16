package com.example.russiablock.view;

import javax.microedition.khronos.opengles.GL10;

import com.example.russiablock.Assets;
import com.example.russiablock.framework.Graphics;
import com.example.russiablock.framework.gl.Camera2D;
import com.example.russiablock.framework.gl.SpriteBatcher;
import com.example.russiablock.framework.math.Vector2;
import com.example.russiablock.model.World;

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
		this.batcher = batcher;
		gameCenter = new Vector2(world.GAME_WIDTH / 2, world.FRUSTUM_HEIGHT / 2);
		consoleCenter = new Vector2(world.CONSOLE_WIDTH / 2 + world.GAME_WIDTH,
				world.FRUSTUM_HEIGHT / 2);
		
	}

	public void render() {

		cam.setViewportAndMatrices(); 
		renderBackground();
		renderGamePart();
		renderConsolePart();
	}

	public void renderBackground() {

	}

	public void renderGamePart() {

		batcher.beginBatch(Assets.texture_1);
		
		
		batcher.drawSprite(gameCenter.x, gameCenter.y, world.GAME_WIDTH,
				world.FRUSTUM_HEIGHT, Assets.courtbg);
		
		batcher.drawSprite(consoleCenter.x, consoleCenter.y, world.CONSOLE_WIDTH,
				world.FRUSTUM_HEIGHT, Assets.menubg);
		// render block

		for (int i = 0, len = world.block.area.length; i < len; i++) {
			batcher.drawSprite(world.block.area[i].x+0.5f, world.block.area[i].y+0.5f, 1,
					1, Assets.blocks[world.block.color]);

			// batcher.drawSprite(0 ,0 , 1,1, Assets.blocks[world.block.color]);
		}

		for (int i = 0; i < world.GAME_WIDTH; i++) {
			for (int j = 0; j < world.FRUSTUM_HEIGHT; j++)
				if (world.walls[i][j] > -1)
					batcher.drawSprite(i+0.5f, j+0.5f, 1, 1,
							Assets.blocks[world.walls[i][j]]);

		}

		batcher.endBatch();

	}

	/** 
	 * 
	 */
	public void renderConsolePart() {
		
	 
		
		 
	
		

	}

}
