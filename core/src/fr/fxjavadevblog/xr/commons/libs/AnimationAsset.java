package fr.fxjavadevblog.xr.commons.libs;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.utils.Disposable;

import fr.fxjavadevblog.xr.commons.displays.AnimatedSprite;
import fr.fxjavadevblog.xr.commons.utils.GdxCommons;

/**
 * catalogue d'animations pour l'ensemble du jeu, accessible sous forme d'ENUM.
 * 
 * @author robin
 *
 */

public enum AnimationAsset implements Disposable
{
	EXPLOSION_BIG("shoots/explosion-sheet.png", 8, 6, 2f, PlayMode.NORMAL), 
	EXPLOSION_LITTLE("shoots/little-explosion.png", 6, 1, 1f, PlayMode.NORMAL), 
	FRIENDLY_SHOOT("shoots/shoot-anim.png", 5, 1, 0.5f, PlayMode.LOOP, circleOf(10, 50, 10)),
	FRIENDLY_BIGSHOOT("shoots/big-shoot.png", 5, 1, 1f, PlayMode.LOOP, circleOf(26, 80, 26)),
	BONUS("commons/bonus.png", 8, 1, 1f, PlayMode.LOOP), 
	POWER_UP("commons/bonus-power-up-anim.png", 7, 1, 1f, PlayMode.LOOP);

	private final String fileName;
	private final int cols;
	private final int rows;
	private final float duration;
	private final Animation.PlayMode playMode;

	private float centerX;
	private float centerY;
	private float radius;

	private Texture texture;
	private Animation<TextureRegion> animation;

	private static Circle circleOf(float x, float y, float radius)
	{
		return new Circle(x, y, radius);
	}

	private AnimationAsset(String fileName, int cols, int rows, float duration, Animation.PlayMode mode, Circle circle)
	{
		this(fileName, cols, rows, duration, mode);
		this.centerX = circle.x;
		this.centerY = circle.y;
		this.radius = circle.radius;
	}

	private AnimationAsset(String fileName, int cols, int rows, float duration, Animation.PlayMode playMode)
	{
		this.fileName = fileName;
		this.playMode = playMode;
		this.cols = cols;
		this.rows = rows;
		this.duration = duration;
	}

	private Animation<TextureRegion> getAnimation()
	{
		if (animation == null)
		{
			this.texture = AssetLib.getInstance().get(fileName, Texture.class);
			TextureRegion[] result = GdxCommons.convertToTextureArray(texture, cols, rows);
			animation = new Animation<>(duration / result.length, result);
			animation.setPlayMode(playMode);
		}
		return animation;
	}

	public AnimatedSprite createAnimatedSprite()
	{
		if (centerX >= 0 || centerY >= 0)
		{
			return new AnimatedSprite(this.getAnimation(), centerX, centerY);
		}
		else
		{
			return new AnimatedSprite(this.getAnimation());
		}
	}

	public float getRadius()
	{
		return radius;
	}

	@Override
	public String toString()
	{
		return this.fileName;
	}

	@Override
	public void dispose()
	{
		if (texture != null)
		{
			texture.dispose();
		}
	}

	public static void disposeAll()
	{
		GdxCommons.disposeAll(AnimationAsset.values());
	}
}
