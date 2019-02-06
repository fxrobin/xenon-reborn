package fr.fxjavadevblog.xr.artefacts.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import fr.fxjavadevblog.xr.artefacts.managers.EnemyManager;
import fr.fxjavadevblog.xr.artefacts.AbstractArtefact;
import fr.fxjavadevblog.xr.commons.utils.DeltaTimeAccumulator;
import fr.fxjavadevblog.xr.commons.utils.RandomUtils;

/**
 * réprésente un ennemie.
 * 
 * @author robin
 *
 */

public class Enemy extends AbstractArtefact
{
	private DeltaTimeAccumulator accumulator;
	private Sprite sprite;

	public Enemy(Enemy other)
	{
		this(other.getSprite().getTexture(), other.getLifePoints(), other.getImpactForce(), other.getBoundingCircle().radius, other.getVectorX(), other.getVectorY());
	}

	public Enemy(Texture texture, int force, int impactForce, float radius, float vX, float vY)
	{
		super(vX, vY, force, impactForce);
		accumulator = new DeltaTimeAccumulator(RandomUtils.randomRange(1, 3), () -> EnemyManager.getInstance().generateBullet(this));
		sprite = new Sprite(texture);
		this.setRadius(radius);
	}

	public Enemy(Texture texture, int force, int impactForce, float radius)
	{
		this(texture, force, impactForce, radius, 0, 0);
	}

	@Override
	public Sprite getSprite()
	{
		return this.sprite;
	}

	@Override
	public void render(SpriteBatch batch, float delta)
	{
		accumulator.addAndCheck(delta);
		super.render(batch, delta);
		sprite.draw(batch);
	}

	public void setOriginCenter()
	{
		sprite.setOriginCenter();
	}

	public void setX(float x)
	{
		sprite.setX(x);
	}

	public void setY(float y)
	{
		sprite.setY(y);
	}

	public float getX()
	{
		return sprite.getX();
	}

	public float getY()
	{
		return sprite.getY();
	}

	public float getHeight()
	{
		return sprite.getHeight();
	}

}
