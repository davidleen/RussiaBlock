package com.example.russiablock.framework.gl;

import java.io.IOException;
import java.io.InputStream;

import javax.microedition.khronos.opengles.GL10;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;

import com.example.russiablock.framework.FileIO;
import com.example.russiablock.framework.Graphics;
import com.example.russiablock.framework.impl.GLGame;

public class Texture {
	 private StringBuilder sb=new StringBuilder();
	 Graphics  graphics;
	 FileIO fileIO;
	 String fileName;
	 int minFilter;
	 int magFilter;
	 int width;
	 int height;
	 int textureId;
	 
	public Texture(GLGame glGame,String fileName)
	{
		graphics=glGame.getGraphics();
		fileIO=glGame.getFileIO();
		this.fileName=fileName;
		doLoad();
	}
	
	private void doLoad()
	{
		GL10 gl=graphics.getGL();
		int[] textureIds=new int[1];
		gl.glGenTextures(1, textureIds, 0);
		  textureId=textureIds[0];
		
		InputStream in=null;
		try{
			in=fileIO.readAsset(fileName);
			Bitmap bm=BitmapFactory.decodeStream(in);
			gl.glBindTexture(GL10.GL_TEXTURE_2D, textureId);
			GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bm, 0);
			setFilters(GL10.GL_NEAREST,GL10.GL_NEAREST);
			gl.glBindTexture(GL10.GL_TEXTURE_2D, 0);
			width=bm.getWidth();
			height=bm.getHeight();
			bm.recycle();
			
			
		}catch(IOException e)
		{
			
			throw new RuntimeException("cannot load  "+fileName+" in the assets ");
		}finally
		{
			if(in!=null)
			{
				 try { in.close(); } catch (IOException e) { }	
			}
		}
	}
	
	public void setFilters(int minFilter,int magFilter)
	{
		this.minFilter=minFilter;
		this.magFilter=magFilter;
		GL10 gl=graphics.getGL();
		gl.glTexParameterf(GL10.GL_TEXTURE_2D,GL10.GL_TEXTURE_MIN_FILTER , minFilter);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D,GL10.GL_TEXTURE_MAG_FILTER , magFilter);
		
		
	}
	
	
	public void reload()
	{
		doLoad();
		bind();
		setFilters(GL10.GL_NEAREST,GL10.GL_NEAREST);
		graphics.getGL().glBindTexture(GL10.GL_TEXTURE_2D, 0);
	}

	public void bind() {
		// TODO Auto-generated method stub
		
		graphics.getGL().glBindTexture(GL10.GL_TEXTURE_2D, textureId);
		
		
	}
	public void dispos()
	{
		GL10 gl=graphics.getGL();
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textureId);
		int[] textureIds = { textureId };
		gl.glDeleteTextures(GL10.GL_TEXTURE_2D, textureIds,0);
	}
}
