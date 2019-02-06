package fr.fxjavadevblog.xr.artefacts;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Circle;

import fr.fxjavadevblog.xr.commons.displays.Renderable;

/**
 * représente un artefact qui possède des points de vie, une force d'impact, un
 * cercle de collision et un sprite courant. Un artefact est "Renderable" par
 * héritage d'interface.
 * 
 * @author robin
 * @see Renderable
 */

public interface Artefact extends Renderable
{
	/**
	 * retourne le cercle de collision.
	 * 
	 * @return
	 */
	Circle getBoundingCircle();

	/**
	 * retourne le nombre de point de vie de l'artefact.
	 * 
	 * @return le nombre de points de vie.
	 */
	int getLifePoints();

	/**
	 * décrémente les points de vie en fonction de la force d'impact exercée.
	 * 
	 * @param force
	 *          force d'impact.
	 */
	void decreaseLife(int force);

	/**
	 * retourne la force d'impact de cet artefact.
	 * 
	 * @return la force d'impact.
	 */
	int getImpactForce();

	/**
	 * retourne "true" si l'artefact est toujours en vie, "false" sinon.
	 * 
	 * @return "true" si l'artefact est toujours en vie, "false" sinon.
	 */
	boolean isAlive();

	/**
	 * retourne le Sprite courant resprésenté par cet artefact.
	 * 
	 * @return le sprite courant.
	 */
	Sprite getSprite();

	/**
	 * affecte le nombre de points de vie.
	 * 
	 * @param lifePoints
	 */
	void setLifePoints(int lifePoints);

	/**
	 * affecte la vitesse sur l'axe Y.
	 * 
	 * @param vectorY
	 */
	void setVectorY(float vectorY);

	/**
	 * affecte la vitesse sur l'axe X.
	 * 
	 * @param vectorX
	 */
	void setVectorX(float vectorX);

	/**
	 * retourne la vitesse sur l'axe Y.
	 * 
	 * @return
	 */
	float getVectorY();

	/**
	 * retourne la vitesse sur l'axe X.
	 * 
	 * @return
	 */
	float getVectorX();

	/**
	 * déplace l'artefact en fonction de sa vitesse sur les 2 axes et en fonction
	 * du temps delta écoulé.
	 * 
	 * @param delta
	 */
	void update(float delta);

	/**
	 * affecte la taille du cercle de collision.
	 * 
	 * @param radius
	 */
	void setRadius(float radius);

	/**
	 * retourne vrai si cet artefact entre en collision avec celui passé en
	 * paramètre.
	 * 
	 * @param otherArtefact
	 * @return "true" si la collision est avérée, "false" sinon.
	 */
	boolean isCollision(Artefact otherArtefact);

	/**
	 * augmente la vie de l'arteface (dans la limite de son maximum).
	 * 
	 * @param points
	 *          points de vie à ajouter.
	 */
	void increaseLife(final int points);

}
