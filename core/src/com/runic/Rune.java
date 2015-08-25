package com.runic;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by Nothrim on 2015-08-13.
 */
public class Rune extends Sprite {
    int id;
    private boolean used;
    private String name;
    public boolean isUsed(){return  used;}
    public String getName(){return name;}
    public Rune(Sprite sprite, int id,String name, float x, float y, boolean used)
    {
        super(sprite);

        this.name=name;
        this.used=used;
        this.id=id;
        this.setPosition(x,y);

    }
    public Rune(Sprite sprite, int id,float x, float y, boolean used)
    {
        super(sprite);

        this.name="Blank";
        this.used=used;
        this.id=id;
        this.setPosition(x,y);

    }

    public void use()
    {
        if(!used) {
            used = true;
            this.setTexture(Assets.getInstance().RuneBlank.getTexture());
            this.id=0;
        }
    }
    public static Sprite loadSprite(int id)
    {
        Sprite sprite;
        switch (id)
        {
            case 0:
                sprite=Assets.getInstance().RuneSword;
                break;
            case 1:
                sprite=Assets.getInstance().RuneArrow;
                break;
            case 2:
                sprite=Assets.getInstance().RuneBeast;
                break;
            case 3:
                sprite=Assets.getInstance().RuneEarth;
                break;
            case 4:
                sprite=Assets.getInstance().RuneFire;
                break;
            case 5:
                sprite=Assets.getInstance().RuneLife;
                break;
            case 6:
                sprite=Assets.getInstance().RuneLight;
                break;
            case 7:
                sprite=Assets.getInstance().RuneLightning;
                break;
            case 8:
                sprite=Assets.getInstance().RuneMiasma;
                break;
            case 9:
                sprite=Assets.getInstance().RuneMoon;
                break;
            case 10:
                sprite=Assets.getInstance().RunePlant;
                break;
            case 11:
                sprite=Assets.getInstance().RuneProtection;
                break;
            case 12:
                sprite=Assets.getInstance().RuneShadow;
                break;
            case 13:
                sprite=Assets.getInstance().RuneSky;
                break;
            case 14:
                sprite=Assets.getInstance().RuneSpell;
                break;
            case 15:
                sprite=Assets.getInstance().RuneSun;
                break;
            case 16:
                sprite=Assets.getInstance().RuneUnderground;
                break;
            default:
                sprite=Assets.getInstance().RuneBlank;
        }
        return sprite;
    }
    public static String loadName(int id)
    {
        String name;
        switch (id)
        {
            case 0:
                name="Sword";
                break;
            case 1:
                name="Arrow";
                break;
            case 2:
                name="Beast";
                break;
            case 3:
                name="Earth";
                break;
            case 4:
                name="Fire";
                break;
            case 5:
                name="Life";
                break;
            case 6:
                name="Light";
                break;
            case 7:
                name="Lightning";
                break;
            case 8:
                name="Miasma";
                break;
            case 9:
                name="Moon";
                break;
            case 10:
                name="Plant";
                break;
            case 11:
                name="Protection";
                break;
            case 12:
                name="Shadow";
                break;
            case 13:
                name="Sky";
                break;
            case 14:
                name="Spell";
                break;
            case 15:
                name="Sun";
                break;
            case 16:
                name="Underground";
                break;
            default:
                name="Blank";
        }
        return name;
    }
}
