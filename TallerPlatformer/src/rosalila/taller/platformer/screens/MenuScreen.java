package rosalila.taller.platformer.screens;

import rosalila.taller.platformer.LitterIvis;
import rosalila.taller.platformer.listener.InputDYAListener;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class MenuScreen extends AbstractScreen {

	public MenuScreen(LitterIvis game) {
		super(game);
	}

	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Texture texture;
	private Sprite sprite;
	private Stage stg;
	public static Image btn1, btn2, btn3, btn4, btn5, intro1, intro2, intro3, intro4, intro5;
	private Texture button1, button2, button3, button4, button5;
	private TextureRegion button1Txt, button2Txt, button3Txt, button4Txt,button5Txt;
	private TextureRegion intro1T, intro2T, intro3T, intro4T, intro5T;
	
	@Override
	public void show() {		
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		
		camera = new OrthographicCamera(1, h/w);
		batch = new SpriteBatch();
		Texture txt = new Texture("background.png");
		txt.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		TextureRegion txtr = new TextureRegion(txt, 512, 275);
		stg = new Stage();
		Image img = new Image(txtr);
		stg.addActor(img);
		img.setFillParent(true);
		
		intro1T = new TextureRegion(new Texture("intro1.png"), 512,275);
		intro2T = new TextureRegion(new Texture("intro2.png"), 512,275);
		intro3T = new TextureRegion(new Texture("intro3.png"), 512,275);
		intro4T = new TextureRegion(new Texture("intro4.png"), 512,275);
		intro5T = new TextureRegion(new Texture("intro5a.png"), 512,275);
		
		intro1 = new Image(intro1T);
		intro2 = new Image(intro2T);
		intro3 = new Image(intro3T);
		intro4 = new Image(intro4T);
		intro5 = new Image(intro5T);
		
		intro1.setVisible(false);
		intro2.setVisible(false);
		intro3.setVisible(false);
		intro4.setVisible(false);
		intro5.setVisible(false);
		
		stg.addActor(intro1);
		stg.addActor(intro2);
		stg.addActor(intro3);
		stg.addActor(intro4);
		stg.addActor(intro5);
		
		int heit = 80;
		button1 = new Texture("1.png");
		button2 = new Texture("2.png");
		button3 = new Texture("3.png");
		button4 = new Texture("4.png");
		button5 = new Texture("5.png");
		
		button1.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		button2.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		button3.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		button4.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		button5.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		button1Txt = new TextureRegion(button1, 86, 73);
		button2Txt = new TextureRegion(button2, 86, 73);
		button3Txt = new TextureRegion(button3, 86, 73);
		button4Txt = new TextureRegion(button4, 86, 73);
		button5Txt = new TextureRegion(button5, 86, 73);
		
		btn1 = new Image(button1);
		btn2 = new Image(button2);
		btn3 = new Image(button3);
		btn4 = new Image(button4);
		btn5 = new Image(button5);
		
		
		btn1.setPosition(5, heit);
		stg.addActor(btn1);
		btn1.addListener(new InputDYAListener(btn1, 1, game));
		
		btn2.setPosition(5 + btn2.getWidth(),heit);
		stg.addActor(btn2);
		btn2.addListener(new InputDYAListener(btn2, 2, game));
		
		btn3.setPosition(5 + btn3.getWidth()*2, heit);
		stg.addActor(btn3);
		btn3.addListener(new InputDYAListener(btn3, 3, game));
		
		btn4.setPosition(5 + btn4.getWidth()*3, heit);
		stg.addActor(btn4);
		btn4.addListener(new InputDYAListener(btn4, 4, game));
		
		btn5.setPosition(5 + btn4.getWidth()*1.6f, -15);
		stg.addActor(btn5);
		btn5.addListener(new InputDYAListener(btn5, 5, game));
				
		
		
		Gdx.input.setInputProcessor(stg);
	}

	@Override
	public void dispose() {
		batch.dispose();
		texture.dispose();
	}

	@Override
	public void resize(int width, int height) {
		stg.setViewport(512, 275);
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			
		stg.draw();
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}
}
