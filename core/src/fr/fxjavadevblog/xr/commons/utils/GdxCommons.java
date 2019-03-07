package fr.fxjavadevblog.xr.commons.utils;

import java.util.Arrays;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.Graphics.Monitor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Disposable;

import fr.fxjavadevblog.xr.commons.Global;

/**
 * classe utilitaire au profit de LIBGDX.
 * 
 * @author robin
 *
 */
public final class GdxCommons
{
	private static Log log = LogFactory.getLog(GdxCommons.class);

	private GdxCommons()
	{
		/* protection */
	}

	/**
	 * retourne "true" si les codes des touches en paramètres sont toutes activées
	 * au clavier.
	 * 
	 * @param keys
	 * @return
	 */
	public static boolean checkConcurrentKeys(int... keys)
	{
		for (int key : keys)
		{
			if (!Gdx.input.isKeyPressed(key)) return false;
		}
		return true;
	}

	/**
	 * définit le centre des sprites en leur milieu.
	 * 
	 * @param sprites
	 */
	public static void setOriginCenter(Sprite... sprites)
	{
		Arrays.stream(sprites).forEach(Sprite::setOriginCenter);
	}

	/**
	 * retourne la coordonnée X du centre du sprite à l'écran.
	 * 
	 * @param s
	 * @return
	 */
	public static float getCenterX(Sprite s)
	{
		return s.getX() + s.getOriginX();
	}

	/**
	 * retourne la coordonnée Y du centre du sprite à l'écran.
	 * 
	 * @param s
	 * @return
	 */
	public static float getCenterY(Sprite s)
	{
		return s.getY() + s.getOriginY();
	}

	/**
	 * lance la méthode "dipose" sur tous les disposables passés en paramètre.
	 * 
	 * @param disposables
	 */
	public static void disposeAll(Disposable... disposables)
	{
		Arrays.stream(disposables).forEach(d -> {
			if (log.isInfoEnabled())
			{
				log.info("Dispose : " + d);
			}
			d.dispose();
		});
	}

	/**
	 * découpe une texture en un tableau de TextureRegion. utile pour obtenir les
	 * images d'une animation.
	 * 
	 * @param texture
	 * @param cols
	 * @param rows
	 * @return
	 */
	public static TextureRegion[] convertToTextureArray(Texture texture, int cols, int rows)
	{
		int totalFrames = cols * rows;
		Rectangle cropStart = new Rectangle(0, 0, texture.getWidth() / cols, texture.getHeight() / rows);
		return convertToTextureArray(texture, totalFrames, cols, rows, cropStart);
	}

	public static TextureRegion[] convertToTextureArray(Texture texture, int totalSprites, int cols, int rows, Rectangle cropRectangle)
	{
		int cropX = (int) cropRectangle.x;
		int cropY = (int) cropRectangle.y;
		int cropWith = (int) cropRectangle.width;
		int cropHeight = (int) cropRectangle.height;

		TextureRegion[][] tmpRegion = TextureRegion.split(texture, texture.getWidth() / cols, texture.getHeight() / rows);
		TextureRegion[] result = new TextureRegion[totalSprites];
		int index = 0;
		for (int i = 0; i < rows; i++)
		{
			for (int j = 0; j < cols; j++)
			{
				TextureRegion tr = tmpRegion[i][j];
				tr.setRegion(tr.getRegionX() + cropX, tr.getRegionY() + cropY, cropWith, cropHeight);
				result[index++] = tr;
			}
		}
		return result;
	}

	/**
	 * efface l'écran.
	 */
	public static void clearScreen()
	{
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}

	/**
	 * efface l'écran.
	 */
	public static void clearScreen(Color color)
	{
		Gdx.gl.glClearColor(color.r, color.g, color.b, color.a);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}

	/**
	 * détermine le "boundingCircle" d'un sprite en prenant son centre comme
	 * origine du cercle et la taille du sprite en largeur / 2.
	 * 
	 * @param sprite
	 * @param boundingCircle
	 */
	public static void computeBoundingCircle(Sprite sprite, Circle boundingCircle)
	{
		boundingCircle.setX(getCenterX(sprite));
		boundingCircle.setY(getCenterY(sprite));
		boundingCircle.setRadius(sprite.getWidth() / 2);
	}

	/**
	 * bascule en plein écran.
	 * 
	 */
	public static void switchFullScreen()
	{
		Monitor currMonitor = Gdx.graphics.getMonitor();
		DisplayMode displayMode = Gdx.graphics.getDisplayMode(currMonitor);

		if (!Gdx.graphics.isFullscreen())
		{
			if (!Gdx.graphics.setFullscreenMode(displayMode))
			{
				System.err.println("Erreur de passage en plein écran"); // NOSONAR
			}
		}
		else
		{
			if (!Gdx.graphics.setWindowedMode(Global.width, Global.height))
			{
				System.err.println("Erreur de passage mode fenêtré"); // NOSONAR
			}
		}
	}

	/**
	 * exécute le runnable si la touche est "just pressed" (appuyé sans
	 * répétition).
	 * 
	 * @param keyCode
	 *          code clavier (voir la classe de constantes Keys de libgdx)
	 * @param runnable
	 *          instance de runnable lançable (référence à une fonction ou une
	 *          lambda ou classe anonyme, ou référence ves une classe)
	 */
	public static void runIfKeyJustPressed(int keyCode, Runnable runnable)
	{
		if (Gdx.input.isKeyJustPressed(keyCode))
		{
			runnable.run();
		}
	}

	public static void adaptCameraToViewPort(Camera camera)
	{
		camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
		camera.update();
	}
	
	public static float calculateCenteredPositionX(Texture texture)
	{
		return (Global.width - texture.getWidth()) / 2f;
	}
	
	public static float calculateCenteredPositionY(Texture texture)
	{
		return (Global.height - texture.getHeight()) / 2f;
	}

}
