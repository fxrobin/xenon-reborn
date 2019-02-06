package fr.fxjavadevblog.xr.commons.fonts;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * représente une font bitmap, chargée à partir d'une texture et d'une char-map.
 * 
 * @author robin
 *
 */
public class BitmapFont
{

	private Map<Character, TextureRegion> regions = new HashMap<>();

	private BitmapFont()
	{
		// protection du constructeur.
	}

	public static BitmapFont build(Texture t, int width, int height, String charMap)
	{

		BitmapFont bitmapFont = new BitmapFont();
		int offset = 0;
		for (char c : charMap.toCharArray())
		{
			TextureRegion tr = new TextureRegion(t, offset, 0, width, height);
			bitmapFont.regions.put(c, tr);
			offset += width;
		}
		return bitmapFont;
	}

	public TextureRegion getChar(char c)
	{
		return regions.get(c);
	}
}
