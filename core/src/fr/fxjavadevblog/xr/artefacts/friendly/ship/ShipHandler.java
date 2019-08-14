package fr.fxjavadevblog.xr.artefacts.friendly.ship;

import com.badlogic.gdx.Gdx;

import fr.fxjavadevblog.xr.artefacts.friendly.weapons.ShootType;
import fr.fxjavadevblog.xr.artefacts.managers.ProjectileManager;
import fr.fxjavadevblog.xr.commons.Global;
import fr.fxjavadevblog.xr.commons.UserControls;
import fr.fxjavadevblog.xr.commons.UserControls.Control;
import fr.fxjavadevblog.xr.commons.utils.GdxCommons;

/**
 * <p>
 * En fonction des entrées du clavier :
 * <ul>
 * <li>cette classe change l'état du vaisseau</li>
 * <li>cette classe génère les tirs</li>
 * </ul>
 * </p>
 * 
 * @author robin
 *
 */
public final class ShipHandler
{
	private static ShipInput.Horizontal hControl;
	private static ShipInput.Vertical vControl;

	private ShipHandler()
	{
		// protection
	}

	public static ShipInput.Horizontal getHorizontalControl()
	{
		return hControl;
	}

	public static ShipInput.Vertical getVerticalControl()
	{
		return vControl;
	}

	public static void handle(Ship ship)
	{
		handleVerticalMovement(ship);
		handleHorizontalMovement(ship);
		handleInertia(ship);
		checkShield(ship);		
		if (canFire(ship))
		{
			checkNormalFire(ship);	
			checkBigFire(ship);
		}
	}

	private static boolean canFire(Ship ship)
	{
		return !ship.isShieldActivated() && !ship.isFullyDestroyed();
	}

	private static void checkBigFire(Ship ship)
	{
		if (Gdx.input.isKeyPressed(UserControls.get(Control.CHARGE_WEAPON)))
		{
			ship.weaponCharge();
		}
		else
		{
			if (ship.getSecondaryWeapon().isReady())
			{
				ProjectileManager.getInstance().addShoot(ShootType.BIG_FLAMES, ship.getCenterX(), ship.getCenterY());
			}
			ship.getSecondaryWeapon().disable();
		}
	}

	private static void checkNormalFire(Ship ship)
	{
		if (Gdx.input.isKeyJustPressed(UserControls.get(Control.NORMAL_FIRE)))
		{
			ProjectileManager.getInstance().addShoot(ShootType.NORMAL_LASER, ship.getCenterX(), ship.getCenterY());
		}
	}

	private static void checkShield(Ship ship)
	{
		if (Gdx.input.isKeyJustPressed(UserControls.get(Control.SHIELD)))
		{
			ship.switchShield();
		}
	}

	private static void handleVerticalMovement(Ship ship)
	{
		vControl = ShipInput.Vertical.NONE;
		/*
		 * précondition au mouvement : que les 2 touches ne soient pas enfoncées
		 */
		int keyUp = UserControls.get(Control.UP);
		int keyDown = UserControls.get(Control.DOWN);

		if (GdxCommons.checkConcurrentKeys(keyUp, keyDown)) return;

		float vY = ship.getVectorY();

		if (Gdx.input.isKeyPressed(keyUp))
		{
			vControl = ShipInput.Vertical.UP;
			vY += Global.SHIP_ACCELLERATION;
			ship.setVectorY(vY > Global.SHIP_SPEED ? Global.SHIP_SPEED : vY);
		}
		else if (Gdx.input.isKeyPressed(keyDown))
		{
			vControl = ShipInput.Vertical.DOWN;
			vY -= Global.SHIP_ACCELLERATION;
			ship.setVectorY(vY < -Global.SHIP_SPEED ? -Global.SHIP_SPEED : vY);
		}
	}

	private static void handleHorizontalMovement(Ship ship)
	{
		hControl = ShipInput.Horizontal.NONE;

		int keyLeft = UserControls.get(Control.LEFT);
		int keyRight = UserControls.get(Control.RIGHT);

		/*
		 * précondition au mouvement : que les 2 touches ne soient pas enfoncées
		 */
		if (GdxCommons.checkConcurrentKeys(keyLeft, keyRight)) return;

		float vX = ship.getVectorX();

		if (Gdx.input.isKeyPressed(keyLeft))
		{
			hControl = ShipInput.Horizontal.LEFT;
			vX -= Global.SHIP_ACCELLERATION;
			ship.setVectorX(vX < -Global.SHIP_SPEED ? -Global.SHIP_SPEED : vX);
		}
		else if (Gdx.input.isKeyPressed(keyRight))
		{
			hControl = ShipInput.Horizontal.RIGHT;
			vX += Global.SHIP_ACCELLERATION;
			ship.setVectorX(vX > Global.SHIP_SPEED ? Global.SHIP_SPEED : vX);
		}
	}

	private static void handleInertia(Ship ship)
	{
		if (hControl == ShipInput.Horizontal.NONE)
		{
			ship.setVectorX(computeInertia(ship.getVectorX()));
		}

		if (vControl == ShipInput.Vertical.NONE)
		{
			ship.setVectorY(computeInertia(ship.getVectorY()));
		}
	}

	private static float computeInertia(float v)
	{
		float result = v;
		if (v > 0)
		{
			result -= Global.SHIP_ACCELLERATION / 2;
		}
		else if (v < 0)
		{
			result += Global.SHIP_ACCELLERATION / 2;
		}
		return result;
	}
}
