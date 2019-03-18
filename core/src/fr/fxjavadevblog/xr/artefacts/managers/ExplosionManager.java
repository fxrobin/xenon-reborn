package fr.fxjavadevblog.xr.artefacts.managers;

import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.Batch;

import fr.fxjavadevblog.xr.artefacts.Artefact;
import fr.fxjavadevblog.xr.commons.displays.AnimatedSprite;
import fr.fxjavadevblog.xr.commons.libs.AnimationAsset;
import fr.fxjavadevblog.xr.commons.libs.SoundAsset;
import fr.fxjavadevblog.xr.screens.game.BackgroundParallaxScrolling;

/**
 * gestionnaire des explosions.
 * 
 * @author robin
 *
 */
public final class ExplosionManager
{
	private static List<AnimatedSprite> explosions = new LinkedList<>();

	private ExplosionManager()
	{
		/* protection */
	}

	/**
	 * ajoute une explosion à l'endroit de l'artefact touché.
	 * 
	 * @param artefact
	 * @param animationAsset
	 */
	public static void addExplosion(Artefact artefact, AnimationAsset animationAsset)
	{
		addExplosion(artefact.getBoundingCircle().x, artefact.getBoundingCircle().y, animationAsset);
	}

	public static void addExplosion(float centerX, float centerY, AnimationAsset animationAsset)
	{
		AnimatedSprite animatedSprite = animationAsset.createAnimatedSprite();
		animatedSprite.setOriginCenterAtPosition(centerX, centerY);
		explosions.add(animatedSprite);
		SoundAsset.EXPLOSION.play();
	}

	public static void render(Batch batch, float delta)
	{
		explosions.forEach(ex -> {
			ex.render(batch, delta);
			/* 2 vitesse du scroll des bords. */
			ex.translateY(-BackgroundParallaxScrolling.getInstance().getSpeed() * 2 * delta);
		});
		removeFinishedExplosions();
	}
	
	private static void removeFinishedExplosions()
	{
		explosions.removeIf(AnimatedSprite::isFinished);
	}

}

