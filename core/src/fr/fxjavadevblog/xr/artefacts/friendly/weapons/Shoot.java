package fr.fxjavadevblog.xr.artefacts.friendly.weapons;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;

import fr.fxjavadevblog.xr.artefacts.AbstractArtefact;
import fr.fxjavadevblog.xr.commons.displays.AnimatedSprite;
import fr.fxjavadevblog.xr.commons.displays.Interpolator;

/**
 * représente un tir quel qu'il soit.
 * 
 * @author robin
 *
 */
public class Shoot extends AbstractArtefact
{
	private static final float INTERPOLATOR_DISTANCE = 5f;
	private static final float INTERPOLATOR_TIME = 0.8f;
	private AnimatedSprite animatedSprite;
	

	public Shoot(AnimatedSprite animatedSprite, int lifeForce, int impactForce, Position parameterObject, float vX, float vY)
	{
		super(vX, vY, lifeForce, impactForce);
		this.animatedSprite = animatedSprite;
		this.animatedSprite.setCenter(parameterObject.x, parameterObject.y);
		this.setInterpolatorX(new Interpolator(Interpolation.sine, INTERPOLATOR_TIME, INTERPOLATOR_DISTANCE, parameterObject.x));
	}

	@Override
	public void render(SpriteBatch batch, float delta)
	{
		super.update(delta);
		super.render(batch, delta);
		animatedSprite.render(batch, delta);
	}

	@Override
	public Sprite getSprite()
	{
		return animatedSprite;
	}

}
