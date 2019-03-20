package fr.fxjavadevblog.xr.artefacts.collectables;

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
public class Bonus extends AbstractArtefact
{
	private AnimatedSprite animatedSprite;
	private BonusType type;

	private Bonus(BonusType type, int lifeForce, int impactForce, float x, float y, float vX, float vY)
	{
		super(vX, vY, lifeForce, impactForce);
		this.type = type;
		this.animatedSprite = type.createAnimatedSprite();
		this.animatedSprite.setOriginCenterAtPosition(x, y);
		this.setInterpolatorX(new Interpolator(Interpolation.sine, 2f, 50, x));
	}
	
	public static Bonus newInstance(BonusType type, float x, float y)
	{
		return new Bonus(type, type.getLifeForce(), type.getImpactForce(), x, y, type.getVX(), type.getVY());
	}

	public BonusType getType()
	{
		return type;
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
