package com.runic;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
    private Texture texture;
    private int health;
    private Rectangle hitbox;
    private float x;
    private float y;
    private float spawnpoint;


    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
    public float getSpawnpoint(){return spawnpoint;}
    public Rectangle getHitbox(){return hitbox;}
    public Castle(Player owner,int health,Texture texture,float x,float y){
        this.owner=owner;
        this.health=health;
        this.texture=texture;
        this.x=x;
        this.y=y;
        hitbox=new Rectangle(x,y,texture.getWidth()/2,texture.getHeight());
        if(x<700)
        {
            spawnpoint=x+texture.getWidth();
        }
        else
            spawnpoint=x;
    }
    public void draw(SpriteBatch sb){
        sb.draw(texture,x,y);
    }
    public float distance(BaseUnit u)
    {
      return Math.abs(u.getX()-spawnpoint);
    }
    public boolean damage(int dmg)
    {
        if(immune)return false;
        else {
            int random = MathUtils.random(dmg / 2, dmg);
            CombatText.create(Integer.toString(random), x, y);
            health -= random;
            if (health<0) {
                owner.lose();
            }
            return true;
        }
    }

}
