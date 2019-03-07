package fr.fxjavadevblog.xr.artefacts.collectables;

import com.badlogic.gdx.audio.Sound;

import fr.fxjavadevblog.xr.artefacts.ArtefactData;
import fr.fxjavadevblog.xr.commons.displays.AnimatedSprite;
import fr.fxjavadevblog.xr.commons.libs.AnimationAsset;

public enum BonusType
{
	NORMAL_BONUS(AnimationAsset.BONUS, 0, -60f, null, 5, 5), 
	POWER_UP_BONUS(AnimationAsset.POWER_UP, 0, -60f, null, 5, 5);

	private final AnimationAsset anim;
	private final Sound sound;
	private ArtefactData data;

	private BonusType(AnimationAsset anim, float vectorX, float vectorY, Sound sound, int lifePoints, int impactForce)
	{
		this.anim = anim;
		this.sound = sound;
		this.data = new ArtefactData(lifePoints, impactForce, vectorX, vectorY);
	}

	public AnimatedSprite createAnimatedSprite()
	{
		return anim.createAnimatedSprite();
	}

	public float getVX()
	{
		return data.getVectorX();
	}

	public float getVY()
	{
		return data.getVectorY();
	}

	public int getLifeForce()
	{
		return data.getLifePoints();
	}

	public int getImpactForce()
	{
		return data.getImpactForce();
	}

	public Sound getSound()
	{
		return sound;
	}
}
