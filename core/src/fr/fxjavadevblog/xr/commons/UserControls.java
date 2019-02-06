package fr.fxjavadevblog.xr.commons;

import java.util.EnumMap;

import com.badlogic.gdx.Input.Keys;

/**
 * représente l'ensemble des contrôles déclarées pour l'utilisateur.
 * 
 * @author robin
 *
 */
public class UserControls
{
	private static EnumMap<Control, Integer> controls = new EnumMap<>(Control.class);

	public enum Control
	{
		UP("Move Up"), 
		DOWN("Move Down"), 
		LEFT("Move Left"), 
		RIGHT("Move Right"), 
		NORMAL_FIRE("Normal Fire"), 
		CHARGE_WEAPON("Charge secondary weapon / Fire when release"), 
		SHIELD("Activation / Deactivate Shield");

		private final String action;

		private Control(String action)
		{
			this.action = action;
		}

		@Override
		public String toString()
		{
			return this.action;
		}
	}

	static
	{
		controls.put(Control.UP, Keys.UP);
		controls.put(Control.DOWN, Keys.DOWN);
		controls.put(Control.LEFT, Keys.LEFT);
		controls.put(Control.RIGHT, Keys.RIGHT);
		controls.put(Control.SHIELD, Keys.CONTROL_RIGHT);
		controls.put(Control.NORMAL_FIRE, Keys.CONTROL_LEFT);
		controls.put(Control.CHARGE_WEAPON, Keys.SHIFT_LEFT);
	}

	public static int get(Control control)
	{
		return controls.get(control);
	}

}
