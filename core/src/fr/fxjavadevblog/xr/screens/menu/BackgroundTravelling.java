package fr.fxjavadevblog.xr.screens.menu;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import fr.fxjavadevblog.xr.commons.Global;
import fr.fxjavadevblog.xr.commons.libs.TextureAsset;
import fr.fxjavadevblog.xr.commons.utils.DeltaTimeAccumulator;

/**
 * affiche un travelling aléatoire d'un fond étoilé.
 * 
 * @author robin
 *
 */

public class BackgroundTravelling
{
	private Texture spaceTexture;

	private float pX = 0;
	private float pY = 0;
	private float vX = random() * 20f - 10f;
	private float vY = random() * 20f - 10f;

	private float aX = 0;
	private float aY = 0;

	/* accumulateur / ticker paramétré sur 4 secondes */
	private DeltaTimeAccumulator dta = new DeltaTimeAccumulator(4);

	private float random()
	{
		return (float) Math.random() * 0.5f - 0.25f;
	}

	public BackgroundTravelling()
	{
		spaceTexture = TextureAsset.BACKGROUND_SPACE.get();
	}

	/**
	 * affiche le fond.
	 * 
	 * @param batch
	 */
	public void drawBackGround(SpriteBatch batch)
	{
		batch.draw(spaceTexture, 0f, 0f, (int) pX, (int) pY, Global.width, Global.height);
	}

	/**
	 * calcule la translation du fond.
	 * 
	 * @param delta
	 */
	public void translateBackGround(float delta)
	{
		if (dta.addAndCheck(delta))
		{
			aX = random();
			aY = random();
		}

		vX += aX;
		vY += aY;

		pX += vX * delta;
		pY += vY * delta;
	}

}
