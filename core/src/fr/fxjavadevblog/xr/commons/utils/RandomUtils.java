package fr.fxjavadevblog.xr.commons.utils;

import java.util.Random;

/**
 * classe utilitaire liée à l'usage du hasard.
 * 
 * @author robin
 *
 */
public final class RandomUtils
{
	private static Random random = new Random();

	private RandomUtils()
	{
		// protection
	}

	/**
	 * retourne une valeur au hasard prise dans le tableau.
	 * 
	 * @param values
	 *          tableau des valeurs
	 * @return instance prise au hasard
	 */
	@SuppressWarnings("unchecked")
	public static <T> T pick(T... values)
	{
		return values[random.nextInt(values.length)];
	}

	/**
	 * retourne un index au hasard conformément aux bornes du tableau.
	 * 
	 * @param values
	 *          tableau des valeurs.
	 * @return un index
	 * 
	 */
	public static int pickIndex(Object[] values)
	{
		return random.nextInt(values.length);
	}

	/**
	 * retourne un float dans l'intervalle donné.
	 * 
	 * @param min
	 *          borne min de l'intervalle
	 * @param max
	 *          borne max de l'intervalle
	 * @return float pris au hasard dans l'intervalle.
	 */
	public static float randomRange(float min, float max)
	{
		return random.nextFloat() * (max - min) + min;
	}
}
