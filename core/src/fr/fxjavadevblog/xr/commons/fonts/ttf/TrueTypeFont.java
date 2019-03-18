package fr.fxjavadevblog.xr.commons.fonts.ttf;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.utils.Disposable;

import fr.fxjavadevblog.xr.commons.libs.FontAsset;

public enum TrueTypeFont implements Disposable
{
	DEFAULT, 
	SHARETECH_12(FontAsset.SHARETECH.resource(), 12), 
	SHARETECH_30(FontAsset.SHARETECH.resource(), 30), 
	SHARETECH_30_BLACK(FontAsset.SHARETECH.resource(), 30, Color.BLACK), 
	PIXEL_30_BLACK(FontAsset.PIXEL.resource(), 30,	Color.BLACK), 
	COMPUTER_30_WHITE(FontAsset.COMPUTER.resource(), 40, Color.WHITE), 
	COMPUTER_30_BLACK(FontAsset.COMPUTER.resource(), 40, Color.BLACK);

	private Log log = LogFactory.getLog(this.getClass());
	private BitmapFont font;

	private TrueTypeFont()
	{
		font = new BitmapFont();
	}

	private TrueTypeFont(String fontResource, int size)
	{
		this(fontResource, size, Color.WHITE);
	}

	private TrueTypeFont(String fontResource, int size, Color color)
	{
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(fontResource));
		FreeTypeFontParameter parameters = new FreeTypeFontParameter();
		parameters.size = size;
		parameters.color = color;
		font = generator.generateFont(parameters);
	}

	public BitmapFont getFont()
	{
		return font;
	}

	@Override
	public void dispose()
	{
		log.info("DISPOSE FONTS ...");
		font.dispose();
		log.info("DISPOSE DONE ...");
	}

}
