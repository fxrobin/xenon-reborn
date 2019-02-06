package fr.fxjavadevblog.xr.commons.fonts;

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