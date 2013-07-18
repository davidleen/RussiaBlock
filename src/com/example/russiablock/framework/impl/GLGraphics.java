package com.example.russiablock.framework.impl;

import javax.microedition.khronos.opengles.GL10;

import com.example.russiablock.framework.Graphics;

import android.opengl.GLSurfaceView;

public class GLGraphics  implements Graphics{
    GLSurfaceView glView;
    GL10 gl;
    
    GLGraphics(GLSurfaceView glView) {
        this.glView = glView;
    }
    
    public GL10 getGL() {
        return gl;
    }
    
   public void setGL(GL10 gl) {
        this.gl = gl;
    }
    
    public int getWidth() {
        return glView.getWidth();
    }
    
    public int getHeight() {
        return glView.getHeight();
    }
}
