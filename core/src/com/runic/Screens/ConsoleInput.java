package com.runic.Screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Clipboard;
import com.runic.Assets;

/**
 * Created by Nothrim on 2015-10-04.
 */
public class ConsoleInput implements InputProcessor {
    Rectangle field;
    Rectangle selector;
    Rectangle inside;
    Color fieldColor;
    StringBuilder stringBuilder;
    boolean backspace;
    boolean constantDeletion=false;
    private float constantDeletionTimer=0;
    private int charactersInLine;
    GlyphLayout text;
    private String label;
    protected boolean selected=false;
    private float screenHeight;
    public ConsoleInput(float x, float y, int width, int height, Color background, String label){
        field=new Rectangle(x,y,width,height);
        inside=new Rectangle(x+5,y,width-10,height-5);
        selector=new Rectangle(x+15,y+field.height-23,15,15);
        stringBuilder=new StringBuilder();
        fieldColor=background;
        charactersInLine=(int)(field.width/10);
        text=new GlyphLayout();
        this.label=label;
        screenHeight=Gdx.graphics.getHeight();
    }
    public void setScreenHeight(float height)
    {
        screenHeight=height;
    }
    public void drawRectangle(ShapeRenderer sr)
    {
        update();
        if(selected)
            sr.rect(field.x,field.y,field.width, field.height, fieldColor,Color.WHITE,fieldColor,Color.WHITE);
        else
            sr.rect(field.x,field.y,field.width,field.height,Color.DARK_GRAY,Color.BLACK,Color.DARK_GRAY,Color.GRAY);


            sr.rect(inside.x,inside.y,inside.width,inside.height,fieldColor,fieldColor,fieldColor,fieldColor);
        if(selected)
        sr.rect(selector.x,selector.y,selector.width,selector.height,Color.BLACK,Color.BLACK,Color.BLACK,Color.BLACK);
    }
    public void drawText(SpriteBatch sb)
    {
        Assets.getInstance().StandardFontSmall.draw(sb, label, field.getX(), field.getY() + field.getHeight() + 15);
        text.setText(Assets.getInstance().NormalFont, stringBuilder);
        selector.x=field.x+15+text.width;
        Assets.getInstance().NormalFont.draw(sb, text, field.getX() + 15, field.getY() + field.height - 10);


    }
    public String getContent(){return stringBuilder.toString();}
    public void update()
    {
        if(constantDeletion && stringBuilder.length()>0)
        {
            if(constantDeletionTimer>0.2f) {
                backspace = true;
                selector.x -= 7;
                stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            }
            else
                constantDeletionTimer+= Gdx.graphics.getDeltaTime();
        }
    }

    @Override
    public boolean keyDown(int keycode) {
        if(selected) {

            if(Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT) && keycode== Input.Keys.V)
            {
                if(Gdx.app.getClipboard().getContents().length()<charactersInLine-stringBuilder.length())
                stringBuilder.append(Gdx.app.getClipboard().getContents());
                return true;
            }
            backspace = false;
            if (keycode == com.badlogic.gdx.Input.Keys.BACKSPACE) {
                if (stringBuilder.length() > 0) {
                    backspace = true;
                    selector.x -= 7;
                    stringBuilder.deleteCharAt(stringBuilder.length() - 1);
                }
                constantDeletion = true;
            }
        }


        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if(selected) {
            if (keycode == com.badlogic.gdx.Input.Keys.BACKSPACE) {
                constantDeletion = false;
                constantDeletionTimer = 0;
            }
        }
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        if(selected) {
            if (!backspace) {
                if (stringBuilder.length() < charactersInLine) {

                    if (Character.isLetter(character) || Character.isDigit(character) || character == '.') {
                            stringBuilder.append(character);
                        }
                    }
                }
            }
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        if(inside.contains(screenX,screenHeight-screenY))
            selected=!selected;
        else
            selected=false;
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
    public void setLabel(String label){this.label=label;}
}
