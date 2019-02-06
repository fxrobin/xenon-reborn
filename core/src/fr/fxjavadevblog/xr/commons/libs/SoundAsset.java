package fr.fxjavadevblog.xr.commons.libs;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Disposable;

import fr.fxjavadevblog.xr.commons.utils.GdxCommons;

public enum SoundAsset implements Disposable
{
	SHOOT("shoot.mp3"), 
	BIG_SHOOT("big-shoot.mp3"), 
	SHIELD_UP("shield_up.mp3"), 
	SHIELD_DOWN("shield_down.mp3"), 
	SHIELD_ACTIVATED("shield-activated.mp3"), 
	CLIC("clic.mp3"), EXPLOSION("explosion.mp3"), 
	SHIP_EXPLOSION("ship_explosion.mp3"), 
	GAME_OVER("game-over.mp3"), 
	BONUS("bonus.mp3");

	private final String fileName;
	private Sound sound;

	private SoundAsset(String fileName)
	{
		this.fileName = "sounds/" + fileName;
	}

	@Override
	public String toString()
	{
		return this.fileName;
	}

	public Sound getSound()
	{
		if (sound == null)
		{
			sound = AssetLib.getInstance().get(this, Sound.class);
		}
		return sound;
	}

	public void play()
	{
		this.getSound().play();
	}

	public void stop()
	{
		this.getSound().stop();
	}

	public void loop()
	{
		this.getSound().loop();
	}

	public void loop(float f)
	{
		this.getSound().loop(f);
	}

	@Override
	public void dispose()
	{
		if (sound != null)
		{
			this.sound.dispose();
		}
	}

	public static void disposeAll()
	{
		GdxCommons.disposeAll(SoundAsset.values());
	}
}