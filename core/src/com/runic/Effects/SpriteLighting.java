package com.runic.Effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.compression.lzma.Base;
import com.runic.Assets;
import com.runic.Units.BaseUnit;

import javax.microedition.khronos.opengles.GL10;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Nothrim on 2015-09-25.
 */
public class SpriteLighting extends SpriteEffect{
    private class LightningLine
    {
        public static final float INITIAL_WIDTH_FACTOR=27f;
        private Vector2 start;
        private Vector2 direction;
        private Vector2 end;
        private float rotation;
        float width=16;
        private LightningLine(Vector2 start, Vector2 end){
            this.start=start;
            this.end=end;
            direction=end.sub(start);
            rotation = MathUtils.atan2(direction.y, direction.x);
        }
        public void draw( SpriteBatch sb)
        {
            sb.draw(Assets.getInstance().projectiles.findRegion("Lightning"), start.x, start.y, 8, 8, direction.len(), width, 1.2f, 1.1f, rotation * MathUtils.radiansToDegrees, false);

        }
        public void update(float shiftX)
        {
            start.x+=shiftX;
        }
        public void follow(Vector2 vertex){
            direction=vertex.sub(start);
            rotation=MathUtils.atan2(direction.y, direction.x);
        }
        public void setWidth(float value)
        {
            width=value;
        }
    }

    private Vector2 direction;
    private Vector2 start;
    private ArrayList<LightningLine> parts;
    private float lenght;
    private ArrayList<Float> points;


    //------calculating variables
    private Vector2 normal;
    private static final float sway=35;
    private static final float jaggednes=1/sway;
    //------------
    private BaseUnit target;

    public SpriteLighting(Vector2 begin,BaseUnit target, float life) {
        super(life);
        this.target=target;
        //-----------------------------------
        start=begin;
        direction=target.getHitbox().getCenter(Vector2.X).sub(start);
        normal=new Vector2(direction.y,-direction.x).nor();
        lenght=direction.len();

        //--------parts of lightning
        parts=new ArrayList<LightningLine>((int)(lenght/18));
        points=new ArrayList<Float>((int)(lenght/19));
        points.add(0f);
        Vector2 prevPoint=begin.cpy();
        float prevDisplacement=0;

        //------------------------------------------
        for(int i=0;i<lenght/20;i++)
        {
            points.add(MathUtils.random());

        }
        Collections.sort(points);
        for(int i=1;i<points.size();i++)
        {
            if(points.get(i)-points.get(i-1)<0.05f)
                points.remove(i);
        }
        for(int i=1;i<points.size();i++)
        {
            float scale = (lenght * jaggednes) * (points.get(i) - points.get(i-1));

        // defines an envelope. Points near the middle of the bolt can be further from the central line.
        float envelope = points.get(i) > 0.95f ? 20 * (1 - points.get(i)) : 1;

        float displacement = MathUtils.random(-sway, sway);
        displacement -= (displacement - prevDisplacement) * (1 - scale);
        displacement *= envelope;
            start=begin.cpy();
        Vector2 point=((start.mulAdd(normal,displacement)).mulAdd(direction,points.get(i)));
        parts.add(new LightningLine(prevPoint.cpy(), point.cpy()));
        prevPoint = point;
        prevDisplacement = displacement;
        }
        parts.add(new LightningLine(prevPoint.cpy(),new Vector2(target.getX(),target.getY())));
    }
    @Override
    protected void update(float deltaTime) {
        super.update(deltaTime);

        for(int i=0;i<parts.size();i++) {
            parts.get(i).setWidth((life-lifeTimer)*LightningLine.INITIAL_WIDTH_FACTOR);
        }
    }

    @Override
    protected void draw(float deltaTime, SpriteBatch sb) {
        super.draw(deltaTime, sb);
        Gdx.gl.glEnable(GL10.GL_BLEND);
        Gdx.gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
        Color c=sb.getColor();

        for(LightningLine l:parts)
        {
            sb.setColor(c.r,c.g,c.b,MathUtils.random(0.8f,1f));
            l.draw(sb);
        }
        sb.setColor(c);
        Gdx.gl.glDisable(GL10.GL_BLEND);
        //sb.draw(Assets.getInstance().projectiles.findRegion("Lightning"), start.x, start.y,8,8,lenght,16,1,1,rotation*MathUtils.radiansToDegrees,false);
    }
}
