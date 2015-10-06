package com.runic.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.runic.Assets;
import com.runic.Network.NetworkManager;

import java.util.regex.Pattern;

/**
 * Created by Nothrim on 2015-10-04.
 */
public class NetMenu extends Screen {
    private static final Pattern PATTERN = Pattern.compile(
            "^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$");
    Stage stage;
    Table table;
    IpInput ipInput;
    ConsoleInput portInput;
    SpriteBatch sb;
    ShapeRenderer sr;
    InputMultiplexer inputMultiplexer;
    public NetMenu(Game game) {
        super(game);
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        stage.getViewport().update(width, height, true);
        ipInput.setScreenHeight(height);
        portInput.setScreenHeight(height);

    }



    @Override
    public void render(float delta) {
        super.render(delta);
        stage.act(delta);
        stage.draw();
        sr.begin(ShapeRenderer.ShapeType.Filled);
        ipInput.drawRectangle(sr);
        portInput.drawRectangle(sr);
        sr.end();
        sb.begin();
        ipInput.drawText(sb);
        portInput.drawText(sb);
        sb.end();
    }

    @Override
    public void show() {
        super.show();
        sb=new SpriteBatch();
        sr=new ShapeRenderer();
        sr.setAutoShapeType(true);
        stage=new Stage(new ScreenViewport());
        table=new Table(Assets.getInstance().MenuSkin);
        table.setFillParent(true);
        TextButton.TextButtonStyle MenuStyle=new TextButton.TextButtonStyle();
        MenuStyle.up=Assets.getInstance().MenuSkin.getDrawable("ButtonUp");
        MenuStyle.down=Assets.getInstance().MenuSkin.getDrawable("ButtonDown");
        MenuStyle.font=new BitmapFont(Gdx.files.internal("MenuFont.fnt"),false);
        MenuStyle.up=Assets.getInstance().MenuSkin.getDrawable("ButtonUp");
        MenuStyle.down=Assets.getInstance().MenuSkin.getDrawable("ButtonDown");
        MenuStyle.font=new BitmapFont(Gdx.files.internal("MenuFont.fnt"),false);
        TextButton button=new TextButton("JOIN",MenuStyle);
        button.setName("0");
        table.add(button);
        table.row();
        button=new TextButton("HOST",MenuStyle);
        button.setName("1");
        table.add(button);
        table.row();
        button=new TextButton("BACK",MenuStyle);
        button.setName("2");
        table.add(button);
        table.row();

        stage.addActor(table);
        ipInput =new IpInput(Gdx.graphics.getWidth()/4-25,0,Gdx.graphics.getWidth()/4,Gdx.graphics.getHeight()/12,Color.GRAY,"IP");
        portInput=new ConsoleInput(25+Gdx.graphics.getWidth()/4+ipInput.field.width,0,Gdx.graphics.getWidth()/4,Gdx.graphics.getHeight()/12,Color.GRAY,"PORT");
        inputMultiplexer=new InputMultiplexer(ipInput,portInput,stage);
        Gdx.input.setInputProcessor(inputMultiplexer);
        table.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                switch (Integer.parseInt(actor.getName()))
                {
                    case 0:
                        if(validateIp(ipInput.getContent()))
                        NetworkManager.getInstance().connectToServer(ipInput.getContent(),portInput.getContent());
                        break;
                    case 1:
                        break;
                    case 2:
                        game.setScreen(new MainMenu(game));
                        break;
                    default:
                        System.out.println("Invaild input");
                }
            }
        });
    }

    @Override
    public void dispose() {
        super.dispose();
        stage.dispose();
        sb.dispose();
        sr.dispose();
    }
    private boolean validateIp(String ip)
    {
        return PATTERN.matcher(ip).matches();
    }
}
