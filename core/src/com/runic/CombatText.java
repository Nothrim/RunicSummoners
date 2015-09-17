package com.runic;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

/**
 * Created by Nothrim on 2015-08-22.
 */
public class CombatText  {
    public static final float COMBAT_TEXT_TIME=2f;
    public static final float COMBAT_TEXT_SHRINK_SPEED=COMBAT_TEXT_TIME*3;
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
    public static boolean create(String text,float x,float y,float BlueTint,float GreenTint){
        for(int i=0;i<combatTexts.length;i++)
        {
            if(combatTexts[i]==null || !combatTexts[i].isActive())
            {
                combatTexts[i]=new CombatText(text,x,y,BlueTint,GreenTint);
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
                if(c.time<COMBAT_TEXT_TIME) {
                    c.time += deltaTime;
                    c.color = new Color(0.5f + c.time/(2*COMBAT_TEXT_TIME), c.color.g, c.color.b, 1f);
                }
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
                Assets.getInstance().StandardFontSmall.setColor(c.color);
                Assets.getInstance().StandardFontSmall.getData().setScale(Math.max(0.8f - c.time / COMBAT_TEXT_SHRINK_SPEED, 0.01f));
                Assets.getInstance().StandardFontSmall.draw(sb, c.getText(), c.x, c.y + 50 * c.time);
            }
        }
        Assets.getInstance().StandardFontSmall.setColor(Color.YELLOW);
        Assets.getInstance().StandardFontSmall.getData().setScale(0.5f);
    }
    private float x;
    private float y;
    private String text;
    public float time;
    private boolean active;
    Color color;
    public CombatText(String text,float x,float y)
    {
        this.text=text;
        this.x=x;
        this.y=y;
        active=true;
        time=0;
        color=new Color(0.5f,0.2f,0,1);
    }
    public CombatText(String text,float x,float y,float BlueTint,float GreenTint)
    {
        this.text=text;
        this.x=x;
        this.y=y;
        active=true;
        time=0;
        color=new Color(0.5f,Math.min(1f,GreenTint+0.2f),Math.min(1f,0+BlueTint),1f);
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
