package fr.fxjavadevblog.xr.artefacts.managers;

import java.util.List;
import java.util.Random;

import fr.fxjavadevblog.xr.artefacts.Artefact;
import fr.fxjavadevblog.xr.artefacts.collectables.BonusType;
import fr.fxjavadevblog.xr.artefacts.enemies.Bullet;

/**
 * gestionnaire de collisions entre des "targets" (cibles) et des "projectiles".
 * 
 * @author robin
 *
 */
public final class CollisionManager
{
	private static CollisionManager instance = new CollisionManager();
	private static Random randomGenerator = new Random();

	public static CollisionManager getInstance()
	{
		return instance;
	}

	private CollisionManager()
	{
		/* protection */
	}

	/**
	 * vérifie les collisions entre une liste de cibles (targets), et une liste de
	 * projectiles (projectiles).
	 * 
	 * @param targets
	 * @param projectiles
	 */
	public void checkCollision(List<? extends Artefact> targets, List<? extends Artefact> projectiles)
	{
		// on vérifie la collision de tous les "targets" avec chacun des
		// "projectiles".
		for (Artefact t : targets)
		{
			for (Artefact p : projectiles)
			{
				checkCollision(t, p);
			}
		}
	}

	/**
	 * vérifie la collision entre deux artefacts, l'un "cible", l'autre
	 * "projectile". Si c'est le cas, le calcul des impacts est lancé, puis la
	 * vérification de l'état "alive" du target.
	 * 
	 * @param target
	 * @param projectile
	 */
	public void checkCollision(Artefact target, Artefact projectile)
	{
		if (target.isCollision(projectile))
		{
			/* collision !!! */
			processCollision(target, projectile);
			checkDestruction(target);
		}
	}

	/**
	 * décrémente la vie de deux artefacts en fonction des forces d'impact de leur
	 * opposant respectif.
	 * 
	 * @param target
	 * @param projectile
	 */
	public void processCollision(Artefact target, Artefact projectile)
	{
		projectile.decreaseLife(target.getImpactForce());
		target.decreaseLife(projectile.getImpactForce());
	}

	/**
	 * vérifie si l'artefact est détruit en lui demandant s'il est "alive". s'il
	 * est détruit, le score est incrémenté de 10 points et un bonus sera
	 * éventuellement généré.
	 * 
	 * @param target
	 */
	public void checkDestruction(Artefact target)
	{
		if (!target.isAlive())
		{
			/* MAJ du score */
			ScoreManager.getInstance().add(10);
			/* Génération des bonus éventuels en fonction de la cible abattue */
			processBonus(target);
		}
	}

	/**
	 * génère éventuellement un bonus (une chance sur 2 de la générer). Le bonus
	 * apparaitra là où l'artefact a été détruit.
	 * 
	 * @param target
	 */
	public void processBonus(Artefact target)
	{
		/* une destruction sur deux génère un bonus et les bullets sont ignorées */
		if (randomGenerator.nextBoolean() && !(target instanceof Bullet))
		{
			/*
			 * puis on choisi au hasard, encore l'un ou l'autres des bonus potentiels.
			 */
			BonusType bonusType = randomGenerator.nextBoolean() ? BonusType.NORMAL_BONUS : BonusType.POWER_UP_BONUS;
			BonusManager.getInstance().addBonus(bonusType, target.getBoundingCircle().x, target.getBoundingCircle().y);
		}
	}
}
