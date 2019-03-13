package fr.fxjavadevblog.xr.artefacts.friendly.ship;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import fr.fxjavadevblog.xr.artefacts.AbstractArtefact;
import fr.fxjavadevblog.xr.artefacts.Event;
import fr.fxjavadevblog.xr.artefacts.friendly.addons.Shield;
import fr.fxjavadevblog.xr.artefacts.friendly.weapons.SecondWeapon;
import fr.fxjavadevblog.xr.commons.Global;
import fr.fxjavadevblog.xr.commons.utils.GdxCommons;

public class Ship extends AbstractArtefact
{
	private ShipRenderer shipRenderer;
	private Shield shield;
	private SecondWeapon secondWeapon;
	private boolean vulnerable;
	private int lifeCount;

	public Ship()
	{
		super(0, 0, Global.SHIP_LIFE_POINTS, 20);
		shipRenderer = new ShipRenderer(this);
		shield = new Shield();
		secondWeapon = new SecondWeapon();
		this.setRadius(shipRenderer.getCurrentSprite().getWidth() / 2);
		vulnerable = false;
		lifeCount = 3;
	}

	public float getCenterX()
	{
		return shipRenderer.getCenterX();
	}

	public float getCenterY()
	{
		return shipRenderer.getCenterY();
	}

	public boolean isShieldActivated()
	{
		return shield.isActivated();
	}

	/*
	 * redéfinition de "decreaseLife" et "increaseLife" pour que les méthodes
	 * n'aient aucune conséquence en cas de bouclier activé.
	 */

	@Override
	public void decreaseLife(int force)
	{
		if (!isShieldActivated() && vulnerable)
		{

			super.decreaseLife(force);
			checkShipIsAliveAndBlink();
		}
	}

	private void checkShipIsAliveAndBlink()
	{
		if (!super.isAlive())
		{
			this.shipRenderer.blink();
			decreaseLifeCount();
		}
	}

	private void decreaseLifeCount()
	{
		lifeCount--;
		this.setChanged();
		if (lifeCount > 0)
		{
			this.setLifePoints(Global.SHIP_LIFE_POINTS);
			this.notifyObservers(Event.HIT);
		}
		else
		{
			this.notifyObservers(Event.DESTROYED);
		}
	}

	@Override
	public void increaseLife(int force)
	{
		if (!isShieldActivated())
		{
			super.increaseLife(force);
		}
	}

	@Override
	public void render(SpriteBatch batch, float delta)
	{
		vulnerable = !shipRenderer.isBlinking();
		super.render(batch, delta);
		shipRenderer.render(batch, delta);
	}

	public void switchShield()
	{
		shield.switchShield();
	}

	@Override
	public void update(float delta)
	{
		super.update(delta);
		shield.update(delta);
		secondWeapon.update(delta);
		this.controlPosition();
	}

	private void controlPosition()
	{
		Sprite currentSprite = shipRenderer.getCurrentSprite();
		if (currentSprite.getX() < 0) currentSprite.setX(0);
		if (currentSprite.getY() < 80) currentSprite.setY(80);
		if (currentSprite.getX() > Global.SCREEN_WIDTH - currentSprite.getWidth()) currentSprite.setX(Global.SCREEN_WIDTH - currentSprite.getWidth());
		if (currentSprite.getY() > Global.SCREEN_HEIGHT - currentSprite.getHeight()) currentSprite.setY(Global.SCREEN_HEIGHT - currentSprite.getHeight());
		GdxCommons.computeBoundingCircle(currentSprite, getBoundingCircle());
	}

	@Override
	public Sprite getSprite()
	{
		return this.shipRenderer.getCurrentSprite();
	}

	public float getShieldEnergy()
	{
		return this.shield.getEnergy();
	}

	public float getSecondWeaponEnergy()
	{
		return this.secondWeapon.getEnergy();
	}

	public int getLifeCount()
	{
		return lifeCount;
	}

	public boolean isFullyDestroyed()
	{
		return lifeCount <= 0;
	}

	public void weaponCharge()
	{
		this.secondWeapon.charge();
	}

	public SecondWeapon getSecondaryWeapon()
	{
		return this.secondWeapon;
	}

	public boolean canFire()
	{
		return !this.isShieldActivated() && !this.isFullyDestroyed();
	}
}
