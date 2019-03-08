package fr.fxjavadevblog.xr.commons.libs;

import java.util.Arrays;

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

	private AssetLib()
	{
		// on force d'emblée la lecture bloquante du logo d'intro
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

	/**
	 * registers all the assets that have not been loaded yet.
	 * 
	 * @param clazz
	 * 		type of the assets
	 * @param assets
	 * 		assets array
	 */
	private void register(Class<?> clazz, Object... assets)
	{	
		Arrays.stream(assets)
		      .map(Object::toString)
		      .filter(this::isNotLoaded)
		      .forEach(fullFileName -> 
				       {
				    	manager.load(fullFileName, clazz);
				      	log.info("Loading : " + fullFileName);
				       });
	}
	
	/**
	 * convenient method to negate the predicate if an asset has been already running.
	 * Used in the method reference filter in "register".
	 * 
	 * @param fullFileName
	 * 		resource full path	
	 * @return 
	 * 		true if the ressource has not been loaded yet
	 */
	private boolean isNotLoaded(String fullFileName)
	{
		return !manager.isLoaded(fullFileName);
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
		log.info("DisposeAll");
		manager.clear();
	}
}
