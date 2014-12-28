package rosalila.taller.platformer.screens;

import rosalila.taller.platformer.GlobalNPCs;
import rosalila.taller.platformer.Ivis;
import rosalila.taller.platformer.State;
import rosalila.taller.platformer.LitterIvis;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

public class PlayScreen extends AbstractScreen {
	int level;

	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;
	private OrthographicCamera camera;
	private Texture koalaTexture;
	private Animation stand;
	private Animation walk;
	private Animation jump;
	private Ivis ivis;

	private Pool<Rectangle> rectPool = new Pool<Rectangle>() {
		@Override
		protected Rectangle newObject() {
			return new Rectangle();
		}
	};
	private Array<Rectangle> tiles = new Array<Rectangle>();

	private static final float GRAVITY = -2.5f;

	public PlayScreen(LitterIvis game, int level) {
		super(game);
		this.level = level;
	}

	@Override
	public void show() {
		// load the koala frames, split them, and assign them to Animations
		koalaTexture = new Texture("ivis2.png");
		TextureRegion[] regions = TextureRegion.split(koalaTexture, 18, 26)[0];
		stand = new Animation(0, regions[0]);
		jump = new Animation(0, regions[1]);
		walk = new Animation(0.15f, regions[2], regions[3], regions[4]);
		walk.setPlayMode(Animation.LOOP_PINGPONG);

		// figure out the width and height of the koala for collision
		// detection and rendering by converting a koala frames pixel
		// size into world units (1 unit == 16 pixels)
		Ivis.WIDTH = 1 / 16f * regions[0].getRegionWidth();
		Ivis.HEIGHT = 1 / 16f * regions[0].getRegionHeight();

		// load the map, set the unit scale to 1/16 (1 unit == 16 pixels)
		map = new TmxMapLoader().load("nivel" + level + ".tmx");
		renderer = new OrthogonalTiledMapRenderer(map, 1 / 16f);

		// create an orthographic camera, shows us 30x20 units of the world
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 30, 20);
		camera.update();

		// create the Koala we want to move around the world
		ivis = new Ivis();
		ivis.position.set(20, 15);

		Music oggMusic = Gdx.audio.newMusic(Gdx.files.internal("music.ogg"));
		oggMusic.play();

		GlobalNPCs.init(level);
	}

	@Override
	public void render(float delta) {
		// clear the screen
		Gdx.gl.glClearColor(0.7f, 0.7f, 1.0f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		// get the delta time
		float deltaTime = Gdx.graphics.getDeltaTime();

		// update the koala (process input, collision detection, position
		// update)
		updateKoala(deltaTime);

		// let the camera follow the koala, x-axis only
		camera.position.x = ivis.position.x;
		camera.update();

		// set the tile map rendere view based on what the
		// camera sees and render the map
		renderer.setView(camera);
		renderer.render();

		// render the koala
		renderKoala(deltaTime);
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void hide() {

	}

	@Override
	public void dispose() {

	}

	private void updateKoala(float deltaTime) {
		if (deltaTime == 0)
			return;
		ivis.stateTime += deltaTime;

		// check input and apply to velocity & state
		if ((Gdx.input.isKeyPressed(Keys.SPACE) || isTouched(0.75f, 1))
				&& ivis.grounded) {
			ivis.velocity.y += Ivis.JUMP_VELOCITY;
			ivis.state = State.Jumping;
			ivis.grounded = false;
		}

		if (Gdx.input.isKeyPressed(Keys.LEFT) || Gdx.input.isKeyPressed(Keys.A)
				|| isTouched(0, 0.25f)) {
			ivis.velocity.x = -Ivis.MAX_VELOCITY;
			if (ivis.grounded)
				ivis.state = State.Walking;
			ivis.facesRight = false;
		}

		if (Gdx.input.isKeyPressed(Keys.RIGHT)
				|| Gdx.input.isKeyPressed(Keys.D) || isTouched(0.25f, 0.5f)) {
			ivis.velocity.x = Ivis.MAX_VELOCITY;
			if (ivis.grounded)
				ivis.state = State.Walking;
			ivis.facesRight = true;
		}

		// apply gravity if we are falling
		ivis.velocity.add(0, GRAVITY);

		// clamp the velocity to the maximum, x-axis only
		if (Math.abs(ivis.velocity.x) > Ivis.MAX_VELOCITY) {
			ivis.velocity.x = Math.signum(ivis.velocity.x) * Ivis.MAX_VELOCITY;
		}

		// clamp the velocity to 0 if it's < 1, and set the state to standign
		if (Math.abs(ivis.velocity.x) < 1) {
			ivis.velocity.x = 0;
			if (ivis.grounded)
				ivis.state = State.Standing;
		}

		// multiply by delta time so we know how far we go
		// in this frame
		ivis.velocity.scl(deltaTime);

		// perform collision detection & response, on each axis, separately
		// if the koala is moving right, check the tiles to the right of it's
		// right bounding box edge, otherwise check the ones to the left
		Rectangle koalaRect = rectPool.obtain();
		koalaRect
				.set(ivis.position.x, ivis.position.y, Ivis.WIDTH, Ivis.HEIGHT);
		int startX, startY, endX, endY;
		if (ivis.velocity.x > 0) {
			startX = endX = (int) (ivis.position.x + Ivis.WIDTH + ivis.velocity.x);
		} else {
			startX = endX = (int) (ivis.position.x + ivis.velocity.x);
		}
		startY = (int) (ivis.position.y);
		endY = (int) (ivis.position.y + Ivis.HEIGHT);
		getTiles(startX, startY, endX, endY, tiles, 1);
		koalaRect.x += ivis.velocity.x;
		for (Rectangle tile : tiles) {
			if (koalaRect.overlaps(tile)) {
				ivis.velocity.x = 0;
				break;
			}
		}
		koalaRect.x = ivis.position.x;

		// if the koala is moving upwards, check the tiles to the top of it's
		// top bounding box edge, otherwise check the ones to the bottom
		if (ivis.velocity.y > 0) {
			startY = endY = (int) (ivis.position.y + Ivis.HEIGHT + ivis.velocity.y);
		} else {
			startY = endY = (int) (ivis.position.y + ivis.velocity.y);
		}
		startX = (int) (ivis.position.x);
		endX = (int) (ivis.position.x + Ivis.WIDTH);
		getTiles(startX, startY, endX, endY, tiles, 1);
		koalaRect.y += ivis.velocity.y;
		for (Rectangle tile : tiles) {
			if (koalaRect.overlaps(tile)) {
				// we actually reset the koala y-position here
				// so it is just below/above the tile we collided with
				// this removes bouncing :)
				if (ivis.velocity.y > 0) {
					ivis.position.y = tile.y - Ivis.HEIGHT;
					// we hit a block jumping upwards, let's destroy it!
					TiledMapTileLayer layer = (TiledMapTileLayer) map
							.getLayers().get(1);
					// layer.setCell((int)tile.x, (int)tile.y, null);
				} else {
					ivis.position.y = tile.y + tile.height;
					// if we hit the ground, mark us as grounded so we can jump
					ivis.grounded = true;
				}
				ivis.velocity.y = 0;
				break;
			}
		}
		// Inicio cambio
		getTiles(startX, startY, endX, endY, tiles, 2);
		for (Rectangle tile : tiles) {
			if (koalaRect.overlaps(tile)) {
				TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers()
						.get(2);
				layer.setCell((int) tile.x, (int) tile.y, null);
			}
		}
		// fin cambio
		rectPool.free(koalaRect);

		// unscale the velocity by the inverse delta time and set
		// the latest position
		ivis.position.add(ivis.velocity);
		ivis.velocity.scl(1 / deltaTime);

		// Apply damping to the velocity on the x-axis so we don't
		// walk infinitely once a key was pressed
		ivis.velocity.x *= Ivis.DAMPING;

	}

	private boolean isTouched(float startX, float endX) {
		// check if any finge is touch the area between startX and endX
		// startX/endX are given between 0 (left edge of the screen) and 1
		// (right edge of the screen)
		for (int i = 0; i < 2; i++) {
			float x = Gdx.input.getX() / (float) Gdx.graphics.getWidth();
			if (Gdx.input.isTouched(i) && (x >= startX && x <= endX)) {
				return true;
			}
		}
		return false;
	}

	private void getTiles(int startX, int startY, int endX, int endY,
			Array<Rectangle> tiles, int num_layer) {
		TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(
				num_layer);
		rectPool.freeAll(tiles);
		tiles.clear();
		for (int y = startY; y <= endY; y++) {
			for (int x = startX; x <= endX; x++) {
				Cell cell = layer.getCell(x, y);
				if (cell != null) {
					TiledMapTile tile = cell.getTile();
					MapProperties properties = tile.getProperties();
					if (properties.containsKey("hola"))
						System.out.println("test");

					Rectangle rect = rectPool.obtain();
					rect.set(x, y, 1, 1);
					tiles.add(rect);
				}
			}
		}
	}

	private void renderKoala(float deltaTime) {
		// based on the koala state, get the animation frame
		TextureRegion frame = null;
		switch (ivis.state) {
		case Standing:
			frame = stand.getKeyFrame(ivis.stateTime);
			break;
		case Walking:
			frame = walk.getKeyFrame(ivis.stateTime);
			break;
		case Jumping:
			frame = jump.getKeyFrame(ivis.stateTime);
			break;
		}

		// draw the koala, depending on the current velocity
		// on the x-axis, draw the koala facing either right
		// or left
		Batch batch = renderer.getSpriteBatch();
		batch.begin();
		if (ivis.facesRight) {
			batch.draw(frame, ivis.position.x, ivis.position.y, Ivis.WIDTH,
					Ivis.HEIGHT);
		} else {
			batch.draw(frame, ivis.position.x + Ivis.WIDTH, ivis.position.y,
					-Ivis.WIDTH, Ivis.HEIGHT);
		}
		GlobalNPCs.render(batch, level);
		batch.end();
	}

}
