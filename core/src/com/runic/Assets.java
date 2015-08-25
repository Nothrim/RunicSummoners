package com.runic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

/**
 * Created by Nothrim on 2015-08-12.
 */
public class Assets {
    public static Assets instance=null;
    public TextureAtlas atlas=new TextureAtlas("Runes.pack");
    public TextureAtlas footman=new TextureAtlas("Footman.pack");
    public Texture Castle=new Texture("Castle.png");
    //footman
    AnimationData FootmanAnimation=new AnimationData(footman);

    //runes
    public Sprite RuneBlank=atlas.createSprite("RuneBlank");
    public Sprite RuneArrow;
    public Sprite RuneBeast;
    public Sprite RuneEarth;
    public Sprite RuneFire;
    public Sprite RuneLife;
    public Sprite RuneLight;
    public Sprite RuneLightning;
    public Sprite RuneMiasma;
    public Sprite RuneMoon;
    public Sprite RunePlant;
    public Sprite RuneProtection;
    public Sprite RuneShadow;
    public Sprite RuneSky;
    public Sprite RuneSpell;
    public Sprite RuneSun;
    public Sprite RuneSword;
    public Sprite RuneUnderground;
    public Texture Selector=new Texture("Selector.png");
    public BitmapFont StandardFontSmall =new BitmapFont(Gdx.files.internal("StandardFont.fnt"));
    public void initialize(){
    dispose();
    atlas=new TextureAtlas("Runes.pack");
        footman=new TextureAtlas("Footman.pack");
        RuneBlank=atlas.createSprite("RuneBlank");
        RuneArrow=atlas.createSprite("RuneArrow");
        RuneBeast=atlas.createSprite("RuneBeast");
        RuneEarth=atlas.createSprite("RuneEarth");
        RuneFire=atlas.createSprite("RuneFire");
        RuneLife=atlas.createSprite("RuneLife");
        RuneLight=atlas.createSprite("RuneLight");
        RuneLightning=atlas.createSprite("RuneLightning");
        RuneMiasma=atlas.createSprite("RuneMiasma");
        RuneMoon=atlas.createSprite("RuneMoon");
        RunePlant=atlas.createSprite("RunePlant");
        RuneProtection=atlas.createSprite("RuneProtection");
        RuneShadow=atlas.createSprite("RuneShadow");
        RuneSky=atlas.createSprite("RuneSky");
        RuneSpell=atlas.createSprite("RuneSpell");
        RuneSun=atlas.createSprite("RuneSun");
        RuneSword=atlas.createSprite("RuneSword");
        RuneUnderground=atlas.createSprite("RuneUnderground");
        Selector=new Texture("Selector.png");
        StandardFontSmall =new BitmapFont(Gdx.files.internal("StandardFont.fnt"));
        StandardFontSmall.getData().setScale(0.5f,0.5f);
        StandardFontSmall.setColor(Color.YELLOW);
        Castle=new Texture("Castle.png");
        //footman
        FootmanAnimation=new AnimationData(footman);


    }
    public Assets(){initialize();}
    public void dispose(){
    atlas.dispose();
        Selector.dispose();
        footman.dispose();
        Castle.dispose();
        StandardFontSmall.dispose();
    }
    public static Assets getInstance(){
        if(instance==null) {
            instance = new Assets();
        }
        return instance;
    }

}
