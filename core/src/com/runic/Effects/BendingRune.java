package com.runic.Effects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.runic.Assets;
import com.runic.Player;
import com.runic.Rune;
import com.runic.World;

/**
 * Created by Nothrim on 2015-09-15.
 */
public class BendingRune extends PolygonEffect {
    public static final float UPDATE_TEMPO=780;
    public static final float[] BENDING_RUNE_VERTICES={0,0  ,42,0  ,42,21  ,42,42  ,0,42  ,0,21};
    public static final short[] BENDING_RUNE_TRIANGLES={0,1,2  ,0,2,5  ,5,2,3 ,5,3,4};
    private static final float SEARCHING_BOUNDRY=5f;
    private float x;
    private float y;
    private float directionX;
    private float directionY;
    private float boundry;
    private float boundry2;
    private int direction;
    private float target0;
    private float target1;
    private boolean finishWhenInPlace;


    public BendingRune(float effectLength,float x,float y,float directionX,float directionY,Player owner)
    {
        super(effectLength,owner);
        float vertices[]=new float[BENDING_RUNE_VERTICES.length];
        for(int i=0;i<BENDING_RUNE_VERTICES.length;i++)
            vertices[i]=BENDING_RUNE_VERTICES[i];
        short triangles[]=new short[BENDING_RUNE_TRIANGLES.length];
        for(int i=0;i<BENDING_RUNE_TRIANGLES.length;i++)
            triangles[i]=BENDING_RUNE_TRIANGLES[i];
        polygonRegion=new PolygonRegion(Assets.getInstance().atlas.findRegion("RuneBlank"),vertices,triangles);
        this.x=x;
        this.y=y;
        this.directionX=directionX;
        this.directionY=directionY;
        polygonRegion.getVertices()[0]-=x-directionX;
        polygonRegion.getVertices()[1]-=y-directionY;
        polygonRegion.getVertices()[2]-=x-directionX;
        polygonRegion.getVertices()[3]-=y-directionY;
        polygonRegion.getVertices()[5]-=(y-directionY)/2;
        polygonRegion.getVertices()[11]-=(y-directionY)/2;
        polygonRegion.getVertices()[4]-=x-directionX;
        polygonRegion.getVertices()[10]-=x-directionX;
        boundry=directionY-y+40;
        boundry2=boundry-20;
        target0=polygonRegion.getVertices()[6]-(x-directionX);
        target1=polygonRegion.getVertices()[8]-(x-directionX);
        finishWhenInPlace=false;
    }
    public BendingRune(int runeID,float effectLength,float x,float y,float directionX,float directionY,Player owner)
    {
        super(effectLength,owner);
        float vertices[]=new float[BENDING_RUNE_VERTICES.length];
        for(int i=0;i<BENDING_RUNE_VERTICES.length;i++)
            vertices[i]=BENDING_RUNE_VERTICES[i];
        short triangles[]=new short[BENDING_RUNE_TRIANGLES.length];
        for(int i=0;i<BENDING_RUNE_TRIANGLES.length;i++)
            triangles[i]=BENDING_RUNE_TRIANGLES[i];

        polygonRegion=new PolygonRegion(Rune.getTextureRegion(runeID),vertices,triangles);
        this.owner=owner;
        this.x=x;
        this.y=y;
        this.directionX=directionX;
        this.directionY=directionY;
        polygonRegion.getVertices()[0]-=x-directionX;
        polygonRegion.getVertices()[1]-=y-directionY;
        polygonRegion.getVertices()[2]-=x-directionX;
        polygonRegion.getVertices()[3]-=y-directionY;
        polygonRegion.getVertices()[5]-=(y-directionY)/2;
        polygonRegion.getVertices()[11]-=(y-directionY)/2;
        polygonRegion.getVertices()[4]-=x-directionX;
        polygonRegion.getVertices()[10]-=x-directionX;
        boundry=directionY-y+40;
        boundry2=boundry-20;
        target0=polygonRegion.getVertices()[6]-(x-directionX-2);
        target1=polygonRegion.getVertices()[8]-(x-directionX+2);
        finishWhenInPlace=false;
    }
    public BendingRune(float effectLength,float x,float y,float directionX,float directionY,boolean finishWhenInPlace,Player owner)
    {
        super(effectLength,owner);
        float vertices[]=new float[BENDING_RUNE_VERTICES.length];
        for(int i=0;i<BENDING_RUNE_VERTICES.length;i++)
            vertices[i]=BENDING_RUNE_VERTICES[i];
        short triangles[]=new short[BENDING_RUNE_TRIANGLES.length];
        for(int i=0;i<BENDING_RUNE_TRIANGLES.length;i++)
            triangles[i]=BENDING_RUNE_TRIANGLES[i];
        polygonRegion=new PolygonRegion(Assets.getInstance().atlas.findRegion("RuneBlank"),vertices,triangles);
        this.x=x;
        this.y=y;
        this.directionX=directionX;
        this.directionY=directionY;
        polygonRegion.getVertices()[0]-=x-directionX;
        polygonRegion.getVertices()[1]-=y-directionY;
        polygonRegion.getVertices()[2]-=x-directionX;
        polygonRegion.getVertices()[3]-=y-directionY;
        polygonRegion.getVertices()[5]-=(y-directionY)/2;
        polygonRegion.getVertices()[11]-=(y-directionY)/2;
        polygonRegion.getVertices()[4]-=x-directionX;
        polygonRegion.getVertices()[10]-=x-directionX;
        boundry=directionY-y+42;
        boundry2=boundry-21;
        target0=polygonRegion.getVertices()[6]-(x-directionX);
        target1=polygonRegion.getVertices()[8]-(x-directionX);
        this.finishWhenInPlace=finishWhenInPlace;
        this.owner=owner;
    }

    @Override
    protected void update(float deltaTime) {
        super.update(deltaTime);
        if(life<length*0.8) {
            direction = life < (length *0.2) ? 1 : -1;
            direction *= life < (length * 0.5f) ? 1 : -1;
            polygonRegion.getVertices()[4] += (deltaTime * UPDATE_TEMPO / 10) * direction;
            polygonRegion.getVertices()[10] -= (deltaTime * UPDATE_TEMPO / 10) * direction;
        }
        else
        {
            polygonRegion.getVertices()[4] =target0;
            polygonRegion.getVertices()[10]=target1;
        }
        if(polygonRegion.getVertices()[9]>boundry) {

            polygonRegion.getVertices()[7] -= deltaTime * UPDATE_TEMPO;
            polygonRegion.getVertices()[9] -= deltaTime * UPDATE_TEMPO;

        }

        else if(finishWhenInPlace && life<length)
            life=length;
        else
        {
            polygonRegion.getVertices()[7] = boundry;
            polygonRegion.getVertices()[9] = boundry;
        }
        if(polygonRegion.getVertices()[5]>boundry2)
        {
            polygonRegion.getVertices()[5] -= deltaTime * UPDATE_TEMPO;
            polygonRegion.getVertices()[11] -= deltaTime * UPDATE_TEMPO;
        }
        if(Math.abs(target0 - polygonRegion.getVertices()[6]) >SEARCHING_BOUNDRY && target0>polygonRegion.getVertices()[6]  )
        {
            polygonRegion.getVertices()[6]+=Math.max(deltaTime *UPDATE_TEMPO,Math.abs(target0 - polygonRegion.getVertices()[6])*deltaTime*10);
        }
        else if(Math.abs(target0 - polygonRegion.getVertices()[6])>SEARCHING_BOUNDRY && target0<polygonRegion.getVertices()[6] )
        {
            polygonRegion.getVertices()[6]-=Math.max(deltaTime *UPDATE_TEMPO,Math.abs(target0 - polygonRegion.getVertices()[6])*deltaTime*10);
        }

        if(Math.abs(target1 - polygonRegion.getVertices()[8])>SEARCHING_BOUNDRY && target1>polygonRegion.getVertices()[8])
        {
            polygonRegion.getVertices()[8]+=Math.max(deltaTime *UPDATE_TEMPO,Math.abs(target1 - polygonRegion.getVertices()[8])*deltaTime*10) ;
        }
        else if(Math.abs(target1 - polygonRegion.getVertices()[8])>SEARCHING_BOUNDRY && target1<polygonRegion.getVertices()[8])
        {
            polygonRegion.getVertices()[8]-=Math.max(deltaTime *UPDATE_TEMPO,Math.abs(target1 - polygonRegion.getVertices()[8])*deltaTime*10) ;
        }



    }

    @Override
    public void draw(PolygonSpriteBatch sb, float deltaTime) {
        super.draw(sb, deltaTime);
        Color old=sb.getColor();
        if(owner.getCurrentCombination()!=null)
            sb.setColor(World.getInstance().blinkingColor.getValue(deltaTime),0.5f,0f,0.3f);
        sb.draw(polygonRegion,x,y);

        sb.setColor(old);

    }

    @Override
    public void onKill() {
        super.onKill();
    }
}
