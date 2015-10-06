package com.runic;

import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.TiledMapTileSets;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Nothrim on 2015-09-04.
 */
public class TiledLight {
    public static final float RAY_POWER=300;
    private float x,y,mapX,mapY,scale;
    private int peakX,peakY;
    TiledMapTileLayer lightningLayer;
    TiledMapTileLayer groundLayer;
    TiledMapTileSet shadowSet;

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public TiledLight(float x,float y,TiledMapTileLayer lightningLayer,TiledMapTileLayer groundLayer,TiledMapTileSets tileSets,float scale)
    {
        this.x=x;
        this.y=y;
        this.lightningLayer=lightningLayer;
        this.groundLayer=groundLayer;
        this.scale=scale;
        shadowSet=tileSets.getTileSet("Shaders");
        mapX=x/scale;
        mapY=y/scale;
        Vector2 tempPeak=getPeak();
        peakX=(int)tempPeak.x;
        peakY=(int)tempPeak.y;
        ShadeAll();
    }
    public void translate(float x,float y)
    {
        this.x+=x;
        this.y+=y;
        mapX=this.x/scale;
        mapY=this.y/scale;
    }
    public int findTileY(int tileX){
        for(int i=peakY;i>=0;i--)
        {
            if(groundLayer.getCell(tileX,i)!=null)
                return i;
        }
        return -1;
    }
    public void update(float screenWidth)
    {
        ShadeAll();
        for(int i=0;i<screenWidth/8;i++)
        {
            float ray=RAY_POWER;
            int tileY=findTileY(i);
            ray-=Math.sqrt((mapX-i)*(mapX-i)+(mapY-tileY)*(mapY-tileY));
            if(ray<0)ray=0;
            for(int j=(int)mapY;j>=0;j--)
            {
                if(groundLayer.getCell(i,j)!=null) {
                    if(ray>0)
                        ray-=5;
                    else
                        ray=0;
                    if(ray>250)
                        lightningLayer.getCell(i, j).setTile(shadowSet.getTile(13));
                    else if(ray>200)
                        lightningLayer.getCell(i, j).setTile(shadowSet.getTile(12));
                    else if(ray>150)
                        lightningLayer.getCell(i, j).setTile(shadowSet.getTile(11));
                    else if(ray>100)
                        lightningLayer.getCell(i, j).setTile(shadowSet.getTile(10));
                    else if(ray>50)
                        lightningLayer.getCell(i, j).setTile(shadowSet.getTile(9));
                    else
                        lightningLayer.getCell(i, j).setTile(shadowSet.getTile(8));
                }
            }
        }

    }
    private Vector2 getPeak()
    {
        if(groundLayer!=null)
        {
            for(int i=groundLayer.getHeight();i>0;i--)
            {
                for(int j=0;j<groundLayer.getWidth();j++)
                {
                 if(groundLayer.getCell(j,i)!=null)
                 {
                     return new Vector2(j,i);
                 }
                }
            }
        }
        return null;
    }
    private void ShadeAll()
    {
        for (int i=0;i<groundLayer.getHeight();i++)
        {
            for(int j=0;j<groundLayer.getWidth();j++)
            {
                if(groundLayer.getCell(j,i)!=null) {
                    if(lightningLayer.getCell(j,i)==null)
                    {
                        TiledMapTileLayer.Cell c=new TiledMapTileLayer.Cell();
                        c.setTile(shadowSet.getTile(7));
                        lightningLayer.setCell(j,i,c);
                    }
                    else
                        lightningLayer.getCell(j,i).setTile(shadowSet.getTile(7));

                }
            }
        }
    }
    public void setX(float x)
    {
        this.x=x;
        this.mapX=x/scale;
    }
}
