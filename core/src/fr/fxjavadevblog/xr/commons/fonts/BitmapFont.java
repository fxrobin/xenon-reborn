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
	private int width;
	private int height;

	private BitmapFont(Texture t, int width, int height, String charMap)
	{
		this.width = width;
		this.height = height;
		int offset = 0;
		for (char c : charMap.toCharArray())
		{
			TextureRegion tr = new TextureRegion(t, offset, 0, width, height);
			this.regions.put(c, tr);
			offset += width;
		}
	}

	public static BitmapFont build(Texture t, int width, int height, String charMap)
	{
		return new BitmapFont(t, width, height, charMap);
	}

	public TextureRegion getChar(char c)
	{
		return regions.get(c);
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public int getHeight()
	{
		return height;
	}
}
