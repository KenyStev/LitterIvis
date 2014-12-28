package rosalila.taller.platformer.listener;

import rosalila.taller.platformer.LitterIvis;
import rosalila.taller.platformer.screens.PlayScreen;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class InputDYAListener extends InputListener {

	Image btn;
	LitterIvis game;
	int num;

	public InputDYAListener(Image btn, int num, LitterIvis game) {
		this.btn = btn;
		this.num = num;
		this.game = game;
	}

	@Override
	public boolean touchDown(InputEvent e, float x, float y, int pointer,
			int button) {
		game.setScreen(new PlayScreen(game, num));
		System.out.println("TouchDown" + num + "!!");
		return true;

	}

	@Override
	public void touchUp(InputEvent e, float x, float y, int pointer, int button) {
		btn.setColor(Color.WHITE);

	}
}