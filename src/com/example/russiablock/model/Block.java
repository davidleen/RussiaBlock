package com.example.russiablock.model;

import java.util.List;
import java.util.Random;

import com.example.russiablock.Assets;
import com.example.russiablock.framework.math.Position;

public class Block {
	Random d = new Random();
	public static final int WORLDWIDTH = 10;
	public static final int TYPE_I = 0;

	public static final int TYPE_L = 1;
	public static final int TYPE_L_ANTI = 2;

	public static final int TYPE_N = 3;

	public static final int TYPE_N_ANTI = 4;

	public static final int TYPE_O = 5;

	private static final int BLOCKNUM = 4;
	Position position;
	public final Position[] area = new Position[BLOCKNUM];
	final Position[] tempArea = new Position[BLOCKNUM];
	int currentTime = 0;
	public int color;

	private int[][][] areas;

	public Block() {
		for (int i = 0; i < BLOCKNUM; i++) {
			area[i] = new Position(0, 0);
			tempArea[i] = new Position(0, 0);
		}
		position = new Position(0, 0);
		init();
	}

	public void setPosition(int x, int y) {
		position.x = x;
		position.y = y; 
		setArea();

	}

	public void setAreas(int[][][] areas) {
		this.areas = areas;
		currentTime = 0;
		setArea();
	}

	public int[][][] getAreas() {
		return areas;

	}

	public void init() {

		color = d.nextInt(Assets.BLOCKNUM);
		switch (d.nextInt(7)) {

		case 0:
			areas = Block_I.location;
			break;

		case 1:
			areas = Block_Z.location;
			break;

		case 2:
			areas = Block_7.location;
			break;

		case 3:
			areas = Block_S.location;
			break;

		case 4:
			areas = Block_J.location;
			break;

		case 5:
			areas = Block_O.location;
			break;

		case 6:
			areas = Block_T.location;
			break;

		default:
			areas = Block_I.location;

		}
		setArea();

	}

	/**
	 * ������ǰ��һ��
	 */
	public void advance() {

		if (position.y == 0) {
			return;
		}

		for (int i = 0; i < BLOCKNUM; i++) {
			area[i].y -= 1;

		}
		position.y -= 1;

	}

	public void rotate(WallItem[][] cells, int worldWidth, int worldHeight) {

		int time = (currentTime + 1) % areas.length;
		for (int i = 0; i < BLOCKNUM; i++) {
			tempArea[i].y = position.y + areas[time][i][1];
			tempArea[i].x = position.x + areas[time][i][0];
		}
		int offsetX = 0;
		for (int i = 0; i < BLOCKNUM; i++) {

			if (tempArea[i].x < 0)
				offsetX = Math.max(offsetX, -tempArea[i].x);
			if (tempArea[i].x >= worldWidth)
				offsetX = Math.min(offsetX, worldWidth - tempArea[i].x - 1);

		}
		// outof left right border
		if (offsetX != 0) {
			for (int i = 0; i < BLOCKNUM; i++) {
				tempArea[i].x += offsetX;

				if (cells[tempArea[i].x][tempArea[i].y] != null)
					return;

			}
		}
		currentTime = time;
		copyArea();
		position.x = area[1].x;

	}

	public void left() {

		position.x -= 1;
		for (int i = 0; i < BLOCKNUM; i++) {
			area[i].x -= 1;
		}
	}

	public void right() {
		position.x += 1;
		for (int i = 0; i < BLOCKNUM; i++) {
			area[i].x += 1;
		}

	}

	private void setArea() {
		for (int i = 0; i < BLOCKNUM; i++) {
			area[i].y = position.y + areas[currentTime][i][1];
			area[i].x = position.x + areas[currentTime][i][0];
		}
	}

	private void copyArea() {
		for (int i = 0; i < BLOCKNUM; i++) {
			area[i].y = tempArea[i].y;
			area[i].x = tempArea[i].x;
		}
	}

}
