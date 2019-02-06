package fr.fxjavadevblog.xr.artefacts;

import java.util.Observable;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Circle;

import fr.fxjavadevblog.xr.commons.Global;
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
	
	private static ShapeRenderer renderer= new ShapeRenderer();
	
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
	 *          décallage à appliquer.
	 */
	private void translateX(final float deltaX)
	{
		this.getSprite().translateX(deltaX);
	}

	/**
	 * déplace le sprite associé sur l'axe des Y.
	 * 
	 * @param deltaY
	 *          décallage à appliquer.
	 */
	private void translateY(final float deltaY)
	{
		this.getSprite().translateY(deltaY);
	}

	@Override
	public void update(float delta)
	{
		this.translateX(delta * data.getVectorX());
		this.translateY(delta * data.getVectorY());
		GdxCommons.computeBoundingCircle(getSprite(), getBoundingCircle());
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
			renderer.begin(ShapeType.Line);
			renderer.circle(this.boundingCircle.x, this.boundingCircle.y, this.boundingCircle.radius);
			renderer.end();			
			batch.begin();
		}
	}
	

}
