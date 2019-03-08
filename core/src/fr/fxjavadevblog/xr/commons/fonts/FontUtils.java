package fr.fxjavadevblog.xr.commons.fonts;

import java.io.IOException;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import fr.fxjavadevblog.xr.commons.libs.AssetLib;

public enum FontUtils
{
	FONT_GREEN("/fonts/font-green.properties"), 
	FONT_XENON("/fonts/font-xenon-2.properties"), 
	FONT_BLUE("/fonts/font-blue.properties");

	private final Log log = LogFactory.getLog(FontUtils.class);


	private BitmapFont bitmapFont;

	private FontUtils(String propertyFile)
	{
		Properties config = new Properties();
		try
		{
			config.load(FontUtils.class.getResourceAsStream(propertyFile));
			Texture font = AssetLib.getInstance().get(config.getProperty("font-file"), Texture.class);
			int fontWidth = Integer.parseInt(config.getProperty("letter-width"));
			int fontHeight = Integer.parseInt(config.getProperty("letter-height"));
			String stringMap = config.getProperty("string-map");
			bitmapFont = BitmapFont.build(font, fontWidth, fontHeight, stringMap);
			
			log.info("Font loaded : " + propertyFile);
			log.info("- font-file : " + config.getProperty("font-file"));
			log.info("- letter-width : " + fontWidth);
			log.info("- letter-height : " + fontHeight);
			log.info("- string-map : " + stringMap);
			
			
		}
		catch (IOException e)
		{
			log.error("Impossible de lire le fichier de ressource de font : " + propertyFile);
		}

	}

	public int getWidth(String txt)
	{
		return txt.length() * bitmapFont.getWidth();
	}

	public int getHeight(String txt) /* NOSONAR */
	{
		return bitmapFont.getHeight();
	}

	public int getFontWidth()
	{
		return bitmapFont.getWidth();
	}

	protected void print(Batch b, float x, float y, String txt)
	{
		for (int i = 0; i < txt.length(); i++)
		{
			print(b, x + (i * bitmapFont.getWidth()), y, txt.charAt(i));
		}
	}

	protected void print(Batch batch, float positionX, float positionY, char character)
	{
		TextureRegion tr = bitmapFont.getChar(character);
		if (tr != null) batch.draw(tr, positionX, positionY);
	}

	protected TextureRegion getTextureRegionOfChar(char character)
	{
		return bitmapFont.getChar(character);
	}

}
