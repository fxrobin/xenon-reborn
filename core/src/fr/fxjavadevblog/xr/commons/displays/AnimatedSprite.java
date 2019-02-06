package fr.fxjavadevblog.xr.commons.displays;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * classe pour gérer les animations d'un sprite en fonction des animations du
 * catalogue.
 * 
 * @author robin
 *
 */

public class AnimatedSprite extends Sprite
{
	private float elapsedTime;
	private Animation<TextureRegion> animation;

	public AnimatedSprite(Animation<TextureRegion> animation)
	{
		super(animation.getKeyFrames()[0]);
		elapsedTime = 0f;
		this.animation = animation;
	}

	public AnimatedSprite(Animation<TextureRegion> animation, float centerX, float centerY)
	{
		this(animation);
		this.setOrigin(centerX, centerY);
	}

	private void update(float delta)
	{
		elapsedTime += delta;
		this.setRegion(animation.getKeyFrame(elapsedTime));
	}

	public void render(Batch batch, float delta)
	{
		this.update(delta);
		this.draw(batch);
	}

	public boolean isFinished()
	{
		return animation.isAnimationFinished(elapsedTime);
	}
	
	public void setOriginCenterAtPosition(float centerX, float centerY)
	{	
		this.setOriginCenter();
		this.setCenter(centerX, centerY);
	}
}
