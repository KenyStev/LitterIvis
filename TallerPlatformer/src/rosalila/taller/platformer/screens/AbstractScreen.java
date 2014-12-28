package rosalila.taller.platformer.screens;

import rosalila.taller.platformer.LitterIvis;

import com.badlogic.gdx.Screen;

public abstract class AbstractScreen implements Screen {
	protected LitterIvis game;
	
	public AbstractScreen(LitterIvis game) {
		this.game=game;
	}
	
	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}
}
