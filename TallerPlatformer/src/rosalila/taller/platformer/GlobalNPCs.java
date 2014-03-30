package rosalila.taller.platformer;

import rosalila.taller.platformer.TallerPlatformer.Koala;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class GlobalNPCs
{
	public static Animation policia;
	public static Animation comprador1;
	public static Animation comprador2;
	public static Animation comprador3;
	public static Animation extorsionada;
	public static Animation asesinado;
	public static Animation jefe;
	
	public static TallerPlatformer game;
	
	public static void init(int level)
	{
		if(level==1)
		{
			Texture texture = new Texture("policia.png"); 
			TextureRegion[] regions = TextureRegion.split(texture, 18, 26)[0];
			policia = new Animation(0, regions[0]);
		}
		if(level==2)
		{
			Texture texture1 = new Texture("comprador1.png"); 
			TextureRegion[] regions = TextureRegion.split(texture1, 18, 26)[0];
			comprador1 = new Animation(0, regions[0]);
			
			Texture texture2 = new Texture("comprador2.png"); 
			TextureRegion[] regions2 = TextureRegion.split(texture2, 18, 26)[0];
			comprador2 = new Animation(0, regions2[0]);
			
			Texture texture3 = new Texture("comprador3.png"); 
			TextureRegion[] regions3 = TextureRegion.split(texture3, 18, 26)[0];
			comprador3 = new Animation(0, regions3[0]);
		}
		if(level==3)
		{
			Texture texture = new Texture("extorsionada.png"); 
			TextureRegion[] regions = TextureRegion.split(texture, 18, 26)[0];
			extorsionada = new Animation(0, regions[0]);
		}
		if(level==4)
		{
			Texture texture = new Texture("asesinado.png"); 
			TextureRegion[] regions = TextureRegion.split(texture, 18, 26)[0];
			asesinado = new Animation(0, regions[0]);
		}
		if(level==5)
		{
			Texture texture = new Texture("jefe.png"); 
			TextureRegion[] regions = TextureRegion.split(texture, 18, 26)[0];
			jefe = new Animation(0, regions[0]);
		}
	}
	
	public static void render(Batch batch,int level)
	{
		if(level==1)
		{
			float x=124f;
			float y=2f;
			batch.draw(policia.getKeyFrame(0),x + Koala.WIDTH, y, - Koala.WIDTH, Koala.HEIGHT);
		}
		if(level==2)
		{
			batch.draw(comprador1.getKeyFrame(0),(float)68f + (float)Koala.WIDTH, (float)10f, -(float)Koala.WIDTH, (float)Koala.HEIGHT);
			batch.draw(comprador2.getKeyFrame(0),(float)110f + (float)Koala.WIDTH, (float)14f, -(float)Koala.WIDTH, (float)Koala.HEIGHT);
			batch.draw(comprador3.getKeyFrame(0),(float)176f + (float)Koala.WIDTH, (float)2f, -(float)Koala.WIDTH, (float)Koala.HEIGHT);
		}
		if(level==3)
		{
			batch.draw(extorsionada.getKeyFrame(0),(float)122f + (float)Koala.WIDTH, (float)2f, -(float)Koala.WIDTH, (float)Koala.HEIGHT);
		}
		if(level==4)
		{
			batch.draw(asesinado.getKeyFrame(0),(float)90f + (float)Koala.WIDTH, (float)7f, -(float)Koala.WIDTH, (float)Koala.HEIGHT);
		}
		if(level==5)
		{
			batch.draw(jefe.getKeyFrame(0),(float)38f + (float)Koala.WIDTH, (float)2f, -(float)Koala.WIDTH, (float)Koala.HEIGHT);
		}
	}
}
