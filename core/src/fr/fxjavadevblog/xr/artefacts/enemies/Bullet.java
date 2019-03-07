package fr.fxjavadevblog.xr.artefacts.enemies;

import fr.fxjavadevblog.xr.artefacts.Artefact;
import fr.fxjavadevblog.xr.artefacts.friendly.ship.Ship;
import fr.fxjavadevblog.xr.commons.libs.TextureAsset;

/**
 * représente un "bullet", un tir de l'ennemi. Seule les collisions avec le
 * vaisseau du joueur sont prises en compte, c'est à dire qu'une bullet n'entre
 * pas en collision avec les tirs du vaisseau.
 * 
 * @author robin
 *
 */
public class Bullet extends Enemy
{
	public Bullet()
	{
		super(TextureAsset.BULLET.get(), 5, 5, 8);
	}

	/*
	 * redéfinition de la méthode "isCollision" pour ne rentrer en collision
	 * qu'avec le vaisseau.
	 */

	@Override
	public boolean isCollision(Artefact otherArtefact)
	{
		return (otherArtefact instanceof Ship && super.isCollision(otherArtefact));
	}
}
