package com.unical.sokoban.screens;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.Viewport;

import com.unical.sokoban.scenes.Hud;
import com.unical.sokoban.Sokoban;
import com.unical.sokoban.ai.MoveBox;
import com.unical.sokoban.sprites.Box;
import com.unical.sokoban.sprites.Player.Action;
import com.unical.sokoban.sprites.Player.Direction;

public class PlayScreen implements Screen {

	public Sokoban sokoban;
	private OrthographicCamera cam;
	private Viewport viewport;
	private Hud hud;
	private float elapsedTime;
	private ArrayList<MoveBox> movements;
	private int step = 0;
	private Texture up;
	private Texture down;
	private Texture right;
	private Texture left;
	private boolean solver = false;
	float destinationX = 0, destinationY = 0;

	public PlayScreen(Sokoban sokoban) {
		this.sokoban = sokoban;
		cam = sokoban.cam;
		viewport = sokoban.viewport;
		cam.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);
		elapsedTime = 0f;
		hud = new Hud(sokoban);
		movements = new ArrayList<MoveBox>();
		up = new Texture("up.png");
		down = new Texture("down.png");
		right = new Texture("right.png");
		left = new Texture("left.png");
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	private void update(float delta) {

		handleInput(delta);

		if (sokoban.getWorld().levelCompleted()) {
			solver = false;
			movements.clear();
			destinationX = 0;
			destinationY = 0;
			elapsedTime += delta;
			if (elapsedTime >= 2f) {
				elapsedTime = 0f;
				Hud.levelNum++;

				if (Hud.levelNum > 5) {
					sokoban.getSounds().stop(1);
					sokoban.setScreen(new EndScreen(sokoban));
				} else {
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
			solver = true;
			try {
				sokoban.getSolver().solve();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			movements = sokoban.getSolver().getMovements();
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

		if (solver) {
			MoveBox movement = nextMove(step);
			Box box = boxToMove(step);
			int dir = chooseDirection(box, movement);
			switch (dir) {
			case 0:
				sokoban.batch.draw(up, movement.getX() * 64, movement.getY() * 64, 64, 64);
				break;
			case 1:
				sokoban.batch.draw(down, movement.getX() * 64, movement.getY() * 64, 64, 64);
				break;
			case 2:
				sokoban.batch.draw(right, movement.getX() * 64, movement.getY() * 64, 64, 64);
				break;
			default:
				sokoban.batch.draw(left, movement.getX() * 64, movement.getY() * 64, 64, 64);
				break;
			}

			destinationX = movement.getX() * 64;
			destinationY = movement.getY() * 64;
			if (destinationX == box.getX() && destinationY == box.getY()) {
				step++;
			}

		}
		
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

	private int chooseDirection(Box b, MoveBox move) {
		int direction = 0;
		float bx = b.getX(), by = b.getY(), mx = move.getX() * 64, my = move.getY() * 64;

		if (bx == mx && my > by) {
			direction = 0;
		} else if (bx == mx && my < by) {
			direction = 1;
		} else if (bx < mx && my == by) {
			direction = 2;
		} else if (bx > mx && my == by) {
			direction = 3;
		}
		return direction;
	}

	private MoveBox nextMove(int step) {
		// TODO Auto-generated method stub
		MoveBox move = null;
		for (int i = 0; i < movements.size(); i++) {
			if (movements.get(i).getStep() == step)
				move = movements.get(i);
		}
		return move;
	}

	private Box boxToMove(int step) {
		// TODO Auto-generated method stub
		Box box = null;
		int id = 0;
		for (int i = 0; i < movements.size(); i++) {
			if (movements.get(i).getStep() == step)
				id = movements.get(i).getBoxId();
		}
		for (int i = 0; i < sokoban.getWorld().boxes.size(); i++) {
			if (sokoban.getWorld().boxes.get(i).getId() == id)
				box = sokoban.getWorld().boxes.get(i);
		}
		return box;
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
