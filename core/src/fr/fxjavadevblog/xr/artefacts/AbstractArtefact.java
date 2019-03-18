package fr.fxjavadevblog.xr.artefacts;

import java.util.Observable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Circle;

import fr.fxjavadevblog.xr.commons.Global;
import fr.fxjavadevblog.xr.commons.displays.Interpolator;
import fr.fxjavadevblog.xr.commons.utils.GdxCommons;

/**
 * représente un artefact de base doté d'une force de vie (lifeForce) et d'une
 * force d'impact (impactForce).
 * 
 * @author robin
 *
 */
public abstract class AbstractArtefact extends Observable implements Artefact
{
	/**
	 * encapsule les données communes d'un artefact : maxLifePoints, lifePoints,
	 * impactForce, vectorX et vectorY.
	 */
	private ArtefactData data;

	/**
	 * cercle de détection des collisions.
	 * 
	 */
	private Circle boundingCircle;

	/**
	 * renderer pour l'affichage des boundingCircles (debug / demo)
	 */
	private static ShapeRenderer renderer = new ShapeRenderer();

	/**
	 * interpolator pour calculer la trajectoire de l'artefact.
	 */
	private Interpolator interpolatorX;
	private Interpolator interpolatorY;

	static
	{
		renderer.setColor(Color.RED);
	}

	public AbstractArtefact(AbstractArtefact other)
	{
		this(other.getVectorX(), other.getVectorY(), other.getLifePoints(), other.getImpactForce());
	}

	/**
	 * construit un artefact doté d'une force de vie (lifeForce) et d'une force
	 * d'impact (impactForce).
	 * 
	 * @param lifePoints
	 * @param impactForce
	 */
	public AbstractArtefact(final float vectorX, final float vectorY, final int lifePoints, final int impactForce)
	{
		data = new ArtefactData(lifePoints, impactForce, vectorX, vectorY);
		boundingCircle = new Circle();
	}

	/**
	 * déplace le sprite associé sur l'axe des X.
	 * 
	 * @param deltaX
	 *            décallage à appliquer.
	 */
	private void translateX(final float deltaX)
	{
		this.getSprite().translateX(deltaX);
	}

	/**
	 * déplace le sprite associé sur l'axe des Y.
	 * 
	 * @param deltaY
	 *            décallage à appliquer.
	 */
	private void translateY(final float deltaY)
	{
		this.getSprite().translateY(deltaY);
	}

	@Override
	public void update(float delta)
	{
		this.updateX(delta);
		this.updateY(delta);
		GdxCommons.computeBoundingCircle(getSprite(), getBoundingCircle());
	}

	private void updateY(float delta)
	{
		if (interpolatorY != null)
		{
			float originalY = interpolatorY.getOriginalValue();
			this.getSprite().setCenterX(originalY);
		}

		this.translateY(delta * data.getVectorY());

		if (interpolatorY != null)
		{
			float newPositionY = interpolatorX.calculate(delta);
			this.getSprite().setCenterX(newPositionY);
		}
	}

	private void updateX(float delta)
	{
		if (interpolatorX != null)
		{
			float originalX = interpolatorX.getOriginalValue();
			this.getSprite().setCenterX(originalX);
		}

		this.translateX(delta * data.getVectorX());

		if (interpolatorX != null)
		{
			float newPositionX = interpolatorX.calculate(delta);
			this.getSprite().setCenterX(newPositionX);
		}
	}

	/**
	 * décrémente la vie d'artefact.
	 */
	@Override
	public void decreaseLife(final int force)
	{
		int newLifePoints = data.getLifePoints() - force;
		data.setLifePoints(newLifePoints);
		this.setChanged();
		this.notifyObservers();
	}

	@Override
	public void increaseLife(final int force)
	{
		int newLifePoints = data.getLifePoints() + force;
		data.setLifePoints(newLifePoints);
		this.setChanged();
		this.notifyObservers();
	}

	/**
	 * retourne le nombre de point de vie restant à l'artefact.
	 */
	@Override
	public int getLifePoints()
	{
		return data.getLifePoints();
	}

	/**
	 * retourne la valeur de la force d'impact de l'artefact.
	 */
	@Override
	public int getImpactForce()
	{
		return data.getImpactForce();
	}

	/**
	 * retourne "true" si l'artefact est toujours vivant.
	 */
	@Override
	public boolean isAlive()
	{
		return data.getLifePoints() > 0;
	}

	@Override
	public float getVectorX()
	{
		return data.getVectorX();
	}

	@Override
	public float getVectorY()
	{
		return data.getVectorY();
	}

	@Override
	public void setVectorX(float vectorX)
	{
		data.setVectorX(vectorX);
	}

	@Override
	public void setVectorY(float vectorY)
	{
		data.setVectorY(vectorY);
	}

	@Override
	public void setLifePoints(int lifePoints)
	{
		data.setLifePoints(lifePoints);
	}

	@Override
	public Circle getBoundingCircle()
	{
		return this.boundingCircle;
	}

	@Override
	public void setRadius(float radius)
	{
		this.boundingCircle.setRadius(radius);
	}

	@Override
	public boolean isCollision(Artefact otherArtefact)
	{
		return this.getBoundingCircle().overlaps(otherArtefact.getBoundingCircle());
	}

	@Override
	public void render(SpriteBatch batch, float delta)
	{
		if (Global.isDisplayBoundingCircles())
		{
			// le spriteBatch en cours doit être d'abord désactivé.
			batch.end();
			Gdx.gl.glLineWidth(Global.BOUNDING_CIRCLE_WIDTH);
			renderer.begin(ShapeType.Line);
			renderer.setProjectionMatrix(batch.getProjectionMatrix());
			renderer.circle(this.boundingCircle.x, this.boundingCircle.y, this.boundingCircle.radius);
			renderer.end();
			batch.begin();
		}
	}

	public void setInterpolatorX(Interpolator interpolatorX)
	{
		this.interpolatorX = interpolatorX;
	}

	public void setInterpolatorY(Interpolator interpolatorY)
	{
		this.interpolatorY = interpolatorY;
	}

}
