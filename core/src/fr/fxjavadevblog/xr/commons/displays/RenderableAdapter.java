package fr.fxjavadevblog.xr.commons.displays;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class RenderableAdapter implements Renderable
{
	private Sprite sprite;

	public RenderableAdapter()
	{

	}

	public RenderableAdapter(Sprite sprite)
	{
		super();
		this.sprite = sprite;
	}

	public void setSprite(Sprite sprite)
	{
		this.sprite = sprite;
	}

	@Override
	public void render(SpriteBatch batch, float delta)
	{
		sprite.draw(batch);
	}

}
