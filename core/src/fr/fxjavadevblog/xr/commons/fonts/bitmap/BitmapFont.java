package fr.fxjavadevblog.xr.commons.fonts.bitmap;

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

	private Map<Character, TextureRegion> charTextureRegions = new HashMap<>();
	private int charWidth;
	private int charHeight;

	private BitmapFont(Texture t, int width, int height, String charMap)
	{
		this.charWidth = width;
		this.charHeight = height;
		int offset = 0;
		for (char c : charMap.toCharArray())
		{
			TextureRegion tr = new TextureRegion(t, offset, 0, width, height);
			this.charTextureRegions.put(c, tr);
			offset += width;
		}
	}

	public static BitmapFont build(Texture t, int charWidth, int height, String charMap)
	{
		return new BitmapFont(t, charWidth, height, charMap);
	}

	public TextureRegion getTextureOf(char c)
	{
		return charTextureRegions.get(c);
	}
	
	public int getCharWidth()
	{
		return charWidth;
	}
	
	public int getCharHeight()
	{
		return charHeight;
	}
}
