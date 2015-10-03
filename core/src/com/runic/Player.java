package com.runic;

import android.graphics.Color;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.runic.Effects.BendingRune;
import com.runic.Effects.PolygonEffect;
import com.runic.Units.BaseUnit;
import com.runic.Units.Footman;
import com.runic.Window.HelpWindow;
import com.runic.Window.Window;

/**
 * Created by Nothrim on 2015-08-07.
 */
public class Player extends InputAdapter{
    public HelpWindow helpWindow=null;
    public Player enemy;
    public static final int RUNE_TABLE_SIZE=50;
    private Vector2 Origin;
    private int gold;
    private Rune RunesTable[];
    private CastingTable castingTable;
    int Selector;
    int id;
    private String CurrentCombination=null;
    public void setCurrentCombination(String s)
    {
        CurrentCombination=s;
    }
    public int whoAmI(){return id;}
    Castle castle;
    private boolean casting=false;
    private float castingTimer=0;
    public boolean isCasting(){return  casting;}
    public float getCastingTimer(){return  castingTimer;}
    public void cast(){
        casting=true;
        if(castingTimer<1)
            castingTimer+=1;
    }
    private BaseUnit[] Army=new BaseUnit[100];
    public BaseUnit[] getArmy(){return Army;}
    public float getX(){return Origin.x;}
    public float getY(){return Origin.y;}
    public CastingTable getCastingTable(){return  castingTable;}
    public boolean spawnUnit(BaseUnit u){
        for(int i=0;i<Army.length;i++)
        {
            if(Army[i]==null || !Army[i].isActive())
            {
                Army[i]=u;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if(id==0) {
            if (keycode == Keybindings.Hotkeys1.get("UP")) {
                if ((Selector - 10) < 0)
                    Selector += 40;
                else
                    Selector -= 10;
            } else if (keycode == Keybindings.Hotkeys1.get("LEFT")) {
                if (Selector != 0) {
                    Selector--;
                } else
                    Selector = RUNE_TABLE_SIZE - 1;
            } else if (keycode == Keybindings.Hotkeys1.get("RIGHT")) {
                Selector = (Selector + 1) % 50;
            } else if (keycode == Keybindings.Hotkeys1.get("DOWN")) {
                Selector = (Selector + 10) % 50;
            } else if (keycode == Keybindings.Hotkeys1.get("SELECT")) {
                if (!RunesTable[Selector].isUsed() && castingTable.canAdd()) {
                    Rune temp = RunesTable[Selector];
                    RunesTable[Selector] = new Rune(Assets.getInstance().RuneBlank, 0, "Blank", temp.getX(), temp.getY(), true);
                    castingTable.add(temp);
                }
            } else if (keycode == Keybindings.Hotkeys1.get("RELASE")) {
                castingTable.cast();
            }
            else if (keycode == Keybindings.Hotkeys1.get("HELP")) {
                if(helpWindow==null || !helpWindow.isActive())
                {
                    helpWindow=new HelpWindow(this);
                    Window.create(helpWindow);
                }
                else
                {
                    helpWindow.close();
                }
            }

        }
        else if(id==1)
        {
            if (keycode == Keybindings.Hotkeys2.get("UP")) {
                if ((Selector - 10) < 0)
                    Selector += 40;
                else
                    Selector -= 10;
            } else if (keycode == Keybindings.Hotkeys2.get("LEFT")) {
                if (Selector != 0) {
                    Selector--;
                } else
                    Selector = RUNE_TABLE_SIZE - 1;
            } else if (keycode == Keybindings.Hotkeys2.get("RIGHT")) {
                Selector = (Selector + 1) % 50;
            } else if (keycode == Keybindings.Hotkeys2.get("DOWN")) {
                Selector = (Selector + 10) % 50;
            } else if (keycode == Keybindings.Hotkeys2.get("SELECT")) {
                if (!RunesTable[Selector].isUsed() && castingTable.canAdd()) {
                    Rune temp = RunesTable[Selector];
                    RunesTable[Selector] = new Rune(Assets.getInstance().RuneBlank, 0, "Blank", temp.getX(), temp.getY(), true);
                    castingTable.add(temp);
                }
            } else if (keycode == Keybindings.Hotkeys2.get("RELASE")) {
                castingTable.cast();
            }
            else if (keycode == Keybindings.Hotkeys2.get("HELP")) {
                if(helpWindow==null || !helpWindow.isActive())
                {
                    helpWindow=new HelpWindow(this);
                    Window.create(helpWindow);
                }
                else
                {
                    helpWindow.close();
                }

            }

        }

        return super.keyUp(keycode);
    }

    @Override
    public boolean keyTyped(char character) {
        return super.keyTyped(character);
    }
    public void setEnemy(Player e){enemy=e;}

    public Player(int gold,Vector2 Origin,int id)
    {
        this.id=id;
        if(Origin.x<700)
        castle=new Castle(this,1000,Origin.x,200);
        else
            castle=new Castle(this,1000,Origin.x+Assets.getInstance().Castle.findRegion("Castle0").getRegionWidth(),200);
        castingTable=new CastingTable(new Vector2(Origin.x,840),this);
        int random;
        String name;
        Sprite sprite;
        this.gold=gold;
        this.Origin=Origin;
        Selector=0;
        RunesTable=new Rune[RUNE_TABLE_SIZE];
        //repair table or rethink
        int j=0;
        int k=0;
        for(int i=0;i<RUNE_TABLE_SIZE;i++)
        {
            k++;
            if(i%10==0) {
                j++;
                k=0;
            }
                random=MathUtils.random(0,16);
                switch (random)
                {
                    case 0:
                        sprite=Assets.getInstance().RuneSword;
                        name="Sword";
                        break;
                    case 1:
                        sprite=Assets.getInstance().RuneArrow;
                        name="Arrow";
                        break;
                    case 2:
                        sprite=Assets.getInstance().RuneBeast;
                        name="Beast";
                        break;
                    case 3:
                        sprite=Assets.getInstance().RuneEarth;
                        name="Earth";
                        break;
                    case 4:
                        sprite=Assets.getInstance().RuneFire;
                        name="Fire";
                        break;
                    case 5:
                        sprite=Assets.getInstance().RuneLife;
                        name="Life";
                        break;
                    case 6:
                        sprite=Assets.getInstance().RuneLight;
                        name="Light";
                        break;
                    case 7:
                        sprite=Assets.getInstance().RuneLightning;
                        name="Lightning";
                        break;
                    case 8:
                        sprite=Assets.getInstance().RuneMiasma;
                        name="Miasma";
                        break;
                    case 9:
                        sprite=Assets.getInstance().RuneMoon;
                        name="Moon";
                        break;
                    case 10:
                        sprite=Assets.getInstance().RunePlant;
                        name="Plant";
                        break;
                    case 11:
                        sprite=Assets.getInstance().RuneProtection;
                        name="Protection";
                        break;
                    case 12:
                        sprite=Assets.getInstance().RuneShadow;
                        name="Shadow";
                        break;
                    case 13:
                        sprite=Assets.getInstance().RuneSky;
                        name="Sky";
                        break;
                    case 14:
                        sprite=Assets.getInstance().RuneSpell;
                        name="Spell";
                        break;
                    case 15:
                        sprite=Assets.getInstance().RuneSun;
                        name="Sun";
                        break;
                    case 16:
                        sprite=Assets.getInstance().RuneUnderground;
                        name="Underground";
                        break;
                    default:
                        sprite=Assets.getInstance().RuneBlank;
                        name="Blank";
                }
                RunesTable[i]=new Rune(sprite,random,name,Origin.x+k*40,Origin.y-j*40,false);
        }
        for(int i=0;i<Army.length;i++)
        {
            Army[i]=null;
        }
    }
    public void draw(SpriteBatch sb)
    {
        update(Gdx.graphics.getDeltaTime());
        for(int i=0;i<RunesTable.length;i++)
        RunesTable[i].draw(sb);
        for(int i=0;i<Army.length;i++)
        {
            if(Army[i]!=null && Army[i].isActive())
            {

                Army[i].draw(sb);
            }
        }
        sb.draw(Assets.getInstance().Selector, RunesTable[Selector].getX(), RunesTable[Selector].getY());
        Assets.getInstance().StandardFontSmall.draw(sb, RunesTable[Selector].getName(), Origin.x, 830);

        castingTable.draw(sb);
        castle.draw(sb);

        if(CurrentCombination!=null) {

            Assets.getInstance().StandardFontSmall.setColor(com.badlogic.gdx.graphics.Color.WHITE);
            Assets.getInstance().StandardFontSmall.draw(sb, CurrentCombination, Origin.x, 810);
            Assets.getInstance().StandardFontSmall.setColor(com.badlogic.gdx.graphics.Color.YELLOW);
        }
    }
    public void refill()
    {
        int id;
        for(int i=0;i<RunesTable.length;i++){
            if(RunesTable[i].isUsed()) {
               id=MathUtils.random(0,16);
                RunesTable[i] = new Rune(Rune.loadSprite(id),id,Rune.loadName(id),RunesTable[i].getX(),RunesTable[i].getY(),false);
            }
        }
    }
    public void refillChosen(int pickedRune)
    {
        int id;
        for(int i=0;i<RunesTable.length;i++)
        {
            if(RunesTable[i].id==pickedRune)
            {
                id=MathUtils.random(0,16);
                RunesTable[i]=new Rune(Rune.loadSprite(id),id,Rune.loadName(id),RunesTable[i].getX(),RunesTable[i].getY(),false);
            }
        }
    }
    public void fillWith(int id,int chanceOfFailure)
    {

        for(int i=0;i<RunesTable.length;i++){
            if(MathUtils.random(0,chanceOfFailure)!=0)
               RunesTable[i] = new Rune(Rune.loadSprite(-1),id,Rune.loadName(-1),RunesTable[i].getX(),RunesTable[i].getY(),true);
            else {
                PolygonEffect.create(new BendingRune(1,castle.getSpawnpoint(),1100,RunesTable[i].getX(),RunesTable[i].getY(),this));
                RunesTable[i] = new Rune(Rune.loadSprite(id), id, Rune.loadName(id), RunesTable[i].getX(), RunesTable[i].getY(), false);
            }
            }
    }
    public void lose()
    {
        System.out.println(whoAmI()+" lost!");
    }
    public Castle getCastle(){return castle;}
    public void dispose()
    {
        castingTable.dispose();
    }
    public String getCurrentCombination(){return CurrentCombination;}
    private void update(float deltaTime)
    {
        if(casting) {
            if (castingTimer > 0)
                castingTimer -= deltaTime;
            else {
                castingTimer = 0;
                casting = false;
            }
        }
    }
}
