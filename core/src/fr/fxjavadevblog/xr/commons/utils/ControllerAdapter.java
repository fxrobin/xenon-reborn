package fr.fxjavadevblog.xr.commons.utils;

import com.kennycason.gdx.controller.Controller;
import com.kennycason.gdx.controller.controls.Controls;

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
		if (this.adaptee.when(control) > 0L && !adaptee.isPressed(control))
		{
			return true;
		}
		else
		{
			return false;
		}
		
	}

	@Override
	public float getAxis(V control)
	{
		return adaptee.getAxis(control);
	}

}
