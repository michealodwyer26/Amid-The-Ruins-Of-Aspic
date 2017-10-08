package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import amid.the.ruins.of.aspic.Platformer;

public class MainMenuScreen implements Screen {

	private Platformer game;
	private Viewport viewport;
	private Stage stage;
	Label text;
		
	public MainMenuScreen(Platformer game) {
		this.game = game;
		
		viewport = new FitViewport(Platformer.V_WIDTH, Platformer.V_HEIGHT, new OrthographicCamera());
		stage = new Stage(viewport, game.batch);
		
		Table table = new Table();
		table.center();
		table.setFillParent(true);
		
		text = new Label("PRESS ENTER", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		
		table.add(text).expandX();
		
		stage.addActor(table);
	}
	
	@Override
	public void show() {
		
	}
	
	private void handleInput() {
		if(Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
			game.setScreen(new GameScreen(game));
		}
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		handleInput();
		
		game.batch.setProjectionMatrix(stage.getCamera().combined);
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void hide() {
		
	}

	@Override
	public void dispose() {
		
	}

}