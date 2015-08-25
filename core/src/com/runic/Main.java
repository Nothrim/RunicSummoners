package com.runic;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.runic.Screens.MainMenu;
import com.uwsoft.editor.renderer.Overlap2D;

public class Main extends Game {

	
	@Override
	public void create () {
		Keybindings.initialize();
		setScreen(new MainMenu(this));

	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0.2f, 0.3f, 0.5f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		screen.render(Gdx.graphics.getDeltaTime());
	}
}
