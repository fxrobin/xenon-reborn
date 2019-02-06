package fr.fxjavadevblog.xr.screens;

import java.util.function.BiFunction;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import fr.fxjavadevblog.xr.screens.game.GamePlayScreen;
import fr.fxjavadevblog.xr.screens.loading.LoadingScreen;
import fr.fxjavadevblog.xr.screens.menu.MenuScreen;

/**
 * enum complexe avec lambda pour la création des écrans associés à leur
 * constructeur.
 * 
 * @author robin
 *
 */
public enum XenonScreen
{
	LOADING(LoadingScreen::new),
	MENU(MenuScreen::new),
	GAME_PLAY(GamePlayScreen::new);

	private BiFunction<XenonControler, SpriteBatch, Screen> supplier;

	private XenonScreen(BiFunction<XenonControler, SpriteBatch, Screen> supplier)
	{
		this.supplier = supplier;
	}

	public Screen createScreen(XenonControler controler)
	{
		return supplier.apply(controler, controler.getBatch());
	}
}