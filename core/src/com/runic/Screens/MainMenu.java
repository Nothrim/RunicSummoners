package com.runic.Screens;


import android.view.Menu;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.runic.Assets;
import org.w3c.dom.Text;

/**
 * Created by Nothrim on 2015-07-30.
 */
public class MainMenu extends  Screen {
    public MainMenu(Game game) {
        super(game);
    }
    Stage stage;
    Table table;

    @Override
    public void show() {
        //menu Button Style

        stage=new Stage(new ScreenViewport());


        table=new Table(Assets.getInstance().MenuSkin);
        table.setFillParent(true);
        TextButton.TextButtonStyle MenuStyle=new TextButton.TextButtonStyle();
        MenuStyle.up=Assets.getInstance().MenuSkin.getDrawable("ButtonUp");
        MenuStyle.down=Assets.getInstance().MenuSkin.getDrawable("ButtonDown");
        MenuStyle.font=new BitmapFont(Gdx.files.internal("MenuFont.fnt"),false);
        TextButton button=new TextButton("START",MenuStyle);
        button.setName("0");
        table.add(button);
        table.row();
        button=new TextButton("OPTIONS", MenuStyle);
        button.setName("1");
        table.add(button);
        table.row();
        button=new TextButton("MULTIPLAYER",MenuStyle);
        button.setName("3");
        table.add(button);
        table.row();
        button=new TextButton("EXIT", MenuStyle);
        button.setName("2");
        table.add(button);
        table.row();
       // table.add(new TextField("", Assets.getInstance().MenuSkin));
        Gdx.input.setInputProcessor(stage);
        table.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                switch(Integer.parseInt(event.getTarget().getName()))
                {
                    case 0:
                        game.setScreen(new GameScreen(game));
                        break;
                    case 1:
                        game.setScreen(new OptionsScreen(game));
                        break;
                    case 2:
                        Gdx.app.exit();
                        break;
                    case 3:
                        game.setScreen(new NetMenu(game));
                        break;
                    default:
                        System.out.println("Invaild input");
                }
            }
        });
        stage.addActor(table);
        super.show();
    }

    @Override
    public void render(float delta) {

       stage.act(delta);
        stage.draw();
        super.render(delta);
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        stage.getViewport().update(width, height, true);

    }

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void resume() {
        super.resume();
    }

    @Override
    public void hide() {
        stage.dispose();
        super.hide();
    }

    @Override
    public void dispose() {

        stage.dispose();
        super.dispose();
    }


}
