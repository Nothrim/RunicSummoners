package com.runic.Projectiles;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.utils.Array;
import com.runic.Assets;
import com.runic.Player;

/**
 * Created by Nothrim on 2015-08-25.
 */
public abstract class BaseProjectile {
    public static BaseProjectile[] Projectiles;
    protected Animation move;
    protected float x;
    protected float y;
    protected float velocityX;
    protected float velocityY;
    protected int damage;
    protected Player owner;
    protected boolean active;
    public static TiledMapTileLayer ground;
    public static void initialize(TiledMapTileLayer collisionLayer)
    {
        ground=collisionLayer;
        Projectiles=new BaseProjectile[500];
    }
    public static int createProjectile(int type,float x, float y,Player owner,float velocityX,float velocityY)
    {
        for(int i=0;i<Projectiles.length;i++)
        {
            if(Projectiles[i]==null || !Projectiles[i].active)
            {
                Projectiles[i]=projectileFactory(type,x,y,owner,velocityX,velocityY);
                return i;
            }
        }

        return -1;
    }
    public static void drawProjectiles(SpriteBatch sb,float deltaTime)
    {
        for(BaseProjectile p: Projectiles)
        {
            if(p!=null && p.active)
            {
                p.draw(sb,deltaTime);
            }
        }
    }
    public static void updateProjectiles(float deltaTime)
    {
        for(BaseProjectile p: Projectiles)
        {
            if(p!=null && p.active)
            {
                p.update(deltaTime);
            }
        }
    }
    private static BaseProjectile projectileFactory(int type,float x,float y,Player owner,float velocityX,float velocityY)
    {
        switch(type){
            case 0:
                return new Arrow(x,y,new Animation(1f,Assets.getInstance().projectiles.findRegion("Arrow")),5,owner,velocityX,velocityY);
        }
        return null;

    }
    public BaseProjectile(float x,float y,Animation move,int damage,Player owner){
        this.x=x;
        this.y=y;
        this.move=move;
        this.damage=damage;
        this.owner=owner;
        active=true;
    }
    public void update(float deltaTime)
    {

    }
    public void draw(SpriteBatch sb,float deltaTime)
    {

    }


}
