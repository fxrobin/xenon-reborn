package fr.fxjavadevblog.xr.screens;

import com.badlogic.gdx.Screen;

import fr.fxjavadevblog.xr.screens.game.GamePlayScreen;
import fr.fxjavadevblog.xr.screens.loading.LoadingScreen;
import fr.fxjavadevblog.xr.screens.menu.MenuScreen;

/**
 * Ancienne factory d'ecran, non fondée sur les enums. (pour la démo)
 * 
 * @author robin
 *
 */
public final class XenonScreenFactory
{
	public static final int LOADING_SCREEN = 0;
	public static final int MENU_SCREEN = 1;
	public static final int GAME_SCREEN = 2;
	
	private XenonScreenFactory()
	{
		// protection de la factory
	}
	
	public static Screen createScreen(int screenType, XenonControler controler)
	{
		if (screenType < 0 || screenType > 2)
		{
			throw new IllegalArgumentException("XenonScreenType not supported : " + screenType);
		}
		else
		{
			switch (screenType)
			{
				case 0:
					return new LoadingScreen(controler, controler.getBatch());
				case 1 : 
					return new MenuScreen(controler, controler.getBatch());
				case 2 : 
					return new GamePlayScreen(controler, controler.getBatch());
				default:
					return null; // ne devrait pas être le cas.
			}
		}
	}
}
