package fr.fxjavadevblog.xr.commons.fonts.ttf;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

public class GdxTrueTypeString
{
	private BitmapFont font;
	private GlyphLayout layout;
	private String text;

	public GdxTrueTypeString(BitmapFont font, String text)
	{
		super();
		this.font = font;
		this.layout = new GlyphLayout();
		this.text = text;
	}

	public void setText(String text)
	{
		this.text = text;
		layout.setText(font, text);
	}

	public float getWidth()
	{
		return layout.width;
	}

	public float getHeight()
	{
		return layout.height;
	}

	public void draw(Batch batch, float x, float y)
	{
		font.draw(batch, text, x, y);
	}
}
