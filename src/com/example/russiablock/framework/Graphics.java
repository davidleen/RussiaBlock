package com.example.russiablock.framework;

import javax.microedition.khronos.opengles.GL10; 
 
public interface Graphics {
    
    
    
    public GL10 getGL() ;
    
    public void setGL(GL10 gl) ;
    
    public int getWidth()  ;
    
    public int getHeight()  ;
}
