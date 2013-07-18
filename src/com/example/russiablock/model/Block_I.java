package com.example.russiablock.model;

public class Block_I {

	static final int CHANGEDAMOUNT = 2;
	static int[][] location1 = new int[][] { { 0, -1 }, { 0, 0 }, { 0, 1 }, { 0, 2 } };
	static int[][] location2 = new int[][] { { -1, 0 }, { 0, 0 }, { 1, 0 }, { 2, 0 } };
	static int[][][] location = new int[][][] { location1, location2 };
	int currentState=0;
	
	
	public Block_I()
	{
		currentState=0;
	}
	
	
	public void reset()
	{
		currentState=0;
	}
	
	
}
