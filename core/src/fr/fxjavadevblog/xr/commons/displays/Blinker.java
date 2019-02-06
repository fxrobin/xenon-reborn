package fr.fxjavadevblog.xr.commons.displays;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import fr.fxjavadevblog.xr.commons.SingleExecutor;
import fr.fxjavadevblog.xr.commons.utils.DeltaTimeAccumulator;

/**
 * permet d'encapsuler la logique de clignotement.
 * 
 * @author robin
 *
 */
public class Blinker implements Renderable
{
	private Renderable renderable;
	private DeltaTimeAccumulator accumulator;
	private boolean displayed;
	private float totalBlinkingTime;
	private boolean hidden;
	private SingleExecutor singleExecutor;

	public boolean isHidden()
	{
		return hidden;
	}

	public void hide()
	{
		hidden = true;
	}

	public void show()
	{
		hidden = false;
		restart();
	}

	private void switchDiplay()
	{
		displayed = !displayed;
	}

	public Blinker(float blinkTime, Renderable renderable)
	{
		this(blinkTime, renderable, Float.MAX_VALUE);
	}

	public Blinker(float blinkTime, Renderable renderable, float totalBlinkingTime)
	{
		this(blinkTime, renderable, totalBlinkingTime, null);
	}

	public Blinker(float blinkRate, Renderable renderable, float totalBlinkingTime, Runnable action)
	{
		this.accumulator = new DeltaTimeAccumulator(blinkRate, this::switchDiplay);
		this.renderable = renderable;
		this.totalBlinkingTime = totalBlinkingTime;
		if (action != null)
		{
			this.singleExecutor = new SingleExecutor(action);
		}
		else
		{
			this.singleExecutor = null;
		}
	}

	@Override
	public void render(SpriteBatch batch, float delta)
	{
		if (!hidden)
		{
			accumulator.addAndCheck(delta);
			// si c'est activé pour l'affichage ou si le temps de blinking est
			// dépassé, alors on affiche
			if (displayed || this.isBlinkingFinished())
			{
				renderable.render(batch, delta);
			}

			if (this.isBlinkingFinished() && singleExecutor != null)
			{
				singleExecutor.execute();
			}
		}
	}

	public boolean isBlinkingFinished()
	{
		return accumulator.getAccumulatedTime() > totalBlinkingTime;
	}

	public void restart()
	{
		this.accumulator.restart();
	}

	public void setRenderable(Renderable r)
	{
		this.renderable = r;
	}

}
