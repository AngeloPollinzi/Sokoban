package com.unical.sokoban.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.Viewport;

import com.unical.sokoban.Sokoban;

public class EndScreen implements Screen{

	private Sokoban sokoban;
	private Stage stage;
	private Viewport viewport;
	private OrthographicCamera camera;
	private TextureAtlas atlas;
	private Skin skin;
	private Texture background;
	
	public EndScreen(Sokoban sokoban) {
		this.sokoban = sokoban;
		background = new Texture("menuBackground.png");
		atlas = new TextureAtlas("lgdxs/skin/lgdxs-ui.atlas");
		skin = new Skin(Gdx.files.internal("lgdxs/skin/lgdxs-ui.json"), atlas);

		camera = sokoban.cam;
		viewport = sokoban.viewport;
		viewport.apply();

		camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
		camera.update();
		
		stage = new Stage(viewport, sokoban.batch);
		sokoban.getSounds().play(3);
	}

	@Override
	public void show() {
		// Stage should control input:
		Gdx.input.setInputProcessor(stage);

		// Create Table
		Table mainTable = new Table();
		// Set table to fill stage
		mainTable.setFillParent(true);
		
		// Set alignment of contents in the table.
		mainTable.center();

		// Create buttons
		TextButton exitButton = new TextButton("Exit", skin);
		// Add listeners to buttons
		exitButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Gdx.app.exit();
			}
		});

		// Add buttons to table
		mainTable.add(exitButton).padTop(150).size(200,50);
		// Add table to stage
		stage.addActor(mainTable);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(.1f, .12f, .16f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		sokoban.batch.begin();
		sokoban.batch.draw(background, 0, 0, Sokoban.WIDTH, Sokoban.HEIGHT);
		sokoban.batch.end();

		stage.act();
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		stage.dispose();
		atlas.dispose();
		background.dispose();
	}
}
