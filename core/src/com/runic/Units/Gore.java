package com.runic.Units;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.MathUtils;

/**
 * Created by Nothrim on 2015-08-28.
 */
public class Gore {
    public static final float GORE_FALL_SPEED=70;
    public static final float SCREEN_TIME=3;
    public static TiledMapTileLayer collisonLayer;
    float time;
    boolean active;
    private float x;
    private float y;
    private float RotationSpeed;
    private float force;
    private TextureRegion texture;
    private int map_x;
    private int map_y;
    private Gore(float x,float y, float RotationSpeed,TextureRegion texture,float force){
        this.x=x;
        this.y=y;
        this.RotationSpeed=RotationSpeed;
        this.texture=texture;
        this.force=force;
        active=true;
        time=0;
    }
    public boolean isActive(){return active;}
    private void update()
    {
        if(time<SCREEN_TIME) {
            time += Gdx.graphics.getDeltaTime();
            if(time<SCREEN_TIME/5)
                y += MathUtils.random(Math.abs(force/2),Math.abs(force))* Gdx.graphics.getDeltaTime();
            else if(collisonLayer.getCell((int)(x/8),(int)((y)/8))==null) {
                    y -= GORE_FALL_SPEED * Gdx.graphics.getDeltaTime();
            }
            map_y=(int)(y/8f);
            if(force>0)
                map_x=(int)((x+texture.getRegionWidth()+force*Gdx.graphics.getDeltaTime())/8f);
            else
                map_x=(int)((x+force*Gdx.graphics.getDeltaTime())/8f);
            if(collisonLayer.getCell(map_x,map_y)==null) {
                x += force * Gdx.graphics.getDeltaTime();
               force*=0.9f;
            }

        }
        else
            active=false;

    }
    public void draw(SpriteBatch sb)
    {
        sb.draw(texture,x,y,texture.getRegionX(),texture.getRegionY(), texture.getRegionWidth(), texture.getRegionHeight(),1,1,time*RotationSpeed);
    }
    public static Gore[] Gores;
    public static void initialize(TiledMapTileLayer collison){
        collisonLayer=collison;
        Gores=new Gore[500];
    }
    public static boolean newGore(float x ,float y,float RotationSpeed, TextureRegion texture,float force){
        for(int i=0;i<Gores.length;i++)
        {
            if(Gores[i]==null || !Gores[i].isActive())
            {
                Gores[i]=new Gore(x,y,RotationSpeed,texture,force);
                return true;
            }
        }
        return false;
    }
    public static void Draw(SpriteBatch sb)
    {
     for(Gore g : Gores)
     {
         if(g!=null && g.isActive()) {
             g.update();
             g.draw(sb);
         }
     }
    }
}
