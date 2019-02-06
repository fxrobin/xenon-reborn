package fr.fxjavadevblog.xr.commons.displays;

import com.badlogic.gdx.math.Interpolation;

/**
 * calcule l'offset de décallage d'un sprite en fonction d'une interpolation,
 * d'une distance max et d'un temps d'interpolation.
 * 
 * @author robin
 *
 */
public class Interpolator
{
	private float accumulatedTime;
	private float interPolationTime;
	private int currentSign = 1;
	private float distance;
	private float originalValue;
	private Interpolation interpolation;

	public Interpolator(Interpolation interpolation, float interPolationTime, float distance, float originalValue)
	{
		super();
		this.accumulatedTime = interPolationTime / 2f; // on commence au milieu de
																										// l'anim.
		this.interpolation = interpolation;
		this.interPolationTime = interPolationTime;
		this.distance = distance;
		this.originalValue = originalValue;
	}

	/**
	 * position décalée.
	 * 
	 * @param delta
	 * @return
	 */
	public float calculate(float delta)
	{
		accumulatedTime += delta * (float) currentSign;
		if (accumulatedTime > interPolationTime || accumulatedTime < 0)
		{
			currentSign = -currentSign;
			accumulatedTime = Math.max(0f, accumulatedTime);
			accumulatedTime = Math.min(interPolationTime, accumulatedTime);
		}
		float offset = interpolation.apply(-distance, distance, accumulatedTime / interPolationTime);
		return originalValue + offset;
	}

	/**
	 * position d'origine du calcul.
	 * 
	 * @return
	 */
	public float getOriginalValue()
	{
		return originalValue;
	}

}
