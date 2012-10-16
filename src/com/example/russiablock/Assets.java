package com.example.russiablock;
 
import com.example.russiablock.framework.gl.Texture;
import com.example.russiablock.framework.gl.TextureRegion;
import com.example.russiablock.framework.impl.GLGame;

public class Assets {
	
	public static Texture texture_1;
	public static Texture texture_2;
	public static Texture items;
	
	
	public static TextureRegion gameBackgroundRegion;
	public static TextureRegion consoleBackgroundRegion;
	
	
	public static TextureRegion brim;
	public static TextureRegion courtbg;
	public static TextureRegion menubg;
	public static TextureRegion top;
	public static final int BLOCKNUM=8;
	public static TextureRegion[] blocks=new TextureRegion[BLOCKNUM];
	public static TextureRegion  mask;
	public static TextureRegion  line;
	public static TextureRegion  speed;
	
	public static TextureRegion leftRegion;
	public static TextureRegion rightRegion;
	public static TextureRegion speedUpRegion;
	public static void load(GLGame game) {
		
		texture_1 = new Texture(game, "1.png");
		brim=  new TextureRegion(texture_1, 0, 0, 12, 320);
		courtbg=  new TextureRegion(texture_1, 12, 0, 200, 340);
		menubg= new TextureRegion(texture_1, 212, 0, 240, 320);
		top= new TextureRegion(texture_1, 212, 320, 240, 12);
		int x=12+200+240;
	 
		for(int i=0;i<BLOCKNUM;i++)
		{
			blocks[i]=new TextureRegion(texture_1, x, 20*i, 20, 20);
		}
		mask=new TextureRegion(texture_1, 12+200+240+20, 0, 32, 40);
		
		line=new TextureRegion(texture_1, 12+200+240 , 160, 60, 16);
		speed=new TextureRegion(texture_1, 12+200+240 ,176, 60, 16);
		
		
		
		texture_2 = new Texture(game, "2.png");
		
		items = new Texture(game, "items.png" );
		
		leftRegion = new TextureRegion(items, 0, 0, 64, 64);
		rightRegion = new TextureRegion(items, 64, 0, 64, 64);
		speedUpRegion = new TextureRegion(items, 128, 0, 64, 64);
		 
		
	}


	public static void reload() {
		// TODO Auto-generated method stub
		
		texture_1.reload();	
	}
}
