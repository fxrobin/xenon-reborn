package fr.fxjavadevblog.xr.commons.utils;

import com.kennycason.gdx.controller.Controller;
import com.kennycason.gdx.controller.controls.Controls;

/**
 * DP adapteur pour un Controller afin d'ajouter la capacité "isRealeased" sur la 
 * lib de Kenny Cason "gdx-controller".
 * 
 * @author robin
 *
 * @param <V>
 */

public class ControllerAdapter<V  extends Controls> extends Controller<V>
{
	private Controller <V> adaptee;
	
	public ControllerAdapter(Controller<V> adaptee)
	{
		super();
		this.adaptee = adaptee;
	}

	@Override
	public boolean isPressed(V control)
	{
		return adaptee.isPressed(control);
	}
	
	public boolean isReleased(V control)
	{
		return this.adaptee.when(control) > 0L && !adaptee.isPressed(control);
	}

	@Override
	public float getAxis(V control)
	{
		return adaptee.getAxis(control);
	}

}
