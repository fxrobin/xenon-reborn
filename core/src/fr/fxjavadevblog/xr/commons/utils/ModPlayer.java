package fr.fxjavadevblog.xr.commons.utils;

import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.quippy.javamod.io.ModfileInputStream;
import de.quippy.javamod.mixer.dsp.AudioProcessor;
import de.quippy.javamod.mixer.dsp.DspProcessorCallBack;
import de.quippy.javamod.multimedia.mod.ModContainer;
import de.quippy.javamod.multimedia.mod.ModMixer;
import de.quippy.javamod.multimedia.mod.loader.Module;
import de.quippy.javamod.multimedia.mod.loader.ModuleFactory;
import de.quippy.javamod.system.Helpers;

/**
 * ModPlayer (music AMIGA / ATARI-ST). Refonte basée sur JavaMod (MOD, XM, S3M,
 * etc.)
 * 
 * @see <a href="http://www.javamod.de/">http://www.javamod.de/</a>
 * 
 * @author robin
 *
 */
public final class ModPlayer implements DspProcessorCallBack
{
	private static Log log = LogFactory.getLog(ModPlayer.class);
	private static ModPlayer instance = new ModPlayer();
	private static Properties props = new Properties();

	private ModMixer mixer;
	private String resourceName;
	private float leftLevel;
	private float rightLevel;

	public static ModPlayer getInstance()
	{
		return instance;
	}

	static
	{
		try
		{
			log.info("Init ModPLayer");
			Helpers.registerAllClasses();
			log.info("Init ModPLayer : classes registered");

			props.setProperty(ModContainer.PROPERTY_PLAYER_ISP, "3");
			props.setProperty(ModContainer.PROPERTY_PLAYER_STEREO, "2");
			props.setProperty(ModContainer.PROPERTY_PLAYER_WIDESTEREOMIX, "0");
			props.setProperty(ModContainer.PROPERTY_PLAYER_NOISEREDUCTION, "0");
			props.setProperty(ModContainer.PROPERTY_PLAYER_NOLOOPS, "1");
			props.setProperty(ModContainer.PROPERTY_PLAYER_MEGABASS, "1");
			props.setProperty(ModContainer.PROPERTY_PLAYER_BITSPERSAMPLE, "16");
			props.setProperty(ModContainer.PROPERTY_PLAYER_FREQUENCY, "48000");
		}
		catch (ClassNotFoundException e)
		{
			if (log.isErrorEnabled())
			{
				log.error("Impossible d'instancier JavaMod", e);
			}
		}
	}

	private ModPlayer()
	{

	}

	public float getLeftLevel()
	{
		return leftLevel;
	}

	public float getRightLevel()
	{
		return rightLevel;
	}

	/**
	 * charge un module placé en ressources.
	 * 
	 * @param musicNameResource
	 */
	private void load(String musicNameResource)
	{
		try
		{
			resourceName = musicNameResource;
			InputStream in = ModPlayer.class.getClassLoader().getResourceAsStream(musicNameResource);
			ModfileInputStream mis = new ModfileInputStream(in, musicNameResource);
			Module module = ModuleFactory.getInstance(mis);
			if (log.isInfoEnabled()) log.info("Chargement de " + musicNameResource);
			ModContainer modContainer = new ModContainer();
			if (log.isInfoEnabled()) log.info("Module " + module.getFileName());
			mixer = modContainer.createNewMixer(module, props);
			if (log.isInfoEnabled()) log.info("Playing " + getMusicName());
		}
		catch (Exception e)
		{
			if (log.isErrorEnabled())
			{
				log.error("Impossible d'instancier JavaMod", e);
			}
		}
	}

	/**
	 * lance la lecture du module sous forme de Thread daemon, en boucle
	 */
	public void playLoop(String musicNameResource)
	{
		play(musicNameResource, true);
	}

	/**
	 * lance la lecture du module sous forme de Thread daemon.
	 */
	public synchronized void play(String musicNameResource, boolean loop)
	{
		Thread t = new Thread(() -> {
			boolean mustLoop = false;
			do
			{
				this.load(musicNameResource);
				ModMixer localMixer = mixer;
				long mixLength = localMixer.getLengthInMilliseconds();
				attachAudioProcessor(localMixer);
				log.info("Starting playback");
				localMixer.startPlayback();
				long position = localMixer.getMillisecondPosition();
				log.info(String.format("loop : %b, mixLength : %d, mixPosition %d", loop, mixLength, position));
				mustLoop = (position >= mixLength && loop);
				log.info(mustLoop && log.isInfoEnabled() ? "Loop MOD !" : "No loop");

			} while (mustLoop);
			log.info("End ModPlayer Thread");
		});
		t.setDaemon(true);
		t.start();
	}

	private void attachAudioProcessor(ModMixer mixer)
	{
		AudioProcessor audioProcessor = new AudioProcessor(1024, 60);
		audioProcessor.addListener(this);
		mixer.setAudioProcessor(audioProcessor);
		log.info("AudioProcessor attached");
	}

	/**
	 * arrête la lecture du module.
	 */
	public synchronized void stop()
	{
		if (mixer != null)
		{
			Thread t = new Thread(mixer::stopPlayback);
			t.setDaemon(true);
			t.start();
		}
	}

	/**
	 * @return le nom du module courant chargé.
	 */
	public String getMusicName()
	{
		return (mixer == null) ? "NO MODULE" : resourceName;
	}

	@Override
	public void currentSampleChanged(float[] left, float[] right)
	{
		leftLevel = calculateLevel(left);
		rightLevel = calculateLevel(right);
	}

	private float calculateLevel(float[] samples)
	{
		float currentLevel = 0;
		if (samples != null)
		{
			for (float v : samples)
			{
				if (v < 0) v *= -1f;
				currentLevel += v;
			}
		}
		return currentLevel;
	}

}
