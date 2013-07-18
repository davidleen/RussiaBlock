package com.example.russiablock.model;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import com.example.russiablock.framework.Pool;
import com.example.russiablock.framework.Pool.PoolObjectFactory;
import com.example.russiablock.framework.math.Position;

public class World {

	public static int GAME_WIDTH = 18;
	public static float COLLAPSEDTICKTIME = 0.05f;
	private static final float COLLAPSEDTIME = COLLAPSEDTICKTIME * GAME_WIDTH;

	public int FRUSTUM_HEIGHT = 40;
	public int CONSOLE_WIDTH = 6;
	private float speedRate = 1f;
	private final String TAG = World.class.getSimpleName();

	public List<Integer> collapsedLine;

	public List<WallItem> walls;

	private List<WallItem> garageWalls;

	private WallItem[][] cells;

	public enum State {
		running, collapsing, gameOver
	}

	public State currentState = State.running;

	Pool<WallItem> pool;

	private boolean isGameOver = false;
	public float timeConsumed = 0;
	private float tickTime = 0.5f;

	public Block block;
	public Block nextBlock;

	private WorldListener listener;

	public World(WorldListener listener) {
		this.listener = listener;
		resetBlock();
		pool = new Pool<WallItem>(new PoolObjectFactory<WallItem>() {

			@Override
			public WallItem createObject() {
				// TODO Auto-generated method stub
				return new WallItem();
			}

		}, 4);

		walls = new ArrayList<WallItem>(GAME_WIDTH * FRUSTUM_HEIGHT);
		cells = new WallItem[GAME_WIDTH][FRUSTUM_HEIGHT];
		garageWalls = new ArrayList<WallItem>(GAME_WIDTH * 4);

		collapsedLine = new ArrayList<Integer>();
	}

	public interface WorldListener {
		public void collide();

		public void collapse();
	}

	enum BlockColor {
		green, white, red

	}

	private void resetBlock() {

		if (block == null)
			block = new Block();
		if (nextBlock == null) {
			nextBlock = new Block();
			nextBlock.setPosition(GAME_WIDTH + 3, FRUSTUM_HEIGHT - 5);
		}

		block.color = nextBlock.color;
		block.setAreas(nextBlock.getAreas());
		block.setPosition(GAME_WIDTH / 2, FRUSTUM_HEIGHT - 1);
		nextBlock.init();

	}

	public World() {

		this(null);
	}

	public void update(float deltaTime) {
		if (isGameOver)
			return;
		timeConsumed += deltaTime;

		switch (currentState) {

		case collapsing: {
			if (timeConsumed > COLLAPSEDTIME) {
				timeConsumed -= COLLAPSEDTIME;

				for (WallItem item : walls) {
					// set cells to null;
					int x = item.position.x;
					int y = item.position.y;
					cells[x][y] = null;
					int offsetY = 0;
					for (int collapsedY : collapsedLine) {

						if (y == collapsedY) {
							garageWalls.add(item);

						} else if (y > collapsedY) {
							offsetY++;
						}
					}
					if (offsetY > 0)
						item.position.y -= offsetY;

				}

				walls.removeAll(garageWalls);

				for (WallItem item : garageWalls) {
					pool.free(item);
				}
				garageWalls.clear();

				// reset cells
				for (WallItem item : walls) {
					cells[item.position.x][item.position.y] = item;
				}

				currentState = State.running;
			}
			break;
		}

		case running: {
			if (timeConsumed > tickTime / speedRate) {
				timeConsumed -= tickTime / speedRate;
				// check collide first

				if (checkCollision()) {

					if (listener != null)
						listener.collide();

					if (checkGameOver()) {

						currentState = State.gameOver;

					} else {

						copyBlockToWall();
						if (checkCollideLineForCollapse(block)) {
							currentState = State.collapsing;
							if (listener != null)
								listener.collapse();
						}
						resetBlock();
					}

				} else

					block.advance();

			}

			break;
		}

		case gameOver: {
			
			
			
			

			break;
		}

		}

	}

	/**
	 * 将方块相应的数据拷贝到wall 上。
	 * 
	 */
	private void copyBlockToWall() {

		// copy block to walls
		for (int i = 0; i < block.area.length; i++) {
			if (block.area[i].y >= FRUSTUM_HEIGHT)
				continue;
			WallItem item = pool.newObject();
			int x = block.area[i].x;
			int y = block.area[i].y;
			item.position.x = x;
			item.position.y = y;
			item.color = block.color;
			cells[x][y] = item;
			walls.add(item);

		}

	}

	private boolean checkCollision() {

		boolean collide = false;
		for (int i = 0; i < block.area.length; i++) {
			int y = block.area[i].y;

			if (y >= FRUSTUM_HEIGHT)
				continue;

			if (y == 0) {
				collide = true;
				break;
			} else {
				if (cells[block.area[i].x][y - 1] != null) {
					collide = true;
					break;
				}

			}
		}

		return collide;

	}

	private boolean checkGameOver() {

		boolean gameOver = false;
		for (int i = 0; i < block.area.length; i++) {
			int y = block.area[i].y;

			if (y >= FRUSTUM_HEIGHT)
				gameOver = true;

		}

		return gameOver;

	}

	// move block to right by one step;
	public void right() {
		// TODO Auto-generated method stub
		boolean collide = false;
		for (int i = 0; i < block.area.length; i++) {
			if (block.area[i].y >= FRUSTUM_HEIGHT)
				continue;
			int x = block.area[i].x;

			if (x + 1 >= GAME_WIDTH) {
				collide = true;
				break;
			} else {
				if (cells[x + 1][block.area[i].y] != null) {
					collide = true;
					break;
				}

			}

		}
		if (!collide) {
			block.right();
		}

	}

	// move block to left by one step;
	public void left() {
		// TODO Auto-generated method stub
		boolean collide = false;
		for (int i = 0; i < block.area.length; i++) {

			if (block.area[i].y >= FRUSTUM_HEIGHT)
				continue;

			int x = block.area[i].x;
			if (x - 1 < 0) {
				collide = true;
				break;
			} else {

				if (cells[x - 1][block.area[i].y] != null) {
					collide = true;
					break;
				}

			}

		}
		if (!collide) {
			block.left();
		}

	}

	public void speedDown() {
		// TODO Auto-generated method stub
		speedRate = 6;

	}

	public void rotate() {
		// TODO Auto-generated method stub

		block.rotate(cells, GAME_WIDTH, FRUSTUM_HEIGHT);

	}

	public void speedNormal() {
		// TODO Auto-generated method stub
		speedRate = 1;
	}

	/**
	 * 检查是否产生可以消除的行
	 * 
	 * @param block
	 */
	private boolean checkCollideLineForCollapse(Block block) {
		collapsedLine.removeAll(collapsedLine);
		int y;
		for (int i = 0; i < block.area.length; i++) {
			y = block.area[i].y;
			if (y >= FRUSTUM_HEIGHT)
				continue;
			if (collapsedLine.indexOf(y) == -1) {

				int j;
				for (j = 0; j < GAME_WIDTH; j++) {
					if (cells[j][y] == null)
						break;
				}
				if (j == GAME_WIDTH) {
					collapsedLine.add(y);
				}

			}

		}
		if (collapsedLine.size() > 0)
			return true;
		return false;

	}
}
