package fr.fxjavadevblog.xr.artefacts.friendly.weapons;

import fr.fxjavadevblog.xr.commons.Global;

/**
 * <p>
 * représente l'arme puissante et son énergie. L'energie va de 100f : chargée au
 * max, 0f déchargée. la décharge est immédiate.
 * </p>
 * 
 */

public class SecondWeapon
{
	/**
	 * énergie courante du bouclier
	 */
	private float energy = 0f;

	/**
	 * bouclier activé ou non.
	 * 
	 */
	private boolean charging;

	/**
	 * arme prête ou non
	 */
	private boolean ready;

	/**
	 * met à jour l'état du bouclier en fonction de "delta".
	 * 
	 * @param delta
	 */
	public void update(float delta)
	{
		if (charging)
		{
			this.calculateCharge(delta);
		}

		// remise à false car on doit maintenir la touche appuyée
		charging = false;
	}

	private void calculateCharge(float delta)
	{
		energy += delta * Global.WEAPON_CHARGING_SPEED;
		energy = Math.min(energy, 100f);
		ready = (energy >= 100f);
	}

	/**
	 * décharge toute l'énergie accumulée.
	 */
	public void fullDischarge()
	{
		energy = 0f;
	}

	public void disable()
	{
		ready = false;
	}

	/**
	 * active le chargement de l'arme secondaire.
	 * 
	 */
	public void charge()
	{
		charging = true;
	}

	public boolean isReady()
	{
		return ready;
	}

	/**
	 * @return le niveau d'énergie de l'arme (max 100f à min 0f)
	 */
	public float getEnergy()
	{
		return this.energy;
	}

}
