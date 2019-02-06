package fr.fxjavadevblog.xr.commons.libs;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public final class AssetLib
{
	// SINGLETON
	private static AssetLib assetLib = new AssetLib();

	public static AssetLib getInstance()
	{
		return assetLib;
	}

	/**
	 * gestionnaire d'assets de LibGDX.
	 */
	private AssetManager manager = new AssetManager();

	/**
	 * logger
	 */
	private Log log = LogFactory.getLog(this.getClass());

	public AssetLib()
	{
		// on force d'emblée la lecture bloquante du son d'intro.
		loadAndWait(Texture.class, TextureAsset.BACKGROUND_BOMBING_PIXELS);
		loadAllAsync();
	}

	/**
	 * charge tous les assets.
	 */
	public void loadAllAsync()
	{
		register(Sound.class, (Object[]) SoundAsset.values());
		register(Music.class, (Object[]) MusicAsset.values());
		register(Texture.class, (Object[]) TextureAsset.values());
		register(Texture.class, (Object[]) AnimationAsset.values());
	}

	public void register(Class<?> clazz, Object... assets)
	{
		for (Object asset : assets)
		{
			String fullFileName = asset.toString();
			if (!manager.isLoaded(fullFileName))
			{
				manager.load(fullFileName, clazz);
				log.info("Loading : " + fullFileName);
			}
			else
			{
				log.info("Already loaded : " + fullFileName);
			}
		}
	}

	public AssetManager getManager()
	{
		return manager;
	}

	public boolean isFullyLoaded()
	{
		return manager.update();
	}

	public int getProgress()
	{
		return (int) (manager.getProgress() * 100);
	}

	public void loadAndWait(Class<?> clazz, Object fileName)
	{
		manager.load(fileName.toString(), clazz);
		manager.finishLoadingAsset(fileName.toString());
	}

	public <T> T get(Object fileName, Class<T> clazz)
	{
		return manager.get(fileName.toString(), clazz);
	}

	public void disposeAll()
	{
		manager.clear();
	}
}
