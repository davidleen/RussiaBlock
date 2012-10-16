package com.example.russiablock.framework.gl;

public class TextureRegion {    
    public final float u1, v1;
    public final float u2, v2;
    public final Texture texture;
    
    /**  texture  texture 对象
     * 
     * @param texture
     * @param x         起点x
     * @param y			起点y	
     * @param width		宽度
     * @param height	高度	
     */
    public TextureRegion(Texture texture, float x, float y, float width, float height) {
        this.u1 = x / texture.width;
        this.v1 = y / texture.height;
        this.u2 = this.u1 + width / texture.width;
        this.v2 = this.v1 + height / texture.height;        
        this.texture = texture;
    }
}
