package fr.fxjavadevblog.xr.commons.utils;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.utils.Timer;

public class MusicPlayer
{
	public static final float VOLUME_MAX = 1.0f;
	public static final float MUSIC_FADE_STEP = 0.01f;
	private static MusicPlayer player = new MusicPlayer();
	private Music music;

	public static MusicPlayer getPlayer()
	{
		return player;
	}

	public void play(Music music)
	{
		this.music = music;
		music.play();
	}

	public void stop()
	{
		music.stop();
	}

	public void fadeOut()
	{
		Timer.schedule(new Timer.Task()
		{
			@Override
			public void run()
			{
				if (music != null)
				{
					if (music.getVolume() >= MUSIC_FADE_STEP)
					{
						music.setVolume(music.getVolume() - MUSIC_FADE_STEP);
					}
					else
					{
						music.stop();
						this.cancel();
					}
				}
			}
		}, 0f, MUSIC_FADE_STEP);
	}

	public void fadeIn()
	{
		music.setVolume(0);
		music.play();

		Timer.schedule(new Timer.Task()
		{
			@Override
			public void run()
			{
				if (music.getVolume() < VOLUME_MAX)
					music.setVolume(music.getVolume() + MUSIC_FADE_STEP);
				else
				{

					this.cancel();
				}
			}
		}, 0f, MUSIC_FADE_STEP);
	}

	public void loop(Music music)
	{
		music.setLooping(true);
		this.play(music);
	}

	public void loop(Music music, float f)
	{
		music.setVolume(f);
		this.loop(music);
	}

	public boolean isPlaying()
	{
		return music.isPlaying();
	}
}
