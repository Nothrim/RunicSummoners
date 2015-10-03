package com.runic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Affine2;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.runic.Units.BaseUnit;
import org.w3c.dom.Text;

/**
 * Created by Nothrim on 2015-08-18.
 */
public class Castle {
    boolean immune=false;
    Player owner;
    private int health;
    private float x;
    private float y;
    private float spawnpoint;
    private float origin=0;
    private float health80;
    private float health60;
    private float health30;
    private float health15;
    private float immuneTimer=0;

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
    public float getSpawnpoint(){return spawnpoint;}
    public Castle(Player owner,int health,float x,float y){
        this.owner=owner;
        this.health=health;
        this.x=x;
        this.y=y;
        if(x<700)
        {
            spawnpoint=x+Assets.getInstance().Castle.findRegion("Castle0").getRegionWidth();
        }
        else
            spawnpoint=x;
        health80=health*0.8f;
        health60=health*0.6f;
        health30=health*0.3f;
        health15=health*0.15f;
    }
    public void draw(SpriteBatch sb){
        if(immune)
            immuneTimer+= Gdx.graphics.getDeltaTime();
        if(immuneTimer>0.25f)
        {
            immuneTimer=0;
            immune=false;
        }
        TextureRegion texture;
        if (health>health80)
            texture=Assets.getInstance().Castle.findRegion("Castle0");
        else if(health>health60)
            texture=Assets.getInstance().Castle.findRegion("Castle1");
        else if(health>health30)
            texture=Assets.getInstance().Castle.findRegion("Castle2");
        else if(health>health15)
            texture=Assets.getInstance().Castle.findRegion("Castle3");
        else
            texture=Assets.getInstance().Castle.findRegion("Castle4");
        //texture.flip(owner.whoAmI()==1,false);
        if(owner.whoAmI()==1) {
            sb.draw(texture,x+texture.getRegionWidth(),y,-texture.getRegionWidth(),texture.getRegionHeight());
        }
        else
            sb.draw(texture,x,y);

    }
    public float distance(BaseUnit u)
    {
      return Math.abs(u.getX()-u.getHitbox().width/2-spawnpoint);
    }
    public boolean damage(int dmg)
    {
        if(immune)return false;
        else {
            immune=true;
            int random = MathUtils.random(dmg / 2, dmg);
            CombatText.create(Integer.toString(random), spawnpoint, y);
            health -= random;
            if (health<0) {
                owner.lose();
            }
            return true;
        }
    }

}
