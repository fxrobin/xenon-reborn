package fr.fxjavadevblog.xr.commons.fonts;

import java.util.Arrays;
import java.util.Objects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import fr.fxjavadevblog.xr.commons.displays.Displayable;

public class GdxBitmapString extends Displayable
{
	// chaque lettre sera un sprite avec sa propre position.
	private Sprite[] sprites;

	// taille totale de la chaine
	private int width;

	// hauteur de la font
	private int letterWidth;

	private float scale;
	
	private FontUtils font;

	public GdxBitmapString(FontUtils font, String text, float scale)
	{
		super();
		this.font = font;
		this.scale = scale;
		this.width = font.getWidth(text);
		this.letterWidth = font.getFontWidth();
		this.populateSprites(text);
	}

	public GdxBitmapString(FontUtils font, String text)
	{
		this(font, text, 1.0f);
	}

	/*
	 * initialisation du tableau de sprites en fonction du TextureRegion calculés
	 */
	private void populateSprites(String text)
	{
		sprites = allocateSpriteArray(text);
		int spriteIndex = 0;
		for (char currentChar : text.toCharArray())
		{
			TextureRegion tr = font.getTextureRegionOfChar(currentChar);
			Sprite s = (tr != null) ? new Sprite(tr) : null;
			if (s != null)
			{
				s.setScale(scale);
			}
			sprites[spriteIndex++] = s;
		}
		this.width = (text.length() * (int) (letterWidth * scale));
	}

	private Sprite[] allocateSpriteArray(String text)
	{
		return new Sprite[text.length()];
	}

	public int getWidth()
	{
		return width;
	}

	@Override
	public void setPosition(float x, float y)
	{
		super.setPosition(x, y);
		// calcul de la position X de chacun des sprites.
		float offsetX = this.getPositionX();
		for (Sprite s : sprites)
		{
			if (s != null)
			{
				s.setX(offsetX);
				s.setY(y);
			}
			offsetX += letterWidth * scale;
		}
		
	}

	@Override
	public void render(SpriteBatch batch, float delta)
	{
		// Arrays.stream(sprites).filter(Objects::nonNull).forEach(s -> s.draw(batch));
		
		for(Sprite s : sprites)
		{
			if (s!=null)
			{
				s.draw(batch);
			}
		}
		
	}
}
