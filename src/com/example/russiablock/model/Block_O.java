package com.example.russiablock.model;

public class Block_O {

	static final int CHANGEDAMOUNT = 1;
	static int[][] location1 = new int[][] { { 0, 0 }, { 0, 1 }, { 1, 0 }, {  1, 1 } };
	 
	static int[][][] location = new int[][][] { location1 };
	int currentState=0;
	
	
	public Block_O()
	{
		currentState=0;
	}
	
	
	public void reset()
	{
		currentState=0;
	}
	
	
}
