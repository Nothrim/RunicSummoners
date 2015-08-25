package com.runic.Screens;

import android.provider.ContactsContract;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.runic.*;
import com.runic.Units.BaseUnit;

/**
 * Created by Nothrim on 2015-08-06.
 */
public class GameScreen extends Screen {
    public GameScreen(Game game) {
        super(game);
    }
    public static final int REFILL_TIME=60;
    SpriteBatch sb;
    Camera GameCamera;
    FitViewport GameView;
    Player p1;
    Player p2;
    TiledMap map;
    OrthogonalTiledMapRenderer mapRenderer;
    InputMultiplexer inputMultiplexer;
    double RefillTimer=0;
    @Override
    public void dispose() {
        map.dispose();
        mapRenderer.dispose();
        sb.dispose();
        super.dispose();
    }
    @Override
    public void show()
    {

        map= new TmxMapLoader().load("RunicSummonersMap.tmx");
        Unit.loadMap(map);
        BaseUnit.setCollisionLayer((TiledMapTileLayer)map.getLayers().get(0));
        mapRenderer=new OrthogonalTiledMapRenderer(map,1);

        sb=new SpriteBatch();
        Combinations.Load();
        GameCamera=new OrthographicCamera();
        p1=new Player(0,new Vector2(0,1080),0);
        p2=new Player(0,new Vector2(1520,1080),1);
        p1.setEnemy(p2);
        p2.setEnemy(p1);
        inputMultiplexer=new InputMultiplexer(p1,p2);
        Gdx.input.setInputProcessor(inputMultiplexer);
        GameView=new FitViewport(1920,1080,GameCamera);
        GameView.apply();

        super.show();
    }



    @Override
    public void render(float delta) {
        super.render(delta);
        mapRenderer.setView((OrthographicCamera) GameCamera);
        mapRenderer.render();
        if(RefillTimer<REFILL_TIME)
        {
            RefillTimer+=delta;
        }
        else
        {
            RefillTimer=0;
            p1.refill();
            p2.refill();
        }
        sb.setProjectionMatrix(GameView.getCamera().combined);
        sb.begin();
        Assets.getInstance().StandardFontSmall.draw(sb, Integer.toString((int) RefillTimer), 960, 1070);
        p1.draw(sb);
        p2.draw(sb);
        CombatText.update(delta);
        CombatText.draw(sb);
        sb.end();
    }

    @Override
    public void resize(int width, int height) {
        GameView.update(width, height, true);
        super.resize(width, height);
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
}
