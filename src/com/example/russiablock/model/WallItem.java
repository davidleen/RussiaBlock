package com.example.russiablock.model;

import com.example.russiablock.framework.math.Position;

public class WallItem {
	
	public Position position;
	
	public int color;
	
	public WallItem()
	{
		position=new Position(-1,-1);
		color=-1;
	}

}
