package fr.fxjavadevblog.xr.screens.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import fr.fxjavadevblog.xr.commons.Global;
import fr.fxjavadevblog.xr.commons.libs.TextureAsset;

public final class BackgroundParallaxScrolling
{
	private float position;
	private float speed;
	private SpriteBatch batch;
	private TiledMapScrolling tiledMapScrolling;
	private boolean displayingTiledMap;

	private static BackgroundParallaxScrolling instance = new BackgroundParallaxScrolling();

	public static BackgroundParallaxScrolling getInstance()
	{
		return instance;
	}

	private BackgroundParallaxScrolling()
	{
		/* protection */
	}

	public void init(SpriteBatch batch)
	{
		this.position = 0;
		this.speed = 60f;
		tiledMapScrolling = new TiledMapScrolling(speed * 1.5f);
		displayingTiledMap = false;
		this.batch = batch;
	}

	public void checkInput()
	{
		if (Gdx.input.isKeyPressed(Keys.PAGE_UP))
		{
			speed += 0.5f;
			tiledMapScrolling.setSpeed(speed * 1.5f);
		}

		if (Gdx.input.isKeyPressed(Keys.PAGE_DOWN))
		{
			speed -= 0.5f;
			tiledMapScrolling.setSpeed(speed * 1.5f);
		}

		if (Gdx.input.isKeyJustPressed(Keys.T)) displayingTiledMap = !displayingTiledMap;
	}

	public void render(float delta)
	{
		position -= delta * speed;
		Texture space = TextureAsset.BACKGROUND_SPACE.get();
		Texture leftbg = TextureAsset.BACKGROUND_LEFT.get();
		Texture rightbg = TextureAsset.BACKGROUND_RIGHT.get();
		batch.draw(space, 0f, 0f, 0, (int) position, Global.width, Global.height);
		displayTileMapIfActivated(delta);
		batch.draw(leftbg, 0f, 0f, 0, (int) position * 2, leftbg.getWidth(), Global.height);
		batch.draw(rightbg, (float) Global.width - rightbg.getWidth(), 0f, 0, (int) position * 2, Global.width, Global.height);
	}

	private void displayTileMapIfActivated(float delta)
	{
		tiledMapScrolling.update(delta);

		if (displayingTiledMap)
		{
			batch.end();
			tiledMapScrolling.render();
			batch.begin();
		}
	}

	public float getSpeed()
	{
		return speed;
	}

}
