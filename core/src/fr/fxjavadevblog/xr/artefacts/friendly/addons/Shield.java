package fr.fxjavadevblog.xr.artefacts.friendly.addons;

import fr.fxjavadevblog.xr.commons.Global;
import fr.fxjavadevblog.xr.commons.libs.SoundAsset;

/**
 * <p>
 * représente bouclier et son énergie. L'energie va de 100f : chargé au max, 0f
 * déchargé. En dessous de 90f il n'est pas activable A 0.f il se désactive
 * automatiquement.
 * </p>
 * 
 * <p>
 * Toutes les secondes le bouclier prend 20% en recharge. Toutes les secondes le
 * bouclier prend 10% en décharge.
 * </p>
 */

public class Shield
{
	/**
	 * énergie courante du bouclier
	 */
	private float energy = 100f;

	/**
	 * bouclier activé ou non.
	 * 
	 */
	private boolean activated;

	/**
	 * met à jour l'état du bouclier en fonction de "delta".
	 * 
	 * @param delta
	 */
	public void update(float delta)
	{
		if (activated)
		{
			this.decharger(delta);
		}
		else
		{
			this.recharger(delta);
		}
	}

	private void recharger(float delta)
	{
		energy += (delta * Global.SHIELD_CHARGING_SPEED);
		energy = (energy > Global.SHIELD_MAX_ENERGY) ? Global.SHIELD_MAX_ENERGY : energy;
	}

	private void decharger(float delta)
	{
		energy -= (delta * Global.SHIELD_DISCHARGING_SPEED);
		if (energy < 0f)
		{
			SoundAsset.SHIELD_DOWN.play();
			activated = false;
		}
	}

	/**
	 * active ou désactive le bouclier en fonction de son état. si le niveau
	 * d'énergie est inférieur à 90%, il ne peut pas être activé.
	 * 
	 */
	public void switchShield()
	{
		if (activated)
		{
			activated = false;
			SoundAsset.SHIELD_DOWN.play();
		}
		else
		{
			/* on active le bouclier seulement si c'est possible */
			this.activateIfThresholdReached();
		}
	}

	/**
	 * active le bouclier si le seuil d'activation est atteint ou dépassé.
	 */
	private void activateIfThresholdReached()
	{
		if (energy > Global.SHIELD_ACTIVATION_THRESHOLD)
		{
			activated = true;
			SoundAsset.SHIELD_ACTIVATED.play();
		}
	}

	/**
	 * @return état du bouclier activé ou non (true or false).
	 */
	public boolean isActivated()
	{
		return activated;
	}

	/**
	 * @return le niveau d'énergie du bouclier (max 100f à min 0f)
	 */
	public float getEnergy()
	{
		return this.energy;
	}

}
