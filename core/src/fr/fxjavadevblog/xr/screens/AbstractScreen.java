package fr.fxjavadevblog.xr.screens;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.kennycason.gdx.controller.Controller;

import fr.fxjavadevblog.xr.commons.utils.ControllerFactory;
import fr.fxjavadevblog.xr.commons.utils.GameControls;

public abstract class AbstractScreen implements Screen
{
	private final Log log = LogFactory.getLog(AbstractScreen.class);
	private static final Controller<GameControls> userInput = ControllerFactory.buildMultiController();

	private final SpriteBatch batch;
	private XenonControler controler;

	public AbstractScreen(XenonControler controler, SpriteBatch batch)
	{
		this.controler = controler;
		this.batch = batch;
	}

	public SpriteBatch getBatch()
	{
		return batch;
	}

	public XenonControler getControler()
	{
		return controler;
	}

	public ShapeRenderer getShapeRenderer()
	{
		return controler.getShapeRenderer();
	}

	public static Controller<GameControls> getUserInput()
	{
		return userInput;
	}
	
	@Override
	public void hide()
	{
		log.info("HIDE SCREEN: NO ACTION");
	}

	@Override
	public void pause()
	{
		log.info("PAUSE SCREEN : NO ACTION");
	}

	@Override
	public void resize(int arg0, int arg1)
	{
		log.info("RESIZE SCREEN : NO ACTION");
	}

	@Override
	public void resume()
	{
		log.info("RESUME SCREEN : NO ACTION");
	}

	@Override
	public void dispose()
	{
		log.info("DISPOSE SCREEN : NO ACTION");
	}
}
