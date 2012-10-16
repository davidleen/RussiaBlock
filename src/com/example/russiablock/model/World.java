package com.example.russiablock.model;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import com.example.russiablock.framework.Pool;
import com.example.russiablock.framework.Pool.PoolObjectFactory;

public class World {

	public int GAME_WIDTH = 18;
	public int FRUSTUM_HEIGHT = 40;
	public int CONSOLE_WIDTH = 6;
	private final String TAG = World.class.getSimpleName();

	enum State {
		running, collapsing
	}

	List<CollapseLine> collapsingLines = new ArrayList<CollapseLine>(4);

	Pool<CollapseLine> pool;

	private WorldListener listener;

	public World(WorldListener listener) {
		this.listener = listener;
		block = new Block();
		block.reset(GAME_WIDTH, FRUSTUM_HEIGHT);
		pool = new Pool<CollapseLine>(new PoolObjectFactory<CollapseLine>() {

			@Override
			public CollapseLine createObject() {
				// TODO Auto-generated method stub
				return new CollapseLine();
			}

		}, 4);

		walls = new int[GAME_WIDTH][FRUSTUM_HEIGHT];
		for (int i = 0; i < GAME_WIDTH; i++)

			for (int j = 0; j < FRUSTUM_HEIGHT; j++) {
				walls[i][j] = -1;
			}
	}

	public interface WorldListener {
		public void collide();

		public void collapse();
	}

	private boolean isGameOver = false;
	private float timeConsumed = 0;
	private float tickTime = 0.5f;

	public Block block;
	public Block nextBlock;

	enum BlockColor {
		green, white, red

	}

	public int[][] walls = new int[][] {};

	public World() {

		this(null);
	}

	public void newBlock() {
		block.reset(GAME_WIDTH, FRUSTUM_HEIGHT);
	}

	public void update(float deltaTime) {
		if (isGameOver)
			return;
		timeConsumed += deltaTime;
		// Log.i(TAG, "timeConsumed:"+timeConsumed) ;
		if (timeConsumed > tickTime) {
			timeConsumed -= tickTime;
			// check collide first

			if (checkCollision()) {
				// copy block to walls
				for (int i = 0; i < block.area.length; i++) {
					if (block.area[i].y >= FRUSTUM_HEIGHT)
						continue;

					walls[block.area[i].x][block.area[i].y] = block.color;
				}
				block.reset(GAME_WIDTH, FRUSTUM_HEIGHT);
			} else

				block.advance(walls, GAME_WIDTH, FRUSTUM_HEIGHT);

			// checkCollapse
			if (checkCollapse()) {

			}

		}

		// check pos

	}

	private boolean checkCollision() {

		boolean collide = false;
		for (int i = 0; i < block.area.length; i++) {
			if (block.area[i].y >= FRUSTUM_HEIGHT)
				continue;
			if (block.area[i].y == 0
					|| walls[block.area[i].x][block.area[i].y - 1] > -1) {
				collide = true;
				break;
			}
		}

		return collide;

	}

	private boolean checkCollapse() {

		return false;

	}

	
	//move block to right  by one step;
	public void right() {
		// TODO Auto-generated method stub
		 boolean collide=false;
		for (int i = 0; i < block.area.length; i++) {
			if (block.area[i].x+1 >= GAME_WIDTH||walls[block.area[i].x+1][block.area[i].y ] > -1 )
			{	collide=true;
				break;
				}
			 
		}
		if(!collide)
		{
			block.right();
		}
		
		
		
	}
	
	//move block to left  by one step;
		public void left() {
			// TODO Auto-generated method stub
			 boolean collide=false;
			for (int i = 0; i < block.area.length; i++) {
				
				if (block.area[i].x-1 <0 ||walls[block.area[i].x-1][block.area[i].y ] > -1 )
				{	collide=true;
					break;
					}
				 
			}
			if(!collide)
			{
				block.left();
			}
			
			
			
		}
}
