package fr.fxjavadevblog.xr.commons.fonts.ttf;


/**
 * enum de fonts TTF utilisé dans le jeu.
 * Attention, ne pas confondre les fonts TTF et les fonts BITMAP.
 * 
 * @author robin
 *
 */
public enum Font
{
	SHARETECH("fonts/ShareTech-Regular.ttf"), 
	PIXEL("fonts/PixelOperatorHB.ttf"),
	COMPUTER("fonts/computer_pixel-7.ttf");

	private final String fontFile;

	private Font(String fontFile)
	{
		this.fontFile = fontFile;
	}

	public String resource()
	{
		return fontFile;
	}
}