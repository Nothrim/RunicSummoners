package com.runic.Effects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.runic.Assets;
import com.runic.Player;
import com.runic.Utils.SmoothChangingFloat;

/**
 * Created by Nothrim on 2015-09-14.
 */
public class PolygonEffect {

    protected float length;
    protected float life;
    protected boolean active;
    protected PolygonRegion polygonRegion;
    protected Player owner;
    public boolean isActive(){return active;}
    public Player getOwner(){return owner;}
    public PolygonEffect(TextureRegion textureRegion,float[] vertices,short[] triangles,float effectLength,Player owner)
    {
        this.owner=owner;
        this.length=effectLength;
        life=0;
        active=true;
        polygonRegion=new PolygonRegion(textureRegion,vertices, triangles);
    }
    public PolygonEffect(float effectLength,Player owner)
    {
        this.owner=owner;
        this.length=effectLength;
        life=0;
        active=true;
    }
    protected void update(float deltaTime)
    {
        if(life<length)
            life+=deltaTime;
        else
        {
            onKill();
            active=false;
        }

    }
    public void kill(){active=false;}
    public void draw(PolygonSpriteBatch sb,float deltaTime)
    {
        update(deltaTime);
    }
    public void onKill(){}
}
