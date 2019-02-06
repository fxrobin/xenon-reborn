package fr.fxjavadevblog.xr.artefacts.friendly;

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
	private AnimatedSprite animatedSprite;
	private Interpolator interpolator;

	public Shoot(AnimatedSprite animatedSprite, int lifeForce, int impactForce, float x, float y, float vX, float vY)
	{
		super(vX, vY, lifeForce, impactForce);
		this.animatedSprite = animatedSprite;
		this.animatedSprite.setCenter(x, y);
		this.interpolator = new Interpolator(Interpolation.sine, 0.8f, 5f, x);
	}

	@Override
	public void render(SpriteBatch batch, float delta)
	{
		animatedSprite.setCenterX(interpolator.getOriginalValue());
		this.update(delta);
		float newPosition = interpolator.calculate(delta);
		animatedSprite.setCenterX(newPosition);
		this.getBoundingCircle().x = newPosition;
		super.render(batch, delta);
		animatedSprite.render(batch, delta);
	}

	@Override
	public Sprite getSprite()
	{
		return animatedSprite;
	}

}
