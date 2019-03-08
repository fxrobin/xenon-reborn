package fr.fxjavadevblog.xr.screens.game;

import java.util.LinkedList;
import java.util.List;

import fr.fxjavadevblog.xr.artefacts.Artefact;
import fr.fxjavadevblog.xr.artefacts.ArtefactsScene;
import fr.fxjavadevblog.xr.artefacts.friendly.ship.Ship;
import fr.fxjavadevblog.xr.artefacts.friendly.ship.ShipHandler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import fr.fxjavadevblog.xr.artefacts.managers.BonusManager;
import fr.fxjavadevblog.xr.artefacts.managers.CollisionManager;
import fr.fxjavadevblog.xr.artefacts.managers.EnemyManager;
import fr.fxjavadevblog.xr.artefacts.managers.ExplosionManager;
import fr.fxjavadevblog.xr.artefacts.managers.ProjectileManager;
import fr.fxjavadevblog.xr.artefacts.managers.ScoreManager;
import fr.fxjavadevblog.xr.commons.Global;
import fr.fxjavadevblog.xr.commons.SingleExecutor;
import fr.fxjavadevblog.xr.commons.displays.Blinker;
import fr.fxjavadevblog.xr.commons.fonts.FontUtils;
import fr.fxjavadevblog.xr.commons.fonts.GdxBitmapString;
import fr.fxjavadevblog.xr.commons.libs.ModAsset;
import fr.fxjavadevblog.xr.commons.libs.SoundAsset;
import fr.fxjavadevblog.xr.commons.libs.TextureAsset;
import fr.fxjavadevblog.xr.commons.utils.GdxCommons;
import fr.fxjavadevblog.xr.commons.utils.ModPlayer;
import fr.fxjavadevblog.xr.screens.AbstractScreen;
import fr.fxjavadevblog.xr.screens.XenonControler;
import fr.fxjavadevblog.xr.screens.XenonScreen;

public class GamePlayScreen extends AbstractScreen implements ArtefactsScene
{

	private Log log = LogFactory.getLog(this.getClass());

	private BackgroundParallaxScrolling scrolling;
	private DashBoard dashBoard;

	private EnemyManager em;
	private CollisionManager cm;
	private Ship ship;

	private Blinker msgBlinker;
	private SingleExecutor gameOverSoundExecutor;

	public GamePlayScreen(XenonControler controler, SpriteBatch batch)
	{
		super(controler, batch);
		log.info("Instanciation de GamePlay");
		scrolling = BackgroundParallaxScrolling.getInstance();
		scrolling.init(batch);
		em = EnemyManager.getInstance();
		cm = CollisionManager.getInstance();
		dashBoard = new DashBoard(this);
		ship = new Ship();
		ship.addObserver(ShipStateObserver::shipExplosion);
		this.createBlinkingMessage();
		msgBlinker.hide();
		gameOverSoundExecutor = new SingleExecutor(SoundAsset.GAME_OVER::play);
	}

	private void createBlinkingMessage()
	{
		GdxBitmapString gameOverMessage = new GdxBitmapString(FontUtils.FONT_BLUE, "GAME OVER", 2f);
		gameOverMessage.setPosition((Global.width - gameOverMessage.getWidth()) / 2f, 400);
		msgBlinker = new Blinker(1f, gameOverMessage, 5, this::closeGame);
	}

	private void closeGame()
	{
		this.getControler().showScreen(XenonScreen.MENU);
	}

	@Override
	public void render(float delta)
	{
		this.checkInputKeys(delta);
		em.generateEnemies(delta);
		this.translateWorld(delta);
		List<Artefact> allPlayerObjects = new LinkedList<>(ProjectileManager.getInstance().getShoots());
		if (!ship.isFullyDestroyed()) allPlayerObjects.add(ship);
		cm.checkCollision(em.getEnemies(), allPlayerObjects);
		BonusManager.getInstance().checkBonus(ship);
		this.renderWorld(delta);
	}

	private void renderWorld(float delta)
	{
		SpriteBatch batch = this.getBatch();

		batch.begin();
		this.scrolling.render(delta);
		this.renderShoots(batch, delta);
		this.em.render(batch, delta);
		BonusManager.getInstance().render(this.getBatch(), delta);
		this.renderShipOrGameOver(delta, batch);
		ExplosionManager.render(batch, delta);
		batch.end();

		/* le dashboard doit s'afficher en dehors du spritebatch précédent */
		dashBoard.render(delta);

	}

	private void renderShipOrGameOver(float delta, SpriteBatch batch)
	{
		if (ship.isFullyDestroyed())
		{
			gameOverSoundExecutor.execute();
			renderGameOverBlinker(delta, batch);
			GdxBitmapString yourScore = new GdxBitmapString(FontUtils.FONT_BLUE, "SCORE IS " + ScoreManager.getInstance().getScore());
			yourScore.setPosition((Global.width - yourScore.getWidth()) / 2f, (float) (Global.height - (double) TextureAsset.TITLE.get().getHeight() / 2) / 2 - 50f);
			yourScore.render(batch, delta);
		}
		else
		{
			this.renderShip(delta);
		}
	}

	private void renderGameOverBlinker(float delta, SpriteBatch batch)
	{
		if (msgBlinker.isHidden())
		{
			msgBlinker.show();
		}
		else
		{
			msgBlinker.render(batch, delta);
		}
	}

	private void renderShoots(SpriteBatch batch, float delta)
	{
		ProjectileManager.getInstance().renderShoots(batch, delta);
	}

	private void renderShip(float delta)
	{
		ship.render(this.getBatch(), delta);
	}

	private void translateWorld(float delta)
	{
		em.act(delta);
	}

	private void checkInputKeys(float delta)
	{
		ShipHandler.handle(ship);
		ship.update(delta);
		ProjectileManager.checkFire(ship);
		this.checkMetaKeys();
		this.scrolling.checkInput();
	}

	private void checkMetaKeys()
	{
		/* test avec un if classique */
		if (Gdx.input.isKeyJustPressed(Keys.ESCAPE))
		{
			SoundAsset.CLIC.play();
			this.closeGame();
		}

		/* test avec une référence de méthode : c'est plus joli ... */
		GdxCommons.runIfKeyJustPressed(Keys.F1, GdxCommons::switchFullScreen);
		
		/* et avec une lambda */
		GdxCommons.runIfKeyJustPressed(Keys.B, Global::switchDiplayBoundingCircles); 

	}

	@Override
	public void show()
	{
		ModPlayer.getInstance().playLoop(ModAsset.XENON_REMIX.toString());
	}

	@Override
	public void hide()
	{
		ModPlayer.getInstance().stop();
	}

	@Override
	public List<Artefact> getArtefacts()
	{
		List<Artefact> world = new LinkedList<>(em.getEnemies());
		world.addAll(ProjectileManager.getInstance().getShoots());
		return world;
	}

	public Ship getShip()
	{
		return ship;
	}

}
