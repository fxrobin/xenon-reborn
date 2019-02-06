package fr.fxjavadevblog.xr.commons.displays;

public abstract class Displayable implements Renderable
{
	private float positionX;
	private float positionY;

	public float getPositionX()
	{
		return positionX;
	}

	public void setPositionX(float positionX)
	{
		this.positionX = positionX;
	}

	public float getPositionY()
	{
		return positionY;
	}

	public void setPositionY(float positionY)
	{
		this.positionY = positionY;
	}

	public void setPosition(float x, float y)
	{
		this.positionX = x;
		this.positionY = y;
	}

}
