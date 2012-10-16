package com.example.russiablock.model;

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

	int[][][] areas;
	int totalRotateCount = 0;
	

	public Block() {
		for (int i = 0; i < BLOCKNUM; i++) {
			area[i] = new Position(0, 0);
			tempArea[i] = new Position(0, 0);
		}
	}

	public void reset(int worldWidth,int worldHeight) {
		position = new Position(worldWidth / 2, worldHeight-1);
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
			areas = Block_L.location;
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
		currentTime = 0;
		totalRotateCount = areas.length;
		setArea();

	}

	/**
	 * ������ǰ��һ��
	 */
	public void advance(int[][] worldLimit, int  worldWidth, int worldHeight) {

		setTempArea();
		if(position.y ==0)
		{
			 
			return ;
		}
 
		
		for (int i = 0; i < BLOCKNUM; i++) {
			tempArea[i].y -= 1;
			 
		}
		position.y -= 1;
		copyArea();

	}

	public void rotate(int[][] worldLimit, int worldWidth, int worldHeight) {

		int time = (currentTime + 1) % totalRotateCount;
		for (int i = 0; i < BLOCKNUM; i++) {
			tempArea[i].y = position.y + areas[time][i][1];
			tempArea[i].x = position.x + areas[time][i][0];
		}
//		int offsetX = 0;
//		for (int i = 0; i < BLOCKNUM; i++) {
//			if (tempArea[i].y >= worldHeight
//					|| worldLimit[tempArea[i].x][tempArea[i].y] > 0)//
//			{// can not rotate
//				return;
//			}
//			if (tempArea[i].x < 0)
//				offsetX = Math.min(offsetX, -tempArea[i].x);
//			if (tempArea[i].x >= worldWidth)
//				offsetX = Math.min(offsetX, worldWidth - tempArea[i].x);
//		}
//		// outof left right border
//		if (offsetX != 0) {
//			for (int i = 0; i < BLOCKNUM; i++) {
//				tempArea[i].x += offsetX;
//
//				if (worldLimit[tempArea[i].x][tempArea[i].y] > 0)//
//				{// check whether relocate ok��
//					return;
//				}
//			}
//		}
		currentTime = time;
		copyArea();
		position.x = area[1].x;

	}

	public void left( ) {

		  position.x -= 1;
		  for (int i = 0; i < BLOCKNUM; i++) {
		  area[i].x -= 1;
		  }
	}

	public void right( ) {
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

	private void setTempArea() {
		for (int i = 0; i < BLOCKNUM; i++) {
			tempArea[i].y = area[i].y;
			tempArea[i].x = area[i].x;
		}
	}

	private void copyArea() {
		for (int i = 0; i < BLOCKNUM; i++) {
			area[i].y = tempArea[i].y;
			area[i].x = tempArea[i].x;
		}
	}

}
