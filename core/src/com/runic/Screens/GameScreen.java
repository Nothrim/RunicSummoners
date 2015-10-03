package com.runic.Screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.runic.*;
import com.runic.Effects.PolygonEffect;
import com.runic.Effects.SpriteEffect;
import com.runic.Particles.PremadeEffect;
import com.runic.Projectiles.BaseProjectile;
import com.runic.Units.BaseUnit;
import com.runic.Units.Dummy;
import com.runic.Units.Gore;
import com.runic.Window.Window;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Nothrim on 2015-08-06.
 */
public class GameScreen extends Screen {
    public GameScreen(Game game) {
        super(game);
    }
    public static final int REFILL_TIME=60;
    public static final int SUN_CHANGE_TIME=1;
    public static final int GAME_SCREEN_WIDTH=1920;
    public static final int GAME_SCREEN_HEIGHT=1080;
    public static final int STANDARD_BUFFER_SIZE=512;
    SpriteBatch sb;
    SpriteBatch ParticleRenderer;
    SpriteBatch windowRenderer;
    Camera GameCamera;
    FitViewport GameView;
    Player p1;
    Player p2;
    TiledMap map;
    OrthogonalTiledMapRenderer mapRenderer;
    InputMultiplexer inputMultiplexer;
    ShapeRenderer shapeRenderer;
    TiledLight sun;
    float sunTimer;
    //shaders
    FrameBuffer blurFramePass;
    ShaderProgram blurShader;
    ShaderProgram combatTextShader;
    ShaderProgram polygonEffectShader;
    PolygonSpriteBatch polygonSpriteBatch;
    //timers and stuff
    double RefillTimer=0;
    @Override
    public void dispose() {
        map.dispose();
        mapRenderer.dispose();
        sb.dispose();
        ParticleRenderer.dispose();
        windowRenderer.dispose();
        p1.dispose();
        p2.dispose();
        shapeRenderer.dispose();
        blurFramePass.dispose();
        blurShader.dispose();
        combatTextShader.dispose();
        polygonEffectShader.dispose();
        polygonSpriteBatch.dispose();
        super.dispose();
    }
    @Override
    public void show()
    {
        sunTimer=0;
        shapeRenderer=new ShapeRenderer();
        map= new TmxMapLoader().load("RunicSummonersMap.tmx");
        Unit.loadMap(map);
        BaseUnit.setCollisionLayer((TiledMapTileLayer) map.getLayers().get(0));
        Gore.initialize((TiledMapTileLayer) map.getLayers().get(0));
        sun=new TiledLight(800,1000,(TiledMapTileLayer)map.getLayers().get(1),(TiledMapTileLayer)map.getLayers().get(0),map.getTileSets(),8);
        mapRenderer=new OrthogonalTiledMapRenderer(map,1);
        sb=new SpriteBatch();
        ParticleRenderer=new SpriteBatch();
        windowRenderer=new SpriteBatch();
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
        sun.update(1920);
        blurFramePass =new FrameBuffer(Pixmap.Format.RGBA8888,STANDARD_BUFFER_SIZE,STANDARD_BUFFER_SIZE,false);

        blurShader =new ShaderProgram(Gdx.files.internal("Shaders/BlurVertex.glsl"),Gdx.files.internal("Shaders/BlurFragment.glsl"));
        combatTextShader=new ShaderProgram(Gdx.files.internal("Shaders/MovingVertex.glsl"),Gdx.files.internal("Shaders/PassthroughFragment.glsl"));
        polygonEffectShader=new ShaderProgram(Gdx.files.internal("Shaders/MovingVertexPolygon.glsl"),Gdx.files.internal("Shaders/PassthroughFragment.glsl"));

        if(!blurShader.isCompiled())
            System.out.println(blurShader.getLog());
        ShaderProgram.pedantic=false;
        BaseProjectile.initialize((TiledMapTileLayer)map.getLayers().get(0));
        Dummy.initialize();
        PolygonEffect.initialize();
        PremadeEffect.initialize();
        SpriteEffect.initialze(1000);
        Window.initialize(10);
        polygonSpriteBatch=new PolygonSpriteBatch(500);
        super.show();

    }


    public void update(float delta)
    {

        if(sunTimer<SUN_CHANGE_TIME)
        {
            sunTimer+=delta;
        }
        else
        {
            sunTimer=0;
            if(sun.getX()>GAME_SCREEN_WIDTH)
                sun.translate(-GAME_SCREEN_WIDTH,0);
            sun.translate(10,0);
            sun.update(GAME_SCREEN_WIDTH);
        }
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
        CombatText.update(delta);
        Dummy.upadte(delta);
    }
    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        if(p1.isCasting() || p2.isCasting()) {
            blurFramePass.begin();
            Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 0);
            Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
            Gdx.gl.glEnable(GL10.GL_BLEND);
            Gdx.gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(1, 1, 0, 0.5f);
            if(p1.isCasting())
                for(int i=1;i<7;i++)
                shapeRenderer.triangle(
                        p1.getCastle().getSpawnpoint()-100, GAME_SCREEN_HEIGHT-p1.getCastle().getY(),
                        p1.getCastle().getSpawnpoint() + 800+5*i, GAME_SCREEN_HEIGHT-p1.getCastle().getY()+100-p1.getCastingTimer()*i*5-100*i,
                        p1.getCastle().getSpawnpoint() + 800+5*i, GAME_SCREEN_HEIGHT-p1.getCastle().getY()+100+i-100*i
                );
            if(p2.isCasting())
            {
                for(int i=1;i<7;i++)
                shapeRenderer.triangle(
                        p2.getCastle().getSpawnpoint()+100, GAME_SCREEN_HEIGHT-p2.getCastle().getY(),
                        p2.getCastle().getSpawnpoint() - 800-5*i, GAME_SCREEN_HEIGHT-p2.getCastle().getY()+100-p2.getCastingTimer()*5*i-100*i,
                        p2.getCastle().getSpawnpoint() - 800-5*i, GAME_SCREEN_HEIGHT-p2.getCastle().getY()+100+i-100*i
                );
            }

            shapeRenderer.end();
            Gdx.gl.glDisable(GL10.GL_BLEND);
            blurFramePass.end();

        }

        sb.setProjectionMatrix(GameView.getCamera().combined);
        sb.begin();
        //maybe if i will need to use double buffer this will be helpfull, unused cause i need vertical blur only
//        if(p1.isCasting() || p2.isCasting()) {
//            blurFramePassY.begin();
//            sb.getProjectionMatrix().setToOrtho2D(0,STANDARD_BUFFER_SIZE,STANDARD_BUFFER_SIZE,-STANDARD_BUFFER_SIZE);
////            blurShader.setUniformf("dir", 1f, 0f);
//            sb.setShader(blurShader);
//            sb.draw(blurFramePass.getColorBufferTexture(), 0, 0);
//            sb.flush();
//            blurFramePassY.end();
////            blurShader.setUniformf("dir", 0f, 1f);
//
//        }


        sb.setShader(null);
        sb.draw(Assets.getInstance().background, 0, 0);
        if(p1.isCasting() || p2.isCasting()) {
            sb.setShader(blurShader);
            sb.draw(blurFramePass.getColorBufferTexture(), 0, 0, GAME_SCREEN_WIDTH, GAME_SCREEN_HEIGHT);
        }
        sb.setShader(combatTextShader);
        SpriteEffect.Draw(delta, sb);
        sb.setShader(null);
        Assets.getInstance().StandardFontSmall.draw(sb, Integer.toString((int) RefillTimer), 960, 1070);
        sb.end();
        mapRenderer.setView((OrthographicCamera) GameCamera);
        mapRenderer.render();
        sb.begin();
        p1.draw(sb);
        p2.draw(sb);

        sb.setShader(combatTextShader);
        CombatText.draw(sb);
        sb.setShader(null);
        Gore.Draw(sb);
        BaseProjectile.drawProjectiles(sb, delta);
        sb.end();

        ParticleRenderer.setProjectionMatrix(GameView.getCamera().combined);
        ParticleRenderer.begin();

        BaseUnit.drawParticles(ParticleRenderer, delta);
        PremadeEffect.drawParticles(ParticleRenderer, delta);

        ParticleRenderer.end();

        shapeRenderer.setProjectionMatrix(GameView.getCamera().combined);
        if(p1.getCurrentCombination()!=null || p2.getCurrentCombination() !=null) {
            Gdx.gl.glEnable(GL10.GL_BLEND);
            Gdx.gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

            shapeRenderer.setColor(PolygonEffect.blinkingColor.getValue(delta), 0.5f, 0, 0.3f);
            if(p1.getCurrentCombination()!=null) {

                shapeRenderer.rect(

                        p1.getCastingTable().getPosition().x,
                        p1.getCastingTable().getPosition().y,
                        40 * p1.getCastingTable().getCurrentPosition(),
                        40
                );
            }
            if(p2.getCurrentCombination()!=null)
            {
                shapeRenderer.rect(

                        p2.getCastingTable().getPosition().x,
                        p2.getCastingTable().getPosition().y-1,
                        40 * p2.getCastingTable().getCurrentPosition(),
                        40
                );
            }

            shapeRenderer.end();
            Gdx.gl.glDisable(GL10.GL_BLEND);
        }
        if(PolygonEffect.polygonEffects[0]!=null)
        {

            polygonSpriteBatch.setProjectionMatrix(GameView.getCamera().combined);
            polygonSpriteBatch.begin();
            polygonSpriteBatch.setShader(polygonEffectShader);
            PolygonEffect.Draw(polygonSpriteBatch, delta);
            polygonSpriteBatch.end();
        }
        if(Window.windows[0]!=null) {
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            Window.DrawBox(shapeRenderer);
            shapeRenderer.end();
            windowRenderer.setProjectionMatrix(GameView.getCamera().combined);
            windowRenderer.begin();
            Window.DrawContent(windowRenderer,delta);
            windowRenderer.end();
        }

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
