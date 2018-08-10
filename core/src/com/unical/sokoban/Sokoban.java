package com.unical.sokoban;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.unical.sokoban.ai.Solver;
import com.unical.sokoban.logic.World;
import com.unical.sokoban.screens.StartScreen;
import com.unical.sokoban.sounds.SoundManager;
import com.unical.sokoban.sprites.Player;


public class Sokoban extends Game {
	
	public OrthographicCamera cam;
	public Viewport viewport;
	public SpriteBatch batch;
	public static int WIDTH = 896;
	public static int HEIGHT = 768;
	public Player player;
	public OrthogonalTiledMapRenderer mapRenderer;
	private World world;
	private Solver solver;
	private TmxMapLoader mapLoader;
	private SoundManager sounds;
	public TiledMap map;
	private int level=0;
	
	@Override
	public void create() {
		batch = new SpriteBatch();
		cam = new OrthographicCamera();
		viewport = new StretchViewport(WIDTH, HEIGHT, cam);
		mapLoader = new TmxMapLoader();
		nextLevel();
		player = new Player();
		world = new World(map, player);
		solver=new Solver(world);
		sounds=new SoundManager();
		setScreen(new StartScreen(this));
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void dispose() {
		batch.dispose();
		map.dispose();
		mapRenderer.dispose();
		sounds.dispose();
	}
	
	public World getWorld() {
		return world;
	}
	
	public void nextLevel() {
		level++;
		map=mapLoader.load("level"+level+".tmx");
		mapRenderer = new OrthogonalTiledMapRenderer(map);
	}
	
	public void resetLevel() {
		map=mapLoader.load("level"+level+".tmx");
		mapRenderer = new OrthogonalTiledMapRenderer(map);
		world.reset(map);
	}

	public SoundManager getSounds() {
		return sounds;
	}

	public void setSounds(SoundManager sounds) {
		this.sounds = sounds;
	}

	public Solver getSolver() {
		return solver;
	}

}
