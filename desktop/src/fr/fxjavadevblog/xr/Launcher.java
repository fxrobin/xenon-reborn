package fr.fxjavadevblog.xr;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.Color;

import fr.fxjavadevblog.xr.commons.Global;
import fr.fxjavadevblog.xr.screens.MainControler;

/**
 * lanceur principal de Xenon_Reborn. Ce lanceur configure Lwjgl et libgdx. Un
 * singleton (instance unique) de MainControler est obtenue et devient le point
 * d'entrée du jeu vidéo.
 * 
 * @author CDT RBN
 *
 */

public final class Launcher
{
	/**
	 * point d'entrée du jeu.
	 * 
	 * @param arg
	 */
	public static void main(final String... arg)
	{
		final LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = Global.width;
		config.height = Global.height;
		config.forceExit = false;
		config.vSyncEnabled = true;
		config.fullscreen = false;
		config.foregroundFPS = 60;
		config.resizable = false;
		config.title = "Xenon Reborn - www.fxjavadevblog.fr";
		config.useGL30 = false;
		config.initialBackgroundColor = Color.BLACK;
		new LwjglApplication(MainControler.getInstance(), config);
	}

	private Launcher()
	{
		// protection.
	}
}
