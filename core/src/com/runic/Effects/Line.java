package com.runic.Effects;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Nothrim on 2015-09-24.
 */
public class Line {
    //if i need to draw lines
//    static Line[] lines=new Line[500];
//    public static int create(Vector2 begin,Vector2 end,float life)
//    {
//        for(int i=0;i< lines.length;i++) {
//          if(lines[i]==null || !lines[i].active)
//          {
//              lines[i]=new Line(begin,end,life);
//              return i;
//          }
//        }
//     return -1;
//    }
//    public static void Draw(float deltaTime,ShapeRenderer shapeRenderer)
//    {
//        for(int i=0;i<lines.length;i++)
//        {
//            if(lines[i]!=null && lines[i].active)
//            {
//                lines[i].draw(deltaTime,shapeRenderer);
//            }
//        }
//    }
//
//    private boolean active;
//    private float life;
//    private Vector2 begin;
//    private Vector2 end;
//    private Line(Vector2 begin,Vector2 end,float life)
//    {
//        this.begin=begin;
//        this.end=end;
//        this.life=life;
//        active=true;
//    }
//    private void update(float deltaTime)
//    {
//        life-=deltaTime;
//        if(life<0)
//            active=false;
//    }
//    private void draw(float deltaTime,ShapeRenderer shapeRenderer)
//    {
//        update(deltaTime);
//        shapeRenderer.line(begin,end);
//    }

}
