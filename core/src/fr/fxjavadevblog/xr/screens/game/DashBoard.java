package fr.fxjavadevblog.xr.screens.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

import fr.fxjavadevblog.xr.artefacts.friendly.Ship;
import fr.fxjavadevblog.xr.artefacts.managers.ScoreManager;
import fr.fxjavadevblog.xr.commons.Global;
import fr.fxjavadevblog.xr.commons.fonts.FontUtils;
import fr.fxjavadevblog.xr.commons.fonts.TrueTypeFont;
import fr.fxjavadevblog.xr.commons.libs.TextureAsset;

/**
 * classe responsable des affichages "tableau" de bord : le score et la status
 * bar.
 * 
 * @author robin
 *
 */

public class DashBoard
{
	private static final String FMT_MSG_BAR = "XENON Reborn // FPS : %d // lifePoints : %d // Shield : %02.2f %% // lifeCount : %d";
	private static final int PADDING = 10;
	private static final int MARGE = 10;
	private int hauteurBarre;

	private Color weaponColor = new Color(0f, 1f, 0f, 0.5f);
	private GamePlayScreen gamePlayScreen;
	private Texture footer;
	private BitmapFont font = TrueTypeFont.SHARETECH_12.getFont();

	public DashBoard(GamePlayScreen gamePlayScreen)
	{
		super();
		this.gamePlayScreen = gamePlayScreen;
		this.footer = TextureAsset.FOOTER.get();
		this.hauteurBarre = (Global.height - footer.getHeight() - (MARGE * 2) - 60);
	}

	public void render()
	{

		this.renderShieldBar();
		this.renderSecondWeaponBar();
		this.gamePlayScreen.getBatch().begin();
		this.renderScore();
		this.renderStatusBar();
		this.renderLifeCount();
		this.gamePlayScreen.getBatch().end();
	}

	private void renderSecondWeaponBar()
	{
		Gdx.gl.glEnable(GL20.GL_BLEND);
		ShapeRenderer shapeRenderer = gamePlayScreen.getShapeRenderer();
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(weaponColor);
		float currentSize = gamePlayScreen.getShip().getSecondWeaponEnergy() * hauteurBarre / 100f;
		shapeRenderer.rect(Global.width - 50f - MARGE, (float) footer.getHeight() + (float) MARGE, 50, currentSize);
		shapeRenderer.end();
	}

	/**
	 * affiche le niveau d'énergie du bouclier.
	 */
	public void renderShieldBar()
	{
		Ship ship = gamePlayScreen.getShip();
		Gdx.gl.glEnable(GL20.GL_BLEND);
		ShapeRenderer shapeRenderer = gamePlayScreen.getShapeRenderer();
		Color color = computeShieldBarColor(ship);
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(color);
		float currentSize = gamePlayScreen.getShip().getShieldEnergy() * hauteurBarre / 100f;
		shapeRenderer.rect(MARGE, footer.getHeight() + (float) MARGE, 50, currentSize);
		shapeRenderer.end();
	}

	/**
	 * @param ship
	 * @return
	 */
	public Color computeShieldBarColor(Ship ship)
	{
		/*
		 * une jolie couleur dégradée du vert au rouge en fonction de la valeur du
		 * bouclier. la teinte rouge est inversée par rapport à la teinte verte et
		 * transparence à 50 %
		 */
		float currentValue = ship.getShieldEnergy() / 100f;
		return new Color(1f - currentValue, currentValue, 0f, 0.5f);
	}

	private void renderScore()
	{
		SpriteBatch batch = gamePlayScreen.getBatch();
		FontUtils.print(batch, 5, Global.height - 43f, String.format("%06d", ScoreManager.getInstance().getScore()));
	}

	private void renderLifeCount()
	{
		SpriteBatch batch = gamePlayScreen.getBatch();
		Texture life = TextureAsset.LIFE.get();

		int offset = Global.width - (gamePlayScreen.getShip().getLifeCount() * (life.getWidth() + PADDING));

		for (int i = 0; i < gamePlayScreen.getShip().getLifeCount(); i++)
		{
			batch.draw(TextureAsset.LIFE.get(), (float) offset + (i * (life.getWidth() + PADDING)), (float) Global.height - (life.getHeight() + PADDING));
		}
	}

	private void renderStatusBar()
	{
		SpriteBatch batch = gamePlayScreen.getBatch();
		int fps = Gdx.graphics.getFramesPerSecond();
		batch.draw(footer, 0, 0, Global.width, footer.getHeight());
		Ship ship = gamePlayScreen.getShip();
		String titleBar = String.format(FMT_MSG_BAR, fps, ship.getLifePoints(), ship.getShieldEnergy(), ship.getLifeCount());
		font.draw(batch, titleBar, 6, 6 + font.getCapHeight());
	}

}
