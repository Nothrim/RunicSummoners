package com.runic;

import Interfaces.IWorld;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.runic.Effects.PolygonEffect;
import com.runic.Effects.SpriteEffect;
import com.runic.Effects.SpriteLighting;
import com.runic.Network.NetID;
import com.runic.Particles.PremadeEffect;
import com.runic.Projectiles.BaseProjectile;
import com.runic.Units.BaseUnit;
import com.runic.Units.Dummy;
import com.runic.Units.Gore;
import com.runic.Utils.SmoothChangingFloat;

/**
 * Created by Nothrim on 2015-10-06.
 */
public class World implements IWorld {
    public static final int REFILL_TIME=60;
    public static final int SUN_CHANGE_TIME=1;
    public static final int GAME_SCREEN_WIDTH=1920;
    public static final int GAME_SCREEN_HEIGHT=1080;
    //array sizes;
    public static final int DUMMY_SIZE=500;
    public static final int POLYGON_EFFECT_SIZE=100;
    public static final int SPRITE_EFFECT_SIZE=1000;

    public static World world=null;
    public static World getInstance()
    {
        if(world==null)
        {
            world=new World();
        }
        return world;
    }

    @Override
    public void netCreate(int playerId, int id) {
        if(playerId==0)
            p1.netSpawn(NetID.getUnit(id,p1));
        else
            p2.netSpawn(NetID.getUnit(id,p2));

    }

    @Override
    public void netCreateProjectile(int id, int playerId, float x, float y, float velocityX, float velocityY) {

            BaseProjectile.netCreateProjectile(id, x, y, (playerId == 0) ? p1 : p2, velocityX, velocityY);

    }

    @Override
    public void netCreateSpriteLightning(Vector2 begin, int owner,int targetId, float life) {
        createSpriteEffect(new SpriteLighting(begin,owner==0?p1.getArmy()[targetId]:p2.getArmy()[targetId],life));
    }

    @Override
    public void netCreatePremadeEffect(int type, float positionX, float positionY) {
        PremadeEffect.spawn(type,positionX,positionY);
    }

    @Override
    public void netDamageCastle(int playerId, int damage) {
        if(playerId==0)
            p1.getCastle().netDamage(damage);
        else
            p2.getCastle().netDamage(damage);
    }

    boolean paused=false;
    public boolean host=false;
    public boolean Multiplayer=false;
    public void stopGame(){paused=!paused;}
    //entities and effects
    public Dummy[] Dummies;
    public PolygonEffect[] polygonEffects;
    public SmoothChangingFloat blinkingColor;
    public SpriteEffect[] spriteEffects;
    //-------
    public Player p1;
    public Player p2;
    public TiledMap map;
    public TiledLight sun;
    //timers and stuff
    float sunTimer;
    public SmoothChangingFloat sunPosition;
    public double RefillTimer=0;
    private World()
    {
        sunTimer=0;
        map= new TmxMapLoader().load("RunicSummonersMap.tmx");
        BaseUnit.setCollisionLayer((TiledMapTileLayer) map.getLayers().get(0));
        Gore.initialize((TiledMapTileLayer) map.getLayers().get(0));
        sun=new TiledLight(800,1000,(TiledMapTileLayer)map.getLayers().get(1),(TiledMapTileLayer)map.getLayers().get(0),map.getTileSets(),8);
        Combinations.Load();
        p1=new Player(0,new Vector2(0,1080),0);
        p2=new Player(0,new Vector2(1520,1080),1);
        p1.setEnemy(p2);
        p2.setEnemy(p1);
        sun.update(1920);
        BaseProjectile.initialize((TiledMapTileLayer) map.getLayers().get(0));
        //initializing arrays and effects
        Dummies=new Dummy[DUMMY_SIZE];
        polygonEffects=new PolygonEffect[POLYGON_EFFECT_SIZE];
        blinkingColor=new SmoothChangingFloat(0.5f,0.99f);
        PremadeEffect.initialize();
        spriteEffects=new SpriteEffect[SPRITE_EFFECT_SIZE];
        //-----------------------------
        sunPosition=new SmoothChangingFloat(0,GAME_SCREEN_WIDTH,true);
    }
    public void update(float deltaTime) {
        if (!paused) {
            for (int i = 0; i < Dummies.length; i++) {
                if (Dummies[i] != null && Dummies[i].isActive()) {
                    Dummies[i].update(deltaTime);
                }
            }
            p1.update(deltaTime);
            p2.update(deltaTime);
            BaseProjectile.updateProjectiles(deltaTime);
            if (sunTimer < SUN_CHANGE_TIME) {
                sunTimer += deltaTime;
            } else {
                sun.setX(sunPosition.getValue(10));
                sunTimer = 0;
//            if(sun.getX()>GAME_SCREEN_WIDTH)
//                sun.translate(-GAME_SCREEN_WIDTH,0);
//            sun.translate(10,0);
                sun.update(GAME_SCREEN_WIDTH);
            }
            if (RefillTimer < REFILL_TIME) {
                RefillTimer += deltaTime;
            } else {
                RefillTimer = 0;
                p1.refill();
                p2.refill();
            }
            CombatText.update(deltaTime);
        }
    }
    public void serverUpdate(float deltaTime)
    {
        for (int i = 0; i < Dummies.length; i++) {
            if (Dummies[i] != null && Dummies[i].isActive()) {
                Dummies[i].update(deltaTime);
            }
        }
        p1.update(deltaTime);
        p2.update(deltaTime);
        BaseProjectile.updateProjectiles(deltaTime);
        if (sunTimer < SUN_CHANGE_TIME) {
            sunTimer += deltaTime;
        } else {
            sun.setX(sunPosition.getValue(10));
            sunTimer = 0;
            sun.update(GAME_SCREEN_WIDTH);
        }
        if (RefillTimer < REFILL_TIME) {
            RefillTimer += deltaTime;
        } else {
            RefillTimer = 0;
            p1.refill();
            p2.refill();
        }
        CombatText.update(deltaTime);
    }
    public void clientUpdate(float deltaTime)
    {
        for (int i = 0; i < Dummies.length; i++) {
            if (Dummies[i] != null && Dummies[i].isActive()) {
                Dummies[i].update(deltaTime);
            }
        }
        p1.clientUpdate(deltaTime);
        p2.clientUpdate(deltaTime);
        BaseProjectile.updateProjectiles(deltaTime);
        if (sunTimer < SUN_CHANGE_TIME) {
            sunTimer += deltaTime;
        } else {
            sun.setX(sunPosition.getValue(10));
            sunTimer = 0;
            sun.update(GAME_SCREEN_WIDTH);
        }
        if (RefillTimer < REFILL_TIME) {
            RefillTimer += deltaTime;
        } else {
            RefillTimer = 0;
            p1.refill();
            p2.refill();
        }
        CombatText.update(deltaTime);
    }
    public void draw()
    {

    }

    public void delete()
    {
        p1.dispose();
        p2.dispose();
        map.dispose();
    }
    //creating entities
    //dummy
    public int newDummy(Dummy d)
    {
        for(int i=0;i<Dummies.length;i++)
        {
            if(Dummies[i]==null || !Dummies[i].isActive())
            {
                Dummies[i]=d;
                return i;
            }
        }
        return -1;
    }
    //polygon effect
    public void clearPolygonEffects(Player owner)
    {
        for(PolygonEffect e : polygonEffects)
        {
            if(e!=null && e.isActive() && e.getOwner().whoAmI()==owner.whoAmI())
            {
                e.kill();
            }
        }
    }
    public int createPolygonEffect(PolygonEffect p)
    {
        for(int i=0;i<polygonEffects.length;i++)
        {
            if(polygonEffects[i]==null || !polygonEffects[i].isActive())
            {
                polygonEffects[i]=p;
                return i;
            }
        }
        return -1;
    }
    public void drawPolygonEffects(PolygonSpriteBatch sb, float deltaTime)
    {
        for(int i=0;i<polygonEffects.length;i++)
        {
            if(polygonEffects[i]!=null && polygonEffects[i].isActive())
                polygonEffects[i].draw(sb,deltaTime);
        }
    }
    //sprite effect

    public int createSpriteEffect(SpriteEffect spriteEffect)
    {
        for(int i=0;i<spriteEffects.length;i++)
        {
            if(spriteEffects[i]==null || !spriteEffects[i].isActive())
            {
                spriteEffects[i]=spriteEffect;
                return i;
            }
        }
        return -1;
    }
    public void drawSpriteEffects(float deltaTime,SpriteBatch sb)
    {
        for(int i=0;i<spriteEffects.length;i++)
        {
            if(spriteEffects[i]!=null && spriteEffects[i].isActive())
            {
                spriteEffects[i].draw(deltaTime,sb);
            }
        }
    }
}
