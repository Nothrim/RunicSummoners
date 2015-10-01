package com.runic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

/**
 * Created by Nothrim on 2015-08-12.
 */
public class Assets {
    public static Assets instance=null;
    public TextureAtlas atlas=new TextureAtlas("Runes.pack");
    public TextureAtlas projectiles=new TextureAtlas("Projectiles.pack");
    public TextureAtlas footman=new TextureAtlas("Footman.pack");
    public TextureAtlas Castle=new TextureAtlas("Castle.pack");
    //footman
    public AnimationData FootmanAnimation=new AnimationData(footman);
    public GoreData FootmanGore=new GoreData(footman);
    //blood knight
    public TextureAtlas BloodKnight=new TextureAtlas("BloodKnight.pack");
    public AnimationData BloodKnightAnimation=new AnimationData(BloodKnight);
    public GoreData BloodKnightGore=new GoreData(BloodKnight);
    //archer
    public TextureAtlas Archer=new TextureAtlas("Archer.pack");
    public AnimationData ArcherAnimation=new AnimationData(Archer);
    public GoreData ArcherGore=new GoreData(Archer);
    //energy cloud
    public TextureAtlas EnergyCloud=new TextureAtlas("EnergyCloud.pack");
    public AnimationData EnergyCloudAnimation=new AnimationData(EnergyCloud);
    public GoreData EnergyCloudGore=new GoreData(EnergyCloud);
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
    public Texture background=new Texture("Background.png");
    public BitmapFont StandardFontSmall =new BitmapFont(Gdx.files.internal("StandardFont.fnt"));
    //particle effects
    public ParticleEffect Blood=new ParticleEffect();
    public ParticleEffect EnergyExplosion=new ParticleEffect();
    //sound effects
    public Sound ArrowFlying=Gdx.audio.newSound(Gdx.files.internal("SoundEffects/ArrowFlying.wav"));
    public Sound ArrowGround=Gdx.audio.newSound(Gdx.files.internal("SoundEffects/ArrowGround.wav"));
    public Sound ArrowGround2=Gdx.audio.newSound(Gdx.files.internal("SoundEffects/ArrowGround2.wav"));
    public Sound ArrowGround3=Gdx.audio.newSound(Gdx.files.internal("SoundEffects/ArrowGround3.wav"));
    public Sound ArrowFlesh=Gdx.audio.newSound(Gdx.files.internal("SoundEffects/ArrowFlesh.wav"));
    public void initialize(){
    dispose();
    atlas=new TextureAtlas("Runes.pack");
        projectiles=new TextureAtlas("Projectiles.pack");
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
        background=new Texture("Background.png");
        StandardFontSmall =new BitmapFont(Gdx.files.internal("StandardFont.fnt"));
        StandardFontSmall.getData().setScale(0.5f,0.5f);
        StandardFontSmall.setColor(Color.YELLOW);
        Castle=new TextureAtlas("Castle.pack");
        //footman
        FootmanAnimation=new AnimationData(footman);
        FootmanGore=new GoreData(footman);
        //blood knight
        BloodKnight=new TextureAtlas("BloodKnight.pack");
        BloodKnightAnimation=new AnimationData(BloodKnight);
        BloodKnightGore=new GoreData(BloodKnight);
        //archer
        Archer=new TextureAtlas("Archer.pack");
        ArcherAnimation=new AnimationData(Archer);
        ArcherGore=new GoreData(Archer);
        //energy cloud
        EnergyCloud=new TextureAtlas("EnergyCloud.pack");
        EnergyCloudAnimation=new AnimationData(EnergyCloud);
        EnergyCloudGore=new GoreData(EnergyCloud);
        //particle effects
        Blood=new ParticleEffect();
        Blood.load(Gdx.files.local("Blood"), Gdx.files.local(""));
        EnergyExplosion=new ParticleEffect();
        EnergyExplosion.load(Gdx.files.local("ParticleEffects/Lightning"),Gdx.files.local(""));
        //sound effects
        ArrowFlying=Gdx.audio.newSound(Gdx.files.internal("SoundEffects/ArrowFlying.wav"));
        ArrowGround=Gdx.audio.newSound(Gdx.files.internal("SoundEffects/ArrowGround.wav"));
        ArrowGround2=Gdx.audio.newSound(Gdx.files.internal("SoundEffects/ArrowGround2.wav"));
        ArrowGround3=Gdx.audio.newSound(Gdx.files.internal("SoundEffects/ArrowGround3.wav"));
        ArrowFlesh=Gdx.audio.newSound(Gdx.files.internal("SoundEffects/ArrowFlesh.wav"));
    }
    public Assets(){initialize();}
    public void dispose(){
    atlas.dispose();
        //textures
        Selector.dispose();
        footman.dispose();
        Castle.dispose();
        StandardFontSmall.dispose();
        background.dispose();
        BloodKnight.dispose();
        Archer.dispose();
        EnergyCloud.dispose();
        //projectiles
        Blood.dispose();
        EnergyExplosion.dispose();
        projectiles.dispose();
        //sounds
        ArrowFlying.dispose();
        ArrowFlesh.dispose();
        ArrowGround.dispose();
        ArrowGround2.dispose();
        ArrowGround3.dispose();
    }
    public static Assets getInstance(){
        if(instance==null) {
            instance = new Assets();
        }
        return instance;
    }

}
