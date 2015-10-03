package com.runic.Window;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Nothrim on 2015-10-03.
 */
public class Window {
    public static Window[] windows;
    public static void initialize(int size)
    {
        windows=new Window[size];
    }
    public boolean isActive(){return  active;}
    public void close(){active=false;}
    public static void DrawContent(SpriteBatch sb,float deltaTime)
    {
        for(Window w: windows)
        {
            if(w!=null && w.active)
            {
                w.drawContent(sb,deltaTime);
            }
        }
    }
    public static void DrawBox(ShapeRenderer sr)
    {
        for(Window w : windows)
        {
            if(w!=null && w.active)
            {
                w.drawBox(sr);
            }
        }
    }
    public static int create(Window w)
    {
        for(int i=0;i<windows.length;i++)
        {
            if(windows[i]==null || !windows[i].active)
            {
                windows[i]=w;
                return i;
            }
        }
        return -1;
    }
    protected Rectangle box;
    protected com.badlogic.gdx.graphics.Color color;
    protected boolean active;
    public Window(float x,float y,int width,int height,Color color)
    {
        box=new Rectangle(x,y,width,height);
        this.color=color;
        active=true;
    }
    public Vector2 getTopLeft(){return new Vector2(box.x,box.y+box.getHeight());}
    public Vector2 getTopCenter(){return new Vector2(box.x+box.getWidth()/2,box.y+box.getHeight());}
    public Vector2 getTopRight(){return new Vector2(box.x+box.getWidth(),box.y+box.getHeight());}

    public void drawBox(ShapeRenderer sr)
    {
        sr.rect(box.x,box.y,box.width,box.height,color,color,color,color);
    }
    public void update(float deltaTime)
    {

    }
    public void drawContent(SpriteBatch sb,float deltaTime)
    {
        update(deltaTime);
    }
}
