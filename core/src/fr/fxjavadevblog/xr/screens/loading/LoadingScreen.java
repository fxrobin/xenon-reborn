package fr.fxjavadevblog.xr.screens.loading;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Interpolation;

import fr.fxjavadevblog.xr.commons.Global;
import fr.fxjavadevblog.xr.commons.SingleExecutor;
import fr.fxjavadevblog.xr.commons.displays.Interpolator;
import fr.fxjavadevblog.xr.commons.fonts.TrueTypeFont;
import fr.fxjavadevblog.xr.commons.libs.AssetLib;
import fr.fxjavadevblog.xr.commons.libs.ModAsset;
import fr.fxjavadevblog.xr.commons.libs.TextureAsset;
import fr.fxjavadevblog.xr.commons.utils.GdxCommons;
import fr.fxjavadevblog.xr.commons.utils.ModPlayer;
import fr.fxjavadevblog.xr.screens.AbstractScreen;
import fr.fxjavadevblog.xr.screens.XenonControler;
import fr.fxjavadevblog.xr.screens.XenonScreen;

/**
 * écran d'accueil de chargement des assets.
 * 
 * @author robin
 *
 */
public class LoadingScreen extends AbstractScreen
{
	private static final String MSG_LOADED = "All resources are loaded / Press SpaceBar / F1 for fullscreen";
	private Log log = LogFactory.getLog(this.getClass());
	
	private AssetLib assetLib = AssetLib.getInstance();
	private ModPlayer modPlayer = ModPlayer.getInstance();
	
	private GlyphLayout layout;
	private BitmapFont font = TrueTypeFont.COMPUTER_30_BLACK.getFont();
	private Texture logo = TextureAsset.BACKGROUND_BOMBING_PIXELS.get();
	
	private SingleExecutor singleExecutor;
	private Interpolator interpolatorX = new Interpolator(Interpolation.sine, 2f, 20, 0);
	private Interpolator interpolatorY = new Interpolator(Interpolation.pow2, 1f, 20, 0);

	public LoadingScreen(XenonControler controler, SpriteBatch batch)
	{
		super(controler, batch);
		ModPlayer.getInstance();
		log.info("Instanciation de LoadingScreen");
		layout = new GlyphLayout();
		singleExecutor = new SingleExecutor(() -> {
			this.getControler().showScreen(XenonScreen.MENU);
			modPlayer.stop();
		});
	}

	@Override
	public void show()
	{
		modPlayer.playLoop(ModAsset.INTRO.toString());
	}

	@Override
	public void render(float delta)
	{
		GdxCommons.clearScreen(Color.WHITE);
		this.checkInput();
		this.getBatch().begin();
		this.renderBackground(delta);
		this.renderProgress();
		this.getBatch().end();
		this.renderMusicBars();
	}

	private void renderMusicBars()
	{
		Gdx.gl.glEnable(GL20.GL_BLEND);
		ShapeRenderer shapeRenderer = this.getShapeRenderer();
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(Color.RED);
		shapeRenderer.rect(10, 10, 50, modPlayer.getLeftLevel());
		shapeRenderer.setColor(Color.GREEN);
		shapeRenderer.rect(Global.width - 50f - 10, 10, 50, modPlayer.getRightLevel());
		shapeRenderer.end();
	}

	private void renderProgress()
	{
		String message = assetLib.isFullyLoaded() ? MSG_LOADED : String.format("LOADING ... %02d %%", assetLib.getProgress());
		layout.setText(font, message);
		font.draw(this.getBatch(), message, (Global.width - layout.width) / 2, 80);
	}

	private void renderBackground(float delta)
	{
		float positionX = interpolatorX.calculate(delta);
		float positionY = interpolatorY.calculate(delta);
		this.getBatch().draw(logo, positionX, positionY, Global.width, Global.height);
	}

	private void checkInput()
	{
		if (Gdx.input.isKeyJustPressed(Keys.F1))
		{
			GdxCommons.switchFullScreen();
		}

		if (Gdx.input.isKeyJustPressed(Keys.SPACE) && assetLib.isFullyLoaded())
		{
			singleExecutor.execute(); // n'execute la méthode qu'une seule fois.
		}
	}
}
