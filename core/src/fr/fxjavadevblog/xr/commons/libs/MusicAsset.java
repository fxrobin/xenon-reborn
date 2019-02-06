package fr.fxjavadevblog.xr.commons.libs;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.utils.Disposable;

import fr.fxjavadevblog.xr.commons.utils.GdxCommons;

public enum MusicAsset implements Disposable
{
	BLANK_MP3("blank.mp3");

	private final String fileName;
	private Music music;

	private MusicAsset(String fileName)
	{
		this.fileName = "musics/" + fileName;
	}

	@Override
	public String toString()
	{
		return this.fileName;
	}

	public Music getMusic()
	{
		if (music == null)
		{
			music = AssetLib.getInstance().get(this, Music.class);
		}
		return music;
	}

	@Override
	public void dispose()
	{
		if (music != null)
		{
			this.music.dispose();
		}
	}

	public static void disposeAll()
	{
		GdxCommons.disposeAll(MusicAsset.values());
	}

}