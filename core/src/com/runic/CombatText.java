package com.runic;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

/**
 * Created by Nothrim on 2015-08-22.
 */
public class CombatText  {
    public static final float COMBAT_TEXT_TIME=2f;
    public static CombatText[] combatTexts=new CombatText[500];
    public static boolean create(String text,float x,float y){
        for(int i=0;i<combatTexts.length;i++)
        {
            if(combatTexts[i]==null || !combatTexts[i].isActive())
            {
                combatTexts[i]=new CombatText(text,x,y);
                return true;
            }
        }
        return false;
    }
    public static void update(float deltaTime)
    {
        for(CombatText c : combatTexts)
        {
            if(c!=null && c.active) {
                if(c.time<COMBAT_TEXT_TIME)
                c.time += deltaTime;
                else
                {
                    c.time=0;
                    c.active=false;
                }
            }
        }
    }
    public static void draw(SpriteBatch sb)
    {
        for(CombatText c : combatTexts)
        {
            if(c!=null && c.active)
            {
                Assets.getInstance().StandardFontSmall.draw(sb,c.getText(),c.x,c.y+50*c.time);            }
        }
    }
    private float x;
    private float y;

    private String text;
    public float time;
    private boolean active;
    public CombatText(String text,float x,float y)
    {
        this.text=text;
        this.x=x;
        this.y=y;
        active=true;
        time=0;
    }
    public boolean isActive(){return active;}

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public String getText() {
        return text;
    }

}
