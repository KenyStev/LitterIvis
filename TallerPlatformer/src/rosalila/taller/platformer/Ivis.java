package rosalila.taller.platformer;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Ivis extends Actor {
	
	public static float WIDTH;
	public static float HEIGHT;
	public static float MAX_VELOCITY = 10f;
	public static float JUMP_VELOCITY = 40f;
	public static float DAMPING = 0.87f;

	public final Vector2 position = new Vector2();
	public final Vector2 velocity = new Vector2();
	public State state = State.Walking;
	public float stateTime = 0;
	public boolean facesRight = true;
	public boolean grounded = false;

	public Ivis() {
		
	}
}
