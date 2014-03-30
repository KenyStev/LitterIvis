package rosalila.taller.platformer.listener;

import rosalila.taller.platformer.GlobalNPCs;
import rosalila.taller.platformer.TallerPlatformer;
import rosalila.taller.platformer.menu;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class InputDYAListener extends InputListener {
	
	Image btn;
	int num;

		public InputDYAListener(Image btn, int num) {
			this.btn = btn;
			this.num=num;
		}

		@Override
		public boolean touchDown(InputEvent e,float x, float y, int pointer, int button)
		{
			switch(this.num){
				case 1: System.out.println("TouchDown1!!" );
//						menu.intro1.setVisible(true);
//						menu.intro2.setVisible(false);
//						menu.intro3.setVisible(false);
//						menu.intro4.setVisible(false);
//						menu.intro5.setVisible(false);
				GlobalNPCs.game = new TallerPlatformer(1);
						btn.setColor(Color.BLUE); return true;
				case 2: System.out.println("TouchDown2!!" );
//						menu.intro1.setVisible(false);
//						menu.intro2.setVisible(true);
//						menu.intro3.setVisible(false);
//						menu.intro4.setVisible(false);
//						menu.intro5.setVisible(false);				
						btn.setColor(Color.BLUE); return true;					
				case 3: System.out.println("TouchDown3!!" );
//						menu.intro1.setVisible(false);
//						menu.intro2.setVisible(false);
//						menu.intro3.setVisible(true);
//						menu.intro4.setVisible(false);
//						menu.intro5.setVisible(false);
						btn.setColor(Color.BLUE); return true;
				case 4: System.out.println("TouchDown4!!" );
//						menu.intro1.setVisible(false);
//						menu.intro2.setVisible(false);
//						menu.intro3.setVisible(false);
//						menu.intro4.setVisible(true);
//						menu.intro5.setVisible(false);
						btn.setColor(Color.BLUE); return true;
				case 5: System.out.println("TouchDown5!!" );
//						menu.intro1.setVisible(false);
//						menu.intro2.setVisible(false);
//						menu.intro3.setVisible(false);
//						menu.intro4.setVisible(false);
//						menu.intro5.setVisible(true);
						btn.setColor(Color.BLUE); return true;
				default: System.out.println("No Tocaste Nada!!" ); return false;
			}

			
		}

		@Override
		public void touchUp(InputEvent e,float x, float y, int pointer, int button)
		{
			btn.setColor(Color.WHITE);
			
		}
	}