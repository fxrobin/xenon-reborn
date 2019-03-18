package fr.fxjavadevblog.xr.commons;

/**
 * classes de constantes et de paramètrage de l'application. la classe est
 * nommée "Global" même si elle ne contient pas que des constantes.
 * 
 * @author robin
 *
 */

public final class Global
{	
	/**
	 * durée du fade-in/fade-out.
	 */
	public static final float FADE_SECONDS = 1.5f;

	/**
	 * largeur de la surface de jeu en pixels.
	 */
	public static final int SCREEN_WIDTH = 1024; /* NOSONAR */

	/**
	 * hauteur de la surface de jeu en pixels.
	 */
	public static final int SCREEN_HEIGHT = 768; /* NOSONAR */

	/**
	 * vitesse max de déplacement du vaisseau.
	 */
	public static final float SHIP_SPEED = 400f;

	/**
	 * accelération du vaisseau.
	 */
	public static final float SHIP_ACCELLERATION = 20f;

	/**
	 * bouclier : maximum 100%
	 */
	public static final float SHIELD_MAX_ENERGY = 100f;

	/**
	 * bouclier : vitesse de chargement du bouclier. (20f = 5 secondes)
	 */
	public static final float SHIELD_CHARGING_SPEED = 5f;

	/**
	 * bouclier : vitesse de chargement de l'arme secondaire. (20f = 5 secondes)
	 */
	public static final float WEAPON_CHARGING_SPEED = 30f;

	/**
	 * bouclier : vitesse de décharge du bouclier (10f = 10 secondes)
	 */
	public static final float SHIELD_DISCHARGING_SPEED = 20f;

	/**
	 * bouclier : seuil d'activation potentiel du bouclier. (90%)
	 */
	public static final float SHIELD_ACTIVATION_THRESHOLD = 90f;

	/**
	 * points de vie max du vaisseau.
	 */
	public static final int SHIP_LIFE_POINTS = 20;

	/**
	 * force d'impact du vaisseau (quand il percute qqch).
	 */
	public static final int SHIP_IMPACT_FORCE = 20;

	
	/**
	 * pour afficher les cercles de détection de collision.
	 */
	private static boolean displayBoundingCircles = false;

	/**
	 * taille du cercle rouge de "debug" des artefacts.
	 */
	public static final int BOUNDING_CIRCLE_WIDTH = 3;

	/**
	 * nombre d'ennemis générés par "escadrille".
	 */
	public static final int ENEMIE_NUMBER_BY_SQUADRONS = 3;

	/**
	 * nombre de secondes d'attente entre chaque génération d'une vague d'ennemis.
	 */
	public static final float GENERATION_INTERVAL = 4f;

	private Global()
	{
		/* protection, empèche l'instanciation depuis l'extérieur */
	}
	
	public static void switchDiplayBoundingCircles() 
	{
		displayBoundingCircles = !displayBoundingCircles;
	}
	
	public static boolean isDisplayBoundingCircles() 
	{
		return displayBoundingCircles;
	}
	
}
