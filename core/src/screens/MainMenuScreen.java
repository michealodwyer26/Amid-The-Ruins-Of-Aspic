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
		
	public MainMenuScreen(Platformer game) {
		this.game = game;
		game.livesRemaining = 3;
		
		viewport = new FitViewport(Platformer.V_WIDTH, Platformer.V_HEIGHT, new OrthographicCamera());
		stage = new Stage(viewport, game.batch);
		
		Table table = new Table();
		Table titleTable = new Table();
		
		titleTable.setFillParent(true);
		
		table.setFillParent(true);
		//table.setDebug(true);

				
		Label title = new Label("AMID THE RUINS OF ASPIC", new Label.LabelStyle(new BitmapFont(Gdx.files.internal("data/fonts/size15/15.fnt"), false), Color.WHITE));
		Label playLabel = new Label("1 - PLAY", new Label.LabelStyle(new BitmapFont(Gdx.files.internal("data/fonts/size15/15.fnt"), false), Color.WHITE));
		Label quitLabel = new Label("2 - QUIT", new Label.LabelStyle(new BitmapFont(Gdx.files.internal("data/fonts/size15/15.fnt"), false), Color.WHITE));
		
		titleTable.center().top();
		titleTable.add(title).expandX().pad(50);
		
		table.center();
		
		table.add(playLabel).expandX();
		table.row();
		
		table.add(quitLabel).expandX();
		table.row();
		
		stage.addActor(titleTable);
		stage.addActor(table);
	}
	
	@Override
	public void show() {
		
	}
	
	private void handleInput() {
		if(Gdx.input.isKeyPressed(Input.Keys.NUM_1)) {
			game.setScreen(new LivesRemainingScreen(game));
		} else if(Gdx.input.isKeyPressed(Input.Keys.NUM_2)) {
			Gdx.app.exit();
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
