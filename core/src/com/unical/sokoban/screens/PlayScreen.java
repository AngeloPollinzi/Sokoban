package com.unical.sokoban.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.Viewport;

import com.unical.sokoban.scenes.Hud;
import com.unical.sokoban.Sokoban;
import com.unical.sokoban.sprites.Player.Action;
import com.unical.sokoban.sprites.Player.Direction;

public class PlayScreen implements Screen {

	public Sokoban sokoban;
	private OrthographicCamera cam;
	private Viewport viewport;
	private Hud hud;
	private float elapsedTime;

	public PlayScreen(Sokoban sokoban) {
		this.sokoban = sokoban;
		cam = sokoban.cam;
		viewport = sokoban.viewport;
		cam.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);
		elapsedTime = 0f;
		hud = new Hud(sokoban);
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	private void update(float delta) {

		handleInput(delta);

		if (sokoban.getWorld().levelCompleted()) {
			elapsedTime += delta;
			if (elapsedTime >= 2f) {
				elapsedTime = 0f;
				Hud.levelNum++;
				
				if (Hud.levelNum > 5) {
					sokoban.getSounds().stop(1);
					sokoban.setScreen(new EndScreen(sokoban));
				}else {
					sokoban.nextLevel();
					sokoban.getWorld().reset(sokoban.map);
				}
			}
		}
		sokoban.getWorld().update(delta);
		hud.update();
		cam.update();
		sokoban.mapRenderer.setView(cam);

	}

	private void handleInput(float delta) {
		// TODO Auto-generated method stub
		if (Gdx.input.isKeyJustPressed(Input.Keys.W)) {
			sokoban.player.action = Action.RUNNING;
			sokoban.player.direction = Direction.UP;
		} else if (Gdx.input.isKeyJustPressed(Input.Keys.S)) {
			sokoban.player.action = Action.RUNNING;
			sokoban.player.direction = Direction.DOWN;
		} else if (Gdx.input.isKeyJustPressed(Input.Keys.D)) {
			sokoban.player.action = Action.RUNNING;
			sokoban.player.direction = Direction.RIGHT;
		} else if (Gdx.input.isKeyJustPressed(Input.Keys.A)) {
			sokoban.player.action = Action.RUNNING;
			sokoban.player.direction = Direction.LEFT;
		} else if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
			sokoban.getSolver().solve();
		} else
			sokoban.player.action = Action.STANDING;

	}

	@Override
	public void render(float delta) {

		update(delta);
		sokoban.batch.setProjectionMatrix(hud.getStage().getCamera().combined);
		sokoban.mapRenderer.render();

		hud.getStage().act();
		hud.getStage().draw();

		sokoban.batch.begin();

		for (int i = 0; i < sokoban.getWorld().targets.size(); i++) {
			sokoban.batch.draw(sokoban.getWorld().targets.get(i).getTexture(),
					sokoban.getWorld().targets.get(i).getX() + 16, sokoban.getWorld().targets.get(i).getY() + 16, 32,
					32);
		}
		for (int i = 0; i < sokoban.getWorld().boxes.size(); i++) {
			sokoban.batch.draw(sokoban.getWorld().boxes.get(i).getTexture(), sokoban.getWorld().boxes.get(i).getX(),
					sokoban.getWorld().boxes.get(i).getY(), 64, 64);
		}

		if (sokoban.player.action == Action.STANDING && sokoban.player.direction == Direction.UP)
			sokoban.batch.draw(sokoban.player.playerUp, sokoban.player.x + 10, sokoban.player.y, 42, 59);
		else if (sokoban.player.action == Action.STANDING && sokoban.player.direction == Direction.DOWN)
			sokoban.batch.draw(sokoban.player.playerDown, sokoban.player.x + 10, sokoban.player.y, 42, 59);
		else if (sokoban.player.action == Action.STANDING && sokoban.player.direction == Direction.RIGHT)
			sokoban.batch.draw(sokoban.player.playerRight, sokoban.player.x + 10, sokoban.player.y, 42, 59);
		else if (sokoban.player.action == Action.STANDING && sokoban.player.direction == Direction.LEFT)
			sokoban.batch.draw(sokoban.player.playerLeft, sokoban.player.x + 10, sokoban.player.y, 42, 59);

		if (sokoban.player.action == Action.RUNNING && sokoban.player.direction == Direction.UP)
			sokoban.batch.draw(sokoban.player.playerUp, sokoban.player.x + 10, sokoban.player.y, 42, 59);
		else if (sokoban.player.action == Action.RUNNING && sokoban.player.direction == Direction.DOWN)
			sokoban.batch.draw(sokoban.player.playerDown, sokoban.player.x + 10, sokoban.player.y, 42, 59);
		else if (sokoban.player.action == Action.RUNNING && sokoban.player.direction == Direction.RIGHT)
			sokoban.batch.draw(sokoban.player.playerRight, sokoban.player.x + 10, sokoban.player.y, 42, 59);
		else if (sokoban.player.action == Action.RUNNING && sokoban.player.direction == Direction.LEFT)
			sokoban.batch.draw(sokoban.player.playerLeft, sokoban.player.x + 10, sokoban.player.y, 42, 59);

		sokoban.batch.end();
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
	}

}
