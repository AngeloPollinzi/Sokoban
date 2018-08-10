package com.unical.sokoban.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.Viewport;

import com.unical.sokoban.Sokoban;

public class Hud {

	private Stage stage;
	private Viewport viewport;
	private OrthographicCamera camera;
	private Label endPointsLabel;
	private Label levelLabel;
	private Label endPoints;
	private Label levels;
	private Label undoLabel;
	private Label resetLabel;
	private ImageButton undoButton;
	private ImageButton resetButton;
	public static Integer ends;
	public static Integer levelNum;

	public Hud(final Sokoban sokoban) {

		camera = sokoban.cam;
		viewport = sokoban.viewport;
		viewport.apply();

		camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
		camera.update();

		stage=new Stage(viewport, sokoban.batch);
		Gdx.input.setInputProcessor(getStage());
		Table table = new Table();
		table.top();
		table.setFillParent(true);
		ends=new Integer(3);
		levelNum=new Integer(1);
		endPoints = new Label(String.format("%01d", ends), new Label.LabelStyle(new BitmapFont(), Color.RED));
		levels = new Label(String.format("%01d", levelNum), new Label.LabelStyle(new BitmapFont(), Color.RED));
		levelLabel = new Label("LEVEL", new Label.LabelStyle(new BitmapFont(), Color.RED));
		endPointsLabel = new Label("END POINTS", new Label.LabelStyle(new BitmapFont(), Color.RED));
		undoLabel = new Label("", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		resetLabel = new Label("", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		levels.setFontScale((float) 1.5);
		levelLabel.setFontScale((float) 1.5);
		endPoints.setFontScale((float) 1.5);
		endPointsLabel.setFontScale((float) 1.5);
		undoLabel.setFontScale((float)0.5);
		Drawable drawable1 = new TextureRegionDrawable(new TextureRegion(new Texture("undo_icon.png")));
		Drawable drawable2= new TextureRegionDrawable(new TextureRegion(new Texture("reset-button.png")));
		undoButton=new ImageButton(drawable1);
		resetButton=new ImageButton(drawable2);
		table.add(resetLabel).expandX();
		table.add(undoLabel).expandX();
		table.add(levelLabel).expandX();
		table.add(endPointsLabel).expandX();
		table.row();
		table.add(resetButton).expandX().size(80).padTop(-35);
		table.add(undoButton).expandX().size(40).padTop(-35);
		table.add(levels).expandX().padTop(-10);
		table.add(endPoints).expandX().padTop(-10);
		
		undoButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				sokoban.getWorld().setUndo(true);
			}
		});
		
		resetButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				sokoban.resetLevel();
			}
		});
		
		getStage().addActor(table);
	}


	public void update() {
		endPoints.setText(String.format("%01d", ends));
		levels.setText(String.format("%01d", levelNum));
	}


	public Stage getStage() {
		return stage;
	}

}
