package com.runic.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.runic.Assets;
import com.runic.Keybindings;
import org.w3c.dom.ls.LSException;

import java.security.Key;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Nothrim on 2015-08-02.
 */
public class OptionsScreen extends  Screen {
    public OptionsScreen(Game game) {
        super(game);
    }
    Stage stage;
    boolean PlayerFlag=true;
    @Override
    public void show() {
        stage=new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        Table table=new Table();
        table.setFillParent(true);
        Label.LabelStyle LStyle=new Label.LabelStyle(new BitmapFont(Gdx.files.internal("StandardFont.fnt")), com.badlogic.gdx.graphics.Color.BLACK.WHITE);
        String player;
        if(PlayerFlag)
        player="Player 1";
        else
        player="Player 2";
        table.add(new WhyImSoLazyLabel(player, LStyle));
        table.row();
        ImageTextButton.ImageTextButtonStyle BStyle=new ImageTextButton.ImageTextButtonStyle(Assets.getInstance().BSkin.getDrawable("OptionsBacground"),Assets.getInstance().BSkin.getDrawable("OptionsBacgroundClicked"),Assets.getInstance().BSkin.getDrawable("OptionsSelection"),LStyle.font);
        for (Map.Entry<String,Integer> kvp : Keybindings.Hotkeys1.entrySet())
        {
            table.add(new Label(kvp.getKey(),LStyle)).width(50).height(30);
            ConfigEntry c=new ConfigEntry(kvp.getValue(),BStyle);
            c.addCaptureListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    ConfigEntry c=(ConfigEntry)actor;

                        if(c.selected==null)
                            c.selected=c;
                        else if(c.selected!=c)
                        {
                            c.selected.setChecked(false);
                            c.selected=c;
                        }
                        else
                        {
                            c.selected=null;
                        }
                }
            });
            table.add(c).width(150).height(30).padLeft(150);
            table.row();
        }
        stage.addActor(table);
        stage.addListener(new InputListener() {


            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (ConfigEntry.selected!=null)
                {
                    ConfigEntry.selected.setKeycode(keycode);
                    ConfigEntry.selected.setText(Input.Keys.toString(keycode));
                }
                return super.keyDown(event, keycode);
            }
        });
        TextureAtlas atlas=new TextureAtlas("Menu.pack");
        Skin MenuSkin=new Skin(atlas);
        TextButton.TextButtonStyle MenuStyle=new TextButton.TextButtonStyle();
        MenuStyle.up=MenuSkin.getDrawable("ButtonUp");
        MenuStyle.down=MenuSkin.getDrawable("ButtonDown");
        MenuStyle.font=new BitmapFont(Gdx.files.internal("MenuFont.fnt"),false);;
        TextButton button=new TextButton("SAVE",MenuStyle);
        button.setName("0");
        TextButton button2=new TextButton("BACK",MenuStyle);
        button2.setName("1");
        table.row();
        TextButton button3=new TextButton("SWITCH",MenuStyle);
        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ArrayList<String>Keys=new ArrayList<String>();
                ArrayList<Integer>Values=new ArrayList<Integer>();
                for(Cell<Actor> a : table.getCells())
                {
                 if(a.getActor().getClass()==Label.class)
                 {
                     Label l=(Label)a.getActor();
                     Keys.add(l.getText().toString());
                 }
                    if(a.getActor().getClass()==ConfigEntry.class)
                    {
                        ConfigEntry c=(ConfigEntry)a.getActor();
                        Values.add(c.getKeycode());
                    }
                }
                for(int i=0;i<Math.min(Keys.size(),Values.size());i++)
                {
                    if(PlayerFlag)
                   Keybindings.Hotkeys1.put(Keys.get(i),Values.get(i));
                    else
                        Keybindings.Hotkeys2.put(Keys.get(i),Values.get(i));

                }
                Keybindings.save();
            }
        });
        button2.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new MainMenu(game));
            }
        });
        button3.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                PlayerFlag=!PlayerFlag;
                Label label=(Label)table.getCells().get(0).getActor();
                if(PlayerFlag) {
                    label.setText("Player 1");
                    String key=null;
                    for(Cell<Actor> a : table.getCells())
                    {

                        if(a.getActor().getClass()==Label.class){
                            Label temp=(Label)a.getActor();
                            key=temp.getText().toString();
                        }
                        if(a.getActor().getClass()==ConfigEntry.class && key!=null && Keybindings.Hotkeys1.containsKey(key))
                        {
                            ConfigEntry temp=(ConfigEntry)a.getActor();
                            temp.setKeycode(Keybindings.Hotkeys1.get(key));
                            temp.setText(Input.Keys.toString(temp.getKeycode()));
                        }
                    }
                }
                else {
                    label.setText("Player 2");
                    String key=null;
                    for(Cell<Actor> a : table.getCells())
                    {

                        if(a.getActor().getClass()==Label.class){
                            Label temp=(Label)a.getActor();
                            key=temp.getText().toString();
                        }
                        if(a.getActor().getClass()==ConfigEntry.class && key!=null && Keybindings.Hotkeys2.containsKey(key))
                        {
                            ConfigEntry temp=(ConfigEntry)a.getActor();
                            temp.setKeycode(Keybindings.Hotkeys2.get(key));
                            temp.setText(Input.Keys.toString(temp.getKeycode()));
                        }
                    }
                }

            }
        });
        table.add(button).width(100).height(30);
        table.add(button3).width(100).height(30);
        table.add(button2).width(100).height(30);
        super.show();
    }


    @Override
    public void render(float delta) {
        stage.act();
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
        super.hide();
    }

    @Override
    public void dispose() {

        stage.dispose();
        super.dispose();
    }
}
