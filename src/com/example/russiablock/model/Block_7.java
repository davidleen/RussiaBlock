package com.example.russiablock.model;

public class Block_7 {

	static final int CHANGEDAMOUNT = 4;
	static int[][] location1 = new int[][] { { -1, 0}, { 0, 0 }, { 0, 1 }, { 0, 2 } };
	static int[][] location2 = new int[][] { { 0, -1 }, { 0, 0 }, { 0, 1 }, { 1, 1 } };
	static int[][] location3 = new int[][] { { 0, 1 }, { 0, 0 }, { 1, 0 }, { 2, 0 } };
	static int[][] location4 = new int[][] { { -1, 0 }, { 0, 0 }, { 1, 0 }, { 1, -1 } };
	 
	static int[][][] location = new int[][][] { location1,location3,  location2, location4 };
	int currentState=0;
	
	
	public Block_7()
	{
		currentState=0;
	}
	
	
	public void reset()
	{
		currentState=0;
	}
	
	
}
